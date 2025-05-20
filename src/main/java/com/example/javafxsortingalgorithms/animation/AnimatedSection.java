package com.example.javafxsortingalgorithms.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;

public class AnimatedSection extends AnimatedItem {

    private static final double SECTION_THICKNESS = 5;
    private static final Color DEFAULT_COLOUR = Color.BLACK;

    private double width;
    private boolean hasEdges;

    private final Rectangle rectangle;
    private Rectangle left;
    private Rectangle right;

    public AnimatedSection(AnimatedArrayDisplay display, ItemPosition position, List<Node> nodes, double exactWidth, boolean hasEdges) {
        super(display, position, nodes);

        this.width = exactWidth;
        this.hasEdges = hasEdges;

        rectangle = new Rectangle();
        rectangle.setWidth(exactWidth);
        rectangle.setHeight(SECTION_THICKNESS);

        getChildren().add(rectangle);

        if (hasEdges) {
            left = new Rectangle();
            left.setWidth(SECTION_THICKNESS);
            left.setHeight(2 * SECTION_THICKNESS);
            left.setLayoutX(0);
            left.setLayoutY(-SECTION_THICKNESS);

            right = new Rectangle();
            right.setWidth(SECTION_THICKNESS);
            right.setHeight(2 * SECTION_THICKNESS);
            right.setLayoutX(width - SECTION_THICKNESS);
            right.setLayoutY(-SECTION_THICKNESS);

            getChildren().addAll(left, right);
        }

        setFill(DEFAULT_COLOUR);
    }

    public void setFill(Color colour) {
        rectangle.setFill(colour);
        if (hasEdges) {
            left.setFill(colour);
            right.setFill(colour);
        }
    }

    public Timeline shrinkTimeline() {
        return new Timeline(
                new KeyFrame(
                        Duration.millis(AnimatedArrayDisplay.ANIMATION_LENGTH),
                        new KeyValue(scaleYProperty(), 0)
                )
        );
    }

    public Timeline unshrinkTimeline() {
        return new Timeline(
                new KeyFrame(
                        Duration.millis(AnimatedArrayDisplay.ANIMATION_LENGTH),
                        new KeyValue(scaleYProperty(), 1)
                )
        );
    }

    public Timeline resizeTimeline(int width) {
        return resizeTimeline(calculateWidth(width));
    }

    public Timeline resizeTimeline(double exactWidth) {
//        KeyFrame keyFrame = new KeyFrame(
//                Duration.millis(ArrayDetailedDisplay.ANIMATION_LENGTH),
//                new KeyValue(rectangle.widthProperty(), exactWidth)
//        );
//        if (hasEdges) {
//            keyFrame.getValues().add(new KeyValue(right.layoutXProperty(), exactWidth - SECTION_THICKNESS));
//        }

        Timeline timeline; //= new Timeline(keyFrame);
        if (hasEdges) {
            timeline = new Timeline(
                    new KeyFrame(
                            Duration.millis(AnimatedArrayDisplay.ANIMATION_LENGTH),
                            new KeyValue(rectangle.widthProperty(), exactWidth),
                            new KeyValue(right.layoutXProperty(), exactWidth - SECTION_THICKNESS)
                    )
            );
        } else {
            timeline = new Timeline(
                    new KeyFrame(
                            Duration.millis(AnimatedArrayDisplay.ANIMATION_LENGTH),
                            new KeyValue(rectangle.widthProperty(), exactWidth)
                    )
            );
        }
        timeline.setOnFinished(event -> width = exactWidth);
        return timeline;
    }

    public void setSectionVisible(boolean b) {
        rectangle.setVisible(b);
        if (hasEdges) {
            left.setVisible(b);
            right.setVisible(b);
        }
    }

    public double calculateWidth(int width) {
        return display.getElementWidth() * width;
    }
}
