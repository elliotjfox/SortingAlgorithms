package com.example.javafxsortingalgorithms.newanimation;

import com.example.javafxsortingalgorithms.AlgorithmController;
import com.example.javafxsortingalgorithms.algorithmupdates.AnimationUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.GenerateAnimationUpdate;
import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class NewAnimatedReadArrow extends NewAnimatedItem {

    private static final Paint READ_ARROW_COLOUR = Color.BLACK;

    private final Polygon polygon;

    public NewAnimatedReadArrow() {
        polygon = new Polygon();
        getChildren().add(polygon);
    }

    @Override
    public void generateVisuals(DisplaySettings settings) {
        polygon.getPoints().clear();

        double length = settings.elementWidth() * 0.75;

        polygon.getPoints().addAll(
                0.0, 0.0,
                -length, length / 2,
                -length + length / 4, 0.0,
                -length, -length / 2
        );

        polygon.setFill(READ_ARROW_COLOUR);
    }

    public GenerateAnimationUpdate moveToTop(int value) {
        return new GenerateAnimationUpdate(
                settings -> new Timeline(
                        new KeyFrame(
                                Duration.millis((double) (AlgorithmController.ANIMATION_LENGTH * value) / settings.maxValue()),
                                new KeyValue(layoutYProperty(), (settings.maxValue() - value) * settings.heightMultiplier())
                        )
                ),
                settings -> setLayoutY((settings.maxValue() - value) * settings.heightMultiplier())
        );
    }

    @Override
    public AnimationUpdate changeFill(Paint fill) {
        return new AnimationUpdate(
                new Timeline(
                        new KeyFrame(
                                Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                                new KeyValue(polygon.fillProperty(), fill)
                        )
                ),
                () -> polygon.setFill(fill)
        );
    }
}
