package com.example.csv;

import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvValidatorTest {

    private CsvValidator validator;
    private CsvProcessor processor; // Helper to get CSVRecords

    @BeforeEach
    void setUp() {
        validator = new CsvValidator();
        processor = new CsvProcessor();
    }

    @Test
    void testValidateHeadersValid() throws IOException {
        Reader reader = new StringReader("Name,Age\nAlice,30");
        assertTrue(validator.validateHeaders(reader, new String[] { "Name", "Age" }));
    }

    @Test
    void testValidateHeadersInvalidMissing() throws IOException {
        Reader reader = new StringReader("Name,Age\nAlice,30");
        assertFalse(validator.validateHeaders(reader, new String[] { "Name", "Age", "City" }));
    }

    @Test
    void testValidateHeadersInvalidExtra() throws IOException {
        Reader reader = new StringReader("Name,Age,City\nAlice,30,NY");
        assertFalse(validator.validateHeaders(reader, new String[] { "Name", "Age" }));
    }

    @Test
    void testValidateHeadersMismatch() throws IOException {
        Reader reader = new StringReader("Name,Age\nAlice,30");
        assertFalse(validator.validateHeaders(reader, new String[] { "Name", "Gender" }));
    }

    @Test
    void testValidateHeadersNull() {
        assertThrows(IllegalArgumentException.class, () -> validator.validateHeaders(null, new String[] {}));
        assertThrows(IllegalArgumentException.class, () -> validator.validateHeaders(new StringReader(""), null));
    }

    @Test
    void testValidateColumnCountValid() throws IOException {
        Reader reader = new StringReader("Col1,Col2\nVal1,Val2\nVal3,Val4");
        List<CSVRecord> records = processor.readCsv(reader);
        assertTrue(validator.validateColumnCount(records, 2));
    }

    @Test
    void testValidateColumnCountInvalid() throws IOException {
        // Create records with inconsistent sizes via parsing
        Reader reader1 = new StringReader("A,B,C\n1,2,3");
        List<CSVRecord> list1 = processor.readCsv(reader1);

        Reader reader2 = new StringReader("A,B\n1,2");
        List<CSVRecord> list2 = processor.readCsv(reader2);

        // Combine them
        java.util.List<CSVRecord> records = new java.util.ArrayList<>(list1);
        records.addAll(list2);

        // Expecting 3 columns, but second record has 2
        assertFalse(validator.validateColumnCount(records, 3));
    }

    @Test
    void testValidateColumnCountEmpty() {
        assertTrue(validator.validateColumnCount(Collections.emptyList(), 5));
    }

    @Test
    void testValidateColumnCountNull() {
        assertThrows(IllegalArgumentException.class, () -> validator.validateColumnCount(null, 5));
    }

    @Test
    void testValidateColumnCountNegative() {
        assertThrows(IllegalArgumentException.class, () -> validator.validateColumnCount(Collections.emptyList(), -1));
    }
}
