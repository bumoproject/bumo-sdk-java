package io.bumo.sdk.core.utils.http.util;

import com.alibaba.fastjson.annotation.JSONType;

/**
 * JSONString is used to package the string that does not need to execute JSON serialization and output directly
 *
 * @author bumo
 */
@JSONType(serializer = JSONStringSerializer.class, deserializer = JSONStringDeserializer.class)
public final class JSONString{

    private String jsonString;

    public JSONString(String jsonString){
        if (jsonString == null) {
            throw new IllegalArgumentException("Null json string!");
        }
        this.jsonString = jsonString;
    }

    private JSONString(){
    }

    @Override
    public String toString(){
        return jsonString;
    }

    public static JSONString toJSONString(Object data){
        if (data == null) {
            return null;
        }
        String jsonString = SerializeUtils.serializeToJSON(data);
        JSONString retn = new JSONString();
        retn.jsonString = jsonString;
        return retn;
    }
}
