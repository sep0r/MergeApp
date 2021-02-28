package ru.demapp;

import ru.demapp.util.ParserCsv;
import ru.demapp.util.SerializerJson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class App {
    static List<String> listPaths = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        List<String> markers = Arrays.asList("mark01", "mark17", "mark23", "mark35", "markFV", "markFX", "markFT");
        getFilesFromZip("doc/source_archive.zip");
        locateFiles("doc/source_archive");
        List<String[]> allRows = getAllRows(listPaths);

        writeFile(Merge.mergeWithoutNull(allRows, markers), "doc/json/result1.json");
        writeFile(Merge.mergeByMarkers(allRows, markers), "doc/json/result2.json");
        writeFile(Merge.mergeIntoArr(allRows, markers), "doc/json/result3.json");
    }

    public static List<String[]> getAllRows(List<String> listPaths) {
        return listPaths.stream()
                .map(p -> {
                    try {
                        return readFile(p);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public static void locateFiles(String direction) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(direction))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(p -> listPaths.add(p.toString()));
        }
    }

    public static List<String[]> readFile(String listPaths) throws IOException {
        return ParserCsv.read(listPaths);
    }

    public static void writeFile(Map merge, String direction) throws IOException {
        SerializerJson.write(merge, direction);
    }

    public static void getFilesFromZip(String direction) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(direction))) {
            ZipEntry entry;
            String name;

            while ((entry = zipInputStream.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fileOutputStream = new FileOutputStream("doc/source_archive/new" + name);
                for (int c = zipInputStream.read(); c != -1; c = zipInputStream.read()) {
                    fileOutputStream.write(c);
                }
                fileOutputStream.flush();
                zipInputStream.closeEntry();
                fileOutputStream.close();
            }
        }
    }
}