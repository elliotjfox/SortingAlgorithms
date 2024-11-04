package com.example.javafxsortingalgorithms.arraydisplay;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class DetailedBinaryTree extends DetailedItem {

    private final List<DetailedBinaryTreeItem> treeItems;
    private final List<Line> lines;

    public DetailedBinaryTree(List<Integer> list) {
        treeItems = new ArrayList<>();
        lines = new ArrayList<>();

        for (Integer i : list) {
            DetailedBinaryTreeItem item = new DetailedBinaryTreeItem(i, list.size());
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

    private int getWidth(int i) {
        return (int) Math.pow(2, roundDown(treeItems.size() - 1) - roundDown(i));
    }

    private int roundDown(int i) {
        return (int) (Math.log(i + 1) / Math.log(2));
    }

    private void lineBetween(DetailedBinaryTreeItem upper, DetailedBinaryTreeItem lower) {
        Line line = new Line(upper.getLayoutX() + 12.5, upper.getLayoutY() + 25, lower.getLayoutX() + 12.5, lower.getLayoutY());
        getChildren().add(line);
        lines.set(treeItems.indexOf(lower), line);
    }

    public Timeline animateSwap(int first, int second) {
        DetailedBinaryTreeItem firstItem = treeItems.get(first);
        DetailedBinaryTreeItem secondItem = treeItems.get(second);
        treeItems.set(first, treeItems.set(second, treeItems.get(first)));
        return new Timeline(
                new KeyFrame(
                        Duration.millis(ArrayDetailedDisplay.ANIMATION_LENGTH),
                        new KeyValue(firstItem.layoutXProperty(), secondItem.getLayoutX()),
                        new KeyValue(firstItem.layoutYProperty(), secondItem.getLayoutY()),
                        new KeyValue(secondItem.layoutXProperty(), firstItem.getLayoutX()),
                        new KeyValue(secondItem.layoutYProperty(), firstItem.getLayoutY())
                )
        );
    }

    // TODO: Make this happen at the same time as the bars moving
    public Timeline extractItem(int toRemove, int toReplace) {
        DetailedBinaryTreeItem toRemoveItem = treeItems.get(toRemove);
        Line toRemoveLine = lines.get(toReplace);
        DetailedBinaryTreeItem toReplaceItem = treeItems.get(toReplace);

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(ArrayDetailedDisplay.ANIMATION_LENGTH),
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

    public Timeline read(int index) {
        Polygon arrow = ArrayDetailedDisplay.createReadArrow();
        arrow.setLayoutX(treeItems.get(index).getLayoutX());
        arrow.setLayoutY(treeItems.get(index).getLayoutY() + 25);
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        event -> getChildren().add(arrow)
                ),
                new KeyFrame(
                        Duration.millis(ArrayDetailedDisplay.ANIMATION_LENGTH),
                        new KeyValue(arrow.layoutYProperty(), treeItems.get(index).getLayoutY())
                )
        );
        timeline.setOnFinished(event -> getChildren().remove(arrow));
        return timeline;
    }

    private static class DetailedBinaryTreeItem extends Group {
        public DetailedBinaryTreeItem(int i, int max) {
            Rectangle rectangle = new Rectangle(25, 25, Color.hsb(360.0 * i / max, 1.0, 1.0));
            getChildren().add(rectangle);
            Label label = new Label(Integer.toString(i));
            label.layoutXProperty().bind(label.widthProperty().divide(2));
            label.layoutYProperty().bind(label.heightProperty().divide(2));
            getChildren().add(label);
        }
    }
}
