package com.adaptc.mws.plugins;

/**
 * This enumeration describes the values available for the modifier of an ACL Report Rule.
 *
 * @author jpratt
 *
 */

public enum AclReportModifier {
	/**
	 * Not
	 * If attribute is met, the requestor is denied access regardless of any other satisfied ACLs.
	 */
	NOT("!"),
	/**
	 * Required
	 * All required ACLs must be satisfied for requestor access to be granted.
	 */
	REQUIRED("*"),
	/**
	 * XOR
	 * All attributes of the type specified other than the ones listed in the ACL satisfy the ACL.
	 */
	XOR("^"),
	/**
	 * CredLock
	 * Matching jobs will be required to run on the resources reserved by this reservation.
	 * You can use this modifier on accounts, classes, groups, qualities of service, and users.
	 */
	CredLock("&"),
	/**
	 * HPEnable
	 * ACLs marked with this modifier are ignored during soft policy scheduling and are
	 * only considered for hard policy scheduling once all eligible soft policy jobs start.
	 */
	HPEnable("~");

	private String moabString;

	private AclReportModifier(String moabString) {
		this.moabString = moabString;
	}

	public String toMoabString() {
		return moabString;
	}

	public static AclReportModifier parse(String string) {
		for (AclReportModifier modifier : values()) {
			if(modifier.name().equalsIgnoreCase(string) ||
					modifier.moabString.equalsIgnoreCase(string)) {
				return modifier;
			}
		}
		return null;
	}
}
