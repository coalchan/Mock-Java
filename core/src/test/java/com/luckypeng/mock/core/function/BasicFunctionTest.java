package com.luckypeng.mock.core.function;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static com.luckypeng.mock.core.function.BasicFunction.*;

public class BasicFunctionTest {

    @Test
    public void testBool() {
        int trueCount = 0;
        int falseCount = 0;
        for (int i = 0; i < 100000; i++) {
            if (bool()) {
                trueCount ++;
            } else {
                falseCount ++;
            }
        }
        assertThat(1.0 * Math.abs(trueCount - falseCount) / trueCount, lessThan(0.1));
    }

    @Test
    public void testNatural() {
        for (int i = 0; i < 100; i++) {
            assertThat(natural(), greaterThanOrEqualTo(0L));
        }
    }

    @Test
    public void testInteger() {
        for (int i = 0; i < 100; i++) {
            assertThat(integer(10), greaterThanOrEqualTo(10L));
            assertThat(integer(10, 10), allOf(greaterThanOrEqualTo(10L), lessThanOrEqualTo(10L)));
            assertThat(integer(10, 100), allOf(greaterThanOrEqualTo(10L), lessThanOrEqualTo(100L)));
        }
    }

    @Test
    public void testDecimal() {
        for (int i = 0; i < 100; i++) {
            assertThat(decimal(10, 10, 2), greaterThan(10.0));
            assertThat(decimal(10, 10, 2, 4),
                    allOf(greaterThan(10.0), lessThan(10.9999)));
        }
    }

    @Test
    public void testCharacter() {
        for (int i = 0; i < 100; i++) {
            assertNotEquals(CharacterEnum.ALL.indexOf(character()), -1);
            assertNotEquals(CharacterEnum.lower.getPool().indexOf(character(CharacterEnum.lower)), -1);
        }
    }

    @Test
    public void testString() {
        for (int i = 0; i < 100; i++) {
            assertThat(string(10, 20).length(),
                    allOf(greaterThanOrEqualTo(10), lessThanOrEqualTo(20)));
            assertNotEquals(CharacterEnum.lower.getPool().indexOf(string(CharacterEnum.lower, 1)), -1);
        }
    }

    @Test
    public void testRange() {
        assertArrayEquals(range(10), new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        assertArrayEquals(range(1, 10), new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        assertArrayEquals(range(1, 10, 3), new int[]{1, 4, 7});
    }
}