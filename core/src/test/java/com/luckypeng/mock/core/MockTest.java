package com.luckypeng.mock.core;

import com.luckypeng.mock.core.function.BasicFunction;
import org.junit.Test;

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
}