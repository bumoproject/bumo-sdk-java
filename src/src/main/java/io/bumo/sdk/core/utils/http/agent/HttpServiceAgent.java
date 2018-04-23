package io.bumo.sdk.core.utils.http.agent;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

import io.bumo.sdk.core.utils.EmptyProperties;
import io.bumo.sdk.core.utils.http.HttpAction;
import io.bumo.sdk.core.utils.http.HttpMethod;
import io.bumo.sdk.core.utils.http.HttpService;
import io.bumo.sdk.core.utils.http.HttpServiceContext;
import io.bumo.sdk.core.utils.http.HttpServiceException;
import io.bumo.sdk.core.utils.http.HttpStatusException;
import io.bumo.sdk.core.utils.http.PathParam;
import io.bumo.sdk.core.utils.http.RequestBody;
import io.bumo.sdk.core.utils.http.RequestBodyConverter;
import io.bumo.sdk.core.utils.http.RequestParam;
import io.bumo.sdk.core.utils.http.RequestParamFilter;
import io.bumo.sdk.core.utils.http.RequestParamMap;
import io.bumo.sdk.core.utils.http.ResponseConverter;
import io.bumo.sdk.core.utils.http.ResponseConverterFactory;
import io.bumo.sdk.core.utils.http.StringConverter;
import io.bumo.sdk.core.utils.http.converters.NullResponseConverter;
import io.bumo.sdk.core.utils.http.converters.StringResponseConverter;
import io.bumo.sdk.core.utils.io.BytesUtils;
import io.bumo.sdk.core.utils.io.EmptyInputStream;
import io.bumo.sdk.core.utils.spring.BeanUtils;
import io.bumo.sdk.core.utils.spring.ClassUtils;
import io.bumo.sdk.core.utils.spring.ReflectionUtils;
import io.bumo.sdk.core.utils.spring.StringUtils;

/**
 * http Service agent
 *
 * @author haiq
 */
public class HttpServiceAgent{

    private static Map<Class<?>, HttpServiceAgent> serviceAgentMap = new ConcurrentHashMap<>();

    private Class<?> serviceClass;

    private ServiceEndpoint serviceEndpoint;

    private ResponseConverter defaultResponseConverter;

    private ResponseConverterFactory responseConverterFactory;

    private AuthorizationHeaderResovler authorizationHeaderResolver;

    private Map<Method, ServiceActionContext> actions = new HashMap<>();

    private HttpServiceAgent(Class<?> serviceClass, ServiceEndpoint serviceEndpoint,
                             AuthorizationHeaderResovler authResolver){
        this.serviceClass = serviceClass;
        this.serviceEndpoint = serviceEndpoint;
        this.authorizationHeaderResolver = authResolver;

        resolveService();
    }

    public static void clearMemoryCache(){
        serviceAgentMap.clear();
    }

    /**
     * Create a HTTP service proxy that maps the specified service interface
     *
     * @param serviceClass    Service interface type
     * @param serviceEndpoint Related settings connected to the service provider server
     * @return
     */
    public static <T> T createService(Class<T> serviceClass, ServiceEndpoint serviceEndpoint,
                                      RequestHeader... authorizationHeaders){
        return createService(serviceClass, serviceEndpoint, null, null, authorizationHeaders);
    }

    /**
     * Create a HTTP service proxy that maps the specified service interface
     *
     * @param serviceClass    The type of interface of the service
     * @param serviceEndpoint Service end point
     * @return
     */
    public static <T> T createService(Class<T> serviceClass, ServiceEndpoint serviceEndpoint){
        return createService(serviceClass, serviceEndpoint, (AuthorizationHeaderResovler) null);
    }

    /**
     * Create a HTTP service proxy that maps the specified service interface
     *
     * @param serviceClass                The type of interface of the service is defined
     * @param serviceEndpoint             Service end point
     * @param authorizationHeaderResolver Security authentication head parser
     * @return
     */
    public static <T> T createService(Class<T> serviceClass, ServiceEndpoint serviceEndpoint,
                                      AuthorizationHeaderResovler authorizationHeaderResolver){
        return createService(serviceClass, serviceEndpoint, null, authorizationHeaderResolver);
    }

