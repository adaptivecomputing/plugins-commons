package com.adaptc.mws.plugins

import spock.lang.Specification
import net.sf.json.JSONSerializer
import spock.lang.Unroll

/**
 * @author bsaville
 */
@Unroll
class MoabRestResponseSpec extends Specification {
	def "Convert data #data"() {
		expect:
		new MoabRestResponse(null, JSONSerializer.toJSON(data), true).convertedData==data

		where:
		data << [
		        [:],
				[test:true],
				[test:null],
				[list:[null]],
				[map:[test:true]],
				[map:[test:null]],
		]
	}
}
