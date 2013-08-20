package com.adaptc.mws.plugins;

import java.util.*;

/**
 * <b>Storage reporting is currently in BETA, fields and operations may change without notice!</b><br />
 * A storage report consists of values for all of the known, changed properties for a specific storage
 * resource.  This typically represents changes in a specific time period (ie between the last
 * poll and the current poll), but can also represent all known information concerning a resource
 * (ie the first time poll is run).
 * <p/>
 * For more information on reporting resources, see the Plugin Reporting section in the
 * MWS documentation.
 * @author bsaville
 */
public class StorageReport {
	/**
	 * The storage resource's name. This should be lower-case, but it will be converted
	 * automatically if it is not in {@link #setName(String)}.
	 */
	private String name;
	/**
	 * The date that the report was made or for which the report is current.  This will be
	 * set to the current date and time if not provided.  This may also be set as a string
	 * which will use {@link com.adaptc.mws.plugins.PluginConstants#STANDARD_DATE_TIME_FORMATTER} to parse into a date.
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
	 * The storage resource's resources information.  Note that no null checks are needed to act on the resources.
	 */
	private ReportResourceMap resources = new ReportResourceMap();
	/**
	 * The storage resource's reported metrics.
	 */
	private Map<String, Double> metrics = new HashMap<String, Double>();
	/**
	 * The list of the storage resource's features.
	 */
	private List<String> features = new ArrayList<String>();
	/**
	 * A list of messages to attach to the storage resource.  Double quotes (whether escaped or not) will be
	 * converted into single quotes.
	 */
	private List<String> messages = new ArrayList<String>();
	/**
	 * The storage resource's partition.
	 */
	private String partition;
	/**
	 * The storage resource's variables.
	 */
	private Map<String, String> variables = new HashMap<String, String>();
	/**
	 * The storage resource's IPv4 address.
	 */
	private String ipAddress;
	/**
	 * The current state of the storage resource.
	 */
	private NodeReportState state;
	/**
	 * The storage resource's current sub-state.
	 */
	private String subState;
	/**
	 * The storage resource's architecture.
	 */
	private String architecture;
    /**
     * The storage resource's attributes which jobs can request with specific constraints and requirements.
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
	 * @see #name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the unique identifier for the storage resource.  This automatically
	 * lower-cases the ID.
	 * @see #name
	 */
	public void setName(String name) {
		if (name ==null)
			this.name = null;
		else
			this.name = name.toLowerCase();
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
	 * Calls {@link com.adaptc.mws.plugins.NodeReportState#parse(String)} to set the {@link #state} field.
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
	 * Creates a new, empty storage resource report.
	 */
	public StorageReport() {}
	/**
	 * Creates a new storage resource report for specified storage resource.
	 * @param name See {@link #setName}
	 */
	public StorageReport(String name) {
		setName(name);
	}
}
