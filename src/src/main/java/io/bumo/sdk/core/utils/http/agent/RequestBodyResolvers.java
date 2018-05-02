package io.bumo.sdk.core.utils.http.agent;

import java.io.InputStream;

import io.bumo.sdk.core.utils.http.HttpServiceException;
import io.bumo.sdk.core.utils.io.EmptyInputStream;

class RequestBodyResolvers{

    public static final RequestBodyResolver NULL_BODY_RESOLVER = new NullBodyResolver();

    /**
     * Create a parser based on a parameter list
     *
     * @param argIndex  The location of the parameters to be output as body
     * @param converter Parameter value converter
     * @return
     */
    public static RequestBodyResolver createArgumentResolver(ArgDefEntry<RequestBodyDefinition> defEntry){
        return new ArgurmentResolver(defEntry);
    }

    private static final class ArgurmentResolver implements RequestBodyResolver{

        private ArgDefEntry<RequestBodyDefinition> defEntry;

        public ArgurmentResolver(ArgDefEntry<RequestBodyDefinition> defEntry){
            this.defEntry = defEntry;
        }

        @Override
        public InputStream resolve(Object[] args){
            Object arg = args[defEntry.getIndex()];
            if (arg == null && defEntry.getDefinition().isRequired()) {
                throw new HttpServiceException("The required body argument is null!");
            }
            return defEntry.getDefinition().getConverter().toInputStream(arg);
        }

    }

    /**
     * The empty body parser
     *
     * @author bumo
     */
    private static final class NullBodyResolver implements RequestBodyResolver{

        @Override
        public InputStream resolve(Object[] args){
            return EmptyInputStream.INSTANCE;
        }

    }
}
