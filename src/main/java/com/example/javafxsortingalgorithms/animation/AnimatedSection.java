package com.example.javafxsortingalgorithms.animation;

import com.example.javafxsortingalgorithms.AlgorithmController;
import com.example.javafxsortingalgorithms.algorithmupdates.AnimationUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.DisplayUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.GenerateAnimationUpdate;
import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class AnimatedSection extends AnimatedItem {

    private double exactWidth;
    private double indexWidth;

    private final Rectangle middle;
    private final Rectangle left;
    private final Rectangle right;

    public AnimatedSection(double exactWidth) {
        this.exactWidth = exactWidth;

        middle = new Rectangle();
        left = new Rectangle();
        right = new Rectangle();
    }

    public AnimatedSection(int indexWidth) {
        this.indexWidth = indexWidth;
        this.exactWidth = -1;

        middle = new Rectangle();
        left = new Rectangle();
        right = new Rectangle();
    }

    @Override
    public void generateVisuals(DisplaySettings settings) {
        getChildren().clear();

        double thickness = settings.elementWidth() / 5;

        if (exactWidth == -1) {
            exactWidth = indexWidth * settings.elementWidth();
        }

        middle.setHeight(thickness);
        middle.setWidth(exactWidth);

        left.setWidth(thickness);
        left.setHeight(2 * thickness);
        left.setLayoutX(0);
        left.setLayoutY(-thickness);

        right.setWidth(thickness);
        right.setHeight(2 * thickness);
        right.setLayoutX(exactWidth - thickness);
        right.setLayoutY(-thickness);

        getChildren().addAll(middle, left, right);
    }

    public DisplayUpdate setWidth(double newExactWidth) {
        return display -> {
            this.exactWidth = newExactWidth;
            left.setWidth(newExactWidth);
            right.setLayoutX(newExactWidth - display.getSettings().elementWidth() / 5);
        };
    }

    public DisplayUpdate setWidth(int newIndexWidth) {
        return display -> {
            this.exactWidth = newIndexWidth * display.getSettings().elementWidth();
            left.setWidth(exactWidth);
            right.setLayoutX(exactWidth - display.getSettings().elementWidth() / 5);
        };
    }

    public GenerateAnimationUpdate changeWidth(double newExactWidth) {
        return new GenerateAnimationUpdate(
                settings -> new Timeline(new KeyFrame(
                        Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                        _ -> exactWidth = newExactWidth,
                        new KeyValue(middle.widthProperty(), newExactWidth),
                        new KeyValue(right.layoutXProperty(), newExactWidth - settings.elementWidth() / 5)
                )),
                settings -> {}
        );
    }

    public GenerateAnimationUpdate changeWidth(int newIndexWidth) {
        return new GenerateAnimationUpdate(
                settings -> new Timeline(new KeyFrame(
                        Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                        _ -> exactWidth = newIndexWidth * settings.elementWidth(),
                        new KeyValue(middle.widthProperty(), newIndexWidth * settings.elementWidth()),
                        new KeyValue(right.layoutXProperty(), newIndexWidth * settings.elementWidth() - settings.elementWidth() / 5)
                )),
                settings -> {}
        );
    }

    @Override
    public AnimationUpdate changeFill(Paint fill) {
        return new AnimationUpdate(
                new Timeline(
                        new KeyFrame(
                                Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                                new KeyValue(middle.fillProperty(), fill),
                                new KeyValue(left.fillProperty(), fill),
                                new KeyValue(right.fillProperty(), fill)
                        )
                ),
                () -> {
                    middle.setFill(fill);
                    left.setFill(fill);
                    right.setFill(fill);
                }
        );
    }
}
