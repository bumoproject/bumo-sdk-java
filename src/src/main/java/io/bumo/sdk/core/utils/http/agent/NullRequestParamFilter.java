package io.bumo.sdk.core.utils.http.agent;

import java.util.Properties;

import io.bumo.sdk.core.utils.http.HttpMethod;
import io.bumo.sdk.core.utils.http.RequestParamFilter;

public class NullRequestParamFilter implements RequestParamFilter{

    public static RequestParamFilter INSTANCE = new NullRequestParamFilter();

    private NullRequestParamFilter(){
    }

    @Override
    public void filter(HttpMethod requestMethod, Properties requestParams){
    }

}
