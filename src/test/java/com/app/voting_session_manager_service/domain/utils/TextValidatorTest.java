package com.app.voting_session_manager_service.domain.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextValidatorTest {

    @Test
    void testValidText() {
        String nome = "John Doe";
        assertTrue(TextValidator.isValidText(nome));
    }

    @Test
    void testEmptyText() {
        String nome = "";
        assertFalse(TextValidator.isValidText(nome));
    }

    @Test
    void testNullText() {
        String nome = null;
        assertFalse(TextValidator.isValidText(nome));
    }

    @Test
    void testTextWithSpaces() {
        String nome = "   ";
        assertFalse(TextValidator.isValidText(nome));
    }

    @Test
    void testTextWithValidCharacters() {
        String nome = " Maria Silva ";
        assertTrue(TextValidator.isValidText(nome));
    }

    @Test
    void testTextWithLeadingAndTrailingSpaces() {
        String nome = "   João    Silva  ";
        assertTrue(TextValidator.isValidText(nome));
    }

    @Test
    void testTextWithNumbers() {
        String nome = "John 123";
        assertTrue(TextValidator.isValidText(nome));
    }

    @Test
    void testSingleCharacterText() {
        String nome = "A";
        assertTrue(TextValidator.isValidText(nome));
    }
}
