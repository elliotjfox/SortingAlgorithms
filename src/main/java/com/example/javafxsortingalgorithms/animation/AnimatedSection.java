package com.example.javafxsortingalgorithms.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoublePropertyBase;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;

public class AnimatedSection extends AnimatedItem {

    private static final double SECTION_THICKNESS = 5;
    private static final Color DEFAULT_COLOUR = Color.BLACK;

    private final boolean hasEdges;
    private DoublePropertyBase widthProperty;

    private final Rectangle rectangle;
    private Rectangle left;
    private Rectangle right;

    public AnimatedSection(AnimatedArrayDisplay display, ItemPosition position, List<Node> nodes, double exactWidth, boolean hasEdges) {
        super(display, position, nodes);

        this.hasEdges = hasEdges;

        widthProperty = new DoublePropertyBase() {
            @Override
            public Object getBean() {
                return this;
            }

            @Override
            public String getName() {
                return "Width";
            }
        };
        widthProperty.set(exactWidth);

        rectangle = new Rectangle();
        rectangle.setHeight(SECTION_THICKNESS);
        rectangle.widthProperty().bind(widthProperty);
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
            right.layoutXProperty().bind(widthProperty.subtract(SECTION_THICKNESS));
            right.setLayoutY(-SECTION_THICKNESS);

            getChildren().addAll(left, right);
        }

        setSectionFill(DEFAULT_COLOUR);
    }

    public void setSectionFill(Paint fill) {
        rectangle.setFill(fill);
        if (hasEdges) {
            left.setFill(fill);
            right.setFill(fill);
        }
    }

    /**
     * Queues an animation of this section resizing to the specified width, in number of indices wide.
     * @param width The number of indices this will be wide
     */
    public void resize(int width) {
        display.animate(resizeTimeline(width));
    }

    /**
     * Queues an animation of this section resizing to the specified width
     * @param exactWidth The width this will be
     */
    public void resize(double exactWidth) {
        display.animate(resizeTimeline(exactWidth));
    }

    public Timeline resizeTimeline(int width) {
        return resizeTimeline(toExact(width));
    }

    public Timeline resizeTimeline(double exactWidth) {
        return new Timeline(
                new KeyFrame(
                        Duration.millis(AnimatedArrayDisplay.ANIMATION_LENGTH),
                        new KeyValue(widthProperty, exactWidth)
                )
        );
    }

    private double toExact(int width) {
        return width * display.getElementWidth();
    }

//    public Timeline shrinkTimeline() {
//        return new Timeline(
//                new KeyFrame(
//                        Duration.millis(AnimatedArrayDisplay.ANIMATION_LENGTH),
//                        new KeyValue(scaleYProperty(), 0)
//                )
//        );
//    }
//
//    public Timeline unshrinkTimeline() {
//        return new Timeline(
//                new KeyFrame(
//                        Duration.millis(AnimatedArrayDisplay.ANIMATION_LENGTH),
//                        new KeyValue(scaleYProperty(), 1)
//                )
//        );
//    }
}
