package com.luckypeng.mock.spark;

import com.alibaba.fastjson.JSONArray;
import org.apache.spark.sql.catalyst.util.ArrayData;
import org.apache.spark.sql.types.*;
import org.apache.spark.unsafe.types.UTF8String;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertArrayEquals;

public class ValueConvertersTest {
    Function<Object, ?>[] valueConverters;


    @Before
    public void init() {
        StructField[] structFields = new StructField[]{
                new StructField("int1", DataTypes.IntegerType, true, Metadata.empty()),
                new StructField("long2", DataTypes.LongType, true, Metadata.empty()),
                new StructField("float3", DataTypes.FloatType, true, Metadata.empty()),
                new StructField("double4", DataTypes.DoubleType, true, Metadata.empty()),
                new StructField("string5", DataTypes.StringType, true, Metadata.empty()),
                new StructField("boolean6", DataTypes.BooleanType, true, Metadata.empty()),
                new StructField("timestamp7", DataTypes.TimestampType, true, Metadata.empty()),
                new StructField("decimal8", DataTypes.createDecimalType(10, 2), true, Metadata.empty()),
                new StructField("array9", ArrayType.apply(DataTypes.StringType), true, Metadata.empty())
        };
        StructType schema = new StructType(structFields);
        valueConverters = ValueConverters.getConverters(schema);
    }

    @Test
    public void test() {
        Object[] values = new Object[]{
                123,
                123456789,
                1.23,
                12321312.56798,
                "ab12_xx#s",
                true,
                "2024-01-01 10:23:45",
                "123.23",
                JSONArray.parseArray("[\"a\", \"b\", \"c\"]")
        };
        Object[] res = new Object[values.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = valueConverters[i].apply(values[i]);
        }

        Object[] expected = new Object[] {
                123,
                123456789L,
                1.23f,
                12321312.56798d,
                UTF8String.fromString("ab12_xx#s"),
                true,
                1704075825000000L,
                Decimal.apply("123.23"),
                ArrayData.toArrayData(new UTF8String[]{UTF8String.fromString("a"), UTF8String.fromString("b"), UTF8String.fromString("c")})
        };

        assertArrayEquals(expected, res);
    }
}