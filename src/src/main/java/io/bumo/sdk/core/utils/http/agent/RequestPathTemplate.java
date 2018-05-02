package io.bumo.sdk.core.utils.http.agent;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import io.bumo.sdk.core.utils.PathUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

class RequestPathTemplate{
    private ServiceEndpoint serviceEndpoint;
    private String servicePath;
    private String actionPath;

    public RequestPathTemplate(ServiceEndpoint serviceEndpoint, String servicePath, String actionPath){
        this.serviceEndpoint = serviceEndpoint;
        this.servicePath = PathUtils.standardize(servicePath);
        this.actionPath = PathUtils.standardize(actionPath);
    }


    /**
     * Update the request path
     *
     * @param pathVariableName
     * @param value
     */
    private static String updateActionPath(String actionPath, String pathVariableName, String value){
        String pathVarName = String.format("{%s}", pathVariableName);
        actionPath = actionPath.replace(pathVarName, value);
        return actionPath;
    }

    /**
     * Return the complete request URL
     *
     * @param pathParams  Path parameters
     * @param queryParams Query parameters
     * @return
     */
    public URI generateRequestURI(Map<String, String> pathParams, Properties queryParams,
                                  Charset encodingCharset){
        // Generation path
        String reallyActionPath = createActionPath(pathParams);
        String path = PathUtils.concatPaths(serviceEndpoint.getContextPath(), servicePath, reallyActionPath);
        path = PathUtils.absolute(path);

        // Generate query string
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setCharset(encodingCharset);
        if (serviceEndpoint.isHttps()) {
            uriBuilder.setScheme("https");
        } else {
            uriBuilder.setScheme("http");
        }

        uriBuilder.setHost(serviceEndpoint.getHost());
        uriBuilder.setPort(serviceEndpoint.getPort());
        uriBuilder.setPath(path.toString());
        List<NameValuePair> queryParameters = RequestUtils.createQueryParameters(queryParams);
        uriBuilder.setParameters(queryParameters);
        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private String createActionPath(Map<String, String> pathParams){
        String reallyActionPath = actionPath;
        if (pathParams != null) {
            for (Entry<String, String> pathParam : pathParams.entrySet()) {
                reallyActionPath = updateActionPath(reallyActionPath, pathParam.getKey(), pathParam.getValue());
            }
        }
        return reallyActionPath;
    }

}
