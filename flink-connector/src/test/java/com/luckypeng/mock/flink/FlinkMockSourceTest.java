package com.luckypeng.mock.flink;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.Test;

/**
 * @author coalchan
 * @date 2019/4/8
 */
public class FlinkMockSourceTest {
    @Test
    public void test() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        JSONObject template = new JSONObject();
        template.put("age", "@int(1, 100)");
        template.put("name", "@cname()");
        template.put("gender", "@bool()");
        JSONArray educations = new JSONArray(10);
        educations.fluentAdd("college").fluentAdd("master").fluentAdd("doctor");
        template.put("education|1", educations);

        FlinkMockSource<Person> source = new FlinkMockSource(template, Person.class);
        DataStream<Person> dataStream = env.addSource(source);
        dataStream.print();
        env.execute();
    }
}

@Data
class Person {
    private int age;
    private String name;
    private String education;
}