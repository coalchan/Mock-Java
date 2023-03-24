package com.luckypeng.mock.spark;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.unsafe.types.UTF8String;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author coalchan
 */
public class ValueConverters {
    public static final Function<String, Integer> INT_CONVERTER = value -> value == null ? null : Integer.parseInt(value);
    public static final Function<String, Long> LONG_CONVERTER = value -> value == null ? null : Long.parseLong(value);
    public static final Function<String, Float> FLOAT_CONVERTER = value -> value == null ? null : Float.parseFloat(value);
    public static final Function<String, Double> DOUBLE_CONVERTER = value -> value == null ? null : Double.parseDouble(value);
    public static final Function<String, UTF8String> UTF8_STRING_CONVERTER = UTF8String::fromString;

    public static Map<String, Function<String, ?>> getConverters(StructType schema) {
        StructField[] fields = schema.fields();
        Map<String, Function<String, ?>> valueConverters = new HashMap<>(fields.length);
        Arrays.stream(fields).forEach(field -> {
            if (field.dataType().equals(DataTypes.IntegerType)) {
                valueConverters.put(field.name(), INT_CONVERTER);
            } else if (field.dataType().equals(DataTypes.LongType)) {
                valueConverters.put(field.name(), LONG_CONVERTER);
            } else if (field.dataType().equals(DataTypes.FloatType)) {
                valueConverters.put(field.name(), FLOAT_CONVERTER);
            } else if (field.dataType().equals(DataTypes.DoubleType)) {
                valueConverters.put(field.name(), DOUBLE_CONVERTER);
            } else if (field.dataType().equals(DataTypes.StringType)) {
                valueConverters.put(field.name(), UTF8_STRING_CONVERTER);
            } else {
                throw new IllegalArgumentException("暂不支持类型" + field.dataType().typeName());
            }
        });
        return valueConverters;
    }
}