    /**
     * Create a HTTP service proxy that maps the specified service interface
     *
     * @param serviceClass                The type of interface of the service is defined
     * @param serviceEndpoint             Service end point
     * @param connectionManager           Connection manager
     * @param authorizationHeaderResolver Security authenticated head parser
     * @param headers                     Ask for the head
     * @return
     */
    public static <T> T createService(Class<T> serviceClass, ServiceEndpoint serviceEndpoint,
                                      ServiceConnectionManager connectionManager, AuthorizationHeaderResovler authorizationHeaderResolver,
                                      RequestHeader... headers){
        ServiceConnection connection = null;
        if (connectionManager == null) {
            connection = ServiceConnectionManager.connect(serviceEndpoint);
        } else {
            connection = connectionManager.create(serviceEndpoint);
        }
        return createService(serviceClass, connection, authorizationHeaderResolver, headers);
    }

    /**
     * Create a HTTP service proxy that maps the specified service interface；
     *
     * @param serviceClass                The type of interface of the service is defined
     * @param authorizationHeaderResolver Security authenticated head parser
     * @param headers                     Ask for the head
     * @return
     */
    public static <T> T createService(Class<T> serviceClass, ServiceConnection connection,
                                      AuthorizationHeaderResovler authorizationHeaderResolver, RequestHeader... headers){
        return createService(serviceClass, connection, authorizationHeaderResolver, headers, null);
    }

