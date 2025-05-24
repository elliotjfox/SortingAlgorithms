package com.example.javafxsortingalgorithms.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.List;

/**
 * A class representing a single item or object that is used in the animation mode.
 */
public class AnimatedItem extends Group {

    /** The display where this item will be shown on. Will be used to determine various position scale settings, and animation lengths. */
    protected final AnimatedArrayDisplay display;
    /** The position where this item is. May be null, or not accurate while this item is being accurate. */
    protected ItemPosition position;

    /**
     * Create an AnimatedItem object that will be used on the provided display.
     * May also have a position specified, and may have a list of nodes to be added as children.
     *
     * @param display The display this item should use
     * @param position The position this item should be positioned at. May be null
     * @param nodes A list of nodes that are the children to this. May be null or empty
     */
    public AnimatedItem(AnimatedArrayDisplay display, ItemPosition position, List<Node> nodes) {
        this.display = display;
        this.position = position;
        if (nodes != null) {
            getChildren().addAll(nodes);
        }
    }

    /**
     * Sets this item's layout x and y to the ones specified by the position, if it has one.
     */
    public void goToPosition() {
        if (position == null) return;

        position.moveItem(this);
    }

    public void setIndex(int index, double y) {
        position = new ItemIndexPosition(index, y);
        goToPosition();
    }

    public void setPosition(double x, double y) {
        position = new ItemExactPosition(x, y);
        goToPosition();
    }

    /**
     * Queues an animation of this item moving to the specified position, in index and height
     * @param index The index of the position
     * @param y The height of the position
     */
    public void moveToIndex(int index, double y) {
        display.animate(moveToIndexTimeline(index, y));
    }

    /**
     * Queues an animation of this item moving to the specified position, in exact x and y
     * @param x The x position of the target position
     * @param y The y position of the target position
     */
    public void moveToPosition(double x, double y) {
        display.animate(moveToPositionTimeline(x, y));
    }

    /**
     * Queues an animation of this item moving to the specified position
     * @param position The target position
     */
    public void moveToPosition(ItemPosition position) {
        display.animate(moveToPositionTimeline(position));
    }

    /**
     * Creates and returns a timeline of this item moving to the specified position, in index and height. The timeline also sets the position field when finished.
     * @param index The target index
     * @param height The target height
     * @return The timeline
     */
    public Timeline moveToIndexTimeline(int index, double height) {
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(AnimatedArrayDisplay.ANIMATION_LENGTH),
                        new KeyValue(layoutXProperty(), index * display.getElementWidth()),
                        new KeyValue(layoutYProperty(), display.getMaxValue() * display.getHeightMultiplier() - height)
                )
        );

        timeline.setOnFinished(event -> position = new ItemIndexPosition(index, height));

        return timeline;
    }

    /**
     * Creates and returns a timeline of this item moving to the specified position, in x and y. The timeline also sets the position field when finished.
     * @param x The target x position
     * @param y The target y position
     * @return The timeline
     */
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

    /**
     * Creates and returns a timeline of this item moving to the specified position. The timeline also sets the position field when finished.
     * @param position The target position
     * @return The timeline
     */
    public Timeline moveToPositionTimeline(ItemPosition position) {
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(AnimatedArrayDisplay.ANIMATION_LENGTH),
                        new KeyValue(layoutXProperty(), position.getLayoutX(display)),
                        new KeyValue(layoutYProperty(), position.getLayoutY(display))
                )
        );

        timeline.setOnFinished(event -> this.position = position);

        return timeline;
    }

    /**
     * Checks whether this item has a position specified, i.e. if position isn't null
     * @return True if position isn't null, false otherwise
     */
    public boolean hasPosition() {
        return position != null;
    }
}
