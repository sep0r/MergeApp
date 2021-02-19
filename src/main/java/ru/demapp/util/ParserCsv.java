package ru.demapp.util;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ParserCsv {
    public static List<String[]> read(String address) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(address), ',', '"', 1);
        return reader.readAll();
    }
}