    /**
     * Create a HTTP service proxy that maps the specified service interface
     *
     * @param serviceClass                The type of interface of the service is defined
     * @param authorizationHeaderResolver Security authenticated head parser
     * @param headers                     Ask for the head
     * @param bindingData                 A binding object specified by the caller<br>
     *                                    This object will be associated to the HttpServiceContext; the caller can pass some data objects through this object to some of the processing components of the calling process, such as {@link ResponseConverter}
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T createService(Class<T> serviceClass, ServiceConnection connection,
                                      AuthorizationHeaderResovler authorizationHeaderResolver, RequestHeader[] headers, Object bindingData){
        if (serviceClass == null) {
            throw new IllegalArgumentException("Service class is null!");
        }
        if (!(connection instanceof HttpServiceConnection)) {
            throw new IllegalArgumentException(
                    "Illegal service connection! It must be created by the ServiceConnectionManager!");
        }
        HttpServiceConnection httpConnection = (HttpServiceConnection) connection;
        // Avoid repeated parsing of the same type of service
        HttpServiceAgent agent = serviceAgentMap.get(serviceClass);
        if (agent == null) {
            synchronized (serviceClass) {
                agent = serviceAgentMap.get(serviceClass);
                if (agent == null) {
                    agent = new HttpServiceAgent(serviceClass, connection.getEndpoint(), authorizationHeaderResolver);
                    serviceAgentMap.put(serviceClass, agent);
                }
            }
        }

        // CloseableHttpClient httpClient = createHttpClient(serviceEndpoint,
        // connectionManager);

        ServiceInvocationHandler invocationHandler = new ServiceInvocationHandler(agent, httpConnection, headers, bindingData);

        T serviceProxy = (T) Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class<?>[] {serviceClass},
                invocationHandler);
        return serviceProxy;
    }

    private void resolveService(){
        // Processing service path
        HttpService serviceAnno = serviceClass.getAnnotation(HttpService.class);
        if (serviceAnno == null) {
            throw new IllegalHttpServiceDefinitionException(
                    "The specific service was not tag with HttpService annotation!");
        }
        String servicePath = serviceAnno.path();
        servicePath = StringUtils.cleanPath(servicePath);
        if (StringUtils.isEmpty(servicePath)) {
            throw new IllegalHttpServiceDefinitionException(
                    "Illegal path or no path was specified for the HttpService!-- path=" + serviceAnno.path());
        }
        // Create the default reply converter for the service
        Class<?> defaultResponseConverterClazz = serviceAnno.defaultResponseConverter();
        if (defaultResponseConverterClazz != null && defaultResponseConverterClazz != ResponseConverter.class) {
            if (ResponseConverter.class.isAssignableFrom(defaultResponseConverterClazz)) {
                defaultResponseConverter = (ResponseConverter) BeanUtils.instantiate(defaultResponseConverterClazz);
            } else {
                throw new IllegalHttpServiceDefinitionException(
                        "The specified service level default response converter doesn't implement the interface "
                                + ResponseConverter.class.getName() + "!");
            }
        }
        Class<?> responseConverterFactoryClazz = serviceAnno.responseConverterFactory();
        if (responseConverterFactoryClazz != null && responseConverterFactoryClazz != ResponseConverterFactory.class) {
            if (ResponseConverterFactory.class.isAssignableFrom(responseConverterFactoryClazz)) {
                this.responseConverterFactory = (ResponseConverterFactory) BeanUtils
                        .instantiate(responseConverterFactoryClazz);
            } else {
                throw new IllegalHttpServiceDefinitionException(
                        "The specified service level response converter factory doesn't implement the interface "
                                + ResponseConverterFactory.class.getName() + "!");
            }

        }

        // Parsing service operations
        Method[] mths = ReflectionUtils.getAllDeclaredMethods(serviceClass);
        for (Method mth : mths) {
            ServiceActionContext actionContext = resolveAction(serviceEndpoint, mth, servicePath);
            actions.put(mth, actionContext);
        }
    }

    private ServiceActionContext resolveAction(ServiceEndpoint serviceEndpoint, Method mth, String servicePath){
        // Generating path template
        HttpAction actionAnno = mth.getAnnotation(HttpAction.class);
        String actionPath = StringUtils.cleanPath(actionAnno.path());
        if (StringUtils.isEmpty(actionPath)) {
            actionPath = mth.getName();
        }
        RequestPathTemplate pathTemplate = new RequestPathTemplate(serviceEndpoint, servicePath, actionPath);

        // Check the request to your method
        if (actionAnno.method() == null) {
            throw new IllegalHttpServiceDefinitionException("The http method of action was not specified!");
        }

        RequestParamFilter reqParamFilter = createRequestParamFilter(actionAnno);
        ResponseConverter responseConverter = createResponseConverter(actionAnno, mth);

        // Obtaining parameter definition
        // In parameter list, RequestBody can only define one at most
        RequestBodyResolver bodyResolver = null;
        Class<?>[] paramTypes = mth.getParameterTypes();
        Annotation[][] paramAnnos = mth.getParameterAnnotations();

        List<ArgDefEntry<RequestParam>> reqParamAnnos = new LinkedList<>();
        List<ArgDefEntry<RequestParamMap>> reqParamMapAnnos = new LinkedList<>();
        List<ArgDefEntry<PathParam>> pathParamAnnos = new LinkedList<>();
        for (int i = 0; i < paramTypes.length; i++) {
            RequestBody reqBodyAnno = findAnnotation(RequestBody.class, paramAnnos[i]);
            RequestParam reqParamAnno = findAnnotation(RequestParam.class, paramAnnos[i]);
            RequestParamMap reqParamsAnno = findAnnotation(RequestParamMap.class, paramAnnos[i]);
            PathParam pathParamAnno = findAnnotation(PathParam.class, paramAnnos[i]);
            if (hasConflictiveAnnotation(reqBodyAnno, reqParamAnno, reqParamsAnno, pathParamAnno)) {
                // Definition of existence of conflict
                throw new IllegalHttpServiceDefinitionException(
                        "The argument[" + i + "] of action has conflictive definition!");
            }
            if (bodyResolver != null && reqBodyAnno != null) {
                throw new IllegalHttpServiceDefinitionException("Define more than one request body for the action!");
            }
            if (reqBodyAnno != null) {
                bodyResolver = createBodyResolver(new ArgDefEntry<>(i, paramTypes[i], reqBodyAnno));
            }
            if (reqParamAnno != null) {
                reqParamAnnos.add(new ArgDefEntry<>(i, paramTypes[i], reqParamAnno));
            }
            if (reqParamsAnno != null) {
                reqParamMapAnnos.add(new ArgDefEntry<>(i, paramTypes[i], reqParamsAnno));
            }
            if (pathParamAnno != null) {
                pathParamAnnos.add(new ArgDefEntry<>(i, paramTypes[i], pathParamAnno));
            }
        }
        RequestParamResolver reqParamResolver = createRequestParamResolver(reqParamAnnos, reqParamMapAnnos);
        PathParamResolver pathParamResolver = createPathParamResolver(pathParamAnnos);
        if (bodyResolver == null) {
            bodyResolver = RequestBodyResolvers.NULL_BODY_RESOLVER;
        }

        // Get the exception list of the declaration
        Class<?>[] thrownExceptionTypes = mth.getExceptionTypes();

        ServiceActionContext actionContext = new ServiceActionContext(mth, actionAnno.method(), pathTemplate,
                pathParamResolver, reqParamFilter, reqParamResolver, bodyResolver, responseConverter,
                thrownExceptionTypes, actionAnno.resolveContentOnHttpError());
        return actionContext;
    }

    @SuppressWarnings("unchecked")
	private static <T> T findAnnotation(Class<T> clazz, Annotation[] annos){
        for (Annotation annotation : annos) {
            if (clazz.isAssignableFrom(annotation.getClass())) {
                return (T) annotation;
            }
        }
        return null;
    }

    private RequestParamFilter createRequestParamFilter(HttpAction actionDef){
        Class<?> reqParamFilterClass = actionDef.requestParamFilter();
        if (reqParamFilterClass == null || reqParamFilterClass == RequestParamFilter.class) {
            return NullRequestParamFilter.INSTANCE;
        }
        if (RequestParamFilter.class.isAssignableFrom(reqParamFilterClass)) {
            return (RequestParamFilter) BeanUtils.instantiate(reqParamFilterClass);
        } else {
            throw new IllegalHttpServiceDefinitionException(
                    "The specified RequestParamFilter doesn't implement the interface "
                            + RequestParamFilter.class.getName() + "!");
        }
    }

    /**
     * Create a reply result converter
     *
     * @param actionDef
     * @return
     */
    private ResponseConverter createResponseConverter(HttpAction actionDef, Method mth){
        Class<?> retnClazz = mth.getReturnType();
        if (Void.class.equals(retnClazz)) {
            return NullResponseConverter.INSTANCE;
        }
        Class<?> respConverterClass = actionDef.responseConverter();
        if (respConverterClass == null || respConverterClass == ResponseConverter.class) {
            // A reply converter that does not set the method level
            if (defaultResponseConverter != null) {
                // If there is no method level reply converter and service level default recovery converter is set, the service level default recovery converter is applied
                return defaultResponseConverter;
            }
            if (responseConverterFactory != null) {
                return responseConverterFactory.createResponseConverter(actionDef, mth);
            }
        }
        if (respConverterClass != null && respConverterClass != ResponseConverter.class) {
            if (ResponseConverter.class.isAssignableFrom(respConverterClass)) {
                return (ResponseConverter) BeanUtils.instantiate(respConverterClass);
            } else {
                throw new IllegalHttpServiceDefinitionException(
                        "The specified response converter doesn't implement the interface "
                                + ResponseConverter.class.getName() + "!");
            }
        }
        // create default response converter;
        return DefaultResponseConverterFactory.INSTANCE.createResponseConverter(actionDef, mth);

        // if (byte[].class == retnClazz) {
        // return ByteArrayResponseConverter.INSTANCE;
        // }
        // if (String.class == retnClazz) {
        // return StringResponseConverter.INSTANCE;
        // }
        // // TODO:Unprocessed basic types, input and output streams
        // return new JsonResponseConverter(retnClazz);
    }

