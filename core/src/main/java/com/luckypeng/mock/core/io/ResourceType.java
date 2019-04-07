package com.luckypeng.mock.core.io;

import lombok.Getter;

/**
 * @author coalchan
 * @date 2019/4/2
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
