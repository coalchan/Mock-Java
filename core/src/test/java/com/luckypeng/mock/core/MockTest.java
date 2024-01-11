package com.luckypeng.mock.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.luckypeng.mock.core.function.BasicFunction;
import com.luckypeng.mock.core.template.TemplateHandler;
import com.luckypeng.mock.core.util.JsonUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class MockTest {

    @Test
    public void mockValue() {
        for (int i = 0; i < 100; i++) {
            assertThat((long) Mock.mockValue("@int(10, 100)"),
                    allOf(greaterThanOrEqualTo(10L), lessThanOrEqualTo(100L)));

            assertThat((long) Mock.mockValue("@int"),
                    allOf(greaterThanOrEqualTo(Long.MIN_VALUE), lessThanOrEqualTo(Long.MAX_VALUE)));

            assertNotEquals(BasicFunction.CharacterEnum.ALL.indexOf((char) Mock.mockValue("@char")), -1);
        }
    }

    @Test
    public void testPick() {
        List<Object> array = Arrays.asList(1, "'abc'", 3.14, true, 1234567890000000000L);
        List<Object> actualList = array.stream()
                .map(obj -> obj instanceof String? obj.toString().substring(1, obj.toString().length() - 1) : obj)
                .collect(Collectors.toList());

        for (int i = 0; i < 100; i++) {
            Object value = Mock.mockValue("@pick(" + array.toString() + ")");
            assertThat(value, isIn(actualList));
        }
    }

    @Test
    public void testCombine() {
        List<Object> array = Arrays.asList(1, "'abc'", 3.14, true, 1234567890000000000L);
        List<Object> actualList = array.stream()
                .map(obj -> obj instanceof String? obj.toString().substring(1, obj.toString().length() - 1) : obj)
                .collect(Collectors.toList());

        for (int i = 0; i < 100; i++) {
            Object value = Mock.mockValue("@combine(" + array.toString() + ")");
            assertTrue(value instanceof JSONArray);
            JSONArray jsonArray = (JSONArray) value;
            for (int j = 0; j < jsonArray.size(); j++) {
                assertThat(jsonArray.get(j), isIn(actualList));
            }
        }
    }

    @Test
    public void test() {
        String jsonTemplate = "{\n" +
                "  \"eventTime\": \"@timestamp(true)\",\n" +
                "  \"userId\": \"@int(1, 100000)\",\n" +
                "  \"ip\": \"@ip()\",\n" +
                "  \"ua|1\": [\n" +
                "    \"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.87 Safari/537.36\",\n" +
                "    \"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0\",\n" +
                "    \"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2\",\n" +
                "    \"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36\",\n" +
                "    \"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586\",\n" +
                "    \"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 BIDUBrowser/8.3 Safari/537.36\",\n" +
                "    \"Mozilla/5.0 (Linux; Android 5.0; SM-N9100 Build/LRX21V) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/37.0.0.0 Mobile Safari/537.36 MicroMessenger/6.0.2.56_r958800.520 NetType/WIFI\",\n" +
                "    \"Mozilla/5.0 (iPhone; CPU iPhone OS 7_1_2 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Mobile/11D257 MicroMessenger/6.0.1 NetType/WIFI\"\n" +
                "  ],\n" +
                "  \"referer\": \"@url('http', 'luckypeng.com')\",\n" +
                "  \"page\": \"@url('http', 'luckypeng.com')\",\n" +
                "  \"sessionId\": \"@guid()\",\n" +
                "  \"duration\": \"@int(0, 3600)\"\n" +
                "}";
        JSONObject jsonObject = JsonUtils.toJson(jsonTemplate);
        List<String> sortedKeys = Arrays.asList("userId", "ip", "eventTime", "duration", "ua", "referer", "page");
        JSONObject sortedTemplate = TemplateHandler.sortedTemplate(jsonObject, sortedKeys);
        String sortedJsonString = "{\"userId\":\"@int(1, 100000)\",\"ip\":\"@ip()\",\"eventTime\":\"@timestamp(true)\",\"duration\":\"@int(0, 3600)\",\"ua|1\":[\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.87 Safari/537.36\",\"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0\",\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2\",\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36\",\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586\",\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 BIDUBrowser/8.3 Safari/537.36\",\"Mozilla/5.0 (Linux; Android 5.0; SM-N9100 Build/LRX21V) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/37.0.0.0 Mobile Safari/537.36 MicroMessenger/6.0.2.56_r958800.520 NetType/WIFI\",\"Mozilla/5.0 (iPhone; CPU iPhone OS 7_1_2 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Mobile/11D257 MicroMessenger/6.0.1 NetType/WIFI\"],\"referer\":\"@url('http', 'luckypeng.com')\",\"page\":\"@url('http', 'luckypeng.com')\"}";
        assertEquals(sortedJsonString, sortedTemplate.toJSONString());
    }
}