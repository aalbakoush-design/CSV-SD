package com.example.csv;

import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvConverter {

    /**
     * Converts a list of CSVRecords to a simple JSON string representation.
     * Note: This is a basic implementation for demonstration. For production, use
     * Jackson or Gson.
     *
     * @param records List of CSVRecords.
     * @return JSON string.
     */
    public String toJson(List<CSVRecord> records) {
        if (records == null) {
            throw new IllegalArgumentException("Records cannot be null");
        }

        StringBuilder json = new StringBuilder();
        json.append("[");

        for (int i = 0; i < records.size(); i++) {
            CSVRecord record = records.get(i);
            json.append("{");
            Map<String, String> map = record.toMap();

            int j = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                json.append("\"").append(entry.getKey()).append("\":\"")
                        .append(entry.getValue().replace("\"", "\\\"")).append("\"");
                if (j < map.size() - 1) {
                    json.append(",");
                }
                j++;
            }

            json.append("}");
            if (i < records.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");
        return json.toString();
    }

    /**
     * Converts a list of CSVRecords to a List of Maps.
     *
     * @param records List of CSVRecords.
     * @return List of Maps.
     */
    public List<Map<String, String>> toMapList(List<CSVRecord> records) {
        if (records == null) {
            throw new IllegalArgumentException("Records cannot be null");
        }

        List<Map<String, String>> result = new ArrayList<>();
        for (CSVRecord record : records) {
            result.add(record.toMap());
        }
        return result;
    }
}
