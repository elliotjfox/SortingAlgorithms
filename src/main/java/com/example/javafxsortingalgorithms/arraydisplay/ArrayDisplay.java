package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.List;

// TODO: Fix this (when the array should be initialized and when it should be bound to this)
public abstract class ArrayDisplay extends Pane {

    public enum ColourAction {
        READ, WRITE
    }

    protected List<Integer> array;
    protected int maxValue;
    protected SettingsPane settingsPane;
    protected final HashMap<Integer, ColourAction> colourActions;
    protected double heightMultiplier;

    // TODO: Put this inside a border pane instead of calculating the border in a billion places
    public ArrayDisplay(List<Integer> array, SettingsPane settingsPane) {
        setSettingsPane(settingsPane);
        setArray(array);

        colourActions = new HashMap<>();
    }

    public ArrayDisplay(SettingsPane settingsPane) {
        setSettingsPane(settingsPane);

        colourActions = new HashMap<>();
    }

    public void resetMax() {
        maxValue = array.getFirst();
        for (Integer i : array) {
            if (i > maxValue) maxValue = i;
        }
    }

    public int getMaxValue() {
        return maxValue;
    }

    public double getHeightMultiplier() {
        return heightMultiplier;
    }

    // TODO: Also stop any animations here
    public void setArray(List<Integer> list) {
        this.array = list;
        resetMax();
        createElements(list.size());
        drawArray();
    }

    public void setSettingsPane(SettingsPane settingsPane) {
        this.settingsPane = settingsPane;
    }

    public void addColourAction(int i, ColourAction action) {
//        System.out.println("trying to put [" + i + ", " + action + "]...");
        if (!colourActions.containsKey(i)) {
            colourActions.put(i, action);
        } else {
            // We want to only overwrite read actions with write actions
            if (action == ColourAction.WRITE) {
                colourActions.put(i, action);
            }
        }
    }

    public void readIndex(int i) {
        addColourAction(i, ColourAction.READ);
    }

    public void writeIndex(int i) {
        addColourAction(i, ColourAction.WRITE);
    }

    public void setHeightMultiplier() {
        if (maxValue > settingsPane.getDisplaySettings().getDisplayMaxHeight()) {
            heightMultiplier = (double) settingsPane.getDisplaySettings().getDisplayMaxHeight() / maxValue;
        } else if (maxValue < settingsPane.getDisplaySettings().getDisplayMinHeight()) {
            heightMultiplier = (double) settingsPane.getDisplaySettings().getDisplayMinHeight() / maxValue;
        } else {
            heightMultiplier = 1;
        }
    }

    protected abstract void createElements(int count);

    public abstract void drawArray();

    // TODO: Implement
    public void onFinish() {}
}
