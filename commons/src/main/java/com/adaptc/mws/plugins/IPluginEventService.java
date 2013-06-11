package com.adaptc.mws.plugins;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * A service for easily creating new events and notification conditions.  This service offloads the burden of
 * generating fields such as "origin" and "code" to the plugin framework and provides enumerations to help write
 * clean code without the use of magic strings.  Note that while the service is called the event service, it also
 * contains methods for dealing with notification conditions which are commonly related to events.
 * <p/>
 * For more information on how to use this service, see the MWS User Guide
 * section on Plugin Event Service.
 * @author bsaville
 */
public interface IPluginEventService {
	/**
	 * The plugin component code for all plugins that do not explicitly define the value in the
	 * project or the plugin.  This is used in generating the event code when creating events.
	 */
	public static final int UNKNOWN_PLUGIN_COMPONENT = 0xFF;

	/**
	 * Determines the severity of the event.
	 */
	public enum Severity {
		/**
		 * Represents an informational event
		 */
		INFO(0x0),
		/**
		 * Represents an warning event
		 */
		WARN(0x1),
		/**
		 * Represents an error event
		 */
		ERROR(0x2),
		/**
		 * Represents a fatal event
		 */
		FATAL(0x3);

		private int code;

		public int getCode() {
			return code;
		}

		private Severity(int code) {
			this.code = code;
		}
	}

	/**
	 * Determines who would be concerned with this event or notification condition.
	 */
	public enum EscalationLevel {
		/**
		 * For typical users
		 */
		USER(0x0),
		/**
		 * For power users and users
		 */
		POWER_USER(0x1),
		/**
		 * For administrators, power users, and users
		 */
		ADMIN(0x2),
		/**
		 * For internal events only, may <b>NOT</b> be used for notification conditions.
		 */
		INTERNAL(0x3);

		private int code;

		public int getCode() {
			return code;
		}

		private EscalationLevel(int code) {
			this.code = code;
		}
	}

	/**
	 * Represents an object associated with an event.
	 */
	public class AssociatedObject {
		/**
		 * The type of the associated object such as "Node", "VM", "Policy", etc.
		 */
		String type;
		/**
		 * The id of the associated object.
		 */
		String id;

		public AssociatedObject() {
			// Default constructor
		}

		/**
		 * Map constructor used to allow map passing for lists of associated objects and making sure casting works
		 * as expected.
		 * @param map A map containing "type" and "id"
		 */
		public AssociatedObject(Map<String, String> map) {
			if (map==null)
				return;
			this.type = map.get("type");
			this.id = map.get("id");
		}
	}

	/**
	 * Creates an event with the specified properties and using the current date as the event date.
	 * @param severity The severity of the event
	 * @param escalationLevel The escalation level of the event
	 * @param eventCode The unique (for each plugin with a correct plugin component code) code of the event
	 * @param eventType The event type, such as "Node Modify" or "VM Migrate", may be null
	 * @param originSuffix The suffix to append to the automatically generated origin, may be null ({@link IPluginEvent#getOriginSuffix()})
	 * @param message The fully resolved message describing the event
	 * @param arguments The arguments of the event message, may be null or empty
	 * @param objects A list of objects associated with the event, may be null or empty
	 */
	public void createEvent(Severity severity, EscalationLevel escalationLevel, int eventCode, String eventType,
							String originSuffix, String message, List<String> arguments, List<AssociatedObject> objects)
			throws Exception;

	/**
	 * Same as {@link #createEvent(Severity, EscalationLevel, int, String, String, String, List, List)} but with
	 * a specified event date.
	 * @param eventDate The date that the event occurred
	 * @param severity The severity of the event
	 * @param escalationLevel The escalation level of the event
	 * @param eventCode The unique (for each plugin with a correct plugin component code) code of the event
	 * @param eventType The event type, such as "Node Modify" or "VM Migrate", may be null
	 * @param originSuffix The suffix to append to the automatically generated origin, may be null ({@link IPluginEvent#getOriginSuffix()})
	 * @param message The fully resolved message describing the event
	 * @param arguments The arguments of the event message, may be null or empty
	 * @param objects A list of objects associated with the event, may be null or empty
	 */
	public void createEvent(Date eventDate, Severity severity, EscalationLevel escalationLevel, int eventCode,
							String eventType, String originSuffix, String message, List<String> arguments,
							List<AssociatedObject> objects)
			throws Exception;

