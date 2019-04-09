package com.luckypeng.mock.core.function;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static com.luckypeng.mock.core.function.TextFunction.*;

public class TextFunctionTest {

    @Test
    public void testCparagraph() {
        for (int i = 0; i < 100; i++) {
            assertThat(cparagraph().split("。").length,
                    allOf(greaterThanOrEqualTo(DEFAULT_PARAGRAPH_MIN), lessThanOrEqualTo(DEFAULT_PARAGRAPH_MAX)));
        }
        assertEquals(cparagraph(10).split("。").length, 10);
    }

    @Test
    public void testCsentence() {
        for (int i = 0; i < 100; i++) {
            assertThat(csentence().length(),
                    allOf(greaterThanOrEqualTo(DEFAULT_SENTENCE_MIN),
                            lessThanOrEqualTo(DEFAULT_SENTENCE_MAX + 1)));
        }
        assertEquals(csentence(10).length(), 11);
    }

    @Test
    public void testCtitle() {
        for (int i = 0; i < 100; i++) {
            assertThat(ctitle().length(),
                    allOf(greaterThanOrEqualTo(DEFAULT_TITLE_MIN), lessThanOrEqualTo(DEFAULT_TITLE_MAX)));
        }
        assertEquals(ctitle(10).length(), 10);
    }

    @Test
    public void testCword() {
        for (int i = 0; i < 100; i++) {
            assertEquals(cword().length(), 1);
        }
        assertEquals(cword(10).length(), 10);
    }

    @Test
    public void testParagraph() {
        for (int i = 0; i < 100; i++) {
            assertThat(paragraph().split("\\. ").length,
                    allOf(greaterThanOrEqualTo(DEFAULT_PARAGRAPH_MIN), lessThanOrEqualTo(DEFAULT_PARAGRAPH_MAX)));
        }
        assertEquals(paragraph(10).split("\\. ").length, 10);
    }

    @Test
    public void testSentence() {
        for (int i = 0; i < 100; i++) {
            assertThat(sentence().split(" ").length,
                    allOf(greaterThanOrEqualTo(DEFAULT_SENTENCE_MIN), lessThanOrEqualTo(DEFAULT_SENTENCE_MAX)));
        }
        assertEquals(sentence(10).split(" ").length, 10);
    }

    @Test
    public void testTitle() {
        for (int i = 0; i < 100; i++) {
            assertThat(title().split(" ").length,
                    allOf(greaterThanOrEqualTo(DEFAULT_TITLE_MIN), lessThanOrEqualTo(DEFAULT_TITLE_MAX)));
        }
        assertEquals(title(10).split(" ").length, 10);
    }

    @Test
    public void testWord() {
        for (int i = 0; i < 100; i++) {
            assertThat(word().length(),
                    allOf(greaterThanOrEqualTo(DEFAULT_WORD_MIN), lessThanOrEqualTo(DEFAULT_WORD_MAX)));
        }
        assertEquals(word(10).length(), 10);
    }
}