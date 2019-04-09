package com.luckypeng.mock.core.function;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static com.luckypeng.mock.core.function.WebFunction.*;

public class WebFunctionTest {

    @Test
    public void testIp() {
        assertEquals(4, ip().split("\\.").length);
    }

    @Test
    public void testEmail() {
        assertEquals(2, email().split("@").length);
    }

    @Test
    public void testUrl() {
        for (int i = 0; i < 100; i++) {
            String[] array = url().split("://");
            assertEquals(2, array.length);
            assertThat(array[0], isIn(PROTOCOLS));
        }
    }

    @Test
    public void testProtocol() {
        for (int i = 0; i < 100; i++) {
            assertThat(protocol(), isIn(PROTOCOLS));
        }
    }

    @Test
    public void testTld() {
        for (int i = 0; i < 100; i++) {
            assertThat(tld(), isIn(DOMAIN_SUFFIX));
        }
    }
}