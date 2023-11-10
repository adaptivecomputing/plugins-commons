package com.adaptc.mws.plugins;

/**
 * Signifies a single node attribute definition in Moab Workload Manager, including
 * both the {@link #getValue()} and {@link #getDisplayValue()} properties.
 * <p>
 * Neither property is required to send a valid attribute to MWM, but if a display value is
 * used, a value must also be present.  A value may be present without a display value.
 * The value should be used by jobs to request a certain attribute a certain value, while the
 * display value is used for display purposes only.
 * @author bsaville
 */
public class ReportAttribute {
	private String value;
	private String displayValue;

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
	 * Retrieves the display value related to the value of this attribute.
	 * @return The current value
	 */
	public String getDisplayValue() {
		return displayValue;
	}

	/**
	 * Sets the display value related to the value of this attribute.
	 * @param displayValue The value to set
	 */
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	/**
	 * Returns the value and display value as a string, such as "[value:val, displayValue:my value]".
	 * @return The total and available amounts in a human-readable string
	 */
	@Override
	public String toString() {
		return "[value:"+value+", displayValue:"+displayValue+"]";
	}
}
