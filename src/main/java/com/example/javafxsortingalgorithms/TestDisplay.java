package com.example.javafxsortingalgorithms;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;

// TODO: Make this also an arrayDisplay
public class TestDisplay extends GridPane {

    public enum DisplayOrder {
        NAME(0),
        NUMBER(1),
        SIZE(2),
        READ_COUNT(3),
        WRITE_COUNT(4),
        PROGRESS(5),
        TIME(6),
        RAW_TIME(7);

        private int col;

        DisplayOrder(int col) {
            this.col = col;
        }

        public int getCol() {
            return col;
        }
    }

    private int testCount;

    private ArrayList<TestEntry> allTestEntries;
    private TestEntry currentTest;

    private final ArrayList<Label> titles;

    public TestDisplay() {
        testCount = 0;
        setVgap(10);
        setHgap(10);

        allTestEntries = new ArrayList<>();
        titles = new ArrayList<>();

        Label name = new Label("Algorithm");
        Label number = new Label("Test #");
        Label size = new Label("Size");
        Label readCount = new Label("# Read Ops");
        Label writeCount = new Label("# Write Ops");
        Label progress = new Label("Progress");
        Label time = new Label("Time");
        Label rawTime = new Label("Raw Time");

        titles.add(name);
        titles.add(number);
        titles.add(size);
        titles.add(readCount);
        titles.add(writeCount);
        titles.add(progress);
        titles.add(time);
//        titles.add(rawTime);

        add(name, DisplayOrder.NAME.getCol(), 0);
        add(number, DisplayOrder.NUMBER.getCol(), 0);
        add(size, DisplayOrder.SIZE.getCol(), 0);
        add(readCount, DisplayOrder.READ_COUNT.getCol(), 0);
        add(writeCount, DisplayOrder.WRITE_COUNT.getCol(), 0);
        add(progress, DisplayOrder.PROGRESS.getCol(), 0);
        add(time, DisplayOrder.TIME.getCol(), 0);
//        add(rawTime, DisplayOrder.RAW_TIME.getCol(), 0);
    }

    public void startTest(String name, int size) {
        testCount++;

        currentTest = new TestEntry(testCount, size, name, this);
        currentTest.updateTestInfo();
        allTestEntries.add(currentTest);
    }

    public TestEntry getCurrentTest() {
        return currentTest;
    }

    // TODO: Fix this
    public void setRawTimeVisible(boolean visible) {
        for (TestEntry entry : allTestEntries) {
            entry.setRawTimeVisible(visible);
        }
        if (visible) add(titles.get(DisplayOrder.RAW_TIME.getCol()), 7, 0);
        else getChildren().remove(titles.get(DisplayOrder.RAW_TIME.getCol()));
    }
}

