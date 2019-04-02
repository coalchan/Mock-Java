package com.luckypeng.mock.core.io;

import lombok.Getter;

/**
 * @author coalchan
 * @date 2019/4/2
 */
@Getter
public enum ResourceType {
    JAR("jar"),
    FILE("file"),
    CLASS_FILE(".class");

    private String typeString;

    ResourceType(String type) {
        this.typeString = type;
    }
}
