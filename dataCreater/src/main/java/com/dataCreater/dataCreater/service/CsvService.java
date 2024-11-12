package com.dataCreater.dataCreater.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.opencsv.CSVWriter;

public class CsvService {
    public static void saveToCSV(HashMap<String, String> data) {
        try {
            // Create a CSVWriter to write the data to a CSV file
            // Create a File object to check the file properties
            File csvFile = new File("output.csv");

            // Open the CSV file in append mode
            FileWriter outputfile = new FileWriter(csvFile, true);
            CSVWriter writer = new CSVWriter(outputfile);

            // If the file is empty, write the header (keys of the HashMap)
            if (!csvFile.exists() || csvFile.length() == 0) {
                String[] header = data.keySet().toArray(new String[0]);
                writer.writeNext(header);
            }

            // Write the data (values of the HashMap)
            String[] dataRow = data.values().toArray(new String[0]);
            writer.writeNext(dataRow);

            // Close the writer
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveMainContentToCSV(HashMap<String, String> data) {
        try {
            // Create a CSVWriter to write the data to a CSV file
            File csvFile = new File("content.csv");

            // Open the CSV file in append mode
            FileWriter outputfile = new FileWriter(csvFile, true);

            // Set the CSVWriter options
            CSVWriter writer = new CSVWriter(outputfile,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            // If the file is empty, write the header (keys of the HashMap)
            if (!csvFile.exists() || csvFile.length() == 0) {
                String[] header = data.keySet().toArray(new String[0]);
                writer.writeNext(header);
            }

            // Write the data (values of the HashMap), ensuring proper escaping of commas
            // and quotes
            String[] dataRow = data.values().toArray(new String[0]);
            writer.writeNext(dataRow);

            // Close the writer
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
