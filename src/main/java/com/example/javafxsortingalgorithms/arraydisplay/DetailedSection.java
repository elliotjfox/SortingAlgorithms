package com.example.javafxsortingalgorithms.arraydisplay;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class DetailedSection extends DetailedItem {

    private double width;
    private boolean hasEdges;

    private final Rectangle rectangle;
    private Rectangle left;
    private Rectangle right;

    public DetailedSection(double width, boolean hasEdges) {
        this.width = width;
        this.hasEdges = hasEdges;

        rectangle = new Rectangle();
        rectangle.setWidth(width);
        rectangle.setHeight(5);

        getChildren().add(rectangle);

        if (hasEdges) {
            left = new Rectangle();
            left.setWidth(5);
            left.setHeight(10);
            left.setLayoutX(0);
            left.setLayoutY(-5);

            right = new Rectangle();
            right.setWidth(5);
            right.setHeight(10);
            right.setLayoutX(width - 5);
            right.setLayoutY(-5);

            getChildren().addAll(left, right);
        }
    }

    public void setFill(Color colour) {
        rectangle.setFill(colour);
        if (hasEdges) {
            left.setFill(colour);
            right.setFill(colour);
        }
    }

    public Timeline shrink() {
        return new Timeline(
                new KeyFrame(
                        Duration.millis(ArrayDetailedDisplay.ANIMATION_LENGTH),
                        new KeyValue(scaleYProperty(), 0)
                )
        );
    }

    public Timeline unshrink() {
        return new Timeline(
                new KeyFrame(
                        Duration.millis(ArrayDetailedDisplay.ANIMATION_LENGTH),
                        new KeyValue(scaleYProperty(), 1)
                )
        );
    }

    public Timeline resizeAnimation(double newWidth) {
        Timeline timeline;
        if (hasEdges) {
            timeline = new Timeline(
                    new KeyFrame(
                            Duration.millis(ArrayDetailedDisplay.ANIMATION_LENGTH),
                            new KeyValue(rectangle.widthProperty(), newWidth),
                            new KeyValue(right.layoutXProperty(), newWidth - 5)
                    )
            );
        } else {
            timeline = new Timeline(
                    new KeyFrame(
                            Duration.millis(ArrayDetailedDisplay.ANIMATION_LENGTH),
                            new KeyValue(rectangle.widthProperty(), newWidth)
                    )
            );
        }
        timeline.setOnFinished(event -> width = newWidth);
        return timeline;
    }

    public void setSectionVisible(boolean b) {
        rectangle.setVisible(b);
        if (left != null) left.setVisible(b);
        if (right != null) right.setVisible(b);
    }
}
