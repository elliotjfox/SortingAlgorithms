package com.example.javafxsortingalgorithms;

import javafx.beans.binding.Bindings;
import javafx.beans.property.LongProperty;
import javafx.beans.property.LongPropertyBase;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;

public class TestEntry {

    private int testId;
    private int testSize;
    private long readCount;
    private long writeCount;
    private double progress;
    private long startTime;

    GridPane gridPane;

    private final Label testNameLabel;
    private final Label testIdLabel;
    private final Label testSizeLabel;
    private final Label readCountLabel;
    private final Label writeCountLabel;
    private final Label progressLabel;
    private final ProgressBar progressBar;
    private final Label timeLabel;
    private final Label rawTimeLabel;

    public TestEntry(int testId, int testSize, String testName, GridPane gridPane) {
        this.testId = testId;
        this.testSize = testSize;
        readCount = 0;
        writeCount = 0;
        progress = 0;
        startTime = System.currentTimeMillis();

        this.gridPane = gridPane;

        testNameLabel = new Label(testName);
        testIdLabel = new Label(Integer.toString(testId));
        testSizeLabel = new Label(Integer.toString(testSize));
        readCountLabel = new Label();
        writeCountLabel = new Label();
        progressLabel = new Label();
        progressBar = new ProgressBar(0);
        timeLabel = new Label();
        rawTimeLabel = new Label();

        gridPane.add(testNameLabel, TestDisplay.DisplayOrder.NAME.getCol(), testId);
        gridPane.add(testIdLabel, TestDisplay.DisplayOrder.NUMBER.getCol(), testId);
        gridPane.add(testSizeLabel, TestDisplay.DisplayOrder.SIZE.getCol(), testId);
        gridPane.add(readCountLabel, TestDisplay.DisplayOrder.READ_COUNT.getCol(), testId);
        gridPane.add(writeCountLabel, TestDisplay.DisplayOrder.WRITE_COUNT.getCol(), testId);
        gridPane.add(progressBar, TestDisplay.DisplayOrder.PROGRESS.getCol() ,testId);
        gridPane.add(progressLabel, TestDisplay.DisplayOrder.PROGRESS.getCol(), testId);
        gridPane.add(timeLabel, TestDisplay.DisplayOrder.TIME.getCol(), testId);
//        gridPane.add(rawTimeLabel, TestDisplay.DisplayOrder.RAW_TIME.getCol(), testId);
    }

    public void addRead() {
        readCount++;
    }

    public void addRead(int count) {
        readCount += count;
    }

    public void addWrite() {
        writeCount++;
    }

    public void addWrite(int count) {
        writeCount += count;
    }

    public void updateProgress(double progress) {
        this.progress = progress;
        this.progress = Math.min(1, this.progress);
        this.progress = Math.max(0, this.progress);
    }

    public void updateTestInfo() {
        readCountLabel.setText(formatLong(readCount));
        writeCountLabel.setText(formatLong(writeCount));
        progressLabel.setText(formatProgress());
        progressBar.setProgress(progress);
        long time = System.currentTimeMillis() - startTime;
        timeLabel.setText(formatTime(time));
        rawTimeLabel.setText(time + "ms");
    }

    public void done() {
        progress = 1;
    }

    public void setRawTimeVisible(boolean visible) {
        if (visible) gridPane.getChildren().remove(rawTimeLabel);
        else if (!gridPane.getChildren().contains(rawTimeLabel)) gridPane.add(rawTimeLabel, TestDisplay.DisplayOrder.RAW_TIME.getCol(), testId);
    }

    private String formatProgress() {
        float formattedProgress = ((int) (progress * 1000)) / 10.0f;
        return formattedProgress + "%";
    }

    private String formatTime(long time) {
        if (time < 1000) {
            return "0".repeat(3 - Long.toString(time).length()) + time + "ms";
        } else if (time / 1000 < 60) {
            return (time / 1000) + "s, " + formatTime(time % 1000);
        } else {
            long sec = time / 1000;
            return (sec / 60) + "m, " + (sec % 60) + "s";
        }
    }

    private String formatLong(long input) {
        // 1 billion+
        if (input > 1e9) {
            double tmp = (((long) (input / 1e7)) / 100.0);
            return tmp + "B";
        // 1 million+
        } else if (input > 1e6) {
            double tmp = (((long) (input / 1e4)) / 100.0);
            return tmp + "M";
        } else {
            return Long.toString(input);
        }
    }
}
