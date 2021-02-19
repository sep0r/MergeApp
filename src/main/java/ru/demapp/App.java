package ru.demapp;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;
import ru.demapp.util.ParserCsv;
import ru.demapp.util.SerializerJson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class App {
    public static void main(String[] args) throws IOException {

//        // assume SLF4J is bound to logback in the current environment
//        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//        // print logback's internal status
//        StatusPrinter.print(lc);

        List<String> markers = Arrays.asList("mark01", "mark17", "mark23", "mark35", "markFV", "markFX", "markFT");

        List<String[]> allRows1 = ParserCsv.read("source_archive/source01.csv");
        List<String[]> allRows2 = ParserCsv.read("source_archive/source02.csv");
        List<String[]> allRows3 = ParserCsv.read("source_archive/source03.csv");

        List<String[]> allRows = Union.unionRows(allRows1, allRows2, allRows3);

        SerializerJson.write(Merge.mergeWithoutNull(allRows, markers), "json/source1.json");
        SerializerJson.write(Merge.mergeByMarkers(allRows, markers), "json/source2.json");
        SerializerJson.write(Merge.mergeIntoArr(allRows, markers), "json/source3.json");
    }
}