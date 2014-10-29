package com.adaptc.mws.plugins;

/**
 * This enumeration describes the values available for the type of an ACL Report Rule.
 *
 * @author jpratt
 *
 */

public enum AclReportType {
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
	JPRIORITY("JPRIORITY"),

	MEMORY("MEMORY"),

	NODE("NODE"),

	PAR("PAR"),

	PROC("PROC"),

	QTIME("QTIME"),

	QUEUE("QUEUE"),

	RACK("RACK"),

	SCHED("SCHED"),

	SYSTEM("SYSTEM"),

	TASK("TASK"),

	VC("VC"),

	XFACTOR("XFACTOR");

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
