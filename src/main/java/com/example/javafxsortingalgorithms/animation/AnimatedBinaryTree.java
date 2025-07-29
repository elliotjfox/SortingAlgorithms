package com.example.javafxsortingalgorithms.animation;

import com.example.javafxsortingalgorithms.AlgorithmController;
import com.example.javafxsortingalgorithms.algorithmupdates.AnimationUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.GenerateAnimationUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.ListUpdate;
import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AnimatedBinaryTree extends AnimatedItem {

    private static final double VERTICAL_PADDING_FRACTION = 0.25;
    private static final double HORIZONTAL_PADDING_FRACTION = 0.25;

    private final List<AnimatedBinaryTreeItem> initialNodes;
    private final List<AnimatedBinaryTreeItem> nodes;

    public AnimatedBinaryTree(List<Integer> list) {
        initialNodes = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            AnimatedBinaryTreeItem item = new AnimatedBinaryTreeItem(list.get(i));
            initialNodes.add(item);
        }
        nodes = new ArrayList<>(initialNodes);
    }

    @Override
    public void generateVisuals(DisplaySettings settings) {
        getChildren().clear();
        System.out.println("Generate Vis");

        for (int i = 0; i < initialNodes.size(); i++) {
            getChildren().add(initialNodes.get(i));
            initialNodes.get(i).generateVisuals(settings);

            initialNodes.get(i).setLayoutX(getItemLayoutX(i, initialNodes.size(), settings.elementWidth()));
            initialNodes.get(i).setLayoutY(getItemLayoutY(i, initialNodes.size(), settings.elementWidth()));
        }
    }

    public GenerateAnimationUpdate swapElement(int first, int second) {
        AnimatedBinaryTreeItem toFirst = nodes.get(second);
        AnimatedBinaryTreeItem toSecond = nodes.get(first);
        int nodesSize = nodes.size();
        ListUpdate.swap(nodes, first, second);
        return new GenerateAnimationUpdate(
                settings -> new Timeline(new KeyFrame(
                        Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                        new KeyValue(toFirst.layoutXProperty(), getItemLayoutX(first, nodesSize, settings.elementWidth())),
                        new KeyValue(toFirst.layoutYProperty(), getItemLayoutY(first, nodesSize, settings.elementWidth())),
                        new KeyValue(toSecond.layoutXProperty(), getItemLayoutX(second, nodesSize, settings.elementWidth())),
                        new KeyValue(toSecond.layoutYProperty(), getItemLayoutY(second, nodesSize, settings.elementWidth()))
                )),
                settings -> {}
        );
    }

    public GenerateAnimationUpdate removeLast() {
        AnimatedBinaryTreeItem last = nodes.removeLast();
        List<AnimatedBinaryTreeItem> tmp = new ArrayList<>(nodes);
        return new GenerateAnimationUpdate(
                settings -> {
                    List<KeyValue> keyValues = new ArrayList<>();
                    keyValues.add(new KeyValue(last.opacityProperty(), 0));
                    for (int i = 0; i < tmp.size(); i++) {
                        keyValues.add(new KeyValue(tmp.get(i).layoutXProperty(), getItemLayoutX(i, tmp.size(), settings.elementWidth())));
                        keyValues.add(new KeyValue(tmp.get(i).layoutYProperty(), getItemLayoutY(i, tmp.size(), settings.elementWidth())));
                    }
                    return new Timeline(new KeyFrame(
                            Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                            "",
                            _ -> {},
                            keyValues
                    ));
                },
                settings -> {}
        );
    }

    public GenerateAnimationUpdate readIndex(int index) {
        AnimatedReadArrow readArrow = new AnimatedReadArrow();
        int nodesSize = nodes.size();
        return new GenerateAnimationUpdate(
                settings -> {
                    getChildren().add(readArrow);
                    readArrow.generateVisuals(settings);
                    readArrow.setLayoutX(getItemLayoutX(index, nodesSize, settings.elementWidth()));
                    readArrow.setLayoutY(getItemLayoutY(index, nodesSize, settings.elementWidth()) + settings.elementWidth());
                    return new Timeline(new KeyFrame(
                            Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                            _ -> getChildren().remove(readArrow),
                            new KeyValue(readArrow.layoutYProperty(), getItemLayoutY(index, nodesSize, settings.elementWidth()))
                    ));
                },
                settings -> {}
        );
    }

    @Override
    public AnimationUpdate changeFill(Paint fill) {
        return null;
    }

    private double getItemLayoutX(int index, int total, double elementSize) {
        return elementSize * (1 + HORIZONTAL_PADDING_FRACTION) * getWidth(index, total, elementSize) * (index + 1.5 - Math.pow(2, roundDown(index)));
    }

    private double getItemLayoutY(int index, int total, double elementSize) {
        return elementSize * (1 + VERTICAL_PADDING_FRACTION) * roundDown(index);
    }

    private double getWidth(int index, int total, double elementSize) {
        return (int) Math.pow(2, roundDown(total - 1) - roundDown(index));
    }

    private double roundDown(int i) {
        return (int) (Math.log(i + 1) / Math.log(2));
    }

    private static class AnimatedBinaryTreeItem extends AnimatedItem {

        private final int value;
        private final StackPane stackPane;
        private final Rectangle rectangle;
        private final Label label;

        public AnimatedBinaryTreeItem(int value) {
            this.value = value;

            stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            rectangle = new Rectangle();
            rectangle.setStroke(Color.BLACK);
            stackPane.getChildren().add(rectangle);
            label = new Label(Integer.toString(value));
            label.setTextAlignment(TextAlignment.CENTER);
            stackPane.getChildren().add(label);

            getChildren().add(stackPane);
        }

        @Override
        public void generateVisuals(DisplaySettings settings) {
            getChildren().clear();

            getChildren().add(stackPane);
            rectangle.setWidth(settings.elementWidth());
            rectangle.setHeight(settings.elementWidth());
            // TODO: Fix magic #s
            rectangle.setFill(Color.hsb(360.0 * value / settings.maxValue(), 1.0, 1.0));
        }

        @Override
        public AnimationUpdate changeFill(Paint fill) {
            return new AnimationUpdate(new Timeline(), () -> {});
        }
    }
}
