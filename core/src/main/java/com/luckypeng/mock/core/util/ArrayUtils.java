package com.luckypeng.mock.core.util;

/**
 * @author coalchan
 * @since 1.0
 */
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {
    public static <T> T[] concatArrays(T[]... arrays) {
        if (arrays == null) {
            return null;
        }
        T[] result = arrays[0];
        for (int i = 1; i < arrays.length; i++) {
            result = org.apache.commons.lang3.ArrayUtils.addAll(result, arrays[i]);
        }
        return result;
    }
}
