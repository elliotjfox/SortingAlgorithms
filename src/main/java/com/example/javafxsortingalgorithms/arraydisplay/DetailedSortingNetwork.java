package com.example.javafxsortingalgorithms.arraydisplay;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.*;

// TODO: Add coloured boxes around sections
public class DetailedSortingNetwork extends DetailedItem {

    private final List<Integer> list;
    private final List<List<Integer>> allComparisons;
    private final List<List<Node>> visuals;
    private final List<Line> lines;

    public DetailedSortingNetwork(List<Integer> list) {
        this.list = list;
        allComparisons = new ArrayList<>();
        visuals = new ArrayList<>();
        lines = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Line line = new Line(i * 25 + 12.5, 0, i * 25 + 12.5, 0);
            getChildren().add(line);
            lines.add(line);
        }
    }

    public void addComparisons(List<Integer> comparisons) {
        drawNewRow(comparisons, allComparisons.size() * 25);
        allComparisons.add(comparisons);
        for (Line line : lines) {
            line.setEndY(allComparisons.size() * 25);
        }
    }

    public Timeline moveUp() {
        Timeline timeline = new Timeline();
        ArrayList<KeyValue> values = new ArrayList<>();
        for (List<Node> row : visuals) {
            if (row == visuals.getFirst()) continue;
            for (Node node : row) {
                values.add(new KeyValue(node.layoutYProperty(), node.getLayoutY() - 25));
            }
        }
        timeline.getKeyFrames().addAll(
                new KeyFrame(
                        Duration.millis(ArrayDetailedDisplay.ANIMATION_LENGTH),
                        "",
                        event -> {},
                        values
                )
        );
        return timeline;
    }

    public Timeline removeFirst() {
        if (visuals.isEmpty()) return new Timeline();
        return new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        event -> {
                            getChildren().removeAll(visuals.getFirst());
                            visuals.removeFirst();
                        }
                ),
                new KeyFrame(
                        Duration.millis(ArrayDetailedDisplay.ANIMATION_LENGTH),
                        event -> {}
                )
        );
    }

    private void drawNewRow(List<Integer> comparisons, double y) {
        List<Node> row = new ArrayList<>();
        for (int i = 0; i < comparisons.size() - 1; i += 2) {

            Circle left = new Circle(10, Color.WHITE);
            Circle right = new Circle(10, Color.WHITE);
            // 2.5 = (width - (radius * 2)) / 2
            left.relocate(25 * comparisons.get(i) + 2.5, y);
            right.relocate(25 * comparisons.get(i + 1) + 2.5, y);
            Line line = new Line(left.getLayoutX(), left.getLayoutY(), right.getLayoutX(), right.getLayoutY());
            if (comparisons.get(i) >= list.size() || comparisons.get(i + 1) >= list.size()) {
                left.setStroke(Color.RED);
                right.setStroke(Color.RED);
                line.setStroke(Color.RED);
            } else {
                left.setStroke(Color.BLACK);
                // TODO: Don't use 25 blindly in so many places
                right.setStroke(Color.BLACK);
                line.setStroke(Color.BLACK);
            }
            row.add(left);
            row.add(right);
            row.add(line);
            getChildren().addAll(line, left, right);
        }
        visuals.add(row);
    }
}
