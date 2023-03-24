package com.luckypeng.mock.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class SparkConnectorTest {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder()
                .master("local[2]")
                .getOrCreate();

        Dataset<Row> dataset = sparkSession.read().schema(getSchema()).format("com.luckypeng.mock.spark.catalog.MockSource")
                .option("template", "{\"id\": \"@int(1,5)\", \"id2\": \"@name\"}")
                .option("total", 50)
                .load();
        dataset.show();
    }

    private static StructType getSchema() {
        StructField[] structFields = new StructField[]{
                new StructField("id2", DataTypes.StringType, true, Metadata.empty()),
                new StructField("id3", DataTypes.StringType, true, Metadata.empty()),
                new StructField("id", DataTypes.IntegerType, true, Metadata.empty())
        };
        return new StructType(structFields);
    }
}
