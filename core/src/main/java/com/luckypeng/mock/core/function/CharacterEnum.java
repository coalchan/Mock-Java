package com.luckypeng.mock.core.function;

import lombok.Getter;
import org.apache.commons.lang3.EnumUtils;

/**
 * @author coalchan
 * @date 2019/4/2
 */
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
