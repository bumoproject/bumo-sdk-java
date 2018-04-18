package org.bumo.sdk.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class SerializeUtil{

    private static final Logger LOGGER = LoggerFactory.getLogger(SerializeUtil.class);

    public static byte[] serialize(Object value){
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }

        byte[] rv;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {
            try {
                if (os != null) os.close();
                if (bos != null) bos.close();
            } catch (Exception e2) {
                LOGGER.error("", e2);
            }
        }
        return rv;
    }

    @SuppressWarnings("unchecked")
	public static <R> R deserialize(byte[] in){
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
                is.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (bis != null) bis.close();
            } catch (Exception e2) {
                LOGGER.error("", e2);
            }
        }
        return (R) rv;
    }

}
