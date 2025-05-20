package com.example.javafxsortingalgorithms.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.List;

public class AnimatedItem extends Group {

    protected final AnimatedArrayDisplay display;
    protected ItemPosition position;

    public AnimatedItem(AnimatedArrayDisplay display) {
        this.display = display;
    }

    public AnimatedItem(AnimatedArrayDisplay display, ItemPosition position, List<Node> nodes) {
        this.display = display;
        this.position = position;
        if (nodes != null) {
            getChildren().addAll(nodes);
        }
    }

    public void goToPosition() {
        if (!hasPosition()) return;

//        System.out.println("Moving");
        position.moveItem(this);
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
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(AnimatedArrayDisplay.ANIMATION_LENGTH),
                        new KeyValue(layoutXProperty(), index * display.getElementWidth()),
                        new KeyValue(layoutYProperty(), display.getMaxValue() * display.getHeightMultiplier() - y)
                )
        );

        timeline.setOnFinished(event -> position = new ItemIndexPosition(index, y));

        return timeline;
    }

    public Timeline moveToPositionTimeline(double x, double y) {
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(AnimatedArrayDisplay.ANIMATION_LENGTH),
                        new KeyValue(layoutXProperty(), x),
                        new KeyValue(layoutYProperty(), display.getMaxValue() * display.getHeightMultiplier() - y)
                )
        );

        timeline.setOnFinished(event -> position = null);

        return timeline;
    }

    public boolean hasPosition() {
        return position != null;
    }
}
