package com.example.javafxsortingalgorithms.newanimation;

import com.example.javafxsortingalgorithms.AlgorithmController;
import com.example.javafxsortingalgorithms.algorithmupdates.AnimationUpdate;
import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class NewAnimatedArrow extends NewAnimatedItem {

    private static final Paint DEFAULT_PAINT = Color.BLACK;
    private static final boolean DEFAULT_DIRECTION = true;

    private final Paint fill;
    private final boolean facingUp;

    private final Polygon arrow;

    public NewAnimatedArrow(Paint fill, boolean facingUp) {
        this.fill = fill;
        this.facingUp = facingUp;

        arrow = new Polygon();
        getChildren().add(arrow);
    }

    public NewAnimatedArrow(Paint fill) {
        this(fill, DEFAULT_DIRECTION);
    }

    public NewAnimatedArrow(boolean facingUp) {
        this(DEFAULT_PAINT, facingUp);
    }

    public NewAnimatedArrow() {
        this(DEFAULT_PAINT, DEFAULT_DIRECTION);
    }

    @Override
    public void generateVisuals(DisplaySettings settings) {
        double length = settings.elementWidth();
        double sineLength = Math.sin(Math.toRadians(60)) * length;

        arrow.getPoints().clear();
        if (facingUp) {
            arrow.getPoints().addAll(
                    length / 2, 0.0,
                    length, sineLength,
                    0.0, sineLength
            );
        } else {
            arrow.getPoints().addAll(
                    0.0, 0.0,
                    length, 0.0,
                    length / 2, sineLength
            );
        }
        arrow.setFill(fill);
    }

    @Override
    public AnimationUpdate changeFill(Paint fill) {
        return new AnimationUpdate(
                new Timeline(
                        new KeyFrame(
                                Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                                new KeyValue(arrow.fillProperty(), fill)
                        )
                ),
                () -> arrow.setFill(fill)
        );
    }
}
