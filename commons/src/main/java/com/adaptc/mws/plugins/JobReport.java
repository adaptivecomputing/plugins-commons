package com.adaptc.mws.plugins;

import java.util.*;

/**
 * A job report consists of values for all of the known, changed properties for a specific job
 * resource.  This typically represents changes in a specific time period (ie between the last
 * poll and the current poll), but can also represent all known information concerning a resource
 * (ie the first time poll is run).
 * <p/>
 * For more information on reporting resources, see the Plugin Reporting section in the
 * MWS documentation.
 * @author bsaville
 */
public class JobReport {
	/**
	 * The job's name.
	 */
	private String name;
	/**
	 * The user-specified name of the job.
	 */
	private String customName;
	/**
	 * The job's current state.
	 */
	private JobReportState state;
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
	 * The job's account.
	 */
	private String account;
	/**
	 * The job's command line arguments.
	 */
	private String commandLineArguments;
	/**
	 * The job's RM extension string.
	 */
	private String extension;
	/**
	 * The date that the job completed.
	 */
	private Date completedDate;
	/**
	 * The latest date that the job should be completed.
	 */
	private Date deadlineDate;
	/**
	 * The job's environment variables.
	 */
	private Map<String, String> environmentVariables = new HashMap<String, String>();
	/**
	 * The job's standard error file path.
	 */
	private String standardErrorFilePath;
	/**
	 * The job's executable command file.
	 */
	private String commandFile;
	/**
	 * The completion code of the job.
	 */
	private Integer completionCode;
	/**
	 * The job's flags.
	 */
	private List<JobReportFlag> flags = new ArrayList<JobReportFlag>();
	/**
	 * The job's group.
	 */
	private String group;
	/**
	 * The job's requested node names.
	 */
	private List<String> nodesRequested = new ArrayList<String>();
	/**
	 * The job's standard input file path.
	 */
	private String standardInputFilePath;
	/**
	 * The job's initial working directory.
	 */
	private String initialWorkingDirectory;
	/**
	 * The job's requirements list.  This may be treated as a single {@link JobReportRequirement}.
	 */
	private JobReportRequirementsList requirements = new JobReportRequirementsList();
	/**
	 * The job's standard output file path.
	 */
	private String standardOutputFilePath;
	/**
	 * The job's partition access list.
	 */
	private List<String> partitionAccessList = new ArrayList<String>();
	/**
	 * The job's system priority.
	 */
	private Long systemPriority;
	/**
	 * The job's QOS.
	 */
	private String qos;
	/**
	 * The date at which the job was submitted.
	 */
	private Date submitDate;
	/**
	 * The identifier of the reservation requested for the job.
	 */
	private String reservationRequested;
	/**
	 * The job's earliest start date.
	 */
	private Date earliestStartDate;
	/**
	 * The job's actual start date.
	 */
	private Date startDate;
	/**
	 * The job's suspend duration.
	 */
	private Long durationSuspended;
	/**
	 * The job's user.
	 */
	private String user;
	/**
	 * The requested duration that the job should run.
	 */
	private Long duration;
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
	 * Sets the unique identifier for the job, preserving the case specified.
	 * @see #name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @see #customName
	 */
	public String getCustomName() {
		return customName;
	}
	/**
	 * @see #customName
	 */
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	/**
	 * @see #state
	 */
	public JobReportState getState() {
		return state;
	}
	/**
	 * @see #state
	 */
	public void setState(JobReportState state) {
		this.state = state;
	}
	/**
	 * Calls {@link JobReportState#parse(String)} to set the {@link #state} field.
	 * @see #state
	 */
	public void setState(String state) {
		this.state = JobReportState.parse(state);
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
	 * @see #account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @see #account
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @see #commandLineArguments
	 */
	public String getCommandLineArguments() {
		return commandLineArguments;
	}
	/**
	 * @see #commandLineArguments
	 */
	public void setCommandLineArguments(String commandLineArguments) {
		this.commandLineArguments = commandLineArguments;
	}
	/**
	 * @see #extension
	 */
	public String getExtension() {
		return extension;
	}
	/**
	 * @see #extension
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}
	/**
	 * @see #completedDate
	 */
	public Date getCompletedDate() {
		return completedDate;
	}
	/**
	 * @see #completedDate
	 */
	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}
	/**
	 * @see #completedDate
	 */
	public void setCompletedDate(String completionDate) {
		try {
			this.completedDate = PluginConstants.STANDARD_DATE_TIME_FORMATTER.parseDateTime(completionDate).toDate();
		} catch(IllegalArgumentException e) {
			this.completedDate = null;
		} catch(NullPointerException e) {
			this.completedDate = null;
		}
	}
	/**
	 * @see #deadlineDate
	 */
	public Date getDeadlineDate() {
		return deadlineDate;
	}
	/**
	 * @see #deadlineDate
	 */
	public void setDeadlineDate(Date deadlineDate) {
		this.deadlineDate = deadlineDate;
	}
	/**
	 * @see #deadlineDate
	 */
	public void setDeadlineDate(String latestCompletedDateRequested) {
		try {
			this.deadlineDate = PluginConstants.STANDARD_DATE_TIME_FORMATTER.parseDateTime(latestCompletedDateRequested).toDate();
		} catch(IllegalArgumentException e) {
			this.deadlineDate = null;
		} catch(NullPointerException e) {
			this.deadlineDate = null;
		}
	}
	/**
	 * @see #environmentVariables
	 */
	public Map<String, String> getEnvironmentVariables() {
		return environmentVariables;
	}
	/**
	 * @see #environmentVariables
	 */
	public void setEnvironmentVariables(Map<String, String> environmentVariables) {
		this.environmentVariables = environmentVariables;
	}
	/**
	 * @see #standardErrorFilePath
	 */
	public String getStandardErrorFilePath() {
		return standardErrorFilePath;
	}
	/**
	 * @see #standardErrorFilePath
	 */
	public void setStandardErrorFilePath(String standardErrorFilePath) {
		this.standardErrorFilePath = standardErrorFilePath;
	}
	/**
	 * @see #commandFile
	 */
	public String getCommandFile() {
		return commandFile;
	}
	/**
	 * @see #commandFile
	 */
	public void setCommandFile(String commandFile) {
		this.commandFile = commandFile;
	}
	/**
	 * @see #completionCode
	 */
	public Integer getCompletionCode() {
		return completionCode;
	}
	/**
	 * @see #completionCode
	 */
	public void setCompletionCode(Integer completionCode) {
		this.completionCode = completionCode;
	}
	/**
	 * @see #flags
	 */
	public List<JobReportFlag> getFlags() {
		return flags;
	}
	/**
	 * @see #flags
	 */
	public void setFlags(List<JobReportFlag> flags) {
		this.flags = flags;
	}
	/**
	 * @see #group
	 */
	public String getGroup() {
		return group;
	}
	/**
	 * @see #group
	 */
	public void setGroup(String group) {
		this.group = group;
	}
	/**
	 * @see #nodesRequested
	 */
	public List<String> getNodesRequested() {
		return nodesRequested;
	}
	/**
	 * @see #nodesRequested
	 */
	public void setNodesRequested(List<String> nodesRequested) {
		this.nodesRequested = nodesRequested;
	}
	/**
	 * @see #standardInputFilePath
	 */
	public String getStandardInputFilePath() {
		return standardInputFilePath;
	}
	/**
	 * @see #standardInputFilePath
	 */
	public void setStandardInputFilePath(String standardInputFilePath) {
		this.standardInputFilePath = standardInputFilePath;
	}
	/**
	 * @see #initialWorkingDirectory
	 */
	public String getInitialWorkingDirectory() {
		return initialWorkingDirectory;
	}
	/**
	 * @see #initialWorkingDirectory
	 */
	public void setInitialWorkingDirectory(String initialWorkingDirectory) {
		this.initialWorkingDirectory = initialWorkingDirectory;
	}
	/**
	 * Retrieves the job requirements.  This list may be manipulated as
	 * a {@link IJobReportRequirement} itself.
	 * @see #requirements
	 */
	public JobReportRequirementsList getRequirements() {
		return requirements;
	}
	/**
	 * @see #requirements
	 */
	public void setRequirements(JobReportRequirementsList requirements) {
		this.requirements = requirements;
	}
	/**
	 * @see #standardOutputFilePath
	 */
	public String getStandardOutputFilePath() {
		return standardOutputFilePath;
	}
	/**
	 * @see #standardOutputFilePath
	 */
	public void setStandardOutputFilePath(String standardOutputFilePath) {
		this.standardOutputFilePath = standardOutputFilePath;
	}
	/**
	 * @see #partitionAccessList
	 */
	public List<String> getPartitionAccessList() {
		return partitionAccessList;
	}
	/**
	 * @see #partitionAccessList
	 */
	public void setPartitionAccessList(List<String> partitionAccessList) {
		this.partitionAccessList = partitionAccessList;
	}
	/**
	 * @see #systemPriority
	 */
	public Long getSystemPriority() {
		return systemPriority;
	}
	/**
	 * @see #systemPriority
	 */
	public void setSystemPriority(Long systemPriority) {
		this.systemPriority = systemPriority;
	}
	/**
	 * @see #qos
	 */
	public String getQos() {
		return qos;
	}
	/**
	 * @see #qos
	 */
	public void setQos(String qos) {
		this.qos = qos;
	}
	/**
	 * @see #submitDate
	 */
	public Date getSubmitDate() {
		return submitDate;
	}
	/**
	 * @see #submitDate
	 */
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	/**
	 * @see #submitDate
	 */
	public void setSubmitDate(String submitDate) {
		try {
			this.submitDate = PluginConstants.STANDARD_DATE_TIME_FORMATTER.parseDateTime(submitDate).toDate();
		} catch(IllegalArgumentException e) {
			this.submitDate = null;
		} catch(NullPointerException e) {
			this.submitDate = null;
		}
	}
	/**
	 * @see #reservationRequested
	 */
	public String getReservationRequested() {
		return reservationRequested;
	}
	/**
	 * @see #reservationRequested
	 */
	public void setReservationRequested(String reservationRequested) {
		this.reservationRequested = reservationRequested;
	}
	/**
	 * @see #earliestStartDate
	 */
	public Date getEarliestStartDate() {
		return earliestStartDate;
	}
	/**
	 * @see #earliestStartDate
	 */
	public void setEarliestStartDate(Date earliestStartDate) {
		this.earliestStartDate = earliestStartDate;
	}
	/**
	 * @see #earliestStartDate
	 */
	public void setEarliestStartDate(String earliestStartDate) {
		try {
			this.earliestStartDate = PluginConstants.STANDARD_DATE_TIME_FORMATTER.parseDateTime(earliestStartDate).toDate();
		} catch(IllegalArgumentException e) {
			this.earliestStartDate = null;
		} catch(NullPointerException e) {
			this.earliestStartDate = null;
		}
	}
	/**
	 * @see #startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @see #startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @see #startDate
	 */
	public void setStartDate(String startDate) {
		try {
			this.startDate = PluginConstants.STANDARD_DATE_TIME_FORMATTER.parseDateTime(startDate).toDate();
		} catch(IllegalArgumentException e) {
			this.startDate = null;
		} catch(NullPointerException e) {
			this.startDate = null;
		}
	}
	/**
	 * @see #durationSuspended
	 */
	public Long getDurationSuspended() {
		return durationSuspended;
	}
	/**
	 * @see #durationSuspended
	 */
	public void setDurationSuspended(Long durationSuspended) {
		this.durationSuspended = durationSuspended;
	}
	/**
	 * @see #user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @see #user
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * @see #duration
	 */
	public Long getDuration() {
		return duration;
	}
	/**
	 * @see #duration
	 */
	public void setDuration(Long duration) {
		this.duration = duration;
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
	 * Creates a new, empty job report.
	 */
	public JobReport() {}
	/**
	 * Creates a new job report for specified job.
	 * @param name See {@link #setName}
	 */
	public JobReport(String name) {
		setName(name);
	}
}
