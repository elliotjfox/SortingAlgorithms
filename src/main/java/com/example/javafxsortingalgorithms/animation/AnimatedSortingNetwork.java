package com.example.javafxsortingalgorithms.animation;

import com.example.javafxsortingalgorithms.AlgorithmController;
import com.example.javafxsortingalgorithms.algorithmupdates.AnimationUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.GenerateAnimationUpdate;
import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AnimatedSortingNetwork extends AnimatedItem {

    private final List<Line> lines;
    private final List<List<Integer>> comparisons;
    private final List<List<Node>> visuals;
    private List<Integer> currentComparisons;

    public AnimatedSortingNetwork() {
        lines = new ArrayList<>();
        comparisons = new ArrayList<>();
        visuals = new ArrayList<>();

        currentComparisons = new ArrayList<>();
    }

    public GenerateAnimationUpdate startNewSection() {
        comparisons.add(currentComparisons);
        currentComparisons = new ArrayList<>();

        return new GenerateAnimationUpdate(
                settings -> {
                    Timeline timeline = new Timeline();
                    List<KeyValue> keyValues = new ArrayList<>();
                    for (List<Node> row : visuals) {
                        if (row == visuals.getFirst()) continue;
                        for (Node node : row) {
                            // Move each node up
                            keyValues.add(new KeyValue(node.layoutYProperty(), node.getLayoutY() - settings.elementWidth()));
                        }
                    }

                    timeline.getKeyFrames().addAll(
                            new KeyFrame(
                                    Duration.ZERO,
                                    _ -> {
                                        getChildren().removeAll(visuals.getFirst());
                                        visuals.removeFirst();
                                    }
                            ),
                            new KeyFrame(
                                    Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                                    "",
                                    _ -> {},
                                    keyValues
                            )
                    );

                    return timeline;
                },
                settings -> {}
        );
    }

    public void addComparison(int i1, int i2) {
        currentComparisons.add(i1);
        currentComparisons.add(i2);
    }

    @Override
    public void generateVisuals(DisplaySettings settings) {
        getChildren().clear();
        lines.clear();
        visuals.clear();

        for (int i = 0; i < settings.size(); i++) {
            Line line = new Line();
            getChildren().add(line);
            lines.add(line);
        }

        double circleDiameter = settings.elementWidth() * 4 / 5;
        for (int i = 0; i < comparisons.size(); i++) {
            List<Integer> currentLine = comparisons.get(i);
            List<Node> currentRow = new ArrayList<>();
            for (int j = 0; j < currentLine.size() - 1; j += 2) {
                Circle left = new Circle(circleDiameter / 2, Color.WHITE);
                left.relocate(settings.elementWidth() * currentLine.get(j) + settings.elementWidth() / 10, settings.height() + settings.elementWidth() * i);
                left.setStroke(Color.BLACK);
                Circle right = new Circle(circleDiameter / 2, Color.WHITE);
                right.relocate(settings.elementWidth() * currentLine.get(j + 1) + settings.elementWidth() / 10, settings.height() + settings.elementWidth() * i);
                right.setStroke(Color.BLACK);
                Line line = new Line(
                        left.getLayoutX(),
                        left.getLayoutY(),
                        right.getLayoutX(),
                        right.getLayoutY()
                );
                currentRow.add(left);
                currentRow.add(right);
                currentRow.add(line);
                getChildren().addAll(line, left, right);
            }
            visuals.add(currentRow);
        }

        for (int i = 0; i < lines.size(); i++) {
            lines.get(i).setStartX((i + 0.5) * settings.elementWidth());
            lines.get(i).setStartY(settings.height());
            lines.get(i).setEndX((i + 0.5) * settings.elementWidth());
            lines.get(i).setEndY(settings.height() + visuals.size() * settings.elementWidth());
        }
    }
}
