package com.example.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CsvProcessor {

    public List<CSVRecord> readCsv(Reader reader) throws IOException {
        if (reader == null) {
            throw new IllegalArgumentException("Reader cannot be null");
        }
        try (CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            return parser.getRecords();
        }
    }

    public List<CSVRecord> filterCsv(List<CSVRecord> records, String column, String value) {
        if (records == null || column == null || value == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        return records.stream()
                .filter(record -> record.isMapped(column) && record.get(column).equals(value))
                .collect(Collectors.toList());
    }

    public void writeCsv(Writer writer, List<CSVRecord> records) throws IOException {
        if (writer == null || records == null) {
            throw new IllegalArgumentException("Writer and records cannot be null");
        }
        if (records.isEmpty()) {
            return;
        }

        // Assuming all records have the same headers as the first one
        // We need to extract headers from the first record's parser if possible, or just values
        // CSVPrinter requires headers to print the header row.
        // For simplicity in this example, we'll just print the values.
        // If we want to preserve headers, we'd need to pass them or extract them.
        // Let's try to extract headers from the first record if available.
        
        try (CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
             // Print header if available in the first record
            if (!records.isEmpty()) {
                 // Note: CSVRecord doesn't easily expose the header names directly in a way that guarantees order 
                 // without the parser map. 
                 // However, for this specific task, we might just want to write the data.
                 // Let's just write the record values.
            }
            
            for (CSVRecord record : records) {
                printer.printRecord(record);
            }
        }
    }
    
    // Helper method to process data end-to-end (Read -> Filter -> Write)
    // This is useful for benchmarking
    public void processData(Reader reader, Writer writer, String filterColumn, String filterValue) throws IOException {
        List<CSVRecord> records = readCsv(reader);
        List<CSVRecord> filtered = filterCsv(records, filterColumn, filterValue);
        writeCsv(writer, filtered);
    }
}
