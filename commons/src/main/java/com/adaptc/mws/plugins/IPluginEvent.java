package com.adaptc.mws.plugins;

/**
 * Convenience interface for working with plugin events.  This may be implemented by an enum and used in combination
 * with the {@link IPluginEventService} to easily create events.
 * @author bsaville
 */
public interface IPluginEvent {
	/**
	 * The delimiter to use when generating an origin or origin suffix.
	 * @see #getOriginSuffix()
	 */
	public static String ORIGIN_DELIMITER = "/";

	/**
	 * The i18n message code to use for the message of the event, which may optionally take arguments.
	 * @return The i18n message code for the message describing the event.
	 */
	public String getMessageCode();

	/**
	 * The i18n message code to use for the comments on the event.  The comments are not used when creating new
	 * events but may be used in documentation describing the event dictionary entry and why it commonly occurs.
	 * This i18n message should not use any arguments or information relating to a specific instance, such as
	 * associated objects.
	 */
	public String getCommentCode();

	/**
	 * The severity of the event.
	 * @return The severity of the event.
	 */
	public IPluginEventService.Severity getSeverity();

	/**
	 * The escalation level of the event.
	 * @return The escalation level of the event.
	 */
	public IPluginEventService.EscalationLevel getEscalationLevel();

	/**
	 * Represents the unique (per plugin component) code for a single event which does not include the severity,
	 * escalation level, component code, or any other piece of the code.
	 * @return The unique (per plugin component) event code
	 */
	public int getEventCode();

	/**
	 * The type of the event, may be null.
	 * @return The type of the event or null if none.
	 */
	public String getEventType();

	/**
	 * If non-null, this string is appended to the end of the origin generated by the plugin framework.  For example,
	 * if creating an event for a plugin type named "MyExample" and an id of "myExample1", the plugin framework will
	 * generate an origin of "MWS/plugins/MyExample/myExample1".  If the string returned from this method were
	 * "MyEventsEnum/EVENT1" (representing the enum name and the enum value name), the full origin used in creating
	 * the event with the IPluginEventService would be "MWS/plugins/MyExample/myExample1/MyEventsEnum/EVENT1".  If the
	 * leading slash is included, it will be trimmed.
	 * @return The suffix of the origin to use when creating an event, or null to not use a suffix.
	 * @see #ORIGIN_DELIMITER
	 */
	public String getOriginSuffix();
}
