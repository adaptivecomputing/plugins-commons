package com.adaptc.mws.plugins.testing.support

import org.apache.commons.lang.StringUtils
import com.adaptc.mws.plugins.Suite

/**
 * @author bsaville
 */
class PluginConstraintTestDelegate {
	private final String propertyName;
	private final Object pluginInstance;
	private final ConfigObject appConfig;

	public PluginConstraintTestDelegate(String propertyName, Object pluginInstance, ConfigObject appConfig) {
		this.propertyName = propertyName
		this.pluginInstance = pluginInstance
		this.appConfig = appConfig
	}

	@SuppressWarnings("unused")
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * Equivalent to getService("applicationContext")
	 */
	@SuppressWarnings("unused")
	public Object getApplicationContext() {
		return getService("applicationContext");
	}

	public ConfigObject getAppConfig() {
		return appConfig
	}

	public Suite getSuite() {
		return (Suite)(((ConfigObject)((ConfigObject)getAppConfig()).get("mws"))).get("suite");
	}

	/**
	 * Retrieves a service from the plugin instance directly.  This requires the plugin instance to have
	 * the bean injected onto it, but this is a reasonable assumption for testing.
	 */
	@SuppressWarnings("unused")
	public Object getService(String name) {
		if (name==null)
			return null;
		try {
			return pluginInstance."get${StringUtils.capitalize(name)}"()
		} catch(MissingMethodException e) {
			throw new Exception("The service ${name} must exist on the plugin instance in order to retrieve it "+
					"for testing")
		}
	}
}