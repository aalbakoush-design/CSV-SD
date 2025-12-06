package com.example.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CsvValidator {

    /**
     * Validates that the CSV provided by the reader contains exactly the expected
     * headers.
     * Note: This consumes the reader.
     *
     * @param reader          The reader for the CSV content.
     * @param expectedHeaders The array of expected header names.
     * @return true if headers match, false otherwise.
     * @throws IOException If an I/O error occurs.
     */
    public boolean validateHeaders(Reader reader, String[] expectedHeaders) throws IOException {
        if (reader == null || expectedHeaders == null) {
            throw new IllegalArgumentException("Reader and expectedHeaders cannot be null");
        }

        try (CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {
            Set<String> actualHeaders = parser.getHeaderMap().keySet();
            // Check if size matches and all expected headers are present
            if (actualHeaders.size() != expectedHeaders.length) {
                return false;
            }
            // Using simple check: expected headers must be in the key set
            for (String header : expectedHeaders) {
                if (!actualHeaders.contains(header)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Validates that all records in the list have the expected number of columns.
     *
     * @param records       List of CSVRecords.
     * @param expectedCount Expected column count.
     * @return true if all records match the count, false otherwise.
     */
    public boolean validateColumnCount(List<CSVRecord> records, int expectedCount) {
        if (records == null) {
            throw new IllegalArgumentException("Records cannot be null");
        }
        if (expectedCount < 0) {
            throw new IllegalArgumentException("Expected count cannot be negative");
        }

        for (CSVRecord record : records) {
            if (record.size() != expectedCount) {
                return false;
            }
        }
        return true;
    }
}
