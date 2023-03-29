package com.luckypeng.mock.cli;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MockCliTest {
    @Test
    public void testMockData() throws IOException {
        String template = "{\"id\": \"@int(1,100)\", \"name\": \"@cname\"}";
//        String format = "JSON";
//        String format = "TSV";
        String format = "CSV";
        int count = 100;

        MockCli.mockData(template, format, count);
    }
}