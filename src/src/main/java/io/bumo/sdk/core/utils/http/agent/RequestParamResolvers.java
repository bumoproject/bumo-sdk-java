package io.bumo.sdk.core.utils.http.agent;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import io.bumo.sdk.core.utils.EmptyProperties;
import io.bumo.sdk.core.utils.http.HttpServiceException;
import io.bumo.sdk.core.utils.spring.CollectionUtils;

/**
 * Request parameter parser
 *
 * @author bumo
 */
abstract class RequestParamResolvers{

    public static final RequestParamResolver NONE_REQUEST_PARAM_RESOLVER = new NoneRequestParamResolver();

    /**
     * 创建解析器；
     *
     * @param definitions 方法参数定义；
     * @return
     */
    public static RequestParamResolver createParamMapResolver(
            List<ArgDefEntry<RequestParamDefinition>> reqParamDefinitions,
            List<ArgDefEntry<RequestParamMapDefinition>> reqParamMapDefinitions){
        if ((!CollectionUtils.isEmpty(reqParamDefinitions)) && (!CollectionUtils.isEmpty(reqParamMapDefinitions))) {
            RequestParamResolver resolver1 = createParamResolver(reqParamDefinitions);
            RequestParamResolver resolver2 = createParamMapResolver(reqParamMapDefinitions);
            return new MultiRequestParamResolverWrapper(resolver1, resolver2);
        }
        if (!CollectionUtils.isEmpty(reqParamDefinitions)) {
            return createParamResolver(reqParamDefinitions);
        }
        if (!CollectionUtils.isEmpty(reqParamMapDefinitions)) {
            return createParamMapResolver(reqParamMapDefinitions);
        }
        return NONE_REQUEST_PARAM_RESOLVER;
    }

    /**
     * 创建解析器；
     *
     * @param definitions 方法参数定义；
     * @return
     */
    public static RequestParamResolver createParamMapResolver(
            List<ArgDefEntry<RequestParamMapDefinition>> definitions){
        return new ArgArrayRequestParamMapResolver(definitions);
    }

    /**
     * 创建解析器；
     *
     * @param definitions 方法参数定义；
     * @return
     */
    public static RequestParamResolver createParamResolver(
            List<ArgDefEntry<RequestParamDefinition>> definitions){
        return new ArgArrayRequestParamResolver(definitions);
    }

    /**
     * 方法参数表解析器；
     *
     * @author haiq
     */
    private static class ArgArrayRequestParamMapResolver implements RequestParamResolver{

        private List<ArgDefEntry<RequestParamMapDefinition>> definitions;

        /**
         * @param definitions
         */
        public ArgArrayRequestParamMapResolver(List<ArgDefEntry<RequestParamMapDefinition>> definitions){
            this.definitions = new LinkedList<>(definitions);
        }

        @Override
        public Properties resolve(Object[] args){
            Properties params = new Properties();
            for (ArgDefEntry<RequestParamMapDefinition> defEntry : definitions) {
                RequestParamMapDefinition def = defEntry.getDefinition();
                Object argValue = args[defEntry.getIndex()];
                if (argValue == null && def.isRequired()) {
                    throw new HttpServiceException("The required argument object is null!");
                }

                Properties props = def.getConverter().toProperties(argValue);
                if (props == null || props.size() == 0) {
                    if (def.isRequired()) {
                        throw new HttpServiceException("The required request parameter map is empty!");
                    }
                    // 非必需参数，忽略空值;
                    continue;
                }
                if (props != null) {
                    // 合并参数；
//                    PropertiesUtils.mergeFrom(params, props, def.getPrefix());
                }
            } // End of for;
            return params;
        }

    }

    /**
     * 方法参数解析器；
     *
     * @author haiq
     */
    private static class ArgArrayRequestParamResolver implements RequestParamResolver{

        private List<ArgDefEntry<RequestParamDefinition>> paramDefinitions;

        /**
         * @param paramDefinitions
         */
        public ArgArrayRequestParamResolver(List<ArgDefEntry<RequestParamDefinition>> paramDefinitions){
            this.paramDefinitions = new LinkedList<>(paramDefinitions);
        }

        @Override
        public Properties resolve(Object[] args){
            Properties params = new Properties();
            for (ArgDefEntry<RequestParamDefinition> defEntry : paramDefinitions) {
                RequestParamDefinition def = defEntry.getDefinition();
                Object arg = args[defEntry.getIndex()];
                if (arg == null && def.isRequired()) {
                    throw new HttpServiceException("The required argument object is null!");
                }

                String value = def.getConverter().toString(arg);
                if (value == null) {
                    if (def.isRequired()) {
                        throw new HttpServiceException("The required argument value is null!");
                    }
                    // not required, and ignore null value;
                    continue;
                }
                if (value.equals(def.getIgnoreValue())) {
                    // ignore ;
                    continue;
                }
                params.setProperty(def.getName(), value);
            } // End of for;
            return params;
        }

    }

    /**
     * Wrapper that combines the analytic results of multiple request parameter parsers
     *
     * @author bumo
     */
    private static class MultiRequestParamResolverWrapper implements RequestParamResolver{

        private RequestParamResolver[] resolvers;

        public MultiRequestParamResolverWrapper(RequestParamResolver... resolvers){
            this.resolvers = resolvers;
        }

        @SuppressWarnings("unused")
		@Override
        public Properties resolve(Object[] args){
            Properties params = new Properties();
            for (RequestParamResolver resolver : resolvers) {
                Properties props = resolver.resolve(args);
            }
            return params;
        }

    }

    /**
     * Empty request parameter parser
     * <p>
     * Always return to the empty request parameter
     *
     * @author bumo
     */
    private static class NoneRequestParamResolver implements RequestParamResolver{

        @Override
        public Properties resolve(Object[] args){
            return EmptyProperties.INSTANCE;
        }

    }

}
