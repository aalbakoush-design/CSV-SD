package com.example.csv;

import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvProcessorTest {

    private CsvProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new CsvProcessor();
    }

    @Test
    void testReadCsvValid() throws IOException {
        String csvData = "Name,Age\nAlice,30\nBob,25";
        Reader reader = new StringReader(csvData);
        List<CSVRecord> records = processor.readCsv(reader);
        assertEquals(2, records.size());
        assertEquals("Alice", records.get(0).get("Name"));
        assertEquals("30", records.get(0).get("Age"));
    }

    @Test
    void testReadCsvEmpty() throws IOException {
        String csvData = "";
        Reader reader = new StringReader(csvData);
        List<CSVRecord> records = processor.readCsv(reader);
        assertTrue(records.isEmpty());
    }

    @Test
    void testReadCsvNullReader() {
        assertThrows(IllegalArgumentException.class, () -> processor.readCsv(null));
    }

    @Test
    void testFilterCsvValid() throws IOException {
        String csvData = "Name,Role\nAlice,Admin\nBob,User\nCharlie,Admin";
        Reader reader = new StringReader(csvData);
        List<CSVRecord> records = processor.readCsv(reader);

        List<CSVRecord> filtered = processor.filterCsv(records, "Role", "Admin");
        assertEquals(2, filtered.size());
        assertEquals("Alice", filtered.get(0).get("Name"));
        assertEquals("Charlie", filtered.get(1).get("Name"));
    }

    @Test
    void testFilterCsvNoMatch() throws IOException {
        String csvData = "Name,Role\nAlice,Admin\nBob,User";
        Reader reader = new StringReader(csvData);
        List<CSVRecord> records = processor.readCsv(reader);

        List<CSVRecord> filtered = processor.filterCsv(records, "Role", "Manager");
        assertTrue(filtered.isEmpty());
    }

    @Test
    void testFilterCsvNullArguments() {
        assertThrows(IllegalArgumentException.class, () -> processor.filterCsv(null, "Col", "Val"));
        assertThrows(IllegalArgumentException.class, () -> processor.filterCsv(List.of(), null, "Val"));
        assertThrows(IllegalArgumentException.class, () -> processor.filterCsv(List.of(), "Col", null));
    }

    @Test
    void testWriteCsvValid(@TempDir Path tempDir) throws IOException {
        Path outputFile = tempDir.resolve("output.csv");
        String csvData = "Name,Age\nAlice,30";
        Reader reader = new StringReader(csvData);
        List<CSVRecord> records = processor.readCsv(reader);

        try (Writer writer = Files.newBufferedWriter(outputFile)) {
            processor.writeCsv(writer, records);
        }

        String content = Files.readString(outputFile);
        assertTrue(content.contains("Alice"));
        assertTrue(content.contains("30"));
    }

    @Test
    void testWriteCsvEmptyList(@TempDir Path tempDir) throws IOException {
        Path outputFile = tempDir.resolve("output.csv");
        try (Writer writer = Files.newBufferedWriter(outputFile)) {
            processor.writeCsv(writer, List.of());
        }
        assertEquals("", Files.readString(outputFile));
    }

    @Test
    void testProcessDataIntegration(@TempDir Path tempDir) throws IOException {
        String csvData = "ID,Status\n1,Active\n2,Inactive\n3,Active";
        Reader reader = new StringReader(csvData);
        Path outputFile = tempDir.resolve("processed.csv");

        try (Writer writer = Files.newBufferedWriter(outputFile)) {
            processor.processData(reader, writer, "Status", "Active");
        }

        String content = Files.readString(outputFile);
        // Expecting lines for ID 1 and 3.
        // Note: The current writeCsv implementation might not write headers, so we
        // check for values.
        assertTrue(content.contains("1,Active"));
        assertTrue(content.contains("3,Active"));
        assertFalse(content.contains("2,Inactive"));
    }
}
