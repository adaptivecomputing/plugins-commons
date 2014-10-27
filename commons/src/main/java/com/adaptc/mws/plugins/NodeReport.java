package com.adaptc.mws.plugins;

import java.util.*;

/**
 * A node report consists of values for all of the known, changed properties for a specific node
 * resource.  This typically represents changes in a specific time period (ie between the last
 * poll and the current poll), but can also represent all known information concerning a resource
 * (ie the first time poll is run).
 * <p/>
 * For more information on reporting resources, see the Plugin Reporting section in the
 * MWS documentation.
 * @author bsaville
 */
public class NodeReport {
	/**
	 * The node's name.
	 */
	private String name;
	/**
	 * The date that the report was made or for which the report is current.  This will be
	 * set to the current date and time if not provided.  This may also be set as a string
	 * which will use {@link PluginConstants#STANDARD_DATE_TIME_FORMATTER} to parse into a date.
	 */
	private Date timestamp;
	/**
	 * If true, this report will be considered a "slave" report.  If all reports for an object are only "slave" reports,
	 * MWS will not report the object to Moab Workload Manager.  Otherwise (if at least one report has this value set
	 * to false), the object will be reported as normal.  By default, all reports are considered "master" reports and
	 * have this field set to false.
	 * <p/>
	 * This enables functionality where additional data is wished to be reported without reporting the object itself
	 * if it has been deleted, no longer exists, etc.
 	 */
	private boolean slaveReport = false;
	/**
	 * The node's resources information.  Note that no null checks are needed to act on the resources.
	 */
	private ReportResourceMap resources = new ReportResourceMap();
	/**
	 * The node's reported metrics.
	 */
	private Map<String, Double> metrics = new HashMap<String, Double>();
	/**
	 * The list of the node's features.
	 */
	private List<String> features = new ArrayList<String>();
	/**
	 * The names of the images that this node can be provisioned as.  This relates to the image name
	 * in the MWS Image Catalog.
	 */
	private List<String> imagesAvailable = new ArrayList<String>();
	/**
	 * A list of messages to attach to the node.  Double quotes (whether escaped or not) will be converted into
	 * single quotes.
	 */
	private List<String> messages = new ArrayList<String>();
	/**
	 * The name of the image that this node is currently provisioned as.  This relates to the image name
	 * in the MWS Image Catalog and hypervisor information (including available virtualized images) is
	 * pulled from the catalog and reported to Moab.
	 */
	private String image;
	/**
	 * If non-null, determines whether the node is added to the migration exclusion list or not
	 * when reported.  A true value disables migrations to or from the node, while a false value
	 * enables them.  A null value will use the lists configured in the migration exclusion
	 * list global policy.
	 */
	private Boolean migrationDisabled;
	/**
	 * The node's partition.
	 */
	private String partition;
	/**
	 * The node's variables.
	 */
	private Map<String, String> variables = new HashMap<String, String>();
	/**
	 * The node's IPv4 address.
	 */
	private String ipAddress;
	/**
	 * The current state of the node's power.
	 */
	private NodeReportPower power;
	/**
	 * The current state of the node.
	 */
	private NodeReportState state;
	/**
	 * The node's current sub-state.
	 */
	private String subState;
	/**
	 * The node's architecture.
	 */
	private String architecture;
    /**
     * The node's attributes which jobs can request with specific constraints and requirements.
	 * Note that no null checks are needed to act on the attributes.
     */
    private ReportAttributeMap attributes = new ReportAttributeMap();
	/**
	 * The ID of the plugin which has generated the report.  NOTE: This is overridden by
	 * the RM services automatically and should not be set by the plugin.
	 */
	private String pluginId;
	/**
	 * The precedence of the plugin which has generated the report.  NOTE: This is overridden by
	 * the RM services automatically and should not be set by the plugin.
	 */
	private Long precedence;
	/**
	 * Specifies the time that the node is supposed to be retired by Moab.
	 * Moab will not schedule any jobs on a node after its time to live has passed.
	 */
	private Date timeToLive;
	/**
	 * An ID that can be used to track the request that created the node.
	 */
	private String requestId;
	/**
	 * Can be used to control which users have access to the node in Moab.
	 */
	private List<AclReportRule> aclRules;

