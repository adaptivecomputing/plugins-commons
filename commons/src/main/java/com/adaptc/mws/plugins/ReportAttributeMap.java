package com.adaptc.mws.plugins;

import java.util.LinkedHashMap;

/**
 * A map containing String to {@link com.adaptc.mws.plugins.ReportAttribute} values.  If entries are attempted to be
 * accessed that do not exist, they are automatically created, allowing a more fluent usage
 * instead of checking for null values before adding attributes.  Additionally, this class
 * may be accessed in one of several ways:
 * <ul>
 *     <li>Using the map methods to access entries: {@link #get(Object)}} and {@link #put(Object, Object)}<br/>
 *     	<code>attributes.get('attribute1').value = "val"<br/>
 *     	attributes.get(attributeName).value = "val"</code>
 *     </li>
 *     <li>Accessing entries using array-like notation: {@link #getAt(String)}<br/>
 *         <code>attributes['attribute1'].value = "val"<br/>
 *         attributes[attributeName].value = "val"</code>
 *     </li>
 *     <li>Accessing entries as properties: {@link #propertyMissing(String)}<br/>
 *         <code>attributes.attribute1.value = "val"<br/>
 *         attributes."${attributeName}".value = "val"</code>
 *     </li>
 * </ul>
 *
 * Additionally, this object may be treated as a map of string to string values, meaning that
 * the value can be set directly on any attribute entry.  This may be used by utilizing the put method.
 * <ul>
 *     <li>{@link #put(Object, Object)}<br/>
 *     	<code>attributes.put('attribute1', "val")<br/>
 *     	attributes.put(attributeName, "val")</code>
 *     </li>
 * </ul>
 * @author bsaville
 */
public class ReportAttributeMap extends LinkedHashMap<String, ReportAttribute> {
	private static final long serialVersionUID = 1L;

	/**
	 * Retrieves an entry and creates it if it does not already exist.
	 * @param key The key to retrieve
	 * @return A {@link com.adaptc.mws.plugins.ReportAttribute} value, never null
	 */
	@Override
	public ReportAttribute get(Object key) {
		if (!containsKey(key))
			put((String)key, new ReportAttribute());
		return super.get(key);
	}

	/**
	 * Puts an entry value into the map.
	 * @param key The key to set
	 * @param value The value to set
	 */
	public void put(Object key, String value) {
		get(key).setValue(value);
	}

	/**
	 * Allows array-like access to entries.<br/>
	 *  <code>attributes['attribute1'].value = "val"<br/>
	 *  attributes[attributeName].value = "val"</code>
	 * @param key The key to retrieve
	 * @return A {@link com.adaptc.mws.plugins.ReportAttribute} value, never null
	 */
	public ReportAttribute getAt(String key) {
		return get(key);
	}

	/**
	 * Allows property access to entries.<br/>
	 *  <code>attributes.attribute1.value = "val"<br/>
	 *  attributes."${attributeName}".value = "val"</code>
	 * @param key The key to retrieve
	 * @return A {@link com.adaptc.mws.plugins.ReportAttribute} value, never null
	 */
	public Object propertyMissing(String key) {
		return get(key);
	}
}
