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
import java.util.Random;

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
     * default delay
     */
    private static boolean noDelay = false;

    /**
     * default delay milliseconds: 10000
     */
    private static long maxDelayMsecs = 10000;

    /**
     * default rate: 1000 records/s
     */
    private static RateLimiter rateLimiter = RateLimiter.create(1000);

    private volatile boolean isRunning = true;

    private static final String EVENT_TIME_KEY_NAME = "eventTime";
    private static final Random RANDOM = new Random(1024);

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
            delayWithEventTime(data);
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
     * if has key of event time, then delay it with random value
     * @param data record
     */
    private void delayWithEventTime(JSONObject data) {
        if (!noDelay && data.containsKey(EVENT_TIME_KEY_NAME)) {
            data.put(EVENT_TIME_KEY_NAME, data.getLongValue(EVENT_TIME_KEY_NAME) - getRandomDelayMsecs());
        }
    }

    /**
     * Get Gaussian delay
     * @return random delay
     */
    private long getRandomDelayMsecs() {
        long delay = -1;
        long x = maxDelayMsecs / 2;
        while(delay < 0 || delay > maxDelayMsecs) {
            delay = (long)(RANDOM.nextGaussian() * x) + x;
        }
        return delay;
    }

    /**
     * set delay, if take 0 then there is no delay
     * @param maxDelaySecs max delay seconds
     */
    public FlinkMockSource<T> setMaxDelayMsecs(long maxDelaySecs) {
        if (maxDelaySecs == 0) {
            noDelay = true;
        }
        maxDelayMsecs = maxDelaySecs * 1000;
        return this;
    }

    /**
     * config rate
     * @param permitsPerSecond records per second
     */
    public FlinkMockSource<T> setRate(double permitsPerSecond) {
        rateLimiter.setRate(permitsPerSecond);
        return this;
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
