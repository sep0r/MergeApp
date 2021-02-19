package ru.demapp;

import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Union {
    private static final Logger log = LoggerFactory.getLogger(Union.class);
    public static List<String[]> unionRows(List<String[]> row1, List<String[]> row2, List<String[]> row3) {
        log.info("Combine into one list");
        return ListUtils.union(ListUtils.union(row1, row2), row3);
    }
}