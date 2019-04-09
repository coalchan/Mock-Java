package com.luckypeng.mock.flink.serialization;

import com.alibaba.fastjson.JSONObject;
import com.luckypeng.mock.core.util.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

/**
 * @author coalchan
 * @date 2019/4/9
 */
public class LineMockDeserializationSchema implements MockDeserializationSchema<String> {
    private static final long serialVersionUID = -3674191250641373039L;

    private static final String DEFAULT_DELIMITER = "\u0001";

    /**
     * 分隔符
     */
    private String delimiter;

    public LineMockDeserializationSchema() {
        this.delimiter = DEFAULT_DELIMITER;
    }

    public LineMockDeserializationSchema(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public String deserialize(JSONObject data) throws IOException {
        if (!ObjectUtils.isEmpty(data)) {
            return StringUtils.join(data.values(), delimiter);
        }
        return "";
    }

    @Override
    public TypeInformation<String> getProducedType() {
        return TypeInformation.of(String.class);
    }
}