    /**
     * Create a path parameter parser
     *
     * @param pathParamAnnos
     * @return
     */
    private PathParamResolver createPathParamResolver(List<ArgDefEntry<PathParam>> pathParamAnnos){
        if (pathParamAnnos.size() == 0) {
            return PathParamResolvers.NONE_PATH_PARAM_RESOLVER;
        }
        List<ArgDefEntry<PathParamDefinition>> pathParamDefs = new LinkedList<>();
        for (ArgDefEntry<PathParam> entry : pathParamAnnos) {
            if (StringUtils.isEmpty(entry.getDefinition().name())) {
                throw new IllegalHttpServiceDefinitionException("The name of path parameter is empty!");
            }

            Class<?> converterClazz = entry.getDefinition().converter();
            StringConverter converter = StringConverterFactory.instantiateStringConverter(converterClazz);
            ArgDefEntry<PathParamDefinition> argDefEntry = new ArgDefEntry<>(entry.getIndex(),
                    entry.getArgType(), new PathParamDefinition(entry.getDefinition().name(), converter));
            pathParamDefs.add(argDefEntry);
        }

        return PathParamResolvers.createResolver(pathParamDefs);
    }

    /**
     * Create a request parameter parser
     *
     * @param reqParamAnnos
     * @return
     */
    private RequestParamResolver createRequestParamResolver(List<ArgDefEntry<RequestParam>> reqParamAnnos,
                                                            List<ArgDefEntry<RequestParamMap>> reqParamsAnnos){
        List<ArgDefEntry<RequestParamDefinition>> reqDefs = RequestParamDefinition
                .resolveSingleParamDefinitions(reqParamAnnos);
        List<ArgDefEntry<RequestParamMapDefinition>> reqMapDefs = RequestParamMapDefinition
                .resolveParamMapDefinitions(reqParamsAnnos);

        return RequestParamResolvers.createParamMapResolver(reqDefs, reqMapDefs);

    }

