package com.example.javafxsortingalgorithms.arraydisplay;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;

public class DetailedItem extends Group {

    protected final ArrayDetailedDisplay display;

    public DetailedItem(ArrayDetailedDisplay display) {
        this.display = display;
    }

    public DetailedItem(ArrayDetailedDisplay display, int index, double y) {
        this(display);
        setIndex(index, y);
    }

    public DetailedItem(ArrayDetailedDisplay display, double x, double y) {
        this(display);
        setPosition(x, y);
    }

    public void setIndex(int index, double y) {
        setLayoutX(index * display.getElementWidth());
        setLayoutY(display.getMaxValue() * display.getHeightMultiplier() - y);
    }

    public void setPosition(double x, double y) {
        setLayoutX(x);
        setLayoutY(display.getMaxValue() * display.getHeightMultiplier() - y);
    }

    public void moveToIndex(int index, double y) {
        display.animate(moveToIndexTimeline(index, y));
    }

    public void moveToPosition(double x, double y) {
        display.animate(moveToPositionTimeline(x, y));
    }

    public Timeline moveToIndexTimeline(int index, double y) {
        return new Timeline(
                new KeyFrame(
                        Duration.millis(ArrayDetailedDisplay.ANIMATION_LENGTH),
                        new KeyValue(layoutXProperty(), index * display.getElementWidth()),
                        new KeyValue(layoutYProperty(), display.getMaxValue() * display.getHeightMultiplier() - y)
                )
        );
    }

    public Timeline moveToPositionTimeline(double x, double y) {
        return new Timeline(
                new KeyFrame(
                        Duration.millis(ArrayDetailedDisplay.ANIMATION_LENGTH),
                        new KeyValue(layoutXProperty(), x),
                        new KeyValue(layoutYProperty(), display.getMaxValue() * display.getHeightMultiplier() - y)
                )
        );
    }
}
