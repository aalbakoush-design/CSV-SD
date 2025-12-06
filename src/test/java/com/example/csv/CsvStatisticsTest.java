package com.example.csv;

import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CsvStatisticsTest {

    private CsvStatistics statistics;
    private CsvProcessor processor;

    @BeforeEach
    void setUp() {
        statistics = new CsvStatistics();
        processor = new CsvProcessor();
    }

    @Test
    void testCountRecords() throws IOException {
        Reader reader = new StringReader("A,B\n1,2\n3,4");
        List<CSVRecord> records = processor.readCsv(reader);
        assertEquals(2, statistics.countRecords(records));
    }

    @Test
    void testCountRecordsEmpty() {
        assertEquals(0, statistics.countRecords(List.of()));
    }

    @Test
    void testCountRecordsNull() {
        assertThrows(IllegalArgumentException.class, () -> statistics.countRecords(null));
    }

    @Test
    void testGetUniqueValues() throws IOException {
        Reader reader = new StringReader("Category,Value\nA,10\nB,20\nA,30\nC,40");
        List<CSVRecord> records = processor.readCsv(reader);

        Set<String> unique = statistics.getUniqueValues(records, "Category");
        assertEquals(3, unique.size());
        assertTrue(unique.contains("A"));
        assertTrue(unique.contains("B"));
        assertTrue(unique.contains("C"));
    }

    @Test
    void testGetUniqueValuesUnmappedColumn() throws IOException {
        Reader reader = new StringReader("A,B\n1,2");
        List<CSVRecord> records = processor.readCsv(reader);

        // "C" is not in header
        Set<String> unique = statistics.getUniqueValues(records, "C");
        assertTrue(unique.isEmpty());
    }

    @Test
    void testGetUniqueValuesNull() {
        assertThrows(IllegalArgumentException.class, () -> statistics.getUniqueValues(null, "Col"));
        assertThrows(IllegalArgumentException.class, () -> statistics.getUniqueValues(List.of(), null));
    }
}
