package com.luckypeng.mock.spark;

import com.alibaba.fastjson.JSONArray;
import org.apache.spark.sql.catalyst.util.ArrayData;
import org.apache.spark.sql.types.*;
import org.apache.spark.unsafe.types.UTF8String;

import java.sql.Timestamp;
import java.util.function.Function;

/**
 * @author coalchan
 */
public class ValueConverters {
    public static final Function<Object, Integer> INT_CONVERTER = value -> value == null ? null : Integer.parseInt(value.toString());
    public static final Function<Object, Long> LONG_CONVERTER = value -> value == null ? null : Long.parseLong(value.toString());
    public static final Function<Object, Float> FLOAT_CONVERTER = value -> value == null ? null : Float.parseFloat(value.toString());
    public static final Function<Object, Double> DOUBLE_CONVERTER = value -> value == null ? null : Double.parseDouble(value.toString());
    public static final Function<Object, Boolean> BOOLEAN_CONVERTER = value -> value == null ? null : Boolean.parseBoolean(value.toString());
    public static final Function<Object, Long> TIMESTAMP_CONVERTER = value -> value == null ? null : Timestamp.valueOf(value.toString()).getTime() * 1000;
    public static final Function<Object, UTF8String> UTF8_STRING_CONVERTER = value -> UTF8String.fromString(value.toString());
    public static final Function<Object, Decimal> DECIMAL_CONVERTER = value -> value == null ? null : Decimal.apply(value.toString());

    public static Function<Object, ?>[] getConverters(StructType schema) {
        StructField[] fields = schema.fields();
        Function<Object, ?>[] valueConverters = new Function[fields.length];
        for (int i = 0; i < fields.length; i++) {
            StructField field = fields[i];
            valueConverters[i] = newConverter(field.dataType());
        }
        return valueConverters;
    }

    public static Function<Object, ?> newConverter(DataType dataType) {
        if (dataType.equals(DataTypes.IntegerType)) {
            return INT_CONVERTER;
        } else if (dataType.equals(DataTypes.LongType)) {
            return LONG_CONVERTER;
        } else if (dataType.equals(DataTypes.FloatType)) {
            return FLOAT_CONVERTER;
        } else if (dataType.equals(DataTypes.DoubleType)) {
            return DOUBLE_CONVERTER;
        } else if (dataType.equals(DataTypes.StringType)) {
            return UTF8_STRING_CONVERTER;
        } else if (dataType.equals(DataTypes.BooleanType)) {
            return BOOLEAN_CONVERTER;
        } else if (dataType.equals(DataTypes.TimestampType)) {
            return TIMESTAMP_CONVERTER;
        } else if (dataType instanceof DecimalType) {
            return DECIMAL_CONVERTER;
        } else if (dataType instanceof ArrayType) {
            return arrayConverter((ArrayType) dataType);
        } else {
            throw new IllegalArgumentException("暂不支持类型" + dataType.typeName());
        }
    }

    public static Function<Object, ?> arrayConverter(ArrayType arrayType) {
        return value -> {
            JSONArray array = (JSONArray) value;
            Object[] results = new Object[array.size()];
            for (int i = 0; i < array.size(); i++) {
                results[i] = newConverter(arrayType.elementType()).apply(array.get(i));
            }
            return ArrayData.toArrayData(results);
        };
    }
}
