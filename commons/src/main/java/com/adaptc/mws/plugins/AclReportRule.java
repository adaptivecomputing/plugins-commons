package com.adaptc.mws.plugins;

/**
 * <p>
 * This class represents a rule that can be in Moab's access control list (ACL)
 * mechanism.
 * </p>
 *
 * <p>
 * The basic AclReportRule information is the object's name and type. The type
 * directly maps to an {@link AclType} value. The default mechanism Moab
 * uses to check the ACL for a particular item is if the user or object coming
 * in has ANY of the values in the ACL, then the user or object is given access.
 * If no values match the user or object in question, the user or object is
 * rejected access.
 * </p>
 *
 * @author jpratt
 */
class AclReportRule {
	/**
	 * The type of the object that is being granted (or denied) access.
	 */
	private AclReportType aclReportType;

	/**
	 * The name of the object that is being granted (or denied) access.
	 */
	private String value;

	/**
	 * Reservation ACLs allow or deny access to reserved resources
	 * but they may also be configured to affect a job's affinity
	 * for a particular reservation. By default, jobs gravitate toward
	 * reservations through a mechanism known as positive affinity. This
	 * mechanism allows jobs to run on the most constrained resources
	 * leaving other, unreserved resources free for use by other jobs
	 * that may not be able to access the reserved resources. Normally
	 * this is a desired behavior. However, sometimes, it is desirable
	 * to reserve resources for use only as a last resort-using the
	 * reserved resources only when there are no other resources
	 * available. This last resort behavior is known as negative
	 * affinity.<br><br>
	 */
	private AclReportAffinity affinity = AclReportAffinity.POSITIVE;

	/**
	 * The type of comparison to make against the ACL object.<br><br>
	 */
	private ReportComparisonOperator comparator = ReportComparisonOperator.LEXIGRAPHIC_EQUAL;


	/**
	 * @see #aclReportType
	 */
	public AclReportType getAclReportType() { return aclReportType; }

	/**
	 * @see #aclReportType
	 */
	public void setAclReportType(AclReportType aclReportType) { this.aclReportType = aclReportType; }

	/**
	 * @see #value
	 */
	public String getValue() { return value; }

	/**
	 * @see #value
	 */
	public void setValue(String value) { this.value = value; }

	/**
	 * @see #aclReportAffinity
	 */
	public AclReportAffinity getAclReportAffinity() { return affinity; }

	/**
	 * @see #aclReportAffinity
	 */
	public void setAclReportAffinity(AclReportAffinity aclReportAffinity) { this.affinity = aclReportAffinity; }

	/**
	 * @see #comparator
	 */
	public ReportComparisonOperator getReportComparisonOperator() { return comparator; }

	/**
	 * @see #comparator
	 */
	public void setReportComparisonOperator(ReportComparisonOperator reportComparisonOperator) { this.comparator = reportComparisonOperator; }

}