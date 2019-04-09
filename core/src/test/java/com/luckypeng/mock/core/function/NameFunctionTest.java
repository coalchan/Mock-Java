package com.luckypeng.mock.core.function;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static com.luckypeng.mock.core.function.NameFunction.*;

public class NameFunctionTest {

    @Test
    public void testFirst() {
        for (int i = 0; i < 100; i++) {
            assertThat(first(), isIn(FIRST_NAMES));
        }
    }

    @Test
    public void testLast() {
        for (int i = 0; i < 100; i++) {
            assertThat(last(), isIn(LAST_NAMES));
        }
    }

    @Test
    public void testName() {
        for (int i = 0; i < 100; i++) {
            String[] names = name().split(" ");
            assertThat(names[0], isIn(FIRST_NAMES));
            assertThat(names[1], isIn(LAST_NAMES));

            names = name(true).split(" ");
            assertThat(names[0], isIn(FIRST_NAMES));
            assertThat(names[2], isIn(LAST_NAMES));
        }
    }

    @Test
    public void testCfirst() {
        for (int i = 0; i < 100; i++) {
            assertThat(cfirst(), isIn(CN_FIRST_NAMES));
        }
    }

    @Test
    public void testClast() {
        for (int i = 0; i < 100; i++) {
            assertThat(clast(), isIn(CN_LAST_NAMES));
        }
    }
}