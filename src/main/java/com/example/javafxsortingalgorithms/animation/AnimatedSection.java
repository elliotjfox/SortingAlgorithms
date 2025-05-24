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

/**
 * A class representing a 'section' that algorithms can use in the animated mode to show
 * things like how they are divided, or what part of the list the algorithm is looking at currently
 */
public class AnimatedSection extends AnimatedItem {

    private static final double SECTION_THICKNESS = 5;
    private static final Color DEFAULT_COLOUR = Color.BLACK;

    private final boolean hasEdges;
    private final DoublePropertyBase widthProperty;

    private final Rectangle rectangle;
    private Rectangle left;
    private Rectangle right;

    /**
     * Creates an AnimatedSection that will be used on the provided display.
     * May also have a position specified, and may have a list of nodes to be added as children.
     * Initial width is also specified, and whether the section will have edges (extra vertical bars on the edges of the section).
     *
     * @param display The display this item will be used by
     * @param position The position this item should be position at. May be null
     * @param nodes A list of nodes that are children to this item. May be null or empty
     * @param exactWidth The initial width of this section
     * @param hasEdges Whether this section has edges
     */
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

    /**
     * Sets the fill of the section.
     * @param fill The new colour of the section
     */
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
     * Queues an animation of this section resizing to the specified width.
     * @param exactWidth The width this will be
     */
    public void resize(double exactWidth) {
        display.animate(resizeTimeline(exactWidth));
    }

    /**
     * Creates and returns a timeline of this section resizing to the specified width, in number of elements wide.
     * @param width The new width for the section, in number of elements wide
     * @return The timeline
     */
    public Timeline resizeTimeline(int width) {
        return resizeTimeline(toExact(width));
    }

    /**
     * Creates and returns a timeline of this section resizing to the specified width.
     * @param exactWidth The new width for the section
     * @return The timeline
     */
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
