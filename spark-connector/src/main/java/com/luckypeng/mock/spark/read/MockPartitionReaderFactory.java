package com.luckypeng.mock.spark.read;

import com.luckypeng.mock.spark.MockParams;
import org.apache.spark.sql.catalyst.InternalRow;
import org.apache.spark.sql.connector.read.InputPartition;
import org.apache.spark.sql.connector.read.PartitionReader;
import org.apache.spark.sql.connector.read.PartitionReaderFactory;
import org.apache.spark.sql.types.StructType;

/**
 * @author coalchan
 */
public class MockPartitionReaderFactory implements PartitionReaderFactory {
    private final StructType schema;
    private final MockParams mockParams;

    public MockPartitionReaderFactory(StructType schema, MockParams options) {
        this.schema = schema;
        this.mockParams = options;
    }

    @Override
    public PartitionReader<InternalRow> createReader(InputPartition partition) {
        return new MockPartitionReader((MockInputPartition) partition, schema, mockParams);
    }
}
