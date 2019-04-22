package io.bumo.common;

public class ToBaseUnit {
    public static Long BU2MO(String buAmount) {
        return unitWithDecimals(buAmount, 8);
    }

    public static String MO2BU(String moAmount) {
        return unitWithoutDecimals(moAmount, 8);
    }

    /**
     * @author riven
     * @param amountWithDecimals The amount without decimals
     * @param decimals The decimals, that cannot be bigger than 18
     * @return The amount with decimals, which means that amountWithDecimals * 10 ^ decimals, but should be bigger than LONG.MAX_VALUE
     * @date 2018/12/13 16:00
     */
    public static String unitWithoutDecimals(String amountWithDecimals, int decimals) {
        if (decimals > 18 || decimals < 0) {
            return null;
        }
        // must be long, and the number is between 1 and 19
        if (!amountWithDecimals.matches("(^0?$)|(^[1-9][0-9]{0,18}?$)")) {
            return null;
        }
        String longMaxValue = String.valueOf(Long.MAX_VALUE);
        if (amountWithDecimals.compareTo(longMaxValue) > 0) {
            return null;
        }

        String afterStr = "";
        String beforeStr = "";
        if (amountWithDecimals.length() > decimals) {
            afterStr = "." + amountWithDecimals.substring(amountWithDecimals.length() - decimals);
            beforeStr = amountWithDecimals.substring(0, amountWithDecimals.length() - decimals);
        } else {
            String addZero = "";
            for (int i = 0; i < decimals - amountWithDecimals.length(); i++) {
                addZero += "0";
            }
            afterStr = "0." + addZero + amountWithDecimals;
        }
        String result = delEndsZero(beforeStr + afterStr);
        if (result.endsWith(".")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * @author riven
     * @param amountWithoutDecimals The amount without decimals, that must be a number smaller than LONG.MAX_VALUE
     * @param decimals The decimals, that should be bigger than and equal to 0 and should be smaller than 19
     * @return The amount with decimals, which means that amountWithDecimals * 10 ^ decimals
     * @date 2018/12/13 16:00
     */
    public static Long unitWithDecimals(String amountWithoutDecimals, int decimals) {
        if (decimals > 18 || decimals < 0) {
            return null;
        }
        // long or float string. if float, the number before point cannot be bigger than 10 and the number after point cannot be bigger then 8
        if (!amountWithoutDecimals.matches("(^0(\\.[0-9]{0," + (decimals - 1) + "}[1-9])?$)|" +
                "(^[1-9][0-9]{0," + (18 - decimals) + "}(\\.[0-9]{0," + (decimals - 1) + "}[1-9])?$)")) {
            return null;
        }
        String longMaxValue = String.valueOf(Long.MAX_VALUE);
        String beforeStr = "";
        String afterStr = "";
        String tempArray[] = amountWithoutDecimals.split("\\.");
        if (tempArray.length > 1) {
            beforeStr = tempArray[0];
            afterStr = tempArray[1];
        } else {
            long temp = (long)Math.pow(10, decimals);
            String zeroNumber = Long.toString(temp);
            String amount = amountWithoutDecimals + zeroNumber.substring(1);
            if (amount.compareTo(longMaxValue) > 0){
                return null;
            }
            return Long.parseLong(amount);
        }
        // 0.020000004
        int endIndex = afterStr.length();
        if (afterStr.length() > decimals) {
            endIndex = decimals;
            afterStr = afterStr.substring(0, endIndex);
        } else {
            String addZero = "";
            for (int i = 0; i < decimals - afterStr.length(); i++) {
                addZero += "0";
            }
            afterStr += addZero;
        }
        String amount = delStartZero(beforeStr + afterStr);
        if (amount.compareTo(longMaxValue) > 0){
            return null;
        }
        return Long.parseLong(amount);
    }

    public static void main(String[] args) {
        Long s1 = ToBaseUnit.BU2MO("99999999999");
        System.out.println(s1);
        //String s2 = ToBaseUnit.MO2BU("999999999999000000");
        //System.out.println(s2);
    }

    public static String delEndsZero(String src) {
        if (src.endsWith("0")) {
            return delEndsZero(src.substring(0, src.length() - 1));
        } else {
            return src;
        }
    }

    public static String delStartZero(String src) {
        if (src.startsWith("0")) {
            return delStartZero(src.substring(1, src.length()));
        } else {
            return src;
        }
    }
}
