package com.luckypeng.mock.flink;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.luckypeng.mock.core.Mock;
import com.luckypeng.mock.flink.serialization.DefaultMockDeserializationSchema;
import com.luckypeng.mock.flink.serialization.MockDeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.ResultTypeQueryable;
import org.apache.flink.shaded.guava18.com.google.common.util.concurrent.RateLimiter;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

import java.util.LinkedHashMap;

import static org.apache.flink.util.Preconditions.checkNotNull;

/**
 * @author coalchan
 * @since 1.0
 */
public class FlinkMockSource<T> extends RichParallelSourceFunction<T> implements ResultTypeQueryable<T> {
    private static final long serialVersionUID = 6488827819119578895L;

    private JSONObject template;
    private MockDeserializationSchema<T> deserializer;
    /**
     * default rate: 1000 records/s
     */
    private static RateLimiter rateLimiter = RateLimiter.create(1000);
    private volatile boolean isRunning = true;

    public FlinkMockSource(String template, Class<T> clazz) {
        checkNotNull(template, "template cannot be null");
        this.template = fromJsonString(template);
        this.deserializer = new DefaultMockDeserializationSchema(clazz);
    }

    public FlinkMockSource(JSONObject template, Class<T> clazz) {
        this.template = checkNotNull(template, "template cannot be null");
        this.deserializer = new DefaultMockDeserializationSchema(clazz);
    }

    public FlinkMockSource(JSONObject template, MockDeserializationSchema deserializer) {
        this.template = checkNotNull(template, "template cannot be null");
        this.deserializer = checkNotNull(deserializer, "deserializer cannot be null");
    }

    @Override
    public void run(SourceContext<T> ctx) throws Exception {
        while (isRunning) {
            JSONObject data = Mock.mock(template);
            T result = deserializer.deserialize(data);
            ctx.collect(result);
            rateLimiter.acquire();
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }

    /**
     * config rate
     * @param permitsPerSecond records per second
     */
    public void setRate(double permitsPerSecond) {
        rateLimiter.setRate(permitsPerSecond);
    }

    @Override
    public TypeInformation<T> getProducedType() {
        return deserializer.getProducedType();
    }

    /**
     * json string to json object
     * @param jsonTemplate json string
     * @return json object
     */
    private JSONObject fromJsonString(String jsonTemplate) {
        LinkedHashMap<String, Object> map =
                JSON.parseObject(jsonTemplate, new TypeReference<LinkedHashMap<String, Object>>(){});
        return new JSONObject(map);
    }
}
