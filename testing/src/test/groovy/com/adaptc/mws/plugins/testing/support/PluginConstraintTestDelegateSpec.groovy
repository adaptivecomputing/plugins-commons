package com.adaptc.mws.plugins.testing.support

import spock.lang.*
import com.adaptc.mws.plugins.Suite

class PluginConstraintTestDelegateSpec extends Specification {
	def "App config and suite resolve correctly"() {
		given:
		ConfigObject privateAppConfig = new ConfigObject()
		privateAppConfig.test = true
		privateAppConfig.mws.suite = Suite.HPC
		def closureDelegate = new PluginConstraintTestDelegate("validator", null, privateAppConfig)

		when: "Simple app config test"
		def appConfigClosure = {
			return appConfig.test
		}
		appConfigClosure.delegate = closureDelegate
		def result = appConfigClosure.call()

		then:
		result==true

		when: "App config changed picks up immediately"
		privateAppConfig.test = false
		result = appConfigClosure.call()

		then:
		result==false

		when: "Suite is pulled correctly"
		appConfigClosure = {
			return suite
		}
		appConfigClosure.delegate = closureDelegate
		result = appConfigClosure.call()

		then:
		result==Suite.HPC
	}
}
