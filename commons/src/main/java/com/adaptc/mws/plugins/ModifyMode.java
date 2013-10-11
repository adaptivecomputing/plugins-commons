package com.adaptc.mws.plugins;

/**
 * Represents a modification mode for certain events in plugins.
 *
 * @see AbstractPlugin
 * @author bsaville
 */
public enum ModifyMode {
	/**
	 * Sets the objects/properties to the specified values, removing or adding elements as necessary.
	 */
	SET("set"),
	/**
	 * Adds the specified values to the objects/properties, while leaving unspecified values unmodified
	 */
	ADD("add"),
	/**
	 * Removes the specified values from the objects/properties, while leaving unspecified values unmodified
	 */
	REMOVE("decr");

	private String moabString;

	private ModifyMode(String moabString) {
		this.moabString = moabString;
	}

	/**
	 * The Moab Workload Manager equivalent string for the modify mode.
	 * @return The MWM equivalent string
	 */
	public String getMoabString() {
		return moabString;
	}

	/**
	 * Parses the specified string to find a matching modify mode, either by the name or the moab string.
	 * @param str The string to parse for
	 * @return The parsed modify mode or null if no match
	 */
	public static ModifyMode parse(String str) {
		if (str==null || str.isEmpty())
			return null;
		for(ModifyMode mode : values()) {
			if (mode.getMoabString().equalsIgnoreCase(str) || mode.name().equalsIgnoreCase(str))
				return mode;
		}
		return null;
	}
}
