package com.adaptc.mws.plugins

import spock.lang.*

import static com.adaptc.mws.plugins.IPluginEventService.Severity.*
import static com.adaptc.mws.plugins.IPluginEventService.EscalationLevel.*

/**
 * @author bsaville
 */
class EventEnumerationSpec extends Specification {
	def "Basic transformation"() {
		expect:
		EventTest.EVENT1 instanceof IPluginEvent
		EventTest.EVENT1.eventName=="My Type"
		EventTest.EVENT1.severity==INFO
		EventTest.EVENT1.escalationLevel==USER
		EventTest.EVENT1.messageCode=="EventTest.EVENT1.message"
		EventTest.EVENT1.commentCode=="EventTest.EVENT1.comment"
		EventTest.EVENT1.eventCode==0

		and:
		EventTest.EVENT2 instanceof IPluginEvent
		EventTest.EVENT2.eventName=="My Type2"
		EventTest.EVENT2.severity==WARN
		EventTest.EVENT2.escalationLevel==POWER_USER
		EventTest.EVENT2.messageCode=="EventTest.EVENT2.message"
		EventTest.EVENT2.commentCode=="EventTest.EVENT2.comment"
		EventTest.EVENT2.eventCode==1
	}

	def "Prefixes and suffixes"() {
		expect:
		value.originSuffix==originSuffix
		value.eventType==eventType

		where:
		value						|| originSuffix						| eventType
		EventTest.EVENT1			|| "EventTest/EVENT1"				| "My Type"
		EventTest.EVENT2			|| "EventTest/EVENT2"				| "My Type2"
		EventTypePrefix.EVENT1		|| "EventTypePrefix/EVENT1"			| "Second My Type"
		EventTypePrefix.EVENT2		|| "EventTypePrefix/EVENT2"			| "Second My Type2"
		EventOriginSuffix.EVENT1	|| "MyOrigin"						| "My Type"
		EventOriginSuffix.EVENT2	|| "MyOrigin"						| "My Type2"
		EventBoth.EVENT1			|| "MyOrigin/Value"					| "Your Type"
		EventBoth.EVENT2			|| "MyOrigin/Value"					| "Your Type2"
	}
}

@EventEnumeration
enum EventTest {
	EVENT1("My Type", INFO, USER),
	EVENT2("My Type2", WARN, POWER_USER)
}

@EventEnumeration
enum EventTypePrefix {
	EVENT1("My Type", INFO, USER),
	EVENT2("My Type2", INFO, POWER_USER)

	static final String EVENT_TYPE_PREFIX = "Second"
}

@EventEnumeration
enum EventOriginSuffix {
	EVENT1("My Type", INFO, USER),
	EVENT2("My Type2", INFO, POWER_USER)

	static final String ORIGIN_SUFFIX = "MyOrigin"
}

@EventEnumeration
enum EventBoth {
	EVENT1("Type", INFO, USER),
	EVENT2("Type2", INFO, POWER_USER)

	static final String EVENT_TYPE_PREFIX = "Your"
	static final String ORIGIN_SUFFIX = "MyOrigin"+IPluginEvent.ORIGIN_DELIMITER+"Value"
}