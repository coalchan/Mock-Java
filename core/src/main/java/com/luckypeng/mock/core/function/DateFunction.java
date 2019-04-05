package com.luckypeng.mock.core.function;

import com.luckypeng.mock.core.function.schema.Functions;
import com.luckypeng.mock.core.function.schema.Function;
import com.luckypeng.mock.core.util.DateUtils;
import org.joda.time.DateTime;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author coalchan
 * @date 2019/4/4
 */
@Functions
public class DateFunction {
    private DateFunction() {}

    private static final Pattern RE_UNIT = Pattern.compile("year|month|day|hour|minute|second|week");

    @Function
    public static String datetime() {
        return datetime(DateUtils.DEFAULT_DATETIME_PATTERN);
    }

    @Function
    public static String date() {
        return datetime(DateUtils.DEFAULT_DATE_PATTERN);
    }

    @Function
    public static String time() {
        return datetime(DateUtils.DEFAULT_TIME_PATTERN);
    }

    @Function(alias = {"date", "time"})
    public static String datetime(String format) {
        long ts = BasicFunction.integer(0, System.currentTimeMillis());
        return DateUtils.toDateTimeString(DateUtils.fromTimeStamp(ts), format);
    }

    @Function
    public static String now() {
        return now(DateUtils.DEFAULT_DATETIME_PATTERN);
    }

    @Function
    public static String now(String unitOrFormat) {
        String unit = "";
        String format = DateUtils.DEFAULT_DATETIME_PATTERN;
        Matcher matcher = RE_UNIT.matcher(unitOrFormat);
        if (matcher.matches()) {
            unit = unitOrFormat;
        } else {
            format = unitOrFormat;
        }
        return now(unit, format);
    }

    @Function
    public static String now(String unit, String format) {
        DateTime dateTime = DateTime.now();

        switch (unit) {
            case "year":
                dateTime = dateTime.withMonthOfYear(1);
            case "month":
                dateTime = dateTime.withDayOfMonth(1);
            case "week":
            case "day":
                dateTime = dateTime.withHourOfDay(0);
            case "hour":
                dateTime = dateTime.withMinuteOfHour(0);
            case "minute":
                dateTime = dateTime.withSecondOfMinute(0);
            case "second":
                dateTime = dateTime.withMillisOfSecond(0);
            default:
                break;
        }
        if ("week".equals(unit)) {
            dateTime = dateTime.withDayOfWeek(1);
        }
        return dateTime.toString(format, Locale.CHINESE);
    }
}
