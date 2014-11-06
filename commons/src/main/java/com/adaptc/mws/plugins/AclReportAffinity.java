package com.adaptc.mws.plugins;

import java.text.ParseException;

/**
 * This enumeration describes the values available for describing how
 * a rule is used in establishing access to an object in Moab. Currently,
 * these ACL affinities are used only for granting access to reservations.
 *
 * @author jpratt
 *
 */

public enum AclReportAffinity {
	/**
	 * Access to the object is repelled using this rule until access is the last
	 * choice.
	 */
	NEGATIVE("-"),

	/**
	 * Access to the object is not affected by affinity.
	 */
	NEUTRAL("="),

	/**
	 * Access to the object is looked at as the first choice.
	 */
	POSITIVE("+");

	private String moabString;

	private AclReportAffinity(String moabString) {
		this.moabString = moabString;
	}

	public String toMoabString() {
		return moabString;
	}

	public static AclReportAffinity parse(String string) {
		for (AclReportAffinity value : values()) {
			if (value.name().equalsIgnoreCase(string) ||
					value.moabString.equalsIgnoreCase(string)) {
				return value;
			}
		}
		return null;
	}
}