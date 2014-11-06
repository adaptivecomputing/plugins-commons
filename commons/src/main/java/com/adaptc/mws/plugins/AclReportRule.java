package com.adaptc.mws.plugins;

/**
 * <p>
 * This class represents a rule that can be in Moab's access control list (ACL)
 * mechanism.
 * </p>
 *
 * <p>
 * The basic AclReportRule information is the object's name and type. The type
 * directly maps to an {@link AclReportType} value. The default mechanism Moab
 * uses to check the ACL for a particular item is if the user or object coming
 * in has ANY of the values in the ACL, then the user or object is given access.
 * If no values match the user or object in question, the user or object is
 * rejected access.
 * </p>
 *
 * @author jpratt
 */
public class AclReportRule {
	/**
	 * The type of the object that is being granted (or denied) access.
	 */
	private AclReportType type;

	/**
	 * The type of comparison to make against the ACL object.
	 */
	private ReportComparisonOperator comparator = ReportComparisonOperator.LEXIGRAPHIC_EQUAL;

	/**
	 * If attribute is met, the requestor is denied access regardless of any other satisfied ACLs.
	 */
	private boolean excludeFromAcl;

	/**
	 * All required ACLs must be satisfied for requestor access to be granted.
	 */
	private boolean requireAll;

	/**
	 * All attributes of the type specified other than the ones listed in the ACL satisfy the ACL.
	 */
	private boolean xorWithAcl;

	/**
	 * Matching jobs will be required to run on the resources reserved by this reservation.
	 * You can use this modifier on accounts, classes, groups, qualities of service, and users.
	 */
	private boolean credentialLock;

	/**
	 * ACLs marked with this modifier are ignored during soft policy scheduling and are
	 * only considered for hard policy scheduling once all eligible soft policy jobs start.
	 */
	private boolean hardPolicyOnly;

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
	 * affinity.
	 */
	private AclReportAffinity affinity = AclReportAffinity.POSITIVE;

	/**
	 * @see #type
	 */
	public AclReportType getType() { return type; }

	/**
	 * @see #type
	 */
	public void setType(AclReportType type) { this.type = type; }

	/**
	 * @see #comparator
	 */
	public ReportComparisonOperator getComparator() { return comparator; }

	/**
	 * @see #comparator
	 */
	public void setComparator(ReportComparisonOperator comparator) { this.comparator = comparator; }

	/**
	 * @see #excludeFromAcl
	 */
	public boolean getExcludeFromAcl() {return excludeFromAcl;}

	/**
	 * @see #excludeFromAcl
	 */
	public void setExcludeFromAcl(boolean excludeFromAcl) {this.excludeFromAcl = excludeFromAcl;}

	/**
	 * @see #requireAll
	 */
	public boolean getRequireAll() {return requireAll;}

	/**
	 * @see #requireAll
	 */
	public void setRequireAll(boolean requireAll) {this.requireAll = requireAll;}

	/**
	 * @see #xorWithAcl
	 */
	public boolean getXorWithAcl() {return xorWithAcl;}

	/**
	 * @see #xorWithAcl
	 */
	public void setXorWithAcl(boolean xorWithAcl) {this.xorWithAcl = xorWithAcl;}

	/**
	 * @see #credentialLock
	 */
	public boolean getCredentialLock() {return credentialLock;}

	/**
	 * @see #credentialLock
	 */
	public void setCredentialLock(boolean credentialLock) {this.credentialLock = credentialLock;}

	/**
	 * @see #hardPolicyOnly
	 */
	public boolean getHardPolicyOnly() {return hardPolicyOnly;}

	/**
	 * @see #hardPolicyOnly
	 */
	public void setHardPolicyOnly(boolean hardPolicyOnly) {this.hardPolicyOnly = hardPolicyOnly;}

	/**
	 * @see #value
	 */
	public String getValue() { return value; }

	/**
	 * @see #value
	 */
	public void setValue(String value) { this.value = value; }

	/**
	 * @see #affinity
	 */
	public AclReportAffinity getAffinity() { return affinity; }

	/**
	 * @see #affinity
	 */
	public void setAffinity(AclReportAffinity affinity) { this.affinity = affinity; }


}