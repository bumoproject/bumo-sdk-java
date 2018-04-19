package org.bumo.sdk.core.utils.http;

/**
 * HttpServiceContext defines information about the context of HTTP service agent
 * 
 * @author bumo
 *
 */
public interface HttpServiceContext {

	/**
	 * Type of service interface
	 * @return
	 */
	Class<?> getServiceClasss();

	/**
	 * When a service agent instance is created, the binding object specified by the caller
	 * @return
	 */
	Object getProxyBindingData();

}
