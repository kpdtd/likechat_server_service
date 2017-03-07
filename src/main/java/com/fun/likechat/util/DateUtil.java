package com.fun.likechat.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTimeUtils;

public class DateUtil {

    public static long OFFSET = 0;

    public static final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";

    public static final String DATE_FORMAT_COMPACT = "yyyyMMdd";

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
        } catch (Exception e) {
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
        if(seconds==null){
            return "00";
        }
        String result = "";
        long h = seconds / 3600;// 时
        long m = (seconds - h * 3600) / 60;// 分
        long s = seconds - h * 3600 - m * 60;// 秒
        if (s > 0) {
            if (s > 10) {
                result = s + "";
            } else {
                result = "0" + s;
            }
        } else {
            result = "00";
        }
        if (h == 0 && m == 0) {
            return result;
        }
        if (m > 0) {
            if (m > 10) {
                result = m + ":" + result;
            } else {
                result = "0" + m + ":" + result;
            }
        } else {
            result = "00:" + result;
        }
        if (h == 0) {
            return result;
        }
        if (h > 0) {
            if (h > 10) {
                result = h + ":"+ result;
            } else {
                result = "0" + h + ":"+ result;
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
        } catch (ParseException e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String args[]) throws ParseException {
        System.out.println(afterNSecondsDate(600));

    }

}