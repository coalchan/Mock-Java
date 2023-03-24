package com.luckypeng.mock.spark.read;

import com.luckypeng.mock.spark.MockParams;
import org.apache.spark.sql.connector.read.Scan;
import org.apache.spark.sql.connector.read.ScanBuilder;
import org.apache.spark.sql.types.StructType;

import java.util.Map;

/**
 * @author coalchan
 */
public class MockScanBuilder implements ScanBuilder {
    private final StructType schema;
    private final Map<String, String> properties;
    private final MockParams mockParams;

    public MockScanBuilder(StructType schema, Map<String, String> properties, MockParams options) {
        this.schema = schema;
        this.properties = properties;
        this.mockParams = options;
    }

    @Override
    public Scan build() {
        return new MockScan(schema, properties, mockParams);
    }
}
