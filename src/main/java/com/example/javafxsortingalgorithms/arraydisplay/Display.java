package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Display extends Pane {
    protected SettingsPane settings;
    protected List<Integer> list;
    protected List<DisplayFrame> frames;
    protected int maxValue;

    public Display(SettingsPane settingsPane) {
        this.settings = settingsPane;
        frames = new ArrayList<>();
    }

    protected abstract void showFrame(DisplayFrame frame);

    public void showFrame(List<Integer> list) {
        showFrame(new DisplayFrame(new ArrayList<>(list), new HashMap<>()));
    }

    public void showNextFrame() {
        if (frames.isEmpty()) return;

        showFrame(frames.removeFirst());
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public void addFrame(DisplayFrame frame) {
        frames.add(frame);
    }

    public boolean hasFramesLeft() {
        return !frames.isEmpty();
    }

    protected boolean isFrameValid(DisplayFrame frame) {
        return frame.getList().size() == list.size();
    }

    protected void resetMax() {
        maxValue = list.getFirst();
        for (Integer i : list) {
            if (i > maxValue) maxValue = i;
        }
    }

    public int getMaxValue() {
        return maxValue;
    }

    public double getElementWidth() {
        return settings.getDisplaySettings().getElementWidth();
    }

    public double getHeightMultiplier() {
        return (double) settings.getDisplaySettings().getDisplayHeight() / maxValue;
    }
}
