package io.bumo.sdk.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.bumo.sdk.core.exception.SdkError;
import io.bumo.sdk.core.exception.SdkException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class SwallowUtil{

    private static final Logger LOGGER = LoggerFactory.getLogger(SwallowUtil.class);

    public static FileOutputStream getFileOutputStream(String path){
        try {
            return new FileOutputStream(path);
        } catch (FileNotFoundException e) {
            LOGGER.error("FileNotFoundException:", e);
            throw new RuntimeException(e);
        }
    }

    public static FileInputStream getFileInputStream(String path){
        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException e) {
            LOGGER.error("FileNotFoundException:", e);
            throw new RuntimeException(e);
        }
    }

    public static String urlEncode(String originStr){
        try {
            return URLEncoder.encode(originStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("UnsupportedEncodingException:", e);
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static byte[] getBytes(String originStr){
        try {
            return originStr.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("UnsupportedEncodingException:", e);
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static void swallowException(SDKConsume consume, SdkError sdkError) throws SdkException{
        try {
            consume.doSelf();
        } catch (Exception e) {
            if (e instanceof SdkException)
                throw (SdkException) e;

            throw new SdkException(sdkError);
        }
    }

    @FunctionalInterface
    public interface SDKConsume{

        void doSelf() throws Exception;

    }
}
