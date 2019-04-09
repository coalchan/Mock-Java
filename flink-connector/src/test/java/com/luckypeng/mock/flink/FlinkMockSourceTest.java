package com.luckypeng.mock.flink;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.luckypeng.mock.flink.serialization.JsonMockDeserializationSchema;
import com.luckypeng.mock.flink.serialization.LineMockDeserializationSchema;
import lombok.Data;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author coalchan
 * @date 2019/4/8
 */
public class FlinkMockSourceTest {
    @Ignore
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

    @Ignore
    @Test
    public void testLine() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 注意：JSON 要有序，否则输出可能会对应不上
        JSONObject template = new JSONObject(true);
        template.put("age", "@int(1, 100)");
        template.put("name", "@cname()");
        template.put("gender", "@bool()");
        JSONArray educations = new JSONArray(10);
        educations.fluentAdd("college").fluentAdd("master").fluentAdd("doctor");
        template.put("education|1", educations);

        FlinkMockSource<Person> source = new FlinkMockSource(template, new LineMockDeserializationSchema());
        DataStream<Person> dataStream = env.addSource(source);
        dataStream.print();
        env.execute();
    }

    @Ignore
    @Test
    public void testJSON() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        JSONObject template = new JSONObject();
        template.put("age", "@int(1, 100)");
        template.put("name", "@cname()");
        template.put("gender", "@bool()");
        JSONArray educations = new JSONArray(10);
        educations.fluentAdd("college").fluentAdd("master").fluentAdd("doctor");
        template.put("education|1", educations);

        FlinkMockSource<Person> source = new FlinkMockSource(template, new JsonMockDeserializationSchema());
        DataStream<Person> dataStream = env.addSource(source);
        dataStream.print();
        env.execute();
    }
}

@Data
class Person {
    private int age;
    private String name;
    private boolean gender;
    private String education;
}