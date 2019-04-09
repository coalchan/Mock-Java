package com.luckypeng.mock.core.function;

import org.junit.Test;
import static com.luckypeng.mock.core.function.MiscFunction.*;

import static org.junit.Assert.*;

public class MiscFunctionTest {

    @Test
    public void testGuid() {
        assertEquals(guid().length(), 36);
    }

    @Test
    public void testId() {
        assertEquals(id().length(), 18);
    }

    @Test
    public void testIncrement() {
        long data = 0;
        for (int i = 0; i < 100; i++) {
            long add = BasicFunction.integer();
            data += add;
            assertEquals(increment(add), data);
        }
    }
}