package com.luckypeng.mock.spark;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.unsafe.types.UTF8String;

import java.sql.Timestamp;
import java.util.function.Function;

/**
 * @author coalchan
 */
public class ValueConverters {
    public static final Function<String, Integer> INT_CONVERTER = value -> value == null ? null : Integer.parseInt(value);
    public static final Function<String, Long> LONG_CONVERTER = value -> value == null ? null : Long.parseLong(value);
    public static final Function<String, Float> FLOAT_CONVERTER = value -> value == null ? null : Float.parseFloat(value);
    public static final Function<String, Double> DOUBLE_CONVERTER = value -> value == null ? null : Double.parseDouble(value);
    public static final Function<String, Boolean> BOOLEAN_CONVERTER = value -> value == null ? null : Boolean.parseBoolean(value);
    public static final Function<String, Long> TIMESTAMP_CONVERTER = value -> value == null ? null : Timestamp.valueOf(value).getTime() * 1000;
    public static final Function<String, UTF8String> UTF8_STRING_CONVERTER = UTF8String::fromString;

    public static Function<String, ?>[] getConverters(StructType schema) {
        StructField[] fields = schema.fields();
        Function<String, ?>[] valueConverters = new Function[fields.length];
        for (int i = 0; i < fields.length; i++) {
            StructField field = fields[i];
            if (field.dataType().equals(DataTypes.IntegerType)) {
                valueConverters[i] = INT_CONVERTER;
            } else if (field.dataType().equals(DataTypes.LongType)) {
                valueConverters[i] = LONG_CONVERTER;
            } else if (field.dataType().equals(DataTypes.FloatType)) {
                valueConverters[i] = FLOAT_CONVERTER;
            } else if (field.dataType().equals(DataTypes.DoubleType)) {
                valueConverters[i] = DOUBLE_CONVERTER;
            } else if (field.dataType().equals(DataTypes.StringType)) {
                valueConverters[i] = UTF8_STRING_CONVERTER;
            } else if (field.dataType().equals(DataTypes.BooleanType)) {
                valueConverters[i] = BOOLEAN_CONVERTER;
            } else if (field.dataType().equals(DataTypes.TimestampType)) {
                valueConverters[i] = TIMESTAMP_CONVERTER;
            } else {
                throw new IllegalArgumentException("暂不支持类型" + field.dataType().typeName());
            }
        }
        return valueConverters;
    }
}
