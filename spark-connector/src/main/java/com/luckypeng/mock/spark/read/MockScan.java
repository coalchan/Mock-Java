package com.luckypeng.mock.spark.read;

import com.luckypeng.mock.spark.MockParams;
import org.apache.spark.sql.connector.read.Batch;
import org.apache.spark.sql.connector.read.Scan;
import org.apache.spark.sql.types.StructType;

import java.util.Map;

/**
 * @author coalchan
 */
public class MockScan implements Scan {
    private final StructType schema;
    private final Map<String, String> properties;
    private final MockParams mockParams;

    public MockScan(StructType schema, Map<String, String> properties, MockParams mockParams) {
        this.schema = schema;
        this.properties = properties;
        this.mockParams = mockParams;
    }

    @Override
    public StructType readSchema() {
        return schema;
    }

    @Override
    public Batch toBatch() {
        return new MockBatch(schema, properties, mockParams);
    }
}