	/**
	 * @see #name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the unique identifier for the node, preserving the case specified.
	 * @see #name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Retrieves the set timestamp for the report.  NOTE: If no timestamp is provided by the plugin, the
	 * timestamp will be assigned a value of the current date and time.
	 * @see #timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}
	/**
	 * @see #timestamp
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @see #timestamp
	 */
	public void setTimestamp(String timestamp) {
		try {
			this.timestamp = PluginConstants.STANDARD_DATE_TIME_FORMATTER.parseDateTime(timestamp).toDate();
		} catch(IllegalArgumentException e) {
			this.timestamp = null;
		} catch(NullPointerException e) {
			this.timestamp = null;
		}
	}
	/**
	 * @see #slaveReport
	 */
	public boolean getSlaveReport() {
		return slaveReport;
	}
	/**
	 * @see #slaveReport
	 */
	public void setSlaveReport(boolean slaveReport) {
		this.slaveReport = slaveReport;
	}
	/**
	 * @see #resources
	 */
	public ReportResourceMap getResources() {
		return resources;
	}
	/**
	 * @see #resources
	 */
	public void setResources(ReportResourceMap resources) {
		this.resources = resources;
	}
	/**
	 * @see #metrics
	 */
	public Map<String, Double> getMetrics() {
		return metrics;
	}
	/**
	 * @see #metrics
	 */
	public void setMetrics(Map<String, Double> metrics) {
		this.metrics = metrics;
	}
	/**
	 * @see #features
	 */
	public List<String> getFeatures() {
		return features;
	}
	/**
	 * @see #features
	 */
	public void setFeatures(List<String> features) {
		this.features = features;
	}
	/**
	 * @see #image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * Sets the image name for the node. This is used along with the MWS Image Catalog to retrieve hypervisor
	 * and supported virtual machine information.
	 * @see #image
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * @see #migrationDisabled
	 */
	public Boolean getMigrationDisabled() {
		return migrationDisabled;
	}
	/**
	 * @see #migrationDisabled
	 */
	public void setMigrationDisabled(Boolean migrationDisabled) {
		this.migrationDisabled = migrationDisabled;
	}
	/**
	 * @see #partition
	 */
	public String getPartition() {
		return partition;
	}
	/**
	 * @see #partition
	 */
	public void setPartition(String partition) {
		this.partition = partition;
	}
	/**
	 * @see #variables
	 */
	public Map<String, String> getVariables() {
		return variables;
	}
	/**
	 * @see #variables
	 */
	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}
	/**
	 * @see #ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	/**
	 * @see #ipAddress
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	/**
	 * @see #power
	 */
	public NodeReportPower getPower() {
		return power;
	}
	/**
	 * @see #power
	 */
	public void setPower(NodeReportPower power) {
		this.power = power;
	}
	/**
	 * Calls {@link NodeReportPower#parse(String)} to set the {@link #power} field.
	 * @see #power
	 */
	public void setPower(String power) {
		this.power = NodeReportPower.parse(power);
	}
	/**
	 * @see #state
	 */
	public NodeReportState getState() {
		return state;
	}
	/**
	 * @see #state
	 */
	public void setState(NodeReportState state) {
		this.state = state;
	}
	/**
	 * Calls {@link NodeReportState#parse(String)} to set the {@link #state} field.
	 * @see #state
	 */
	public void setState(String state) {
		this.state = NodeReportState.parse(state);
	}
	/**
	 * @see #subState
	 */
	public String getSubState() {
		return subState;
	}
	/**
	 * @see #subState
	 */
	public void setSubState(String subState) {
		this.subState = subState;
	}
	/**
	 * @see #architecture
	 */
	public String getArchitecture() {
		return architecture;
	}
	/**
	 * @see #architecture
	 */
	public void setArchitecture(String architecture) {
		this.architecture = architecture;
	}
	/**
	 * @see #imagesAvailable
	 */
	public List<String> getImagesAvailable() {
		return imagesAvailable;
	}
	/**
	 * @see #imagesAvailable
	 */
	public void setImagesAvailable(List<String> imagesAvailable) {
		this.imagesAvailable = imagesAvailable;
	}
	/**
	 * @see #messages
	 */
	public List<String> getMessages() {
		return messages;
	}
	/**
	 * @see #messages
	 */
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
    /**
     * @see #attributes
     */
    public ReportAttributeMap getAttributes() {
        return attributes;
    }
    /**
     * @see #attributes
     */
    public void setAttributes(ReportAttributeMap attributes) {
        this.attributes = attributes;
    }
	/**
	 * @see #pluginId
	 */
	public String getPluginId() {
		return pluginId;
	}
	/**
	 * Sets the ID of the plugin which has generated the report.  NOTE: This is overridden by
	 * the RM services automatically and should not be set by the plugin.
	 * @see #pluginId
	 */
	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}
	/**
	 * @see #precedence
	 */
	public Long getPrecedence() {
		return precedence;
	}
	/**
	 * Sets the precedence, corresponding to the precedence of the plugin which has generated the report.
	 * NOTE: This is overridden by the RM services automatically and should not be set by the plugin.
	 * @see #precedence
	 */
	public void setPrecedence(Long precedence) {
		this.precedence = precedence;
	}
	/**
	 * @see #timeToLive
	 */
	public Date getTimeToLive() {
		return timeToLive;
	}
	/**
	 * @see #timeToLive
	 */
	public void setTimeToLive(Date timeToLive) {
		this.timeToLive = timeToLive;
	}
	/**
	 * @see #requestId
	 */
	public String getRequestId() {
		return requestId;
	}
	/**
	 * @see #requestId
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	/**
	 * @see #aclRules
	 */
	public List<AclReportRule> getAclRules() {
		return aclRules;
	}
	/**
	 * @see #aclRules
	 */
	public void setAclRules(List<AclReportRule> aclRules) { this.aclRules = aclRules; }

	/**
	 * Creates a new, empty node report.
	 */
	public NodeReport() {}
	/**
	 * Creates a new node report for specified node.
	 * @param name See {@link #setName}
	 */
	public NodeReport(String name) {
		setName(name);
	}
}
