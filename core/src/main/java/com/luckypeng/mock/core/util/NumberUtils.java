package com.luckypeng.mock.core.util;

/**
 * @author coalchan
 * @date 2019/4/5
 */
public class NumberUtils {
    private NumberUtils() {}

    public static Integer parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Long parseLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public enum Operation {
        /**
         * 加减乘除
         */
        add, subtract, multiply, divide
    }

    /**
     * 两个数字的四则运算，并返回具体类型计算的结果类型
     * @param n1
     * @param n2
     * @param operation
     * @return
     */
    public static Number compute(Number n1, Number n2, Operation operation) {
        Double d1 = null, d2 = null;
        Long g1 = null, g2 = null;
        if (n1 instanceof Double || n1 instanceof Float) {
            // 浮点数一律转化为 double 来计算
            d1 = n1.doubleValue();
        } else {
            // 整数一律转化为 long 来计算
            g1 = n1.longValue();
        }
        if (n2 instanceof Double || n2 instanceof Float) {
            d2 = n2.doubleValue();
        } else {
            g2 = n2.longValue();
        }
        if (d1 != null) {
            if (d2 != null) {
                return compute(d1, d2, operation);
            } else {
                return compute(d1, g2, operation);
            }
        } else {
            if (d2 != null) {
                return compute(g1, d2, operation);
            } else {
                return compute(g1, g2, operation);
            }
        }
    }

    private static Number compute(Double n1, Double n2, Operation operation) {
        switch (operation) {
            case add: return n1 + n2;
            case subtract: return n1 - n2;
            case multiply: return n1 * n2;
            case divide: return n1 / n2;
            default: throw new RuntimeException("不支持 Double 与 Double 之间的该运算: " + operation);
        }
    }

    private static Number compute(Long n1, Double n2, Operation operation) {
        switch (operation) {
            case add: return n1 + n2;
            case subtract: return n1 - n2;
            case multiply: return n1 * n2;
            case divide: return n1 / n2;
            default: throw new RuntimeException("不支持 Long 与 Double 之间的该运算: " + operation);
        }
    }

    private static Number compute(Double n1, Long n2, Operation operation) {
        switch (operation) {
            case add: return n1 + n2;
            case subtract: return n1 - n2;
            case multiply: return n1 * n2;
            case divide: return n1 / n2;
            default: throw new RuntimeException("不支持 Double 与 Long 之间的该运算: " + operation);
        }
    }

    private static Number compute(Long n1, Long n2, Operation operation) {
        switch (operation) {
            case add: return n1 + n2;
            case subtract: return n1 - n2;
            case multiply: return n1 * n2;
            case divide: return n1 / n2;
            default: throw new RuntimeException("不支持 Long 与 Long 之间的该运算: " + operation);
        }
    }
}
