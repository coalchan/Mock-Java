package com.luckypeng.mock.core.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;
import java.util.Locale;

/**
 * @author coalchan
 * @since 1.0
 */
public class DateUtils {
    private DateUtils() {}

    /**
     * 日期格式：yyyyMMdd
     */
    public static final String DEFAULT_DATE_YYYY_MM_DD = "yyyyMMdd";

    /**
     * 默认日期格式：yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 默认时间格式：HH:mm:ss
     */
    public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

    /**
     * 默认时间格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将日期字符串转换为java.util.Date对象
     * @param dateString 日期字符串
     * @param pattern 日期格式
     * @return 日期
     */
    public static Date toDate(String dateString, String pattern) {
        return DateTime.parse(dateString, DateTimeFormat.forPattern(pattern)).toDate();
    }

    /**
     * 将java.util.Date对象转换为字符串
     * @param date 日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String toDateTimeString(Date date, String pattern) {
        return new DateTime(date).toString(pattern, Locale.CHINESE);
    }

    /**
     * 时间戳转化
     * @param ts 毫秒级时间戳
     * @return 日期
     */
    public static Date fromTimeStamp(long ts) {
        return new DateTime(ts).toDate();
    }
}


