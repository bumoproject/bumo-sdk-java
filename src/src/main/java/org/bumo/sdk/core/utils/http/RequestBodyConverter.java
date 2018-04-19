package org.bumo.sdk.core.utils.http;

import java.io.InputStream;

/**
 * Parameter converter
 * 
 * It defines how to transform a parameter from a specific type to an input stream for sending HTTP request bodies
 * 
 * @author bumo
 *
 */
public interface RequestBodyConverter {
	
	InputStream toInputStream(Object param);
	
}
