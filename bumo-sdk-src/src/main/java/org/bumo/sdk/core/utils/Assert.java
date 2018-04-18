package org.bumo.sdk.core.utils;

import java.util.Collection;
import java.util.List;

import org.bumo.sdk.core.exception.SdkError;
import org.bumo.sdk.core.exception.SdkException;
import org.bumo.sdk.core.utils.spring.StringUtils;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class Assert{

    public static void isTrue(boolean expr, SdkError sdkError) throws SdkException{
        if (!expr) throw new SdkException(sdkError);
    }

    public static void notTrue(boolean expr, SdkError sdkError) throws SdkException{
        if (expr) throw new SdkException(sdkError);
    }

    public static void isNull(Object object, SdkError sdkError) throws SdkException{
        if (object != null) throw new SdkException(sdkError);
    }

    public static void notNull(Object object, SdkError sdkError) throws SdkException{
        if (object == null) throw new SdkException(sdkError);
    }

    public static void notEmpty(String str, SdkError sdkError) throws SdkException{
        if (StringUtils.isEmpty(str)) throw new SdkException(sdkError);
    }

    public static void notEmpty(List<?> list, SdkError sdkError) throws SdkException{
        if (list == null || list.isEmpty()) throw new SdkException(sdkError);
    }

    public static void gteZero(long balance, SdkError sdkError) throws SdkException{
        if (balance < 0) throw new SdkException(sdkError);
    }

    public static void gtZero(long balance, SdkError sdkError) throws SdkException{
        if (balance <= 0) throw new SdkException(sdkError);
    }

    public static void gteExpect(long actual, long expect, SdkError sdkError) throws SdkException{
        if (actual < expect) throw new SdkException(sdkError);
    }

    public static <T> void checkCollection(Collection<T> collection, SDKConsumer<T> consumer) throws SdkException{
        for (T t : collection) {
            consumer.accept(t);
        }
    }


    @FunctionalInterface
    public interface SDKConsumer<T>{

        void accept(T t) throws SdkException;

    }

}
