package com.example.csv;

import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CsvConverterTest {

    private CsvConverter converter;
    private CsvProcessor processor;

    @BeforeEach
    void setUp() {
        converter = new CsvConverter();
        processor = new CsvProcessor();
    }

    @Test
    void testToJson() throws IOException {
        Reader reader = new StringReader("Name,Age\nAlice,30");
        List<CSVRecord> records = processor.readCsv(reader);

        String json = converter.toJson(records);
        // Basic check
        assertTrue(json.contains("\"Name\":\"Alice\""));
        assertTrue(json.contains("\"Age\":\"30\""));
        assertTrue(json.startsWith("[{"));
        assertTrue(json.endsWith("}]"));
    }

    @Test
    void testToJsonEmpty() {
        assertEquals("[]", converter.toJson(List.of()));
    }

    @Test
    void testToJsonNull() {
        assertThrows(IllegalArgumentException.class, () -> converter.toJson(null));
    }

    @Test
    void testToMapList() throws IOException {
        Reader reader = new StringReader("ID,Value\n1,100\n2,200");
        List<CSVRecord> records = processor.readCsv(reader);

        List<Map<String, String>> maps = converter.toMapList(records);
        assertEquals(2, maps.size());
        assertEquals("1", maps.get(0).get("ID"));
        assertEquals("200", maps.get(1).get("Value"));
    }

    @Test
    void testToMapListEmpty() {
        assertTrue(converter.toMapList(List.of()).isEmpty());
    }

    @Test
    void testToMapListNull() {
        assertThrows(IllegalArgumentException.class, () -> converter.toMapList(null));
    }
}
