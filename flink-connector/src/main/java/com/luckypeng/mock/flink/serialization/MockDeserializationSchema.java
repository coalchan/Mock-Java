package com.luckypeng.mock.flink.serialization;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.java.typeutils.ResultTypeQueryable;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author coalchan
 * @since 1.0
 */
public interface MockDeserializationSchema<T> extends Serializable, ResultTypeQueryable<T> {
    /**
     * Deserializes the byte message.
     *
     * @param data Mock data, as a JSON Object.
     *
     * @return The deserialized message as an object (null if the message cannot be deserialized).
     * @throws IOException exception while in deserializing data
     */
    T deserialize(JSONObject data) throws IOException;
}
