package ru.demapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Merge {
    private static final Logger log = LoggerFactory.getLogger(Merge.class);

    public static Map<String, Integer> mergeWithoutNull(List<String[]> allRows, List<String> markers) {
        log.info("Data is combined without considering all markers");
        Map<String, Integer> mapResult1 = new TreeMap<>();
        sortingAndSum(allRows, markers, mapResult1);
        return mapResult1;
    }

    public static Map<String, Integer> mergeByMarkers(List<String[]> allRows, List<String> markers) {
        log.info("Data combined with all markers");
        Map<String, Integer> mapResult2 = new TreeMap<>();
        for (String marker : markers) {
            mapResult2.put(marker, null);
        }
        sortingAndSum(allRows, markers, mapResult2);
        return mapResult2;
    }

    public static Map<String, Integer[]> mergeIntoArr(List<String[]> allRows, List<String> markers) {
        log.info("Data combined to arrays and sorted");
        Map<String, Integer[]> mapResult3 = new TreeMap<>();
        for (String[] arr : allRows) {
            String str = arr[0];
            Integer[] num = {Integer.valueOf(arr[1])};
            for (String marker : markers) {
                if (str.equalsIgnoreCase(String.valueOf(marker))) {
                    if (mapResult3.containsKey(marker)) {
                        mapResult3.merge(marker, num, (a, b) -> {
                                    Integer[] arrr = Arrays.copyOf(a, a.length + b.length);
                                    System.arraycopy(b, 0, arrr, a.length, b.length);
                                    Arrays.sort(arrr);
                                    return arrr;
                                }
                        );
                    } else {
                        mapResult3.put(marker, num);
                    }
                }
            }
        }
        return mapResult3;
    }

    private static void sortingAndSum(List<String[]> allRows, List<String> markers, Map<String, Integer> mapResult) {
        log.info("Different markers conversion to standard markers");
        for (String[] arr : allRows) {
            String str = arr[0];
            Integer num = Integer.valueOf(arr[1]);
            for (String marker : markers) {
                if (str.equalsIgnoreCase(marker)) {
                    if (mapResult.containsKey(marker)) {
                        mapResult.merge(marker, num, Integer::sum);
                    } else {
                        mapResult.put(marker, num);
                    }
                }
            }
        }
    }
}