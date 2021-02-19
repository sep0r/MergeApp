package ru.demapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AppTest 
{
    public AppTest() {
        new Merge();
    }

    protected static final List<String> markers = Arrays.asList("mark01", "mark02", "mark03", "mark04");

    protected static final String[] arr1 = {"mark01", "10"};
    protected static final String[] arr2 = {"mark02", "30"};
    protected static final String[] arr3 = {"mark03", "20"};
    protected static final String[] arr4 = {"mark02", "10"};
    protected static final String[] arr5 = {"MaRk01", "40"};
    protected static final String[] arr6 = {"mark03", "10"};

    protected static final List<String[]> rows1;
    protected static final List<String[]> rows2;
    protected static final List<String[]> rows3;

    List<String[]> allRows;
    Map<String, Integer> mapResult1;
    Map<String, Integer> mapResult2;
    Map<String, Integer[]> mapResult3;

    static {
        rows1 = Arrays.asList(arr1, arr2);
        rows2 = Arrays.asList(arr3, arr4);
        rows3 = Arrays.asList(arr5, arr6);
    }

    @Before
    public void setUp(){
        allRows = Union.unionRows(rows1, rows2, rows3);
        mapResult1 = Merge.mergeWithoutNull(allRows, markers);
        mapResult2 = Merge.mergeByMarkers(allRows, markers);
        mapResult3 = Merge.mergeIntoArr(allRows, markers);
    }

    @Test
    public void combiningLists() {
        Assert.assertEquals(6, allRows.size());
    }

    @Test
    public void savedData() {
        boolean res = mapResult1.containsKey("mark01");
        Assert.assertTrue(res);
    }

    @Test
    public void conversionDifferentMarkersToStandartMarkers() {
        boolean res = mapResult1.containsKey("MarK01");
        Assert.assertFalse(res);
    }

    @Test
    public void workSortAndSumIdenticalRegisters() {
        int amount1 = mapResult1.get("mark01");
        int amount2 = mapResult1.get("mark02");
        int amount3 = mapResult1.get("mark03");
        Assert.assertEquals(50, amount1);
        Assert.assertEquals(40, amount2);
        Assert.assertEquals(30, amount3);
    }

    @Test
    public void emptyValueMarker() {
        Assert.assertFalse(mapResult1.containsKey("mark04"));
    }

    @Test
    public void counterArr() {
        Integer [] num  = mapResult3.get("mark01");
        int count = num.length;
        Assert.assertEquals(2, count);
    }
}
