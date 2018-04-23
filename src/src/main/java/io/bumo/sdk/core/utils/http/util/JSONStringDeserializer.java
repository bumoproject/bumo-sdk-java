package io.bumo.sdk.core.utils.http.util;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;

public class JSONStringDeserializer implements ObjectDeserializer{

    @SuppressWarnings("unchecked")
	@Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName){
        if (type instanceof Class && JSONString.class.isAssignableFrom((Class<?>) type)) {
            String jsonString = parser.parseObject(String.class);
            return (T) new JSONString(jsonString);
        }
        return (T) parser.parse(fieldName);
    }

    @Override
    public int getFastMatchToken(){
        return JSONToken.LBRACE;
    }

}
