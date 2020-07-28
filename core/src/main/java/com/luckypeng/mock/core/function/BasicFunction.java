package com.luckypeng.mock.core.function;

import com.luckypeng.mock.core.function.schema.Functions;
import com.luckypeng.mock.core.function.schema.Function;
import lombok.Getter;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author coalchan
 * @since 1.0
 */
@Functions
public class BasicFunction {
    private BasicFunction() {}

    public static final long DEFAULT_FLOAT_MIN = Long.MIN_VALUE;
    public static final long DEFAULT_FLOAT_MAX = Long.MAX_VALUE;
    public static final int DEFAULT_FLOAT_D_MIN = 0;
    public static final int DEFAULT_FLOAT_D_MAX = 16;
    public static final int DEFAULT_STRING_MIN = 3;
    public static final int DEFAULT_STRING_MAX = 7;

    @Function(alias = {"boolean"})
    public static boolean bool() {
        return RandomUtils.nextBoolean();
    }

    @Function(alias = {"boolean"})
    public static boolean bool(double min, double max, boolean current) {
        return Math.random() > 1.0 / (min + max) * min ? !current : current;
    }

    @Function(alias = {"int"})
    public static long natural() {
        return integer(0);
    }

    @Function(alias = {"int"})
    public static long integer() {
        return integer(Long.MIN_VALUE);
    }

    @Function(alias = {"int", "natural"})
    public static long integer(long min) {
        return integer(min, Long.MAX_VALUE);
    }

    @Function(alias = {"int", "natural"})
    public static long integer(long min, long max) {
        return Math.round(Math.random() * (max - min)) + min;
    }

    @Function(alias = {"float"})
    public static double decimal() {
        return decimal(DEFAULT_FLOAT_MIN);
    }

    @Function(alias = {"float"})
    public static double decimal(long min) {
        return decimal(min, DEFAULT_FLOAT_MAX);
    }

    @Function(alias = {"float"})
    public static double decimal(long min, long max) {
        return decimal(min, max, DEFAULT_FLOAT_D_MIN);
    }

    @Function(alias = {"float"})
    public static double decimal(long min, long max, int dMin) {
        return decimal(min, max, dMin, DEFAULT_FLOAT_D_MAX);
    }

    @Function(alias = {"float"})
    public static double decimal(long min, long max, int dMin, int dMax) {
        return new BigDecimal(integer(min, max) + RandomUtils.nextDouble(0, 1))
                .setScale((int) integer(dMin, dMax), BigDecimal.ROUND_FLOOR).doubleValue();
    }

    @Function(alias = {"char"})
    public static char character() {
        return character(CharacterEnum.ALL);
    }

    @Function(alias = {"char"})
    public static char character(CharacterEnum characterEnum) {
        return character(characterEnum.getPool());
    }

    @Function(alias = {"char"})
    public static char character(String pool) {
        pool = CharacterEnum.searchPool(pool);
        return pool.charAt((int) integer(0, pool.length() - 1));
    }

    @Function(alias = {"str"})
    public static String string() {
        return string(DEFAULT_STRING_MIN, DEFAULT_STRING_MAX);
    }

    @Function(alias = {"str"})
    public static String string(int length) {
        return string(CharacterEnum.ALL, length);
    }

    @Function(alias = {"str"})
    public static String string(int min, int max) {
        return string(CharacterEnum.ALL, min, max);
    }

    @Function(alias = {"str"})
    public static String string(CharacterEnum characterEnum, int min, int max) {
        return string(characterEnum.getPool(), min, max);
    }

    @Function(alias = {"str"})
    public static String string(String pool, int min, int max) {
        return string(pool, (int) integer(min, max));
    }

    @Function(alias = {"str"})
    public static String string(CharacterEnum characterEnum, int length) {
        return string(characterEnum.getPool(), length);
    }

    @Function(alias = {"str"})
    public static String string(String pool, int length) {
        pool = CharacterEnum.searchPool(pool);
        String text = "";
        for (int i = 0; i < length; i++) {
            text += character(pool);
        }
        return text;
    }

    @Function
    public static int[] range(int stop) {
        return range(0, stop);
    }

    @Function
    public static int[] range(int start, int stop) {
        return range(start, stop, 1);
    }

    @Function
    public static int[] range(int start, int stop, int step) {
        int length = (stop - 1 - start) / step + 1;
        int[] result = new int[length];
        int i = 0;
        while (i < length) {
            result[i] = start;
            i++;
            start += step;
        }
        return result;
    }

    /**
     * 从数组中随机选取一个
     * @param array array
     * @param <T> type of element in array
     * @return a random element in array
     */
    public static <T> T pick(T[] array) {
        return array[(int) integer(0, array.length - 1)];
    }

    /**
     * 从列表中随机选取一个
     * @param list list
     * @param <T> type of element in array
     * @return a random element in array
     */
    public static <T> T pick(List<T> list) {
        return list.get((int) integer(0, list.size() - 1));
    }

    /**
     * 从数组中随机选取一个
     * @param array
     * @return
     */
    @Function(alias = "pick")
    public static Object pickOne(Object[] array) {
        return pick(array);
    }

    @Getter
    public enum CharacterEnum {
        /**
         * 字符池
         */
        lower("abcdefghijklmnopqrstuvwxyz"),
        upper("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
        number("0123456789"),
        symbol("!@#$%^&*()[]");

        private String pool;

        CharacterEnum(String pool) {
            this.pool = pool;
        }

        public static String ALL;

        static {
            ALL = "";
            for (CharacterEnum value: CharacterEnum.values()) {
                ALL += value.pool;
            }
        }

        public static String searchPool(String pool) {
            CharacterEnum existPool = EnumUtils.getEnum(CharacterEnum.class, pool);
            if (existPool != null) {
                return existPool.getPool();
            }
            return pool;
        }
    }
}
