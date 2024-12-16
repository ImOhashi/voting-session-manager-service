package com.app.voting_session_manager_service.domain.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CpfValidatorTest {

    @Test
    void testValidCpf() {
        String cpf = "123.456.789-09";
        assertTrue(CpfValidator.isValidCPF(cpf));
    }

    @Test
    void testInvalidCpf() {
        String cpf = "123.456.789-00";
        assertFalse(CpfValidator.isValidCPF(cpf));
    }

    @Test
    void testCpfWithRepeatedDigits() {
        String cpf = "111.111.111-11";
        assertFalse(CpfValidator.isValidCPF(cpf));
    }

    @Test
    void testCpfWithoutFormatting() {
        String cpf = "12345678909";
        assertTrue(CpfValidator.isValidCPF(cpf));
    }

    @Test
    void testEmptyCpf() {
        String cpf = "";
        assertFalse(CpfValidator.isValidCPF(cpf));
    }

    @Test
    void testCpfWithSpaces() {
        String cpf = " 123.456.789-09 ";
        assertTrue(CpfValidator.isValidCPF(cpf));
    }

    @Test
    void testCpfWithInvalidLength() {
        String cpf = "123.456.789-0";
        assertFalse(CpfValidator.isValidCPF(cpf));
    }
}
