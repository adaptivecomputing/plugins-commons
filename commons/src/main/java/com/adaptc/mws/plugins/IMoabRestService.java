package com.adaptc.mws.plugins;

import java.util.Date;
import java.util.Map;
import groovy.lang.Closure;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

/**
 * The Moab REST service gives easy access to all supported REST resources
 * in Moab Web Services.  The {@link #isAPIVersionSupported(int)} method may
 * also be used to determine which API versions may be used for the current
 * running instance of MWS.
 * <p>
 * Methods are provided to perform each HTTP method:
 * GET, POST, PUT, and DELETE.  Several variations are given of each method
 * in order to utilize simpler or more complicated requests.  Each operation 
 * is performed internally.  In other words, no external network
 * connection is utilized to perform these calls.
 * <p>
 * The methods for each HTTP method may be called as follows (using the post
 * method as an example):
 * <pre>
 * // String url
 * moabRestService.post("/rest/jobs")
 * // Map options, String url
 * moabRestService.post("/rest/jobs", hooks:true)
 * // String url, Closure data
 * moabRestService.post("/rest/jobs") {
 *     [data:"here"]
 * }
 * // Map options, String url, Closure data
 * moabRestService.post("/rest/jobs", hooks:true) {
 *     [data:"here"]
 * }
 * </pre>
 * <p>
 * For more information on how to use this service, see the MWS User Guide
 * section on Moab Rest Service.
 * @author bsaville
 */
public interface IMoabRestService {
	/**
	 * Returns whether or not the specified API version is supported by this version of Moab Web Services.
	 * @param apiVersion The API version as an integer (i.e. 1, 2, etc)
	 * @return True if the API version is supported, false if configuration or MWS version prevents the API version from being used
	 */
	boolean isAPIVersionSupported(int apiVersion);

	/**
	 * Converts an MWS specific date string into an actual Date object.  This may be used to convert the date output
	 * from Moab Rest Service's {@link #get} methods.
	 * @param dateString The date string to convert into a date
	 * @return A converted date object
	 */
	Date convertDateString(String dateString);

	/**
	 * Shortcut for calling {@link #get(Map, String, Closure)} with 
	 * no options or data.
	 * @param url The URL to call
	 * @return The response result
	 */
	MoabRestResponse get(String url);
	/**
	 * Shortcut for calling {@link #get(Map, String, Closure)} with 
	 * no data.
	 * @param url The URL to call
	 * @return The response result
	 */
	MoabRestResponse get(Map<String, ?> options, String url);
	/**
	 * Shortcut for calling {@link #get(Map, String, Closure)} with 
	 * no options.
	 * @param url The URL to call
	 * @return The response result
	 */
	MoabRestResponse get(String url, Closure<?> data);
	/**
	 * Performs an internal HTTP GET operation for the requested URL with
	 * optional options and/or data.  Valid options and data types
	 * are documented in {@link IMoabRestService}.
	 * @param options Map of options to use for the request
	 * @param url The URL to call RESTfully
	 * @param data A closure which returns a valid request body
	 * @return The response result
	 */
	MoabRestResponse get(Map<String, ?> options, String url, Closure<?> data);
	
	/**
	 * Shortcut for calling {@link #put(Map, String, Closure)} with 
	 * no options or data.
	 * @param url The URL to call
	 * @return The response result
	 */
	MoabRestResponse put(String url);
	/**
	 * Shortcut for calling {@link #put(Map, String, Closure)} with 
	 * no data.
	 * @param url The URL to call
	 * @return The response result
	 */
	MoabRestResponse put(Map<String, ?> options, String url);
	/**
	 * Shortcut for calling {@link #put(Map, String, Closure)} with 
	 * no options.
	 * @param url The URL to call
	 * @return The response result
	 */
	MoabRestResponse put(String url, Closure<?> data);
	/**
	 * Performs an internal HTTP PUT operation for the requested URL with
	 * optional options and/or data.  Valid options and data types
	 * are documented in {@link IMoabRestService}.
	 * @param options Map of options to use for the request
	 * @param url The URL to call
	 * @param data A closure which returns a valid request body
	 * @return The response result
	 */
	MoabRestResponse put(Map<String, ?> options, String url, Closure<?> data);
	
	/**
	 * Shortcut for calling {@link #post(Map, String, Closure)} with 
	 * no options or data.
	 * @param url The URL to call
	 * @return The response result
	 */
	MoabRestResponse post(String url);
	/**
	 * Shortcut for calling {@link #post(Map, String, Closure)} with 
	 * no data.
	 * @param url The URL to call
	 * @return The response result
	 */
	MoabRestResponse post(Map<String, ?> options, String url);
	/**
	 * Shortcut for calling {@link #post(Map, String, Closure)} with 
	 * no options.
	 * @param url The URL to call
	 * @return The response result
	 */
	MoabRestResponse post(String url, Closure<?> data);
	/**
	 * Performs an internal HTTP POST operation for the requested URL with
	 * optional options and/or data.  Valid options and data types
	 * are documented in {@link IMoabRestService}.
	 * @param options Map of options to use for the request
	 * @param url The URL to call
	 * @param data A closure which returns a valid request body
	 * @return The response result
	 */
	MoabRestResponse post(Map<String, ?> options, String url, Closure<?> data);
	
	/**
	 * Shortcut for calling {@link #delete(Map, String, Closure)} with 
	 * no options or data.
	 * @param url The URL to call
	 * @return The response result
	 */
	MoabRestResponse delete(String url);
	/**
	 * Shortcut for calling {@link #delete(Map, String, Closure)} with 
	 * no data.
	 * @param url The URL to call
	 * @return The response result
	 */
	MoabRestResponse delete(Map<String, ?> options, String url);
	/**
	 * Shortcut for calling {@link #delete(Map, String, Closure)} with 
	 * no options.
	 * @param url The URL to call
	 * @return The response result
	 */
	MoabRestResponse delete(String url, Closure<?> data);
	/**
	 * Performs an internal HTTP DELETE operation for the requested URL with
	 * optional options and/or data.  Valid options and data types
	 * are documented in {@link IMoabRestService}.
	 * @param options Map of options to use for the request
	 * @param url The URL to call
	 * @param data A closure which returns a valid request body
	 * @return The response result
	 */
	MoabRestResponse delete(Map<String, ?> options, String url, Closure<?> data);
}
