package com.luckypeng.mock.spark.read;

import com.luckypeng.mock.spark.MockParams;
import org.apache.spark.sql.connector.read.Batch;
import org.apache.spark.sql.connector.read.InputPartition;
import org.apache.spark.sql.connector.read.PartitionReaderFactory;
import org.apache.spark.sql.types.StructType;

import java.util.Map;

/**
 * @author coalchan
 */
public class MockBatch implements Batch {
    private final StructType schema;
    private final Map<String, String> properties;
    private final MockParams mockParams;

    public MockBatch(StructType schema, Map<String, String> properties, MockParams mockParams) {
        this.schema = schema;
        this.properties = properties;
        this.mockParams = mockParams;

    }

    @Override
    public InputPartition[] planInputPartitions() {
        return new InputPartition[]{new MockInputPartition()};
    }

    @Override
    public PartitionReaderFactory createReaderFactory() {
        return new MockPartitionReaderFactory(schema, mockParams);
    }
}
