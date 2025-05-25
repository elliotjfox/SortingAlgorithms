package com.example.javafxsortingalgorithms.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a binary tree that is used by HeapSort.
 */
public class AnimatedBinaryTree extends AnimatedItem {

    private final List<AnimatedBinaryTreeItem> treeItems;
    private final List<Line> lines;


    // TODO: Make this adaptable to different sizes maybe?
    /**
     * Create an AnimatedBinaryTree object that will be used on the provided display.
     * May have a position specified, and may have a list of nodes to be added as children.
     * Also has the list this binary tree is made off of.
     *
     * @param display The display this item will be used by
     * @param position The position this item should be at. May be null
     * @param nodes A list of nodes that are children to this item. May be null or empty
     * @param list The list of integers this will use as a blueprint
     */
    public AnimatedBinaryTree(AnimatedArrayDisplay display, ItemPosition position, List<Node> nodes, List<Integer> list) {
        super(display, position, nodes);

        treeItems = new ArrayList<>();
        lines = new ArrayList<>();

        for (Integer i : list) {
            AnimatedBinaryTreeItem item = new AnimatedBinaryTreeItem(i, list.size());
            treeItems.add(item);
            getChildren().add(item);
            lines.add(null);
        }

        for (int i = 0; i < treeItems.size(); i++) {
            int xPos = i - (int) Math.pow(2, roundDown(i)) + 1;
            treeItems.get(i).setLayoutX(getWidth(i) * 30 * xPos + getWidth(i) * 15);
            treeItems.get(i).setLayoutY(roundDown(i) * 35);
        }

        for (int i = 0; i < treeItems.size(); i++) {
            if (i * 2 + 1 < treeItems.size()) lineBetween(treeItems.get(i), treeItems.get(i * 2 + 1));
            if (i * 2 + 2 < treeItems.size()) lineBetween(treeItems.get(i), treeItems.get(i * 2 + 2));
        }
    }

    /**
     * Queues an animation of two elements in the tree swapping position.
     * @param first The index of the first element to swap
     * @param second The index of the second element to swap
     */
    public void swap(int first, int second) {
        display.animate(swapTimeline(first, second));
    }

    /**
     * Queues an animation of one of the elements being removed from the binary tree and being replaced.
     * @param toRemove The index of the element to remove
     * @param toReplace The index of the element replacing the removed element
     */
    public void extractItem(int toRemove, int toReplace) {
        display.animate(extractItemTimeline(toRemove, toReplace));
    }

    /**
     * Queues an animation of an element being read.
     * @param index The index of the element being read
     */
    public void read(int index) {
        display.animate(readTimeline(index));
    }

    /**
     * Creates and returns a timeline of an animation of two element swapping position
     * @param first The index of the first element to swap
     * @param second The index of the second element to swap
     * @return The timeline
     */
    public Timeline swapTimeline(int first, int second) {
        AnimatedBinaryTreeItem firstItem = treeItems.get(first);
        AnimatedBinaryTreeItem secondItem = treeItems.get(second);
        treeItems.set(first, treeItems.set(second, treeItems.get(first)));
        return new Timeline(
                new KeyFrame(
                        Duration.millis(AnimatedArrayDisplay.ANIMATION_LENGTH),
                        new KeyValue(firstItem.layoutXProperty(), secondItem.getLayoutX()),
                        new KeyValue(firstItem.layoutYProperty(), secondItem.getLayoutY()),
                        new KeyValue(secondItem.layoutXProperty(), firstItem.getLayoutX()),
                        new KeyValue(secondItem.layoutYProperty(), firstItem.getLayoutY())
                )
        );
    }

    /**
     * Creates and returns a timeline of an animation of an element being removed and replaced by another.
     * @param toRemove The index of the element to remove
     * @param toReplace The index of the element replacing the other
     * @return The timeline
     */
    public Timeline extractItemTimeline(int toRemove, int toReplace) {
        AnimatedBinaryTreeItem toRemoveItem = treeItems.get(toRemove);
        Line toRemoveLine = lines.get(toReplace);
        AnimatedBinaryTreeItem toReplaceItem = treeItems.get(toReplace);

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(AnimatedArrayDisplay.ANIMATION_LENGTH),
                        new KeyValue(toReplaceItem.layoutXProperty(), toRemoveItem.getLayoutX()),
                        new KeyValue(toReplaceItem.layoutYProperty(), toRemoveItem.getLayoutY()),
                        new KeyValue(toRemoveItem.layoutXProperty(), toReplaceItem.getLayoutX()),
                        new KeyValue(toRemoveItem.layoutYProperty(), toReplaceItem.getLayoutY()),
                        new KeyValue(toRemoveItem.opacityProperty(), 0),
                        new KeyValue(toRemoveLine.opacityProperty(), 0)
                )
        );
        timeline.setOnFinished(event -> {
            treeItems.set(toRemove, treeItems.set(toReplace, toRemoveItem));
            toRemoveItem.setOpacity(0);
            getChildren().remove(toRemoveLine);
            lines.set(toReplace, null);
        });
        return timeline;
    }

    /**
     * Creates and returns a timeline of an animation of an element being read.
     * @param index The index of the element being read
     * @return The timeline
     */
    public Timeline readTimeline(int index) {
        Polygon arrow = PolygonWrapper.readArrow();
        arrow.setLayoutX(treeItems.get(index).getLayoutX());
        arrow.setLayoutY(treeItems.get(index).getLayoutY() + 25);
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        event -> getChildren().add(arrow)
                ),
                new KeyFrame(
                        Duration.millis(AnimatedArrayDisplay.ANIMATION_LENGTH),
                        new KeyValue(arrow.layoutYProperty(), treeItems.get(index).getLayoutY())
                )
        );
        timeline.setOnFinished(event -> getChildren().remove(arrow));
        return timeline;
    }

    private int getWidth(int i) {
        return (int) Math.pow(2, roundDown(treeItems.size() - 1) - roundDown(i));
    }

    private int roundDown(int i) {
        return (int) (Math.log(i + 1) / Math.log(2));
    }

    private void lineBetween(AnimatedBinaryTreeItem upper, AnimatedBinaryTreeItem lower) {
        Line line = new Line(upper.getLayoutX() + 12.5, upper.getLayoutY() + 25, lower.getLayoutX() + 12.5, lower.getLayoutY());
        getChildren().add(line);
        lines.set(treeItems.indexOf(lower), line);
    }

    private static class AnimatedBinaryTreeItem extends StackPane {
        public AnimatedBinaryTreeItem(int i, int max) {
            setAlignment(Pos.CENTER);
            Rectangle rectangle = new Rectangle(25, 25, Color.hsb(360.0 * i / max, 1.0, 1.0));
            rectangle.setStroke(Color.BLACK);
            getChildren().add(rectangle);
            Label label = new Label(Integer.toString(i));
            getChildren().add(label);
            label.setTextAlignment(TextAlignment.CENTER);
        }
    }
}
