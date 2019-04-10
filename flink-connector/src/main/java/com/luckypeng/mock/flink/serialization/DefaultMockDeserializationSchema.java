package com.luckypeng.mock.flink.serialization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

/**
 * @author coalchan
 * @since 1.0
 */
public class DefaultMockDeserializationSchema<T> implements MockDeserializationSchema<T> {
    private static final long serialVersionUID = -7810853850065207962L;
    private Class<T> clazz;

    public DefaultMockDeserializationSchema(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T deserialize(JSONObject data) throws IOException {
        return JSON.toJavaObject(data, clazz);
    }

    @Override
    public TypeInformation<T> getProducedType() {
        return TypeInformation.of(clazz);
    }
}
