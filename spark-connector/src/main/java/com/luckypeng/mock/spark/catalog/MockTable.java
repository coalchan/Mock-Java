package com.luckypeng.mock.spark.catalog;

import com.luckypeng.mock.spark.MockParams;
import com.luckypeng.mock.spark.read.MockScanBuilder;
import org.apache.spark.sql.connector.catalog.SupportsRead;
import org.apache.spark.sql.connector.catalog.TableCapability;
import org.apache.spark.sql.connector.read.ScanBuilder;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author coalchan
 */
public class MockTable implements SupportsRead {
    private final StructType schema;
    private final Map<String, String> properties;
    private Set<TableCapability> capabilities;

    public MockTable(StructType schema, Map<String, String> properties) {
        this.schema = schema;
        this.properties = properties;
    }

    @Override
    public String name() {
        return "dummy_table";
    }

    @Override
    public StructType schema() {
        return schema;
    }

    @Override
    public Set<TableCapability> capabilities() {
        if (capabilities == null) {
            this.capabilities = new HashSet<>();
            capabilities.add(TableCapability.BATCH_READ);
        }
        return capabilities;
    }

    @Override
    public ScanBuilder newScanBuilder(CaseInsensitiveStringMap options) {
        MockParams mockParams = new MockParams(options.get("template"), options.getInt("total", 50));
        return new MockScanBuilder(schema, properties, mockParams);
    }
}
