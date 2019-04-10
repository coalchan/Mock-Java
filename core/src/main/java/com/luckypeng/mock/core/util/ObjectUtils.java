package com.luckypeng.mock.core.util;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @author coalchan
 * @since 1.0
 */
public class ObjectUtils {
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

        // else
        return false;
    }

    public static <T> T coalesce(T... objs) {
        for (T obj: objs) {
            if (obj != null) {
                return obj;
            }
        }
        return null;
    }

    /**
     * 读取文件到字符串
     * @param fileName relative path of file
     * @return string of file
     */
    public static String fromFile(String fileName) {
        InputStream inputStream = ObjectUtils.class.getClassLoader().getResourceAsStream(fileName);
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
