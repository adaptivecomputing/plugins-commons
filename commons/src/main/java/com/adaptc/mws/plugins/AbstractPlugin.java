package com.adaptc.mws.plugins;

import java.util.Map;
import java.util.List;

import org.apache.commons.logging.Log;

/**
 * Optional base class for all plugin types and therefore plugin instances which extends
 * {@link AbstractPluginInfo}.  Contains method definitions to facilitate creating
 * new plugin types.
 * @author bsaville
 */
public abstract class AbstractPlugin extends AbstractPluginInfo {
	// Extra info
	/**
	 * Returns a log that can be used throughout the Plugin.
	 * @return A logging instance
	 */
	public Log getLog() {
		return null;
	}

	/**
	 * Returns an i18n message from any file ending in "-messages.properties" included in the project JAR file.
	 * @param parameters The parameters used to retrieve the message, such as code, error, default, and args.
	 * @return The resulting i18n message
	 */
	public String message(Map parameters) {
		return null;
	}
	
	// Lifecycle control
	/**
	 * Hook to do any initialization needed before the plugin starts.  Can be empty.
	 */
	public void beforeStart() {}
	/**
	 * Starts the Plugin, including polling if enabled.
	 * @throws PluginStartException If there is a problem while starting the Plugin
	 */
	public void start() throws PluginStartException {}
	/**
	 * Hook to do any post-start logic.  Can be empty.
	 */
	public void afterStart() {}
	/**
	 * Hook to start any processes needed to teardown the plugin before it stops.  Can be empty.
	 */
	public void beforeStop() {}
	/**
	 * Stops the Plugin, including polling if started.
	 * @throws PluginStopException If there is a problem while stopping the Plugin
	 */
	public void stop() throws PluginStopException {}
	/**
	 * Hook to do any post-stop teardown of the plugin after it stops.  Can be empty.
	 */
	public void afterStop() {}
	/**
	 * Verifies the configuration of the plugin and performs any initial setup needed each time the configuration
	 * is loaded or changed.  This is called when the {@link IPluginControlService#start} method or the
	 * {@link #start()} method is called, as well as any time the configuration of the Plugin
	 * is modified.
	 * 
	 * @throws InvalidPluginConfigurationException Thrown when configuration is invalid.  This exception should contain error messages.
	 *
	 * @see InvalidPluginConfigurationException#InvalidPluginConfigurationException(String)
	 * @see InvalidPluginConfigurationException#InvalidPluginConfigurationException(List)
	 */
	public void configure() throws InvalidPluginConfigurationException {}

	// plugin events
	/**
	 * Cancels the specified job.
	 * @param jobName The name of the job to requeue
	 * @return True if successful, false if an error occurred
	 */
	public boolean jobCancel(String jobName) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Modifies the specified job with the attributes given.
	 * @param attributes The attributes to modify on the job
	 * @param jobName The name of the job to modify
	 * @param modifyMode The modification mode for the operation
	 * @return True if successful, false if an error occurred
	 */
	public boolean jobModify(String jobName, Map<String, Object> attributes, ModifyMode modifyMode) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Requeues the specified job.
	 * @param jobName The name of the job to requeue
	 * @return True if successful, false if an error occurred
	 */
	public boolean jobRequeue(String jobName) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Resumes the specified job.
	 * @param jobName The name of the job to resume
	 * @return True if successful, false if an error occurred
	 */
	public boolean jobResume(String jobName) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Starts the specified job with a list of allocated nodes and username.
	 * @param jobName The name of the job to start
	 * @param nodes The nodes allocated to the job (also called the task list)
	 * @param username The user starting the job
	 * @return True if successful, false if an error occurred
	 */
	public boolean jobStart(String jobName, List<String> nodes, String username) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Submits a new job specified by the attributes given.  The first plugin to return a valid ID will cause MWS to
	 * not call any further plugins for the job submission.
	 * @param job The job as a map, with field names and structure matching the MWS job API (v2 and greater)
	 * @param submissionFlags Flags from the Moab submission
	 * @return A job ID if successful, null or empty string if an error occurred
	 */
	public String jobSubmit(Map<String, Object> job, String submissionFlags) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Suspends the specified job.
	 * @param jobName The name of the job to suspend
	 * @return True if successful, false if an error occurred
	 */
	public boolean jobSuspend(String jobName) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Modifies the specified node(s) with the attributes given.
	 * @param attributes The attributes to modify on the node(s)
	 * @param nodes The name(s) of the node(s) to modify
	 * @param modifyMode The modification mode for the operation
	 * @return True if successful, false if an error occurred
	 */
	public boolean nodeModify(List<String> nodes, Map<String, String> attributes, ModifyMode modifyMode) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Changes the power state of the specified node(s).
	 * @param state The new requested power state of the node(s)
	 * @param nodes The name(s) of the node(s) to modify
	 * @return True if successful, false if an error occurred
	 */
	public boolean nodePower(List<String> nodes, NodeReportPower state) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Changes the power state of the specified VM(s).
	 * @param state The new requested power state of the node(s)
	 * @param virtualMachines The name(s) of the VM(s) to modify
	 * @return True if successful, false if an error occurred
	 */
	public boolean virtualMachinePower(List<String> virtualMachines, NodeReportPower state) {
		throw new UnsupportedOperationException();
	}
}
