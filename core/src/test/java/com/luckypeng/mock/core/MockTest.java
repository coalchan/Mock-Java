package com.luckypeng.mock.core;

import com.luckypeng.mock.core.function.BasicFunction;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

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
}