package com.example.javafxsortingalgorithms;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class TrialDisplay extends GridPane {

    private int trialCount;

    private final List<TrialRow> rows;
    private TrialRow currentTrial;

    public TrialDisplay() {
        trialCount = 0;
        setVgap(10);
        setHgap(10);

        rows = new ArrayList<>();

        Label name = new Label("Algorithm");
        Label number = new Label("Test #");
        Label size = new Label("Size");
        Label readCount = new Label("# Reads");
        Label writeCount = new Label("# Writes");
        Label comparisonCount = new Label("# Comparisons");
        Label progress = new Label("Progress");
        Label time = new Label("Time");

        add(name, TrialInfo.NAME.getCol(), 0);
        add(number, TrialInfo.NUMBER.getCol(), 0);
        add(size, TrialInfo.SIZE.getCol(), 0);
        add(readCount, TrialInfo.READ_COUNT.getCol(), 0);
        add(writeCount, TrialInfo.WRITE_COUNT.getCol(), 0);
        add(comparisonCount, TrialInfo.COMPARISON_COUNT.getCol(), 0);
        add(progress, TrialInfo.PROGRESS.getCol(), 0);
        add(time, TrialInfo.TIME.getCol(), 0);
    }

    public TrialRow startTrial(String name, int size) {
        trialCount++;

        currentTrial = new TrialRow(name, trialCount, size);
        currentTrial.addToDisplay(this, trialCount);
        rows.add(currentTrial);
        return currentTrial;
    }
}
