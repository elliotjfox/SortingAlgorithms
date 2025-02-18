package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.List;

// TODO: Maybe rewrite this class and subclasses
public abstract class ArrayDisplay extends Pane {

    public enum ColourAction {
        READ, WRITE
    }

    protected final SettingsPane settingsPane;

    protected List<Integer> list;
    protected int maxValue;

    protected final HashMap<Integer, ColourAction> colourActions;
    private final DoubleProperty heightMultiplier;
    protected Timeline finishTimeline;

    public ArrayDisplay(SettingsPane settingsPane) {
        heightMultiplier = new DoublePropertyBase() {
            @Override
            public Object getBean() {
                return this;
            }

            @Override
            public String getName() {
                return "Height Multiplier";
            }
        };
        colourActions = new HashMap<>();

        this.settingsPane = settingsPane;
        bindHeight();
    }

    public ArrayDisplay(SettingsPane settingsPane, List<Integer> list) {
        this(settingsPane);
        setList(list);
    }

    public void setList(List<Integer> list) {
        this.list = list;

        resetMax();
        bindHeight();
        initializeElements(list.size());
        update();
        stopFinishTimeline();
    }

    public void readIndex(int i) {
        addColourAction(i, ColourAction.READ);
    }

    public void readIndex(int... indices) {
        for (int index : indices) {
            readIndex(index);
        }
    }

    public void writeIndex(int i) {
        addColourAction(i, ColourAction.WRITE);
    }

    public void writeIndex(int... indices) {
        for (int index : indices) {
            writeIndex(index);
        }
    }

    protected void resetMax() {
        maxValue = list.getFirst();
        for (Integer i : list) {
            if (i > maxValue) maxValue = i;
        }
    }

    protected void bindHeight() {
        heightMultiplier.bind(Bindings.createDoubleBinding(
                () -> (double) settingsPane.getDisplaySettings().getDisplayHeight() / maxValue,
                settingsPane.getDisplaySettings().height()
        ));
    }

    private void addColourAction(int i, ColourAction action) {
        if (!colourActions.containsKey(i)) {
            colourActions.put(i, action);
        } else {
            if (action == ColourAction.WRITE) {
                colourActions.put(i, action);
            }
        }
    }

    public void playFinish() {
        if (finishTimeline != null) {
            finishTimeline.playFromStart();
        }
    }

    public void stopFinishTimeline() {
        if (finishTimeline != null) {
            finishTimeline.stop();
            finishTimeline.getOnFinished().handle(new ActionEvent());
        }
    }

    protected abstract void initializeElements(int count);

    public abstract void update();

    public int getMaxValue() {
        return maxValue;
    }

    public double getElementWidth() {
        return settingsPane.getDisplaySettings().getElementWidth();
    }

    public double getHeightMultiplier() {
        return heightMultiplier.getValue();
    }
}
