package org.bumo.sdk.core.utils;
import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.Locale;



// TODO: Auto-generated Javadoc

/**

 * The Class DateUtil.

 */

public class DateUtil {

	

	/** The Constant STD_DATE_FOMAT. */

	public static final String STD_DATE_FOMAT= "yyyy-MM-dd";

	

	/** The Constant STD_TIME_FOMAT. */

	public static final String STD_TIME_FOMAT= "yyyy-MM-dd hh:mm:ss";

	

	/** The Constant ONE_DAY_MSECS. */

	public static final long ONE_DAY_MSECS = 1000*60*60*24;

	

	

	/** The Constant DATE_TIME_FMT. */

	public static final String DATE_TIME_FMT = "yyyy-MM-dd HH:mm:ss";

	

	/** The Constant DATE_FMT. */

	public static final String DATE_FMT = "yyyy-MM-dd";

	

	/**

	 * To date.

	 *

	 * @param dtStr the dt str

	 * @return the date

	 */

	public static Date toDate(String dtStr){

		try {

			return new SimpleDateFormat(STD_DATE_FOMAT).parse(dtStr);

		} catch (ParseException e) {

			e.printStackTrace();

		}

		return null;

	}

	

	/**

	 * To date string.

	 *

	 * @param date the date

	 * @return the string

	 */

	public static String toDateString(Date date){

		if(date==null)

			return "";

		return new SimpleDateFormat(STD_DATE_FOMAT).format(date);

	}

	

	/**

	 * Now to date string.

	 *

	 * @return the string

	 */

	public static String nowToDateString(){

		return toDateString(new Date());

	}

	

	/**

	 * Next day to date string.

	 *

	 * @return the string

	 */

	public static String nextDayToDateString(){

		return DateUtil.toDateString(nextDay());

	}

	

	

	/**

	 * To time.

	 *

	 * @param dtStr the dt str

	 * @return the date

	 */

	public static Date toTime(String dtStr){

		try {

			return new SimpleDateFormat(STD_TIME_FOMAT).parse(dtStr);

		} catch (ParseException e) {

			e.printStackTrace();

		}

		return null;

	}

	

	/**

	 * To time string.

	 *

	 * @param date the date

	 * @return the string

	 */

	public static String toTimeString(Date date){

		if(date==null)

			return "";

		return new SimpleDateFormat(STD_TIME_FOMAT).format(date);

	}

	

	/**

	 * Now to time string.

	 *

	 * @return the string

	 */

	public static String nowToTimeString(){

		return toTimeString(new Date());

	}

	

	/**

	 * Next n day.

	 *

	 * @param n the n

	 * @return the date

	 */

	public static Date  nextNDay(int n){

		

		return new Date(new Date().getTime()+n*ONE_DAY_MSECS);

	}

	

	/**

	 * Next day.

	 *

	 * @return the date

	 */

	public static Date nextDay(){

		return nextNDay(1);

	}

	

	

	/**

	 * 格式化string为Date.

	 *

	 * @param str the str

	 * @return date

	 */

	public static Date toDateLocaleUK(String str) {

		if (null == str || "".equals(str)) {

			return null;

		}

		try {

			String fmtstr = null;

			if (str.indexOf(':') > 0) {

				fmtstr = DateUtil.DATE_TIME_FMT;

			} else {

				fmtstr = DateUtil.DATE_FMT;

			}

			return new SimpleDateFormat(fmtstr, Locale.UK).parse(str);

		} catch (Exception e) {

			return null;

		}

	}



	/**

	 * 日期转化为String.

	 *

	 * @param date the date

	 * @return date string

	 */

	public static String toStringLocaleUK(Date date) {

		if (null == date) {

			return null;

		}

		try {

			return new SimpleDateFormat(DateUtil.DATE_TIME_FMT,	Locale.US).format(date);

		} catch (Exception e) {

			return null;

		}

	}



}