	/**
	 * Creates an event using a {@link IPluginEvent} value with the instance specific information of arguments and
	 * associated objects.  The arguments will be used in combination with the message code to fully resolve
	 * the message when creating the actual event.  The current date will be used as the event date.
	 * @param pluginEvent The non-null {@link IPluginEvent} value specifying the attributes of the event
	 * @param arguments The arguments of the event message, may be null or empty
	 * @param objects A list of objects associated with the event, may be null or empty
	 */
	public void createEvent(IPluginEvent pluginEvent, List<String> arguments, List<AssociatedObject> objects)
			throws Exception;

	/**
	 * Same as {@link #createEvent(IPluginEvent, List, List)} but with a specified event date.
	 * @param eventDate The date that the event occurred
	 * @param pluginEvent The non-null {@link IPluginEvent} value specifying the attributes of the event
	 * @param arguments The arguments of the event message, may be null or empty
	 * @param objects A list of objects associated with the event, may be null or empty
	 */
	public void createEvent(Date eventDate, IPluginEvent pluginEvent, List<String> arguments,
							List<AssociatedObject> objects)
			throws Exception;

	/**
	 * Creates a notification condition with the specified properties and using the current date as the observed
	 * date and no expiration duration.
	 * @param escalationLevel The escalation level of the notification, may not be INTERNAL
	 * @param message The full resolved message describing the notification
	 * @param associatedObject The object associated with the notification, such as Node "node1", may be null
	 * @param details Arbitrary details associated with the notification, may be null or empty
	 */
	public void createNotificationCondition(EscalationLevel escalationLevel, String message,
											AssociatedObject associatedObject, Map<String, String> details)
			throws Exception;

	/**
	 * Creates a notification condition with the specified properties and using the current date as the observed date.
	 * @param escalationLevel The escalation level of the notification, may not be INTERNAL
	 * @param message The full resolved message describing the notification
	 * @param associatedObject The object associated with the notification, such as Node "node1", may be null
	 * @param details Arbitrary details associated with the notification, may be null or empty
	 * @param expirationDuration The duration before the notification is marked as expired if not observed again
	 */
	public void createNotificationCondition(EscalationLevel escalationLevel, String message,
											AssociatedObject associatedObject, Map<String, String> details,
											Long expirationDuration)
			throws Exception;

	/**
	 * Same as {@link #createNotificationCondition(EscalationLevel, String, AssociatedObject, Map)} but with a
	 * specified observed date.
	 * @param escalationLevel The escalation level of the notification, may not be INTERNAL
	 * @param message The full resolved message describing the notification
	 * @param associatedObject The object associated with the notification, such as Node "node1", may be null
	 * @param details Arbitrary details associated with the notification, may be null or empty
	 */
	public void createNotificationCondition(Date observedDate, EscalationLevel escalationLevel, String message,
											AssociatedObject associatedObject, Map<String, String> details)
			throws Exception;

	/**
	 * Same as {@link #createNotificationCondition(EscalationLevel, String, AssociatedObject, Map, Long)} but
	 * with a specified observed date.
	 * @param observedDate The date that the notification condition was observed
	 * @param escalationLevel The escalation level of the notification, may not be INTERNAL
	 * @param message The full resolved message describing the notification
	 * @param associatedObject The object associated with the notification, such as Node "node1", may be null
	 * @param details Arbitrary details associated with the notification, may be null or empty
	 * @param expirationDuration The duration before the notification is marked as expired if not observed again
	 */
	public void createNotificationCondition(Date observedDate, EscalationLevel escalationLevel, String message,
											AssociatedObject associatedObject, Map<String, String> details,
											Long expirationDuration)
			throws Exception;
}
