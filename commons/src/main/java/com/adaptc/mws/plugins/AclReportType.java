package com.adaptc.mws.plugins;

/**
 * This enumeration describes the values available for the type of an ACL Report Rule.
 *
 * @author jpratt
 *
 */

enum AclReportType {
	/**
	 * User
	 */
	USER("USER"),
	/**
	 * Group
	 */
	GROUP("GROUP"),
	/**
	 * Account or Project
	 */
	ACCOUNT("ACCT"),
	/**
	 * Class or Queue
	 */
	CLASS("CLASS"),
	/**
	 * Quality of Service
	 */
	QOS("QOS"),
	/**
	 * Cluster
	 */
	CLUSTER("CLUSTER"),
	/**
	 * Job ID
	 */
	JOB_ID("JOB"),
	/**
	 * Reservation ID
	 */
	RESERVATION_ID("RSV"),
	/**
	 * Job Template
	 */
	JOB_TEMPLATE("JTEMPLATE"),
	/**
	 * Job Attribute
	 */
	JOB_ATTRIBUTE("JATTR"),
	/**
	 * Duration in Seconds
	 */
	DURATION("DURATION"),
	/**
	 * Processor Seconds
	 */
	PROCESSOR_SECONDS("PS"),

	// The following enums are here for completeness but not
	// documented or tested.
	/**
	 * Not supported
	 */
	JPRIORITY("JPRIORITY"),

	/**
	 * Not supported
	 */
	MEMORY("MEMORY"),

	/**
	 * Not supported
	 */
	NODE("NODE"),

	/**
	 * Not supported
	 */
	PAR("PAR"),

	/**
	 * Not supported
	 */
	PROC("PROC"),

	/**
	 * Not supported
	 */
	QTIME("QTIME"),

	/**
	 * Not supported
	 */
	QUEUE("QUEUE"),

	/**
	 * Not supported
	 */
	RACK("RACK"),

	/**
	 * Not supported
	 */
	SCHED("SCHED"),

	/**
	 * Not supported
	 */
	SYSTEM("SYSTEM"),

	/**
	 * Not supported
	 */
	TASK("TASK"),

	/**
	 * Not supported
	 */
	VC("VC"),

	/**
	 * Not supported
	 */
	XFACTOR("XFACTOR");

	// See MAttrO and MHRObj in stratus/src/moab/MConst.c.
	private String moabString;

	private AclReportType(String moabString) {
		this.moabString = moabString;
	}

	public String toMoabString() {
		return moabString;
	}

	public static AclReportType parse(String string) {
		for (AclReportType type : values()) {
			if(type.name().equalsIgnoreCase(string) ||
					type.moabString.equalsIgnoreCase(string)) {
				return type;
			}
		}
		return null;
	}
}
