package com.adaptc.mws.plugins

import spock.lang.Specification

/**
 * 
 * @author bsaville
 */
class ReportAttributeMapSpec extends Specification {
	ReportAttributeMap attributeMap

	def setup() {
		attributeMap = new ReportAttributeMap()
	}

	def "Get entries"() {
		when:
		attributeMap.get('attribute1').value = "val1"

		then:
		attributeMap.size()==1
		attributeMap.get('attribute1').value=="val1"

		when:
		attributeMap['attribute2'].value = "val2"

		then:
		attributeMap.size()==2
		attributeMap['attribute2'].value=="val2"

		when:
		attributeMap.attribute3.value = "val3"

		then:
		attributeMap.size()==3
		attributeMap.attribute3.value=="val3"
	}
}
