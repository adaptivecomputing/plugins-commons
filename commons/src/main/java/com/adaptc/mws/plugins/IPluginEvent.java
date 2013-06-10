package com.adaptc.mws.plugins;

/**
 * Convenience interface for working with plugin events.  This may be implemented by an enum and used in combination
 * with the {@link IPluginEventService} to easily create events.
 * @author bsaville
 */
public interface IPluginEvent {
	/**
	 * The i18n message code to use for the event, which may optionally take arguments.
	 * @return The i18n message code for the message describing the event.
	 */
	public String getMessageCode();

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
}
