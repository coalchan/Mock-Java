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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author coalchan
 */
public class MockPartitionReader implements PartitionReader<InternalRow> {
    private final JSONObject template;
    private final int total;
    private final Map<String, Function<String, ?>> valueConverters;
    private int count;

    public MockPartitionReader(MockInputPartition partition, StructType schema, MockParams mockParams) {
        this.total = mockParams.getTotal();
        this.count = this.total;
        this.valueConverters = ValueConverters.getConverters(schema);
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

        Iterator<Map.Entry<String, Object>> iterator = data.entrySet().iterator();
        Object[] convertedValues = new Object[data.size()];
        int i = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            Object castedValue = valueConverters.get(entry.getKey()).apply(entry.getValue().toString());
            convertedValues[i++] = castedValue;
        }
        return InternalRow.apply(JavaConverters.asScalaIteratorConverter(Arrays.asList(convertedValues).iterator()).asScala().toSeq());
    }

    @Override
    public void close() throws IOException {
        // Do nothing
    }
}
