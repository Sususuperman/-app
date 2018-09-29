package com.cs.common.utils;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author james
 */
public abstract class DateUtils {

    public static String dateRange(long time) {
        return dateRange(System.currentTimeMillis(), time);
    }

    public static String dateRange(long startTime, long endTime) {
        String strTime = null;
        if (startTime != 0 && endTime != 0) {
            int diff = (int) ((startTime - endTime) / 1000);

            if (diff <= 60) {
                strTime = "刚刚";
            } else if (diff <= 60 * 60) {
                strTime = diff / 60 + "分钟前";
            } else if (diff <= 60 * 60 * 24) {
                strTime = diff / (60 * 60) + "小时前";
            } else if (diff <= 60 * 60 * 24 * 365) {
                strTime = tomd(endTime);
            } else {
                strTime = toymd(endTime);
            }

        }

        return strTime;
    }

    //long 转化 月日
    public static String tomd(long dateline) {
        SimpleDateFormat sdf = new SimpleDateFormat("M月d日");
        Date dt = new Date(dateline * 1000);
        return sdf.format(dt);
    }

    //long 转化 年月
    public static String toym(long dateline) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        Date dt = new Date(dateline * 1000);
        return sdf.format(dt);
    }

    //long 转化 年月日
    public static String toymd(long dateline) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
        Date dt = new Date(dateline * 1000);
        return sdf.format(dt);
    }

    /**
     * @param dateline
     * @return 返回yyyy-MM-dd HH:mm:ss格式的日期
     */
    public static String transforMillToDateInfo(long dateline) {
        String content = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 前面的dateline是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
        Date dt = new Date(dateline * 1000);
        content = sdf.format(dt); // 得到精确到秒的表示：2006-08-31 21:08:00
        return content;
    }

    //日期类型转换 yyyy-MM-dd h:mm:ss
    public static CharSequence format(Date date) {
        return DateFormat.format("yyyy-MM-dd h:mm:ss", date);
    }

    /**
     * 将yyyy-MM-dd时间字符串格式化为毫秒
     *
     * @param date
     * @return
     */
    public static long transforDateToMill(String date) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startTime = sdf.parse(date);
            return startTime.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 将yyyy-MM-dd HH:mm时间字符串格式化为秒
     *
     * @param date
     * @return
     */
    public static long transDateToMillTime(String date) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date startTime = sdf.parse(date);
            return startTime.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }


    /**
     * 获取当天的日期   年-月-日
     *
     * @return
     */
    public static final String getDate() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;
        String parse = c.get(Calendar.YEAR) + "-" + month + "-"
                + c.get(Calendar.DAY_OF_MONTH);
        return parse;
    }

    /**
     * 将时间毫秒格式化为字符串
     *
     * @param mill
     * @return
     */
    public static String transforMillToDate(long mill) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date(mill);

        return sdf.format(date);
    }

    /**
     * 将时间毫秒格式化为字符串
     *
     * @param mill
     * @return
     */
    public static String transforMillToMoth(long mill) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Date date = new Date(mill);

        return sdf.format(date);
    }

    /**
     * 将时间毫秒格式化为字符串
     *
     * @param mill
     * @return
     */
    public static String transforMill(long mill) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date date = new Date(mill);

        return sdf.format(date);
    }

    /**
     * 将时间毫秒格式化为字符串
     *
     * @param mill
     * @return
     */
    public static String transforHourMill(long mill) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");

        Date date = new Date(mill);

        return sdf.format(date);
    }

    public static final Integer Str2Second(String s) {
        try {
            int sum = 0;
            String[] ss = s.split("[:：]");
            sum += Integer.parseInt(StringUtils.trimLeadingWhitespace(ss[0])) * 3600;
            sum += Integer.parseInt(StringUtils.trimLeadingWhitespace(ss[1])) * 60;

            if (ss.length == 3) {
                sum += Integer.parseInt(StringUtils.trimLeadingWhitespace(ss[2]));
            }
            return sum;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String GetNowDateChinesne() {
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        temp_str = sdf.format(dt);
        return temp_str;
    }

    public static String GetPevDateChinesne() {
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        temp_str = sdf.format(dt.getTime() - 24 * 60 * 60 * 1000);
        return temp_str;
    }

    /**
     * yyyy年MM月dd日HH时
     **/
    public static String GetNowTimeChinesne(String pattern) {
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        temp_str = sdf.format(dt);
        return temp_str;
    }

    /**
     * yyyy年MM月dd日HH时
     **/
    public static String GetPevTimeChinesne(String pattern) {
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        temp_str = sdf.format(dt.getTime() - 24 * 60 * 60 * 1000);
        return temp_str;
    }


    public static String tranforDatelineToString(long dateline) {
        String temp_str = "";
        Date dt = new Date(dateline * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        temp_str = sdf.format(dt);
        return temp_str;
    }

    /**
     * 获
     *
     * @param time
     * @return
     */
    public static String toByTimeGetString(long time) {
        long currentTime = System.currentTimeMillis() / 1000;
        long time1 = time - currentTime;
        String txt = "";
        if (time1 < 60 * 60 * 24 * 15) {
            txt = "15天以内";
        } else if (time1 > 60 * 60 * 24 * 15 && time1 < 60 * 60 * 24 * 30) {
            txt = "30天以内";
        } else {
            txt = transforMill(time * 1000);
        }
        return txt;
    }

    /**
     * 获
     *
     * @param txt
     * @return
     */
    public static long toByStringGetTime(String txt) {
        long currentTime = System.currentTimeMillis() / 1000;
        long time = 0;
        if (txt.equals("15天以内")) {
            time = currentTime + 60 * 60 * 24 * 15;
        } else if (txt.equals("30天以内")) {
            time = currentTime + 60 * 60 * 24 * 30;
        } else {
            time = transforDateToMill(txt);
        }
        return time;
    }

    /**
     * 求两个日期相差的天数
     *
     * @param date      截至时间
     * @param dateBegin 开始时间
     * @return 两个日期相差的天数
     */
    public static int getDateDays(Date date, Date dateBegin) {
        int days = 0;
        try {
            long betweenTime = date.getTime() - dateBegin.getTime();
            days = (int) (betweenTime / 1000 / 60 / 60 / 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 求两个日期相差的小时
     *
     * @param date      截至时间
     * @param dateBegin 开始时间
     * @return 两个日期相差的小时
     */
    public static int getDateHour(Date date, Date dateBegin) {
        int days = 0;
        try {
            long betweenTime = date.getTime() - dateBegin.getTime();

            days = (int) (betweenTime / (60 * 60 * 1000) % 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    //获得当前年
    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        long currentTime = System.currentTimeMillis();
        String date = sdf.format(new Date(currentTime));
        return date;
    }

    /**
     * 获取某年某月第一天的0点时间
     *
     * @param year
     * @param moth
     * @param day
     * @return
     */
    public static long getMoth00_00(int year, int moth, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, moth - 1, day, 0, 0, 0);
//		Logger.e("getMoth59_59", transforMillToDateInfo(calendar.getTimeInMillis()/1000));
        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * 获取某年某月最后一天59分59秒的时间
     *
     * @param year
     * @param moth
     * @param day
     * @return
     */
    public static long getMoth59_59(int year, int moth, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, moth - 1, day, 0, 0, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis() / 1000;
    }
//    /**
//     * 获取某年某月最后一天59分59秒的时间
//     *
//     * @param year
//     * @param moth
//     * @param day
//     * @return
//     */
//    public static long getMoth59_59(int year, int moth, int day) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(year, moth -1, day, 0, 0, 0);
//        calendar.set(Calendar.HOUR_OF_DAY, 23);
//        calendar.set(Calendar.MINUTE, 59);
//        calendar.set(Calendar.SECOND, 59);
//        calendar.set(Calendar.MILLISECOND, 999);
//        return calendar.getTimeInMillis() / 1000;
//    }

    /**
     * 获取某年某月第一天的0点时间
     *
     * @param year_moth 例如：201609
     * @return
     */
    public static long getMoth00_00(String year_moth) {
        String year = year_moth.substring(0, 4);
        String moth = year_moth.substring(4, year_moth.length());
        return getMoth00_00(Integer.parseInt(year), Integer.parseInt(moth), 1);
    }

    /**
     * 获取某年某月最后一天59分59秒的时间
     *
     * @param year_moth 例如：201609
     * @return
     */
    public static long getMoth59_59(String year_moth) {
        String year = year_moth.substring(0, 4);
        String moth = year_moth.substring(4, year_moth.length());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Integer.parseInt(moth) - 1);
        int maximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return getMoth59_59(Integer.parseInt(year), Integer.parseInt(moth), maximum);
    }


    /**
     * 将时间
     */
    public static String getStrTime(Long cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }
}
