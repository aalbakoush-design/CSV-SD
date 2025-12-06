package com.example.csv;

import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;

public class CsvMerger {

    /**
     * Merges two lists of CSVRecords into a single list.
     * Note: This assumes compatible schemas (headers) or just concatenates rows.
     *
     * @param list1 First list of records.
     * @param list2 Second list of records.
     * @return Merged list.
     */
    public List<CSVRecord> merge(List<CSVRecord> list1, List<CSVRecord> list2) {
        if (list1 == null || list2 == null) {
            throw new IllegalArgumentException("Lists cannot be null");
        }

        List<CSVRecord> merged = new ArrayList<>(list1);
        merged.addAll(list2);
        return merged;
    }
}
