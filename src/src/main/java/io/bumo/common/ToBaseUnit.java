package io.bumo.common;

public class ToBaseUnit {
	public static Long BU2MO(String buAmount){
		// long or float string. if float, the number before point cannot be bigger than 10 and the number after point cannot be bigger then 8
		if (!buAmount.matches("^[0-9]{1,10}(\\.[0-9]{1,8})?$")) {
			return null;
		}
		String beforeStr = "";
		String afterStr = "";
		String tempArray[] = buAmount.split("\\.");
		if(tempArray.length > 1) {
			beforeStr = tempArray[0];
			afterStr = tempArray[1];
		} else {
			return Long.parseLong(buAmount + "00000000");
		}
		// 0.020000004
		int endIndex = afterStr.length();
		if(afterStr.length() > 8) {
			endIndex = 8;
			afterStr = afterStr.substring(0, endIndex);
		} else {
			String addZero = "";
			for (int i = 0; i < 8 - afterStr.length(); i++) {
				addZero += "0";
			}
			afterStr += addZero;
		}
		return Long.parseLong(delStartZero(beforeStr + afterStr));
	}
	
	public static String MO2BU(String moAmount){
		// must be long, and the number is between 1 and 18
		if (!moAmount.matches("^[0-9]{1,18}?$")) {
			return null;
		}

		String afterStr = "";
		String beforeStr = "";
		if(moAmount.length() > 8){
			afterStr = "." + moAmount.substring(moAmount.length()-8);
			beforeStr = moAmount.substring(0, moAmount.length()-8);
		}else{
			String addZero = "";
			for (int i = 0; i < 8 - moAmount.length(); i++) {
				addZero += "0";
			}
			afterStr = "0." +addZero+ moAmount;
		}
		String result = delEndsZero(beforeStr + afterStr);
		if(result.endsWith(".")){
			result = result.substring(0,result.length()-1);
		}
		return result;
	}
	public static void main(String[] args) {
		Long s1 = ToBaseUnit.BU2MO("99999999999");
		System.out.println(s1);
		//String s2 = ToBaseUnit.MO2BU("999999999999000000");
		//System.out.println(s2);
	}
	public static String delEndsZero(String src) {
		if (src.endsWith("0")){
		   return delEndsZero(src.substring(0, src.length() - 1));
		} else {
			return src;
		}
	}
	public static String delStartZero(String src){
		if (src.startsWith("0")){
		   return delStartZero(src.substring(1, src.length()));
		} else {
			return src;
		}
	}
}
