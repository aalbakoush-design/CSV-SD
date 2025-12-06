package com.example.csv;

import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvMergerTest {

    private CsvMerger merger;
    private CsvProcessor processor;

    @BeforeEach
    void setUp() {
        merger = new CsvMerger();
        processor = new CsvProcessor();
    }

    @Test
    void testMergeValid() throws IOException {
        Reader reader1 = new StringReader("A,B\n1,2");
        Reader reader2 = new StringReader("A,B\n3,4");
        List<CSVRecord> list1 = processor.readCsv(reader1);
        List<CSVRecord> list2 = processor.readCsv(reader2);

        List<CSVRecord> merged = merger.merge(list1, list2);
        assertEquals(2, merged.size());
        assertEquals("1", merged.get(0).get("A"));
        assertEquals("3", merged.get(1).get("A"));
    }

    @Test
    void testMergeEmpty() {
        List<CSVRecord> list1 = List.of();
        List<CSVRecord> list2 = List.of();
        assertTrue(merger.merge(list1, list2).isEmpty());
    }

    @Test
    void testMergeOneEmpty() throws IOException {
        Reader reader = new StringReader("A,B\n1,2");
        List<CSVRecord> list1 = processor.readCsv(reader);
        List<CSVRecord> list2 = List.of();

        List<CSVRecord> merged = merger.merge(list1, list2);
        assertEquals(1, merged.size());
    }

    @Test
    void testMergeNull() {
        assertThrows(IllegalArgumentException.class, () -> merger.merge(null, List.of()));
        assertThrows(IllegalArgumentException.class, () -> merger.merge(List.of(), null));
    }
}
