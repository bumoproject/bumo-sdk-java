package org.bumo.sdk.core.utils.http.agent;

import java.util.Properties;

import org.bumo.sdk.core.utils.http.HttpMethod;
import org.bumo.sdk.core.utils.http.RequestParamFilter;

public class NullRequestParamFilter implements RequestParamFilter{

    public static RequestParamFilter INSTANCE = new NullRequestParamFilter();

    private NullRequestParamFilter(){
    }

    @Override
    public void filter(HttpMethod requestMethod, Properties requestParams){
    }

}
