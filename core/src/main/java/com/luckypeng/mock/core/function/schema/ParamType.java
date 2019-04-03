package com.luckypeng.mock.core.function.schema;

/**
 * @author coalchan
 * @date 2019/4/3
 */
public class ParamType {
    private static final int INT = 1;
    private static final int LONG = 2;
    private static final int DOUBLE = 3;
    private static final int BOOLEAN = 4;
    private static final int STRING = 5;

    private static final String TRUE = "true";
    private static final String FALSE = "false";

    public static Object toSpecificType(String value) {
        if (TRUE.equals(value) || FALSE.equals(value)) {
            return Boolean.parseBoolean(value);
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e1) {
                try {
                    return Double.parseDouble(value);
                } catch (NumberFormatException e2) {
                    return value;
                }
            }
        }
    }

    /**
     * 参数类型是否匹配
     * @param value
     * @param type
     * @return
     */
    public static boolean isFitType(Object value, Class type) {
        int classType = getValueType(value);
        type = getNonPrimitiveClass(type);
        switch (classType) {
            case INT:
                return type == Integer.class || type == Long.class;
            case LONG:
                return type == Long.class;
            case DOUBLE:
                return type == Double.class;
            case BOOLEAN:
                return type == Boolean.class;
            case STRING:
                return type == String.class;
            default:
                return false;
        }
    }

    private static int getValueType(Object value) {
        if (value instanceof Integer) {
            return INT;
        } else if (value instanceof Long) {
            return LONG;
        } else if (value instanceof Double) {
            return DOUBLE;
        } else if (value instanceof Boolean) {
            return BOOLEAN;
        } else if (value instanceof String) {
            return STRING;
        } else {
            throw new RuntimeException("不支持该数据类型的解析: " + value);
        }
    }

    /**
     * Convert primitive class names to java.lang.* class names.
     *
     * @param clazz the class (for example: int)
     * @return the non-primitive class (for example: java.lang.Integer)
     */
    public static Class<?> getNonPrimitiveClass(Class<?> clazz) {
        if (!clazz.isPrimitive()) {
            return clazz;
        } else if (clazz == boolean.class) {
            return Boolean.class;
        } else if (clazz == byte.class) {
            return Byte.class;
        } else if (clazz == char.class) {
            return Character.class;
        } else if (clazz == double.class) {
            return Double.class;
        } else if (clazz == float.class) {
            return Float.class;
        } else if (clazz == int.class) {
            return Integer.class;
        } else if (clazz == long.class) {
            return Long.class;
        } else if (clazz == short.class) {
            return Short.class;
        } else if (clazz == void.class) {
            return Void.class;
        }
        return clazz;
    }
}
