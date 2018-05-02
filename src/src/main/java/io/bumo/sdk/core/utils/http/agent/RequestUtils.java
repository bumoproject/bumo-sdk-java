package io.bumo.sdk.core.utils.http.agent;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public abstract class RequestUtils{

    @SuppressWarnings("unchecked")
	public static List<NameValuePair> createQueryParameters(Properties queryParams){
        if (queryParams == null || queryParams.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        for (String propName : queryParams.stringPropertyNames()) {
            params.add(new BasicNameValuePair(propName, queryParams.getProperty(propName)));
        }
        return params;
    }
}
