package com.luckypeng.mock.spark.read;

import com.alibaba.fastjson.JSONObject;
import com.luckypeng.mock.core.Mock;
import com.luckypeng.mock.core.template.TemplateHandler;
import com.luckypeng.mock.core.util.JsonUtils;
import com.luckypeng.mock.spark.MockParams;
import com.luckypeng.mock.spark.ValueConverters;
import org.apache.spark.sql.catalyst.InternalRow;
import org.apache.spark.sql.connector.read.PartitionReader;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import scala.collection.JavaConverters;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author coalchan
 */
public class MockPartitionReader implements PartitionReader<InternalRow> {
    private final JSONObject template;
    private final int total;
    private final Function<String, ?>[] valueConverters;
    private int count;
    private int columnCount;
    private StructType schema;

    public MockPartitionReader(MockInputPartition partition, StructType schema, MockParams mockParams) {
        this.total = mockParams.getTotal();
        this.count = this.total;
        this.valueConverters = ValueConverters.getConverters(schema);
        this.columnCount = schema.length();
        this.schema = schema;
        List<String> fields = Stream.of(schema.fields()).map(StructField::name).collect(Collectors.toList());
        this.template = TemplateHandler.sortedTemplate(JsonUtils.toJson(mockParams.getTemplate()), fields);
    }

    @Override
    public boolean next() throws IOException {
        return count > 0;
    }

    @Override
    public InternalRow get() {
        count--;
        JSONObject data = Mock.mock(template);
        Object[] convertedValues = new Object[columnCount];

        if (columnCount == data.size()) {
            // 当指定的 schema 与 mock 规则中的列数一致时，可以直接遍历 mock 数据，提升性能
            Collection<Object> values = data.values();
            int k = 0;
            for (Object value : values) {
                convertedValues[k] = valueConverters[k].apply(value.toString());
                k++;
            }
        } else {
            // 当指定的 schema 与 mock 规则不一致时，按照 schema 中的列进行值转换，没有的补 null
            for (int i = 0; i < columnCount; i++) {
                Object value = data.get(schema.apply(i).name());
                if (value != null) {
                    value = valueConverters[i].apply(value.toString());
                }
                convertedValues[i] = value;
            }
        }
        return InternalRow.apply(JavaConverters.asScalaIteratorConverter(Arrays.asList(convertedValues).iterator()).asScala().toSeq());
    }

    @Override
    public void close() throws IOException {
        // Do nothing
    }
}
