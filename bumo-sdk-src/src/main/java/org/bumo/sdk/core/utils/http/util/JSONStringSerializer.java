package org.bumo.sdk.core.utils.http.util;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.io.IOException;
import java.lang.reflect.Type;

public class JSONStringSerializer implements ObjectSerializer{

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
            throws IOException{
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
            return;
        }
        JSONString jsonString = (JSONString) object;
        out.write(jsonString.toString());
    }

}
