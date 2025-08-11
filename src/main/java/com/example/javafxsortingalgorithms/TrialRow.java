package com.example.javafxsortingalgorithms;

import javafx.scene.control.Label;

public class TrialRow {

    private final String name;
    private final int number;
    private final int size;
    private long readCount;
    private long writeCount;
    private long comparisonCount;
    private double progress;
    private long time;
    private long startTime;

    private final Label nameLabel;
    private final Label numberLabel;
    private final Label sizeLabel;
    private final Label readCountLabel;
    private final Label writeCountLabel;
    private final Label comparisonCountLabel;
    private final Label progressLabel;
    private final Label timeLabel;

    public TrialRow(String name, int number, int size) {
        this.name = name;
        this.number = number;
        this.size = size;

        nameLabel = new Label();
        numberLabel = new Label();
        sizeLabel = new Label();
        readCountLabel = new Label();
        writeCountLabel = new Label();
        comparisonCountLabel = new Label();
        progressLabel = new Label();
        timeLabel = new Label();

        updateLabels();
    }

    public void start() {
        startTime = System.currentTimeMillis();
        time = 0;
    }

    public void addRead(int count) {
        if (count > 0) readCount += count;
    }

    public void addWrite(int count) {
        if (count > 0) writeCount += count;
    }

    public void addComparison(int count) {
        if (count > 0) comparisonCount += count;
    }

    public void setProgress(double progress) {
        this.progress = Math.clamp(progress, 0, 1);
    }

    public void done() {
        setProgress(1);
    }

    public void updateLabels() {
        updateTime();
        nameLabel.setText(name);
        numberLabel.setText(Integer.toString(number));
        sizeLabel.setText(Integer.toString(size));
        readCountLabel.setText(formatLargeNumber(readCount));
        writeCountLabel.setText(formatLargeNumber(writeCount));
        comparisonCountLabel.setText(formatLargeNumber(comparisonCount));
        progressLabel.setText(formatProgress(progress));
        timeLabel.setText(formatTime(time));
    }

    public void addToDisplay(TrialDisplay display, int rowNumber) {
        updateLabels();
        display.add(nameLabel, TrialInfo.NAME.getCol(), number);
        display.add(numberLabel, TrialInfo.NUMBER.getCol(), number);
        display.add(sizeLabel, TrialInfo.SIZE.getCol(), number);
        display.add(readCountLabel, TrialInfo.READ_COUNT.getCol(), number);
        display.add(writeCountLabel, TrialInfo.WRITE_COUNT.getCol(), number);
        display.add(comparisonCountLabel, TrialInfo.COMPARISON_COUNT.getCol(), number);
        display.add(progressLabel, TrialInfo.PROGRESS.getCol(), number);
        display.add(timeLabel, TrialInfo.TIME.getCol(), number);
    }

    private void updateTime() {
        time = System.currentTimeMillis() - startTime;
    }

    private static String formatLargeNumber(long input) {
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

    private static String formatTime(long time) {
        if (time < 1000) {
            return "0".repeat(3 - Long.toString(time).length()) + time + "ms";
        } else if (time / 1000 < 60) {
            return (time / 1000) + "s, " + formatTime(time % 1000);
        } else {
            long sec = time / 1000;
            return (sec / 60) + "m, " + (sec % 60) + "s";
        }
    }

    private static String formatProgress(double progress) {
        float formattedProgress = ((int) (progress * 1000)) / 10.0f;
        return formattedProgress + "%";
    }
}