    /**
     * @param reqBodyAnnoEntry
     * @return
     */
    private RequestBodyResolver createBodyResolver(ArgDefEntry<RequestBody> reqBodyAnnoEntry){
        Class<?> converterClazz = reqBodyAnnoEntry.getDefinition().converter();
        RequestBodyConverter converter = null;
        if (converterClazz == RequestBodyConverter.class || converterClazz == null) {
            // create default body converter;
            converter = new TypeAutoAdapterBodyConverter(reqBodyAnnoEntry.getArgType());
        } else {
            if (!ClassUtils.isAssignable(RequestBodyConverter.class, converterClazz)) {
                throw new IllegalHttpServiceDefinitionException(
                        "The specified body converter doesn't implement the interface "
                                + RequestBodyConverter.class.getName() + "!");
            }
            converter = (RequestBodyConverter) BeanUtils.instantiate(converterClazz);
        }

        RequestBodyDefinition reqBodyDef = new RequestBodyDefinition(reqBodyAnnoEntry.getDefinition().required(),
                converter);
        ArgDefEntry<RequestBodyDefinition> reqBodyDefEntry = new ArgDefEntry<>(
                reqBodyAnnoEntry.getIndex(), reqBodyAnnoEntry.getArgType(), reqBodyDef);
        return RequestBodyResolvers.createArgumentResolver(reqBodyDefEntry);
    }

    /**
     * Check whether three or more than two of the two parameters are non empty
     *
     * @param reqBodyAnno
     * @param reqParamAnno
     * @param pathParamAnno
     * @return Return to true when two or more than two are non empty
     * <p>
     * All null or only one null, return to false
     */
    private static boolean hasConflictiveAnnotation(RequestBody reqBodyAnno, RequestParam reqParamAnno,
                                                    RequestParamMap reqParamsAnno, PathParam pathParamAnno){
        return 1 < (reqBodyAnno == null ? 0 : 1) + (reqParamAnno == null ? 0 : 1) + (reqParamsAnno == null ? 0 : 1)
                + (pathParamAnno == null ? 0 : 1);
    }

    /**
     * Parse the invoked method, mapped to a HTTP request
     */
    private Object invoke(HttpServiceContext serviceContext, CloseableHttpClient httpClient, RequestHeader[] headers,
                          Method method, Object[] args) throws Throwable{
        ServiceActionContext actionContext = actions.get(method);
        if (actionContext == null) {
            throw new UnsupportedOperationException("The invoked method was not a service action!");
        }
        try {
            HttpServiceRequest request = resolveRequest(actionContext, args);

            HttpUriRequest httpRequest = buildRequest(request);

            // Setting the default Content-Type
            Header[] contentTypeHeaders = httpRequest.getHeaders("Content-Type");
            if (contentTypeHeaders == null || contentTypeHeaders.length == 0) {
                httpRequest.setHeader("Content-Type", "application/json");
            }
            // Set the predefined head
            setHeaders(httpRequest, headers);
            // Set the header that is generated by the parsing request
            setHeaders(httpRequest, request.getHeaders());
            if (authorizationHeaderResolver != null) {
                AuthorizationHeader auth = authorizationHeaderResolver.generateHeader(request);
                // Set the authentication property
                buildAuthorization(httpRequest, auth);
            }

            CloseableHttpResponse response = httpClient.execute(httpRequest);
            try {
                // HTTP exception
                if (response.getStatusLine().getStatusCode() >= 400) {
                    processAndThrowHttpException(actionContext, request, response);
                    // Note: the last step has been thrown out of the exception
                    return null;
                }
                InputStream respStream = response.getEntity().getContent();
                Object respObject = actionContext.getResponseConverter().getResponse(request, respStream,
                        serviceContext);
                return respObject;
            } finally {
                response.close();
            }
        } catch (Exception e) {
            if (isCustomThownException(e, actionContext)) {
                throw e;
            }
            if (e instanceof HttpServiceException) {
                throw (HttpServiceException) e;
            }
            throw new HttpServiceException(e.getMessage(), e);
        }
    }

