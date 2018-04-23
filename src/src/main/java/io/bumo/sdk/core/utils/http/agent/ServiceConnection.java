package io.bumo.sdk.core.utils.http.agent;

import java.io.Closeable;

public interface ServiceConnection extends Closeable {
	
	ServiceEndpoint getEndpoint();
	
	@Override
	void close();
	
}
