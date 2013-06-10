package com.adaptc.mws.plugins;

import org.codehaus.groovy.transform.GroovyASTTransformationClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation serves to transform enumerations at compile time to automatically implement the {@link IPluginEvent}
 * interface.  To utilize it, simply annotate an enum with EventEnumeration, then use the constructor taking three
 * arguments: String eventName, {@link IPluginEventService.Severity}, {@link IPluginEventService.EscalationLevel}.
 * The properties documented below may also be used to customize the output of certain methods, such as the event type
 * prefix and the origin suffix.
 * <p/>
 * The fields and methods added are:
 * <ul>
 *     <li>String eventName (includes getter): The first parameter to the constructor, used in the getEventType() method.</li>
 *     <li>{@link IPluginEventService.Severity} severity (includes getter): The severity of the value.</li>
 *     <li>{@link IPluginEventService.EscalationLevel} escalationLevel (includes getter): The escalation level of the value.</li>
 *     <li>String getMessageCode(): The i18n message code to use for the message in the format of [EnumClassName].[EnumValueName].message.</li>
 *     <li>String getCommentCode(): The i18n message code to use for the comment in the format of [EnumClassName].[EnumValueName].comment.</li>
 *     <li>int getEventCode(): Uses the return of ordinal() for the event code.
 *     		(WARNING: Unit tests or similar should be present to test that these ordinal values do not change)</li>
 *     <li>String getEventType(): Uses the event name (first parameter of constructor). If a static String property
 *     	named with the value of {@link #EVENT_TYPE_PREFIX_PROPERTY_NAME} is present on the enum, its value will be
 *     	prefixed with the first constructor parameter separated by a space.  For example, if the enum had the static
 *     	prefix value set to "Node", and the first constructor parameter was "Modify", the event type returned would
 *     	be "Node Modify".</li>
 *     <li>String getOriginSuffix(): If the enum class contains a static String property named with the value of
 *     	{@link #ORIGIN_SUFFIX_PROPERTY_NAME}, this value will be returned.  Otherwise, the suffix
 *     	[EnumClassName]/[EnumValueName] will be returned.</li>
 * </ul>
 * <p/>
 * Underneath the covers, this utilizes the groovy AST transformation process with the
 * {@link com.adaptc.mws.plugins.transformations.EventEnumerationASTTransformation} class.  This occurs at compile
 * time.
 * @see IPluginEvent
 * @author bsaville
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
@GroovyASTTransformationClass({"com.adaptc.mws.plugins.transformations.EventEnumerationASTTransformation"})
public @interface EventEnumeration {
	/**
	 * The prefix to use for the event type.  This should be a static String property on the enum if it is desired
	 * to be used.
	 * @see EventEnumeration
	 * @see IPluginEvent#getEventType()
	 */
	public static final String EVENT_TYPE_PREFIX_PROPERTY_NAME = "EVENT_TYPE_PREFIX";
	/**
	 * The suffix to use for the origin.  This should be a static String property on the enum if it is desired
	 * to be used.
	 * @see EventEnumeration
	 * @see IPluginEvent#getOriginSuffix()
	 */
	public static final String ORIGIN_SUFFIX_PROPERTY_NAME = "ORIGIN_SUFFIX";
}