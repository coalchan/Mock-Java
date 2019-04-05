package com.luckypeng.mock.core.function;

import com.luckypeng.mock.core.function.schema.Function;
import com.luckypeng.mock.core.function.schema.FunctionInfo;
import lombok.Getter;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;

/**
 * @author coalchan
 * @date 2019/4/2
 */
@Function
public class BasicFunction {
    private BasicFunction() {}

    public static final long DEFAULT_FLOAT_MIN = Long.MIN_VALUE;
    public static final long DEFAULT_FLOAT_MAX = Long.MAX_VALUE;
    public static final int DEFAULT_FLOAT_D_MIN = 0;
    public static final int DEFAULT_FLOAT_D_MAX = 16;

    @FunctionInfo(alias = {"boolean"}, desc = "返回一个随机的布尔值")
    public static boolean bool() {
        return RandomUtils.nextBoolean();
    }

    @FunctionInfo(alias = {"boolean"}, desc = "给定概率值min和max以及min对应的current，返回一个随机的布尔值")
    public static boolean bool(double min, double max, boolean current) {
        return Math.random() > 1.0 / (min + max) * min ? !current : current;
    }

    @FunctionInfo(alias = {"int"}, desc = "返回一个随机的自然数（大于等于 0 的整数）")
    public static long natural() {
        return integer(0);
    }

    @FunctionInfo(alias = {"int"}, desc = "返回一个随机的整数")
    public static long integer() {
        return integer(Long.MIN_VALUE);
    }

    @FunctionInfo(alias = {"int", "natural"}, desc = "给定最小整数min，返回一个随机的整数")
    public static long integer(long min) {
        return integer(min, Long.MAX_VALUE);
    }

    @FunctionInfo(alias = {"int", "natural"}, desc = "给定最小整数min，最大整数max，返回一个随机的整数")
    public static long integer(long min, long max) {
        return Math.round(Math.random() * (max - min)) + min;
    }

    @FunctionInfo(alias = {"float"}, desc = "返回一个随机的浮点数")
    public static double _float() {
        return _float(DEFAULT_FLOAT_MIN);
    }

    @FunctionInfo(alias = {"float"}, desc = "给定整数部分的最小值min，返回一个随机的浮点数")
    public static double _float(long min) {
        return _float(min, DEFAULT_FLOAT_MAX);
    }

    @FunctionInfo(alias = {"float"}, desc = "给定整数部分的最小值min，整数部分的最大值max，返回一个随机的浮点数")
    public static double _float(long min, long max) {
        return _float(min, max, DEFAULT_FLOAT_D_MIN);
    }

    @FunctionInfo(alias = {"float"}, desc = "给定整数部分的最小值min，整数部分的最大值max，小数部分位数的最小值dmin，返回一个随机的浮点数")
    public static double _float(long min, long max, int dMin) {
        return _float(min, max, dMin, DEFAULT_FLOAT_D_MAX);
    }

    @FunctionInfo(alias = {"float"}, desc = "给定整数部分的最小值min，整数部分的最大值max，小数部分位数的最小值dmin，小数部分位数的最大值dmax，返回一个随机的浮点数")
    public static double _float(long min, long max, int dMin, int dMax) {
        return new BigDecimal(integer(min, max) + RandomUtils.nextDouble(0, 1)).setScale((int) integer(dMin, dMax), BigDecimal.ROUND_FLOOR).doubleValue();
    }

    @FunctionInfo(alias = {"char"}, desc = "从已有的字符池中返回一个随机字符")
    public static Character character() {
        return character(CharacterEnum.ALL);
    }

    @FunctionInfo(alias = {"char"}, desc = "选择一个已有的字符池，从中返回一个随机字符")
    public static Character character(CharacterEnum characterEnum) {
        return character(characterEnum.getPool());
    }

    @FunctionInfo(alias = {"char"}, desc = "给定一个字符池，从中返回一个随机字符")
    public static Character character(String pool) {
        pool = CharacterEnum.searchPool(pool);
        return pool.charAt(RandomUtils.nextInt(0, pool.length() - 1));
    }

    @FunctionInfo(alias = {"str"}, desc = "从已有的字符池中，从中选择指定长度在3和7之间的随机字符串")
    public static String string() {
        return string(3, 7);
    }

    @FunctionInfo(alias = {"str"}, desc = "从已有的字符池中，从中选择指定长度为length的随机字符串")
    public static String string(int length) {
        return string(CharacterEnum.ALL, length);
    }

    @FunctionInfo(alias = {"str"}, desc = "从已有的字符池中，给定最小长度min，最大长度max，从中选择随机字符串")
    public static String string(int min, int max) {
        return string(CharacterEnum.ALL, min, max);
    }

    @FunctionInfo(alias = {"str"}, desc = "选择一个字符池，最小长度min，最大长度max，从中选择随机字符串")
    public static String string(CharacterEnum characterEnum, int min, int max) {
        return string(characterEnum.getPool(), min, max);
    }

    @FunctionInfo(alias = {"str"}, desc = "给定一个字符池，最小长度min，最大长度max，从中选择随机字符串")
    public static String string(String pool, int min, int max) {
        return string(pool, (int) integer(min, max));
    }

    @FunctionInfo(alias = {"str"}, desc = "选择一个字符池，从中选择指定长度为length的随机字符串")
    public static String string(CharacterEnum characterEnum, int length) {
        return string(characterEnum.getPool(), length);
    }

    @FunctionInfo(alias = {"str"}, desc = "给定一个字符池，从中选择指定长度为length的随机字符串")
    public static String string(String pool, int length) {
        pool = CharacterEnum.searchPool(pool);
        String text = "";
        for (int i = 0; i < length; i++) {
            text += character(pool);
        }
        return text;
    }

    @FunctionInfo(desc = "给定结束值stop（不包含），起始值为0，步长为1，返回一个整型数组")
    public static int[] range(int stop) {
        return range(0, stop);
    }

    @FunctionInfo(desc = "给定起始值start, 结束值stop（不包含），步长为1，返回一个整型数组")
    public static int[] range(int start, int stop) {
        return range(start, stop, 1);
    }

    @FunctionInfo(desc = "给定起始值start, 结束值stop（不包含），以及步长step，返回一个整型数组")
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
     * @param array
     * @param <T>
     * @return
     */
    public static <T> T pick(T[] array) {
        return array[(int) integer(0, array.length - 1)];
    }

    @Getter
    public enum CharacterEnum {
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
