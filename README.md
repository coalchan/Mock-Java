# Mock-Java

[![Build Status](https://travis-ci.org/coalchan/Mock-Java.svg?branch=master)](https://travis-ci.org/coalchan/Mock-Java)
[![Maven Central](https://img.shields.io/maven-central/v/com.luckypeng.mock/mock-parent.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.luckypeng.mock%22%20AND%20a:%22mock-parent%22)

[Mock.js](https://github.com/nuysoft/Mock) 的 Java 版本。

## 使用

## 1.0 版本

```xml
<dependency>
    <groupId>com.luckypeng.mock</groupId>
    <artifactId>mock-java</artifactId>
    <version>1.0</version>
</dependency>
``` 

## 1.1-SNAPSHOT 版本（支持了 pick 函数）

```xml
<dependency>
    <groupId>com.luckypeng.mock</groupId>
    <artifactId>mock-java</artifactId>
    <version>1.1-SNAPSHOT</version>
</dependency>
```

注意由于是 SNAPSHOT 版本，所以需要在 pom.xml 引入：

```xml
<repositories>
    <repository>
        <id>oss-snapshots</id>
        <url>https://oss.sonatype.org/content/repositories/snapshots</url>
        <releases>
            <enabled>false</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
```
