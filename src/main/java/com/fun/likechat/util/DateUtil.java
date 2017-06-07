package com.fun.likechat.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTimeUtils;

/**
 * SimpleDateFormat 语法：  
         G -- 年代标志符
         y -- 年
         M -- 月 
         d -- 日
         h -- 时 12小时制 (1~12)
         H -- 时 24小时制 (0~23)
         m -- 分
         s -- 秒
         S -- 毫秒
         E -- 星期
         D -- 一年中的第几天
         F -- 一月中第几个星期几
         w -- 一年中第几个星期
         W -- 一月中第几个星期

         a 上午 / 下午 标记符 
         k 时 在一天中 (1~24)
         K 时 在上午或下午 (0~11)
         z 时区
**/
public class DateUtil {

	public static long OFFSET = 0;

	public static final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";

	public static final String DATE_FORMAT_COMPACT = "yyyyMMdd";
	
	public static final String DATE_FORMAT_MONTH_DAY_HOUR = "MM-dd HH:mm";

	public static final String DATE_FORMAT_COMPACTFULL = "yyyyMMddHHmmss";

	public static final String DATE_YEAR_MONTH = "yyyyMM";

	private static void setOffset() {
		DateTimeUtils.setCurrentMillisOffset(OFFSET);
	}

	public static Date getCurrentDate() {
		setOffset();
		Date currentDate = new Date(DateTimeUtils.currentTimeMillis());
		return currentDate;
	}

	/**
	 * 得到当前日期
	 * 
	 * @return String 当前日期 yyyyMMdd HH:mm:ss格式
	 * @author kevin
	 */
	public static String getCurDateTime() {
		DateFormat sdf = new SimpleDateFormat(DATE_FORMAT_FULL);
		return sdf.format(getCurrentDate());
	}

	public static String getCurDateTime(String format) {
		try {
			DateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(getCurrentDate());
		}
		catch(Exception e) {
			return getCurDateTime(); // 如果无法转化，则返回默认格式的时间。
		}
	}

	/**
	 * 获取当天开始的时间
	 * 
	 * @return
	 */
	public static Date getTodayStartTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentDate());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		return cal.getTime();
	}

	public static int getCurrentHour() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentDate());
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static int getCurrentMinutes() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentDate());
		return cal.get(Calendar.MINUTE);
	}

	public static Date getFristDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentDate());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		return cal.getTime();
	}

	public static Date getFirstDayOfNextMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentDate());
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		return cal.getTime();
	}

	public static Date afterNDaysDate(int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentDate());
		cal.add(Calendar.DAY_OF_YEAR, days);
		return cal.getTime();
	}

	public static Date afterNSecondsDate(int seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentDate());
		cal.add(Calendar.SECOND, seconds);
		return cal.getTime();
	}

	public static Date afterNMonthDate(int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentDate());
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}

	/**
	 * 
	 * getTimeString 方法描述: 将秒数转换成 1:34:44的格式 逻辑描述:
	 * 
	 * @return
	 * @since Ver 1.00
	 */
	public static String getTimeString(Integer seconds) {
		if(seconds == null) {
			return "00";
		}
		String result = "";
		long h = seconds / 3600;// 时
		long m = (seconds - h * 3600) / 60;// 分
		long s = seconds - h * 3600 - m * 60;// 秒
		if(s > 0) {
			if(s > 10) {
				result = s + "";
			}
			else {
				result = "0" + s;
			}
		}
		else {
			result = "00";
		}
		if(h == 0 && m == 0) {
			return result;
		}
		if(m > 0) {
			if(m > 10) {
				result = m + ":" + result;
			}
			else {
				result = "0" + m + ":" + result;
			}
		}
		else {
			result = "00:" + result;
		}
		if(h == 0) {
			return result;
		}
		if(h > 0) {
			if(h > 10) {
				result = h + ":" + result;
			}
			else {
				result = "0" + h + ":" + result;
			}
		}
		return result;
	}

	/**
	 * 字符串转日期
	 * 
	 * @return Date
	 * @author kevin
	 */
	public static Date parseStringToDate(String thedate, String format) {
		DateFormat sdf = new SimpleDateFormat(format);
		Date dd1 = null;
		try {
			dd1 = sdf.parse(thedate);
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		return dd1;
	}

	public static Date afterNSecondsDate(Date date, int time) {
		Date dt = null;
		try {
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(date);
			rightNow.add(Calendar.SECOND, time);
			dt = rightNow.getTime();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return dt;
	}

	// 比较两个字符串格式日期大小,带格式的日期
	public static boolean isBefore(String strdat1, String strdat2, String format) {
		try {
			Date dat1 = parseStringToDate(strdat1, format);
			Date dat2 = parseStringToDate(strdat2, format);
			return dat1.before(dat2);
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据出生日期获取人的年龄
	 * @param 
	 * @return
	 */
	public static String getPersonAgeByBirthDate(Date dateBirthDate) {
		if(dateBirthDate == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strBirthDate = dateFormat.format(dateBirthDate);

		// 读取当前日期
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DATE);
		// 计算年龄
		int age = year - Integer.parseInt(strBirthDate.substring(0, 4)) - 1;
		if(Integer.parseInt(strBirthDate.substring(5, 7)) < month) {
			age++;
		}
		else if(Integer.parseInt(strBirthDate.substring(5, 7)) == month && Integer.parseInt(strBirthDate.substring(8, 10)) <= day) {
			age++;
		}
		return String.valueOf(age);
	}
	
	/**
	 * 日期转字符串
	 * 
	 * @return String
	 * @author kevin
	 */
	public static String parseDateToString(Date date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		if(date != null) {
			return df.format(date.getTime());
		}
		return null;
	}

	// parseDateToString(Date thedate, String format)的重载方法
	public static String parseDateToString(Date thedate) {
		// String format = "yyyy-MM-dd";
		return parseDateToString(thedate, DATE_FORMAT_SHORT);
	}
	
	/**
     * 判断时间是不是今天
     * @param date
     * @return    是返回true，不是返回false
     */
    public static boolean isNow(Date date) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        //获取今天的日期
        String nowDay = sf.format(now);
        //对比的时间
        String day = sf.format(date);
        return day.equals(nowDay);
    }

	public static void main(String args[]) throws ParseException {
		System.out.println(parseDateToString(new Date(),DATE_FORMAT_MONTH_DAY_HOUR));
		System.out.println(isNow(parseStringToDate("2017-05-18 23:35:19",DATE_FORMAT_FULL)));
		
		System.out.println("今天" + "05-18 23:35".substring(5));

	}

}