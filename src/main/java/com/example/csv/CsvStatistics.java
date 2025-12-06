package com.example.csv;

import org.apache.commons.csv.CSVRecord;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CsvStatistics {

    /**
     * Returns the total number of records.
     *
     * @param records List of CSVRecords.
     * @return Count of records.
     */
    public int countRecords(List<CSVRecord> records) {
        if (records == null) {
            throw new IllegalArgumentException("Records cannot be null");
        }
        return records.size();
    }

    /**
     * Returns a set of unique values for a specific column.
     *
     * @param records List of CSVRecords.
     * @param column  The column name to inspect.
     * @return Set of unique values.
     */
    public Set<String> getUniqueValues(List<CSVRecord> records, String column) {
        if (records == null || column == null) {
            throw new IllegalArgumentException("Records and column cannot be null");
        }

        Set<String> uniqueValues = new HashSet<>();
        for (CSVRecord record : records) {
            if (record.isMapped(column)) {
                uniqueValues.add(record.get(column));
            }
        }
        return uniqueValues;
    }
}
