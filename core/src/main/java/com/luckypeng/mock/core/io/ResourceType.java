package com.luckypeng.mock.core.io;

import lombok.Getter;

/**
 * @author coalchan
 * @since 1.0
 */
@Getter
public enum ResourceType {
    /**
     * 资源类型：jar 包、文件目录、class 文件
     */
    JAR("jar"),
    FILE("file"),
    CLASS_FILE(".class");

    private String typeString;

    ResourceType(String type) {
        this.typeString = type;
    }
}
