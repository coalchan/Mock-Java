# Mock-Java

[![Build Status](https://travis-ci.org/coalchan/Mock-Java.svg?branch=master)](https://travis-ci.org/coalchan/Mock-Java)
[![Maven Central](https://img.shields.io/maven-central/v/com.luckypeng.mock/mock-parent.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.luckypeng.mock%22%20AND%20a:%22mock-parent%22)

Inspired by [Mock.js](https://github.com/nuysoft/Mock)，并与此做了更多扩展，可以单独使用，也适合大数据场景的使用，比如 spark, flink 等。

## 使用

### 单独使用
```xml
<dependency>
    <groupId>com.luckypeng.mock</groupId>
    <artifactId>mock-java</artifactId>
    <version>1.1</version>
</dependency>
```

参考 [MockTest.java](core/src/test/java/com/luckypeng/mock/core/MockTest.java)

### 在 Spark 中使用

```xml
<dependency>
    <groupId>com.luckypeng.mock</groupId>
    <artifactId>mock-spark-connector</artifactId>
    <version>1.1</version>
</dependency>
```

参考 [SparkConnectorTest.java](spark-connector/src/test/java/com/luckypeng/mock/spark/SparkConnectorTest.java)

### 在 Flink 中使用

```xml
<dependency>
    <groupId>com.luckypeng.mock</groupId>
    <artifactId>mock-flink-connector</artifactId>
    <version>1.1</version>
</dependency>
```

参考 [FlinkMockSourceTest.java](flink-connector/src/test/java/com/luckypeng/mock/flink/FlinkMockSourceTest.java) 和 [PageViewTest.java](flink-connector/src/test/java/com/luckypeng/mock/flink/PageViewTest.java)

### 使用 cli

1. 下载 [Jar](https://repo1.maven.org/maven2/com/luckypeng/mock/mock-cli/1.2/mock-cli-1.2-executable.jar)
2. `mv mock-cli-1.2-executable.jar mock-cli`
3. `chmod +x mock-cli`
4. `mock-cli --help`