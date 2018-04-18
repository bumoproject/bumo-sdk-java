package org.bumo.sdk.core.utils.http.agent;

import org.bumo.sdk.core.utils.http.HttpServiceException;

public class IllegalHttpServiceDefinitionException extends HttpServiceException{

	private static final long serialVersionUID = -6866487415383367958L;

	public IllegalHttpServiceDefinitionException() {
	}
	
	public IllegalHttpServiceDefinitionException(String message) {
		super(message);
	}
	
	public IllegalHttpServiceDefinitionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}
