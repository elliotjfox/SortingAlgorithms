package com.example.javafxsortingalgorithms.animation;

import com.example.javafxsortingalgorithms.AlgorithmController;
import com.example.javafxsortingalgorithms.algorithmupdates.AnimationUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.DisplayUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.GenerateAnimationUpdate;
import com.example.javafxsortingalgorithms.animation.position.XPosition;
import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class AnimatedSection extends AnimatedItem implements ColourableAnimatedItem {
    
    private static final double THICKNESS_FRACTION = 1 / 5.0;

    private XPosition width;

    private final Rectangle middle;
    private final Rectangle left;
    private final Rectangle right;

    public AnimatedSection(XPosition width) {
        this.width = width;

        middle = new Rectangle();
        left = new Rectangle();
        right = new Rectangle();
    }

    @Override
    public void generateVisuals(DisplaySettings settings) {
        getChildren().clear();

        double thickness = settings.elementWidth() * THICKNESS_FRACTION;

        middle.setHeight(thickness);
        middle.setWidth(width.getX(settings));

        left.setWidth(thickness);
        left.setHeight(2 * thickness);
        left.setLayoutX(0);
        left.setLayoutY(-thickness);

        right.setWidth(thickness);
        right.setHeight(2 * thickness);
        right.setLayoutX(width.getX(settings) - thickness);
        right.setLayoutY(-thickness);

        getChildren().addAll(middle, left, right);
    }

    public DisplayUpdate setWidth(XPosition width) {
        return display -> {
            this.width = width;
            middle.setWidth(width.getX(display.getSettings()));
            right.setLayoutX(width.getX(display.getSettings()) - display.getSettings().elementWidth() * THICKNESS_FRACTION);
        };
    }

    public GenerateAnimationUpdate changeWidth(XPosition width) {
        return new GenerateAnimationUpdate(
                settings -> new Timeline(new KeyFrame(
                        Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                        _ -> this.width = width,
                        new KeyValue(middle.widthProperty(), width.getX(settings)),
                        new KeyValue(right.layoutXProperty(), width.getX(settings) - settings.elementWidth() * THICKNESS_FRACTION)
                )),
                _ -> {}
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
