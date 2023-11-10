package com.adaptc.mws.plugins;

import java.util.*;

/**
 * A VM report consists of values for all of the known, changed properties for a specific VM
 * resource.  This typically represents changes in a specific time period (ie between the last
 * poll and the current poll), but can also represent all known information concerning a resource
 * (ie the first time poll is run).
 * <p>
 * For more information on reporting resources, see the Plugin Reporting section in the
 * MWS documentation.
 * @author bsaville
 */
public class VirtualMachineReport {
	/**
	 * The VM's name.
	 */
	private String name;
	/**
	 * The VM's current state.
	 */
	private NodeReportState state;
	/**
	 * The VM's current power state.
	 */
	private NodeReportPower power;
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
	 * <p>
	 * This enables functionality where additional data is wished to be reported without reporting the object itself
	 * if it has been deleted, no longer exists, etc.
	 */
	private boolean slaveReport = false;
	/**
	 * The image name for the VM. This is used along with the MWS Image Catalog to retrieve operating
	 * system information.
	 */
	private String image;
	/**
	 * The names of the images that this VM can be provisioned as.  This relates to the image name
	 * in the MWS Image Catalog.
	 */
	private List<String> imagesAvailable = new ArrayList<String>();
	/**
	 * If non-null, determines whether the VM is added to the migration exclusion list or not
	 * when reported.  A true value disables migrations of the VM, while a false value
	 * enables them.  A null value will use the lists configured in the migration exclusion
	 * list global policy.
	 */
	private Boolean migrationDisabled;
	/**
	 * The name of the host (hypervisor/node) that the VM resides on.
	 */
	private String host;
	/**
	 * The VM's IPv4 address.
	 */
	private String ipAddress;
	/**
	 * The VM's resources information.  Note that no null checks are needed to act on the resources.
	 */
	private ReportResourceMap resources = new ReportResourceMap();
	/**
	 * The VM's reported metrics.
	 */
	private Map<String, Double> metrics = new HashMap<String, Double>();
	/**
	 * The VM's variables.
	 */
	private Map<String, String> variables = new HashMap<String, String>();
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
	 * @see #name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @see #image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * Sets the image name for the VM. This is used along with the MWS Image Catalog to retrieve operating
	 * system information.
	 * @see #image
	 */
	public void setImage(String image) {
		this.image = image;
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
	 * @see #host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @see #host
	 */
	public void setHost(String host) {
		this.host = host;
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
	 * Creates a new, empty VM report.
	 */
	public VirtualMachineReport() {}
	/**
	 * Creates a new VM report for specified VM.
	 * @param name See {@link #setName}
	 */
	public VirtualMachineReport(String name) {
		setName(name);
	}
}
