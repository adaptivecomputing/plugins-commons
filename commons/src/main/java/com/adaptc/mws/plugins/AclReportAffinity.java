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

enum AclReportAffinity {
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
	POSITIVE("+"),

	/**
	 * Access to the object given the rule gives preemptible status to the
	 * accessor. Supported only during GET.
	 */
	PREEMPTIBLE("PREEMPTIBLE"),

	/**
	 * The rule in question must be satisified in order to gain access to the
	 * object. Supported only during GET.
	 */
	REQUIRED("REQUIRED"),

	/**
	 * The rule does not have its affinity available. Supported only during GET.
	 */
	UNAVAILABLE("UNAVAILABLE");

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