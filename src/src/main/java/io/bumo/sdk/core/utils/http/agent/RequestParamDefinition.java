package io.bumo.sdk.core.utils.http.agent;

import java.util.LinkedList;
import java.util.List;

import io.bumo.sdk.core.utils.http.RequestParam;
import io.bumo.sdk.core.utils.http.StringConverter;
import io.bumo.sdk.core.utils.spring.StringUtils;

class RequestParamDefinition{

    private String name;

    private boolean required;

    private String ignoreValue;

    private StringConverter converter;

    public RequestParamDefinition(String name, boolean required, String ignoreValue, StringConverter converter){
        this.name = name;
        this.required = required;
        this.ignoreValue = ignoreValue;
        this.converter = converter;
    }

    public String getName(){
        return name;
    }

    public boolean isRequired(){
        return required;
    }

    public String getIgnoreValue(){
        return ignoreValue;
    }

    public StringConverter getConverter(){
        return converter;
    }


    public static List<ArgDefEntry<RequestParamDefinition>> resolveSingleParamDefinitions(List<ArgDefEntry<RequestParam>> reqParamAnnos){
        List<ArgDefEntry<RequestParamDefinition>> reqDefs = new LinkedList<>();
        for (ArgDefEntry<RequestParam> entry : reqParamAnnos) {
            RequestParam reqParamAnno = entry.getDefinition();
            RequestParamDefinition reqDef = resolveDefinition(reqParamAnno);
            reqDefs.add(new ArgDefEntry<>(entry.getIndex(), entry.getArgType(), reqDef));
        }
        return reqDefs;
    }

    public static RequestParamDefinition resolveDefinition(RequestParam reqParamAnno){
        if (StringUtils.isEmpty(reqParamAnno.name())) {
            throw new IllegalHttpServiceDefinitionException("The name of request parameter is empty!");
        }

        Class<?> converterClazz = reqParamAnno.converter();
        StringConverter converter = StringConverterFactory.instantiateStringConverter(converterClazz);
        RequestParamDefinition reqDef = new RequestParamDefinition(reqParamAnno.name(), reqParamAnno.required(),
                reqParamAnno.ignoreValue(), converter);
        return reqDef;
    }
}
