package com.adaptc.mws.plugins;

import java.util.List;

/**
 * <b>Storage reporting is currently in BETA, fields and operations may change without notice!</b><br />
 * The storage resource management service consists of methods to retrieve
 * and save storage reports.  These reports are consolidated and returned to Moab.
 * <p/>
 * For more information on how to use this service, see the MWS User Guide
 * section on the Storage RM Service.
 * @author bsaville
 */
public interface IStorageRMService {
	/**
	 * Retrieves a list of all storage reports which were given originally by this plugin.
	 * @return A list of all storage reports made by this plugin.
	 */
	public List<StorageReport> list();
	/**
	 * Saves a list of storage reports in the cache while at the same time clearing out any and all storage
	 * reports made previously by the calling plugin.  In effect, this replaces all storage reports
	 * made previously.  These will be reported to Moab through the Cluster Query.
	 * @param storageReports The list of storage reports to save.
	 */
	public void save(List<StorageReport> storageReports);
	/**
	 * Adds a list of storage reports to the cache without clearing out any storage reports.  This should be
	 * used to make incremental updates to the reports.  These will be reported to Moab through the
	 * Cluster Query.
	 * @param storageReports The list of storage reports to add.
	 */
	public void update(List<StorageReport> storageReports);
}
