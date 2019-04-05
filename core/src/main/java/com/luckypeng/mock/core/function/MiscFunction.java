package com.luckypeng.mock.core.function;

import com.alibaba.fastjson.JSON;
import com.luckypeng.mock.core.function.schema.Function;
import com.luckypeng.mock.core.function.schema.Functions;
import com.luckypeng.mock.core.util.DateUtils;
import com.luckypeng.mock.core.util.ObjectUtils;
import com.luckypeng.mock.core.function.BasicFunction.CharacterEnum;

import java.util.UUID;

/**
 * @author coalchan
 * @date 2019/4/5
 */
@Functions
public class MiscFunction {
    private MiscFunction() {}

    private static final String[] zips =
            JSON.parseObject(ObjectUtils.fromFile("china-zip.json")).keySet().toArray(new String[]{});

    private static long base = 0;

    @Function
    public static String guid() {
        return UUID.randomUUID().toString();
    }

    @Function
    public static String id() {
        return BasicFunction.pick(zips) +
                DateFunction.datetime(DateUtils.DEFAULT_DATE_YYYYMMdd) +
                BasicFunction.string(CharacterEnum.number, 3) +
                BasicFunction.character(CharacterEnum.number.getPool() + "X");
    }

    @Function
    public static long increment() {
        return increment(1);
    }

    @Function
    public static long increment(long step) {
        base += step;
        return base;
    }
}
