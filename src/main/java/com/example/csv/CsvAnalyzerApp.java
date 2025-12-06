package com.example.csv;

import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CsvAnalyzerApp {

    public static void main(String[] args) {
        if (args.length < 2) {
            printUsage();
            System.exit(1);
        }

        String command = args[0];
        String inputFile = args[1];

        CsvProcessor processor = new CsvProcessor();
        CsvValidator validator = new CsvValidator();
        CsvConverter converter = new CsvConverter();
        CsvStatistics statistics = new CsvStatistics();

        try (Reader reader = Files.newBufferedReader(Paths.get(inputFile))) {
            List<CSVRecord> records = processor.readCsv(reader);
            System.out.println("Loaded " + records.size() + " records from " + inputFile);

            switch (command.toLowerCase()) {
                case "filter":
                    handleFilter(args, processor, records);
                    break;
                case "stats":
                    handleStats(args, statistics, records);
                    break;
                case "convert":
                    handleConvert(args, converter, records);
                    break;
                case "validate":
                    handleValidate(args, validator, inputFile); // Re-read for headers if needed or pass records
                    break;
                default:
                    System.err.println("Unknown command: " + command);
                    printUsage();
                    System.exit(1);
            }

        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void handleFilter(String[] args, CsvProcessor processor, List<CSVRecord> records)
            throws IOException {
        if (args.length < 5) {
            System.err.println("Usage: filter <input> <column> <value> <output>");
            return;
        }
        String column = args[2];
        String value = args[3];
        String outputFile = args[4];

        List<CSVRecord> result = processor.filterCsv(records, column, value);
        System.out.println("Filtered down to " + result.size() + " records.");

        try (Writer writer = Files.newBufferedWriter(Paths.get(outputFile))) {
            processor.writeCsv(writer, result); // Note: Simple write, might lose headers in current impl
            System.out.println("Written to " + outputFile);
        }
    }

    private static void handleStats(String[] args, CsvStatistics statistics, List<CSVRecord> records) {
        if (args.length < 3) {
            System.out.println("Total Records: " + statistics.countRecords(records));
            return;
        }
        String column = args[2];
        Set<String> unique = statistics.getUniqueValues(records, column);
        System.out.println("Column '" + column + "' has " + unique.size() + " unique values.");
    }

    private static void handleConvert(String[] args, CsvConverter converter, List<CSVRecord> records) {
        String json = converter.toJson(records);
        System.out.println(json);
    }

    private static void handleValidate(String[] args, CsvValidator validator, String inputFile) throws IOException {
        if (args.length < 3) {
            System.err.println("Usage: validate <input> <col1,col2,...>");
            return;
        }
        String[] expectedHeaders = args[2].split(",");
        try (Reader reader = Files.newBufferedReader(Paths.get(inputFile))) {
            boolean valid = validator.validateHeaders(reader, expectedHeaders);
            if (valid) {
                System.out.println("Validation passed: Headers match.");
            } else {
                System.err.println("Validation failed: Headers do not match expected list.");
                System.exit(1);
            }
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar app.jar <command> <input-file> [args]");
        System.out.println("Commands:");
        System.out.println("  filter  <input> <column> <value> <output>  Filter rows and save to file");
        System.out.println("  stats   <input> [column]                   Show stats (count or unique values)");
        System.out.println("  convert <input>                            Convert to JSON (prints to stdout)");
        System.out.println("  validate <input> <header1,header2...>      Validate headers");
    }
}