    private void setHeaders(HttpUriRequest httpRequest, RequestHeader[] headers){
        if (headers == null) {
            return;
        }
        for (RequestHeader header : headers) {
            httpRequest.setHeader(header.getName(), header.getValue());
        }
    }

    private void setHeaders(HttpUriRequest httpRequest, Properties customHeaders){
        Set<String> names = customHeaders.stringPropertyNames();
        for (String name : names) {
            httpRequest.setHeader(name, customHeaders.getProperty(name));
        }
    }

    /**
     * Determine whether the specified exception belongs to the interface method of the specified service operation, and declare the exception through throws；
     *
     * @param e
     * @param actionContext
     * @return
     */
    private boolean isCustomThownException(Exception e, ServiceActionContext actionContext){
        Class<?> exType = e.getClass();
        Class<?>[] thrownExTypes = actionContext.getThrownExceptionTypes();
        for (Class<?> thrExType : thrownExTypes) {
            if (thrExType.isAssignableFrom(exType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Handle HTTP errors and throw HttpStatusException exceptions；
     *
     * @param actionContext
     * @param response
     */
    private void processAndThrowHttpException(ServiceActionContext actionContext, ServiceRequest request,
                                              CloseableHttpResponse response) throws HttpStatusException{
        String content = null;
        if (actionContext.isResolveContentOnHttpError()) {
            try {
                InputStream respStream = response.getEntity().getContent();
                content = (String) StringResponseConverter.INSTANCE.getResponse(request, respStream, null);
            } catch (UnsupportedOperationException e) {
                throw new HttpServiceException(e.getMessage(), e);
            } catch (IOException e) {
                throw new HttpServiceException(e.getMessage(), e);
            } catch (Exception e) {
                if (e instanceof HttpServiceException) {
                    throw (HttpServiceException) e;
                }
                throw new HttpServiceException(e.getMessage(), e);
            }
        }
        String errMsg = String.format("[status=%s] %s", response.getStatusLine().getStatusCode(), content);
        throw new HttpStatusException(response.getStatusLine().getStatusCode(), errMsg);
    }

    private HttpServiceRequest resolveRequest(ServiceActionContext actionContext, Object[] args) throws IOException{
        switch (actionContext.getRequestMethod()) {
            case GET:
                return resolveGetRequest(actionContext, args);
            case POST:
            case PUT:
                return resolvePostOrPutRequest(actionContext, args);
            case DELETE:
                return resolveDeleteRequest(actionContext, args);
            default:
                throw new UnsupportedOperationException(
                        "Unsupported http method '" + actionContext.getRequestMethod() + "'!");
        }
    }

    /**
     * Create a request
     *
     * @return
     */
    private HttpUriRequest buildRequest(ServiceRequest request){
        ByteBuffer bodyBytes = null;
        if (request.getBody() != null) {
            // bodyStream = new ByteArrayInputStream(request.getBody().array());

            bodyBytes = request.getBody();
        }
        Properties reqParams = request.getRequestParams();
        switch (request.getHttpMethod()) {
            case GET:
                return new HttpGet(request.getUri());
            case POST:
                HttpPost httppost = new HttpPost(request.getUri());

                if (reqParams != null) {
                    // Submission in form form；
                    Set<String> propNames = reqParams.stringPropertyNames();
                    List<NameValuePair> formParams = new ArrayList<>();
                    for (String propName : propNames) {
                        formParams.add(new BasicNameValuePair(propName, reqParams.getProperty(propName)));
                    }
                    UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
                    httppost.setEntity(formEntity);
                    // Setting the default Content-Type
                    httppost.setHeader(formEntity.getContentType());
                }
                if (bodyBytes != null) {
                    // Query parameters are submitted in stream body mode
                    ByteArrayEntity entity = new ByteArrayEntity(bodyBytes.array());
                    httppost.setEntity(entity);
                    // Setting default Content-Type；
                    httppost.setHeader(entity.getContentType());
                }
                return httppost;
            case PUT:
                HttpPut httpput = new HttpPut(request.getUri());
                if (reqParams != null) {
                    // Submission in form form
                    Set<String> propNames = reqParams.stringPropertyNames();
                    List<NameValuePair> formParams = new ArrayList<>();
                    for (String propName : propNames) {
                        formParams.add(new BasicNameValuePair(propName, reqParams.getProperty(propName)));
                    }
                    UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
                    httpput.setEntity(formEntity);
                }
                if (bodyBytes != null) {
                    // Query parameters are submitted in stream body mode
                    ByteArrayEntity entity = new ByteArrayEntity(bodyBytes.array());
                    httpput.setEntity(entity);
                }
                return httpput;
            case DELETE:
                LocalHttpDelete httpDelete = new LocalHttpDelete(request.getUri());
                // Query parameters are submitted in delete body mode
                if (bodyBytes != null) {
                    ByteArrayEntity entity = new ByteArrayEntity(bodyBytes.array());
                    httpDelete.setEntity(entity);
                }
                return httpDelete;
            default:
                throw new UnsupportedOperationException("Unsupported http method '" + request.getHttpMethod() + "'!");
        }
    }

    /**
     * Setting the Authorization property of the HTTP request header
     *
     * @param request
     * @param setting
     */
    private void buildAuthorization(HttpUriRequest request, RequestHeader setting){
        request.addHeader(setting.getName(), setting.getValue());
    }

    /**
     * Create http put requestion
     *
     * @param actionContext
     * @param args
     * @return
     * @throws IOException
     */
    private HttpServiceRequest resolvePostOrPutRequest(ServiceActionContext actionContext, Object[] args)
            throws IOException{
        // Parsing path parameters
        Map<String, String> pathParams = actionContext.getPathParamResolver().resolve(args);
        HttpMethod httpMethod = actionContext.getRequestMethod();

        // Parsing RequestBody
        InputStream inputStream = actionContext.getRequestBodyResolver().resolve(args);
        boolean noBody = (EmptyInputStream.INSTANCE == inputStream || inputStream == null);

        // Parsing RequestParam；
        Properties reqParams = actionContext.getRequestParamResolver().resolve(args);
        reqParams = reqParams == null ? EmptyProperties.INSTANCE : reqParams;
        boolean noReqParams = (EmptyProperties.INSTANCE == reqParams || reqParams.isEmpty());
        actionContext.getRequestParamFilter().filter(httpMethod, reqParams);

        // If only the parameters marked by RequestBody are used, the serialization output of the RequestBody parameter is used as the request body
        if ((!noBody) && noReqParams) {
            URI uri = actionContext.getPathTemplate().generateRequestURI(pathParams, EmptyProperties.INSTANCE,
                    ServiceActionContext.DEFAULT_CHARSET);
            byte[] bytes = BytesUtils.copyToBytes(inputStream);
            ByteBuffer body = ByteBuffer.wrap(bytes);
            return new HttpServiceRequest(httpMethod, uri, null, body, args);
        }
        // If there is no RequestBody tagging parameter and only RequestParam, RequestParam is submitted in form form through request body
        if (noBody && (!noReqParams)) {
            URI uri = actionContext.getPathTemplate().generateRequestURI(pathParams, EmptyProperties.INSTANCE,
                    ServiceActionContext.DEFAULT_CHARSET);
            return new HttpServiceRequest(httpMethod, uri, reqParams, null, args);
        }

        // If the parameters of the RequestBody annotation and the RequestParam annotation are at the same time, the serialized output of the RequestBody parameter is used as the request body, and the RequestParam is used as the URL parameter
        if ((!noBody) && (!noReqParams)) {
            URI uri = actionContext.getPathTemplate().generateRequestURI(pathParams, reqParams,
                    ServiceActionContext.DEFAULT_CHARSET);
            byte[] bytes = BytesUtils.copyToBytes(inputStream);
            ByteBuffer body = ByteBuffer.wrap(bytes);
            return new HttpServiceRequest(httpMethod, uri, null, body, args);
        }

        // Neither RequestBody nor RequestParam
        URI uri = actionContext.getPathTemplate().generateRequestURI(pathParams, EmptyProperties.INSTANCE,
                ServiceActionContext.DEFAULT_CHARSET);
        return new HttpServiceRequest(httpMethod, uri, null, null, args);
    }

    /**
     * Create http get requestion
     *
     * @param actionContext
     * @param args
     * @return
     */
    private HttpServiceRequest resolveGetRequest(ServiceActionContext actionContext, Object[] args){
        Map<String, String> pathParams = actionContext.getPathParamResolver().resolve(args);
        Properties reqParams = actionContext.getRequestParamResolver().resolve(args);
        URI uri = actionContext.getPathTemplate().generateRequestURI(pathParams, reqParams,
                ServiceActionContext.DEFAULT_CHARSET);
        // For get requests, the request parameters have been encoded in URI, so there is no need to transmit them again
        return new HttpServiceRequest(HttpMethod.GET, uri, null, null, args);
    }

    /**
     * Create http delete requestion
     *
     * @param actionContext
     * @param args
     * @return
     * @throws IOException
     */
    private HttpServiceRequest resolveDeleteRequest(ServiceActionContext actionContext, Object[] args)
            throws IOException{
        Map<String, String> pathParams = actionContext.getPathParamResolver().resolve(args);
        Properties reqParams = actionContext.getRequestParamResolver().resolve(args);
        InputStream inputStream = actionContext.getRequestBodyResolver().resolve(args);
        URI uri = actionContext.getPathTemplate().generateRequestURI(pathParams, reqParams,
                ServiceActionContext.DEFAULT_CHARSET);
        byte[] bytes = BytesUtils.copyToBytes(inputStream);
        return new HttpServiceRequest(HttpMethod.DELETE, uri, null, ByteBuffer.wrap(bytes), args);
    }

    private static class ServiceInvocationHandler implements InvocationHandler, HttpServiceContext{

        private HttpServiceAgent serviceAgent;

        private HttpServiceConnection connection;

        private RequestHeader[] headers;

        private Object bindingData;

        public ServiceInvocationHandler(HttpServiceAgent serviceAgent, HttpServiceConnection connection,
                                        RequestHeader[] headers, Object bindingData){
            this.serviceAgent = serviceAgent;
            this.connection = connection;
            this.headers = headers;
            this.bindingData = bindingData;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
            return serviceAgent.invoke(this, connection.getHttpClient(), headers, method, args);
        }

        @Override
        public Class<?> getServiceClasss(){
            return serviceAgent.serviceClass;
        }

        @Override
        public Object getProxyBindingData(){
            return bindingData;
        }

    }

    @SuppressWarnings("unused")
	private static class HttpServiceContextImpl implements HttpServiceContext{

        private Class<?> serviceClass;

        private Object proxyBindingData;

        public HttpServiceContextImpl(Class<?> serviceClass, Object proxyBindingData){
            this.serviceClass = serviceClass;
            this.proxyBindingData = proxyBindingData;
        }

        @Override
        public Class<?> getServiceClasss(){
            return serviceClass;
        }

        @Override
        public Object getProxyBindingData(){
            return proxyBindingData;
        }

    }

    /**
     * HttpServiceRequest is a HTTP request model transformed to a real service call
     *
     * @author bumo
     */
    private static class HttpServiceRequest implements ServiceRequest{

        private HttpMethod method;

        private URI uri;

        private ByteBuffer body;

        private Properties headers = new Properties();

        private Properties requestParams;

        private Object[] args;

        public HttpServiceRequest(HttpMethod method, URI uri, Properties requestParams, ByteBuffer body,
                                  Object[] args){
            this.method = method;
            this.uri = uri;
            this.requestParams = requestParams;
            this.body = body;
            this.args = args;
        }

        /*
         * (non-Javadoc)
         *
         * @see cn.bumo.baas.utils.http.agent.Request#getMethod()
         */
        @Override
        public HttpMethod getHttpMethod(){
            return method;
        }

        @Override
        public Properties getRequestParams(){
            return requestParams;
        }

        /*
         * (non-Javadoc)
         *
         * @see cn.bumo.baas.utils.http.agent.Request#getUri()
         */
        @Override
        public URI getUri(){
            return uri;
        }

        /*
         * (non-Javadoc)
         *
         * @see cn.bumo.baas.utils.http.agent.Request#getBody()
         */
        @Override
        public ByteBuffer getBody(){
            return body;
        }

        @SuppressWarnings("unused")
		public void setHeader(String name, String value){
            headers.setProperty(name, value);
        }

        public Properties getHeaders(){
            return headers;
        }

        @Override
        public Object[] getArgs(){
            return args;
        }

    }
}
