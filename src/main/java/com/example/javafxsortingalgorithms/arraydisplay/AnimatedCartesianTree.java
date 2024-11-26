package com.example.javafxsortingalgorithms.arraydisplay;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AnimatedCartesianTree extends AnimatedItem {

    private final List<DetailedCartesianTreeItem> treeItems;
    private final List<Boolean> found;
    private final List<Line> lines;

    public AnimatedCartesianTree(ArrayAnimatedDisplay display, List<Integer> list) {
        super(display);

        treeItems = new ArrayList<>();
        found = new ArrayList<>();
        lines = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            DetailedCartesianTreeItem item = new DetailedCartesianTreeItem(list.get(i), display.getMaxValue());
            treeItems.add(item);
            found.add(false);
            getChildren().add(item);
            item.setLayoutY(0);
            item.setLayoutX(display.getElementWidth() * i);
        }
    }

    private Timeline lowerOthersTimeline(int highestIndex, int from, int to) {
        found.set(highestIndex, true);
        List<KeyValue> keyValues = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            if (found.get(i)) continue;
            keyValues.add(new KeyValue(treeItems.get(i).layoutYProperty(), treeItems.get(i).layoutYProperty().getValue() + 30));
        }
        return new Timeline(
                new KeyFrame(
                        Duration.millis(ArrayAnimatedDisplay.ANIMATION_LENGTH),
                        "",
                        event -> {},
                        keyValues
                )
        );
    }

    public void lowerOthers(int highestIndex, int from, int to) {
        display.animate(lowerOthersTimeline(highestIndex, from, to));
    }

    private static class DetailedCartesianTreeItem extends StackPane {
        public DetailedCartesianTreeItem(int i, int max) {
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
