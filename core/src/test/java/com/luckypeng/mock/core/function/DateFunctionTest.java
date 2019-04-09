package com.luckypeng.mock.core.function;

import com.luckypeng.mock.core.util.DateUtils;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static com.luckypeng.mock.core.function.DateFunction.*;

public class DateFunctionTest {

    @Test
    public void testDatetime() {
        for (int i = 0; i < 100; i++) {
            assertThat(date(),
                    allOf(greaterThanOrEqualTo("1970-01-01"),
                            lessThanOrEqualTo(DateUtils.toDateTimeString(new Date(), DateUtils.DEFAULT_DATE_PATTERN))));
            assertThat(datetime(),
                    allOf(greaterThanOrEqualTo("1970-01-01 09:00:00"),
                            lessThanOrEqualTo(
                                    DateUtils.toDateTimeString(new Date(), DateUtils.DEFAULT_DATETIME_PATTERN))));
        }
    }

    @Test
    public void testTime() {
        for (int i = 0; i < 100; i++) {
            assertThat(time(), allOf(greaterThanOrEqualTo("00:00:00"), lessThanOrEqualTo("23:59:59")));
        }
    }

    @Test
    public void testNow() {
        assertEquals(now("year"),
                DateUtils.toDateTimeString(new Date(), "yyyy-01-01 00:00:00"));
        assertEquals(now("month"),
                DateUtils.toDateTimeString(new Date(), "yyyy-MM-01 00:00:00"));
        assertEquals(now("day"),
                DateUtils.toDateTimeString(new Date(), "yyyy-MM-dd 00:00:00"));
        assertEquals(now("hour"),
                DateUtils.toDateTimeString(new Date(), "yyyy-MM-dd HH:00:00"));
        assertEquals(now("minute"),
                DateUtils.toDateTimeString(new Date(), "yyyy-MM-dd HH:mm:00"));
        assertEquals(now("second"),
                DateUtils.toDateTimeString(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }
}