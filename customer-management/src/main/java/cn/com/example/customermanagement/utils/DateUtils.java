package cn.com.example.customermanagement.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author 日期转换线程安全类
 */
public class DateUtils {

    final static Map<String, ThreadLocal<DateFormat>> threadLocalPool = new HashMap<String, ThreadLocal<DateFormat>>();
    final static SimpleDateFormat YYYYMMddSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    final static ThreadLocal<DateFormat> DefaultThreadLocal = new ThreadLocal<DateFormat>() {
        @Override
        protected synchronized DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    static {
        threadLocalPool.put("yyyy-MM-dd HH:mm:ss", DefaultThreadLocal);
    }

    public static Date parse(String dateStr) throws ParseException {

        return DefaultThreadLocal.get().parse(dateStr);

    }

    public static Date parse(final String format, String dateStr) throws ParseException {
        ThreadLocal<DateFormat> threadLocal = threadLocalPool.get(format);
        if (threadLocal == null) {
            threadLocal = new ThreadLocal<DateFormat>() {
                @Override
                protected synchronized DateFormat initialValue() {
                    return new SimpleDateFormat(format);
                }
            };
            threadLocalPool.put(format, threadLocal);
        }
        return threadLocal.get().parse(dateStr);

    }

    public static String format(Date date) {
        return DefaultThreadLocal.get().format(date);
    }
    
    public static String format(final String format,Date date) {
        ThreadLocal<DateFormat> threadLocal = threadLocalPool.get(format);
        if (threadLocal == null) {
            threadLocal = new ThreadLocal<DateFormat>() {
                @Override
                protected synchronized DateFormat initialValue() {
                    return new SimpleDateFormat(format);
                }
            };
            threadLocalPool.put(format, threadLocal);
        }
        return threadLocal.get().format(date);
    }


    public static Date dateAddY(Date date,Integer num) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, num);//把日期往后增加一年.整数往后推,负数往前移动
        date=calendar.getTime();   //这个时间就是日期往后推一天的结果
        return date;
    }
    public static Date dateAddM(Date date,Integer num) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, num);//把日期往后增加一个月.整数往后推,负数往前移动
        date=calendar.getTime();   //这个时间就是日期往后推一天的结果
        return date;
    }
    public static Date dateAddD(Date date,Integer num) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,num);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime();   //这个时间就是日期往后推一天的结果
        return date;
    }

    /**
     * 获取yyyy-MM-dd格式的日期字符串
     * @return
     */
    public static String getDateYYYYMMdd() {
        return YYYYMMddSimpleDateFormat.format(new Date());
    }


}
