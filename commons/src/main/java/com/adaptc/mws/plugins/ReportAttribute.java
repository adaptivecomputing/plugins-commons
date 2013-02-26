package com.adaptc.mws.plugins;

/**
 * Signifies a single node attribute definition in Moab Workload Manager, including
 * both the {@link #getValue()} and {@link #getDisplayName()} properties.
 * <p/>
 * Neither property is required to send a valid attribute to MWM, but if a display name is
 * used, a value must also be present.  A value may be present without a display name.
 * The value should be used by jobs to request a certain attribute a certain value, while the
 * display name is used for display purposes only.
 * @author bsaville
 */
public class ReportAttribute {
	private String value;
	private String displayName;

	/**
	 * Retrieves the value of this attribute, which is what must requested by a job.
	 * @return The current value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value of this attribute, which is what must be requested by a job.
	 * @param value The value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Retrieves the display name related to the value of this attribute.
	 * @return The current value
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the display name related to the value of this attribute.
	 * @param displayName The value to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Returns the value and display name as a string, such as "[value:val, displayName:my value]".
	 * @return The total and available amounts in a human-readable string
	 */
	@Override
	public String toString() {
		return "[value:"+value+", displayName:"+displayName+"]";
	}
}
