package org.bumo.sdk.core.utils.http.agent;

/**
 * AuthorizationHeaderResovler It is a policy interface to generate the authentication head based on the actual request
 * 
 * @author haiq
 *
 */
public interface AuthorizationHeaderResovler {
	
	public AuthorizationHeader generateHeader(ServiceRequest request);
	
}
