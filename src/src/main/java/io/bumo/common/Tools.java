package io.bumo.common;

import java.util.List;

/**
 * @Author riven
 * @Date 2018/7/26 21:57
 */
public class Tools {
    /**
     * @Author riven
     * @Method isEmpty
     * @Params [cs]
     * @Return java.lang.Boolean
     * @Date 2018/7/26 22:07
     */
    public static Boolean isEmpty(final CharSequence cs) {
        return null == cs || cs.length() == 0;
    }

    /**
     * @Author riven
     * @Method isEmpty
     * @Params [obj]
     * @Return java.lang.Boolean
     * @Date 2018/7/26 22:08
     */
    public static Boolean isEmpty(final Object obj) {
        return null == obj || "".equals(obj);
    }

    /**
     * @Author riven
     * @Method isEmpty
     * @Params [objs]
     * @Return java.lang.Boolean
     * @Date 2018/7/26 22:40
     */
    public static Boolean isEmpty(final Object[] objs) {
        return null == objs || objs.length == 0;
    }

    /**
     * @Author riven
     * @Method isEmpty
     * @Params [objectList]
     * @Return java.lang.Boolean
     * @Date 2018/7/27 00:09
     */
    public static Boolean isEmpty(final List<Object> objectList) {
        return null == objectList || objectList.size() == 0;
    }

    /**
     * @Author riven
     * @Method isNULL
     * @Params [obj]
     * @Return java.lang.Boolean
     * @Date 2018/7/26 22:44
     */
    public static Boolean isNULL(final Object obj) {
        return null == obj;
    }
}
