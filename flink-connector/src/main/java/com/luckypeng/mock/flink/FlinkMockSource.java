package com.luckypeng.mock.flink;

import com.alibaba.fastjson.JSONObject;
import com.luckypeng.mock.core.Mock;
import com.luckypeng.mock.flink.serialization.DefaultMockDeserializationSchema;
import com.luckypeng.mock.flink.serialization.MockDeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.ResultTypeQueryable;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

import static org.apache.flink.util.Preconditions.checkNotNull;

/**
 * @author coalchan
 * @since 1.0
 */
public class FlinkMockSource<T> extends RichParallelSourceFunction<T> implements ResultTypeQueryable<T> {
    private static final long serialVersionUID = 6488827819119578895L;

    private JSONObject template;
    private MockDeserializationSchema<T> deserializer;

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
        for (int i = 0; i < 1000; i++) {
            JSONObject data = Mock.mock(template);
            T result = deserializer.deserialize(data);
            ctx.collect(result);
        }
    }

    @Override
    public void cancel() {

    }

    @Override
    public TypeInformation<T> getProducedType() {
        return deserializer.getProducedType();
    }
}
