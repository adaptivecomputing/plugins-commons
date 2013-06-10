package com.adaptc.mws.plugins.transformations;

import com.adaptc.mws.plugins.EventEnumeration;
import com.adaptc.mws.plugins.IPluginEvent;
import com.adaptc.mws.plugins.IPluginEventService;
import org.apache.commons.lang.StringUtils;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.Types;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

import java.lang.reflect.Modifier;

/**
 * @author bsaville
 */
@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class EventEnumerationASTTransformation implements ASTTransformation {
	private static final ClassNode MY_TYPE = new ClassNode(EventEnumeration.class);
	private static final String MY_TYPE_NAME = "@" + MY_TYPE.getNameWithoutPackage();

	private static final ClassNode PLUGIN_EVENT_INTERFACE = new ClassNode(IPluginEvent.class);

	private static final String GET_EVENT_CODE_METHOD_NAME = "getEventCode";
	private static final ClassNode GET_EVENT_CODE_METHOD_TYPE = ClassHelper.int_TYPE;
	private static final String GET_EVENT_TYPE_METHOD_NAME = "getEventType";
	private static final ClassNode GET_EVENT_TYPE_METHOD_TYPE = ClassHelper.STRING_TYPE;
	private static final String GET_ORIGIN_SUFFIX_METHOD_NAME = "getOriginSuffix";
	private static final ClassNode GET_ORIGIN_SUFFIX_METHOD_TYPE = ClassHelper.STRING_TYPE;
	private static final String GET_MESSAGE_CODE_METHOD_NAME = "getMessageCode";
	private static final ClassNode GET_MESSAGE_CODE_METHOD_TYPE = ClassHelper.STRING_TYPE;
	private static final String GET_COMMENT_CODE_METHOD_NAME = "getCommentCode";
	private static final ClassNode GET_COMMENT_CODE_METHOD_TYPE = ClassHelper.STRING_TYPE;

	private static final String MESSAGE_SEPARATOR = ".";
	private static final String MESSAGE_CODE_SUFFIX = MESSAGE_SEPARATOR + "message";
	private static final String COMMENT_CODE_SUFFIX = MESSAGE_SEPARATOR + "comment";

	private static final String SEVERITY_FIELD_NAME = "severity";
	private static final ClassNode SEVERITY_TYPE = new ClassNode(IPluginEventService.Severity.class);
	private static final String E_LEVEL_FIELD_NAME = "escalationLevel";
	private static final ClassNode E_LEVEL_TYPE = new ClassNode(IPluginEventService.EscalationLevel.class);
	private static final String EVENT_NAME_FIELD_NAME = "eventName";
	private static final ClassNode EVENT_NAME_TYPE = ClassHelper.STRING_TYPE;

	public static final ClassNode[] EMPTY_CLASS_ARRAY = new ClassNode[0];
	public static final Parameter[] ZERO_PARAMETERS = new Parameter[0];
	public static final Token ASSIGNMENT_OPERATOR = Token.newSymbol(Types.EQUAL, -1, -1);
	public static final Token PLUS_OPERATOR = Token.newSymbol(Types.PLUS, -1, -1);

	@Override
	public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
		if (!(nodes[0] instanceof AnnotationNode) || !(nodes[1] instanceof AnnotatedNode)) {
			throw new RuntimeException("Internal error: wrong types: "+nodes[0].getClass() + " / "+ nodes[1].getClass());
		}

		AnnotatedNode parent = (AnnotatedNode) nodes[1];
		AnnotationNode annotation = (AnnotationNode) nodes[0];
		if (!MY_TYPE.equals(annotation.getClassNode()) || !(parent instanceof ClassNode)) {
			return;
		}

		ClassNode classNode = (ClassNode) parent;
		String cName = classNode.getName();
		if (classNode.isInterface()) {
			throw new RuntimeException("Error processing interface '" + cName + "'. " +
					MY_TYPE_NAME + " not allowed for interfaces.");
		}

		validateEnum(classNode);
		addFieldsAndConstructor(classNode);
		addMessageCodeMethods(classNode);
		addEventCodeMethod(classNode);
		addEventTypeMethod(classNode);
		addOriginSuffixMethod(classNode);
		addInterface(classNode);
	}

	/**
	 * Verifies that the applied class is an enumeration and that existing methods/properties are of the correct type.
	 * This is to make sure that further manipulation works correctly.
	 * @param classNode
	 */
	private void validateEnum(ClassNode classNode) {
		// Make sure the class is an enum
		if (!classNode.isEnum())
			throw new RuntimeException("The event enumeration class " + classNode.getName() +
					" must be an enum");

		// Make sure that if the origin suffix property exists, it is static and a string
		if (classNode.hasProperty(EventEnumeration.ORIGIN_SUFFIX_PROPERTY_NAME)) {
			PropertyNode originSuffixProperty = classNode.getProperty(EventEnumeration.ORIGIN_SUFFIX_PROPERTY_NAME);
			if (!originSuffixProperty.isStatic() || !originSuffixProperty.getType().equals(ClassHelper.STRING_TYPE)) {
				throw new RuntimeException("The event enumeration class " + classNode.getName() +
						" has a "+EventEnumeration.ORIGIN_SUFFIX_PROPERTY_NAME+" property which is not static or is "+
						"not a string, please correct and recompile.");
			}
		}
		// Make sure that if the event type prefix property exists, it is a static and a string
		if (classNode.hasProperty(EventEnumeration.EVENT_TYPE_PREFIX_PROPERTY_NAME)) {
			PropertyNode eventTypePrefixProperty = classNode.getProperty(EventEnumeration.EVENT_TYPE_PREFIX_PROPERTY_NAME);
			if (!eventTypePrefixProperty.isStatic() || !eventTypePrefixProperty.getType().equals(ClassHelper.STRING_TYPE)) {
				throw new RuntimeException("The event enumeration class " + classNode.getName() +
						" has a "+EventEnumeration.EVENT_TYPE_PREFIX_PROPERTY_NAME+" property which is not static or is "+
						"not a string, please correct and recompile.");
			}
		}
	}

	/**
	 * Adds event name, severity, and escalation level fields as well as constructor to take these as parameters.
	 * <pre>
	 	String eventName
		EventSeverity severity
		EscalationLevel escalationLevel

	 	private $INIT(String eventName, EventSeverity severity, EscalationLevel escalationLevel) {
	 		this.eventName = eventName
	 		this.severity = severity
	 		this.escalationLevel = escalationLevel
	 	}
	 * </pre>
	 * @param classNode
	 */
	private void addFieldsAndConstructor(ClassNode classNode) {
		classNode.addField(EVENT_NAME_FIELD_NAME, Modifier.PRIVATE, EVENT_NAME_TYPE, null);
		classNode.addMethod("get"+StringUtils.capitalize(EVENT_NAME_FIELD_NAME), Modifier.PUBLIC,
				EVENT_NAME_TYPE, ZERO_PARAMETERS, EMPTY_CLASS_ARRAY,
				new ReturnStatement(new VariableExpression(EVENT_NAME_FIELD_NAME)));
		classNode.addField(SEVERITY_FIELD_NAME, Modifier.PRIVATE, SEVERITY_TYPE, null);
		classNode.addMethod("get"+StringUtils.capitalize(SEVERITY_FIELD_NAME), Modifier.PUBLIC,
				SEVERITY_TYPE, ZERO_PARAMETERS, EMPTY_CLASS_ARRAY,
				new ReturnStatement(new VariableExpression(SEVERITY_FIELD_NAME)));
		classNode.addField(E_LEVEL_FIELD_NAME, Modifier.PRIVATE, E_LEVEL_TYPE, null);
		classNode.addMethod("get"+StringUtils.capitalize(E_LEVEL_FIELD_NAME), Modifier.PUBLIC,
				E_LEVEL_TYPE, ZERO_PARAMETERS, EMPTY_CLASS_ARRAY,
				new ReturnStatement(new VariableExpression(E_LEVEL_FIELD_NAME)));

		String eventNameFieldName = "eventName";
		String severityFieldName = "severity";
		String escalationLevelFieldName = "escalationLevel";
		BlockStatement constructorStatements = new BlockStatement();
		constructorStatements.addStatement(
				new ExpressionStatement(
						new BinaryExpression(
								new PropertyExpression(VariableExpression.THIS_EXPRESSION, EVENT_NAME_FIELD_NAME),
								ASSIGNMENT_OPERATOR,
								new VariableExpression(eventNameFieldName)
						)
				)
		);
		constructorStatements.addStatement(
				new ExpressionStatement(
						new BinaryExpression(
								new PropertyExpression(VariableExpression.THIS_EXPRESSION, SEVERITY_FIELD_NAME),
								ASSIGNMENT_OPERATOR,
								new VariableExpression(severityFieldName)
						)
				)
		);
		constructorStatements.addStatement(
				new ExpressionStatement(
						new BinaryExpression(
								new PropertyExpression(VariableExpression.THIS_EXPRESSION, E_LEVEL_FIELD_NAME),
								ASSIGNMENT_OPERATOR,
								new VariableExpression(escalationLevelFieldName)
						)
				)
		);
		ConstructorNode constructorNode = classNode.addConstructor(
				Modifier.PRIVATE,
				new Parameter[]{
						// Enum constructors are quite different, this actually turns into:
						//	private $INIT(String s, int i, String eventName, EventSeverity eventSeverity, EscalationLevel escalationLevel)
						new Parameter(EVENT_NAME_TYPE, eventNameFieldName),
						new Parameter(SEVERITY_TYPE, severityFieldName),
						new Parameter(E_LEVEL_TYPE, escalationLevelFieldName),
				},
				EMPTY_CLASS_ARRAY,
				constructorStatements
		);
		constructorStatements.setVariableScope(constructorNode.getVariableScope());
	}

	/**
	 * Adds a getEventCode() method to the event enumeration.
	 * <pre>
		public int getEventCode() {
			return this.ordinal()
		}
	 * </pre>
	 * @param classNode
	 */
	private void addEventCodeMethod(ClassNode classNode) {
		classNode.addMethod(GET_EVENT_CODE_METHOD_NAME, Modifier.PUBLIC, GET_EVENT_CODE_METHOD_TYPE,
				ZERO_PARAMETERS, EMPTY_CLASS_ARRAY,
				new ReturnStatement(
						new MethodCallExpression(
								VariableExpression.THIS_EXPRESSION,
								"ordinal",
								MethodCallExpression.NO_ARGUMENTS
						)
				)
		);
	}

	/**
	 * Adds a "getEventType" method which returns the type of the event constructed from the event name field in
	 * combination with the static property EVENT_TYPE_PREFIX (if present).
	 * <pre>
	 	public String getEventType() {
			// if the enum has the event type prefix static field
	 		return EVENT_TYPE_PREFIX + " " + eventName
	 		// else
	 		return eventName
	 	}
	 * </pre>
	 * @param classNode
	 */
	private void addEventTypeMethod(ClassNode classNode) {
		Expression expression;
		if (classNode.hasProperty(EventEnumeration.EVENT_TYPE_PREFIX_PROPERTY_NAME)) {
			expression = new BinaryExpression(
					new PropertyExpression(VariableExpression.THIS_EXPRESSION, EventEnumeration.EVENT_TYPE_PREFIX_PROPERTY_NAME),
					PLUS_OPERATOR,
					new BinaryExpression(
							new ConstantExpression(" "),
							PLUS_OPERATOR,
							new PropertyExpression(VariableExpression.THIS_EXPRESSION, EVENT_NAME_FIELD_NAME)
					)
			);
		} else {
			expression = new PropertyExpression(VariableExpression.THIS_EXPRESSION, EVENT_NAME_FIELD_NAME);
		}
		classNode.addMethod(GET_EVENT_TYPE_METHOD_NAME,
				Modifier.PUBLIC,
				GET_EVENT_TYPE_METHOD_TYPE,
				ZERO_PARAMETERS,
				EMPTY_CLASS_ARRAY,
				new ReturnStatement(expression)
		);
	}

	/**
	 * Adds a "getOriginSuffix" method which returns the fully constructed origin of the event generated from a constant (MWS),
	 * the enumeration class name, and the enum value name.
	 * <pre>
	 	public String getOriginSuffix() {
	 		// if the enum has the origin suffix static field
	 		return ORIGIN_SUFFIX
	 		// else
	 		return this.class.simpleName+"/"+this.name()
	 	}
	 * </pre>
	 * @param classNode
	 */
	private void addOriginSuffixMethod(ClassNode classNode) {
		Expression expression;
		if (classNode.hasProperty(EventEnumeration.ORIGIN_SUFFIX_PROPERTY_NAME)) {
			expression = new VariableExpression(EventEnumeration.ORIGIN_SUFFIX_PROPERTY_NAME);
		} else {
			expression = new BinaryExpression(
					new MethodCallExpression(
							new MethodCallExpression(
									VariableExpression.THIS_EXPRESSION,
									"getClass",
									MethodCallExpression.NO_ARGUMENTS
							),
							"getSimpleName",
							MethodCallExpression.NO_ARGUMENTS
					),
					PLUS_OPERATOR,
					new BinaryExpression(
							new ConstantExpression(IPluginEvent.ORIGIN_DELIMITER),
							PLUS_OPERATOR,
							new MethodCallExpression(
									VariableExpression.THIS_EXPRESSION,
									"name",
									MethodCallExpression.NO_ARGUMENTS
							)
					)
			);
		}
		classNode.addMethod(GET_ORIGIN_SUFFIX_METHOD_NAME,
				Modifier.PUBLIC,
				GET_ORIGIN_SUFFIX_METHOD_TYPE,
				ZERO_PARAMETERS,
				EMPTY_CLASS_ARRAY,
				new ReturnStatement(expression)
		);
	}

	/**
	 * Adds methods for retrieving message codes.
	 * <pre>
	 	public String getMessageCode() {
	 		return getClass().getSimpleName() + "." + name() + ".message"
	 	}
	 	public String getCommentCode() {
	 		return getClass().getSimpleName() + "." + name() + ".comment"
		}
	 * </pre>
	 * @param classNode
	 */
	private void addMessageCodeMethods(ClassNode classNode) {
		BinaryExpression getMessagePrefixExpression = new BinaryExpression(
				new MethodCallExpression(
						new MethodCallExpression(
								VariableExpression.THIS_EXPRESSION,
								"getClass",
								MethodCallExpression.NO_ARGUMENTS
						),
						"getSimpleName",
						MethodCallExpression.NO_ARGUMENTS
				),
				PLUS_OPERATOR,
				new BinaryExpression(
						new ConstantExpression(MESSAGE_SEPARATOR),
						PLUS_OPERATOR,
						new MethodCallExpression(
								VariableExpression.THIS_EXPRESSION,
								"name",
								MethodCallExpression.NO_ARGUMENTS
						)
				)
		);
		classNode.addMethod(GET_MESSAGE_CODE_METHOD_NAME, Modifier.PUBLIC, GET_MESSAGE_CODE_METHOD_TYPE,
				ZERO_PARAMETERS, EMPTY_CLASS_ARRAY,
				new ReturnStatement(new BinaryExpression(
						getMessagePrefixExpression,
						PLUS_OPERATOR,
						new ConstantExpression(MESSAGE_CODE_SUFFIX)
				)));
		classNode.addMethod(GET_COMMENT_CODE_METHOD_NAME, Modifier.PUBLIC, GET_COMMENT_CODE_METHOD_TYPE,
				ZERO_PARAMETERS, EMPTY_CLASS_ARRAY,
				new ReturnStatement(new BinaryExpression(
						getMessagePrefixExpression,
						PLUS_OPERATOR,
						new ConstantExpression(COMMENT_CODE_SUFFIX)
				)));
	}

	/**
	 * Adds the {@link IPluginEvent} interface to the class so that it can show at runtime
	 * that it is an event enumeration.
	 * @param classNode
	 */
	private void addInterface(ClassNode classNode) {
		classNode.addInterface(new ClassNode(IPluginEvent.class));
	}
}
