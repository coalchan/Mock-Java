package com.luckypeng.mock.flink.serialization;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

/**
 * @author coalchan
 * @date 2019/4/9
 */
public class JsonMockDeserializationSchema implements MockDeserializationSchema<String> {
    private static final long serialVersionUID = -5517924869302002505L;

    @Override
    public String deserialize(JSONObject data) throws IOException {
        return data.toJSONString();
    }

    @Override
    public TypeInformation<String> getProducedType() {
        return TypeInformation.of(String.class);
    }
}
