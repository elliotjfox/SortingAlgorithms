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

public class AnimatedBucket extends AnimatedItem implements ColourItem {

    private static final double THICKNESS_FRACTION = 1 / 5.0;

    private XPosition width;

    private final Rectangle middle;
    private final Rectangle left;
    private final Rectangle right;

    public AnimatedBucket(XPosition width) {
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
        middle.setLayoutX(0);
        middle.setLayoutY(settings.elementWidth() - thickness);

        left.setWidth(thickness);
        left.setHeight(settings.elementWidth());
        left.setLayoutX(0);
        left.setLayoutY(0);

        right.setWidth(thickness);
        right.setHeight(settings.elementWidth());
        right.setLayoutX(width.getX(settings) - thickness);
        right.setLayoutY(0);

        getChildren().addAll(middle, left, right);
    }

//    @Override
//    public Timeline createCreationTimeline(DisplaySettings settings) {
//        getChildren().clear();
//
//        double thickness = settings.elementWidth() * THICKNESS_FRACTION;
//
//        middle.setHeight(thickness);
//        middle.setWidth(0);
//        middle.setLayoutX(width.getX(settings) / 2);
//        middle.setLayoutY(0);
//
//        left.setWidth(0);
//        left.setHeight(0);
//        left.setLayoutX(width.getX(settings) / 2);
//        left.setLayoutY(0);
//
//        right.setWidth(0);
//        right.setHeight(0);
//        right.setLayoutX(width.getX(settings) / 2);
//        right.setLayoutY(0);
//
//        getChildren().addAll(middle, left, right);
//
//        return new Timeline(
//                new KeyFrame(
//                        Duration.millis(AlgorithmController.ANIMATION_LENGTH / 2),
//                        new KeyValue(middle.widthProperty(), width.getX(settings)),
//                        new KeyValue(middle.layoutXProperty(), 0),
//                        new KeyValue(middle.layoutYProperty(), 0),
//                        new KeyValue(left.widthProperty(),thickness),
//                        new KeyValue(left.layoutXProperty(), 0),
//                        new KeyValue(left.heightProperty(), 0),
//                        new KeyValue(right.widthProperty(),thickness),
//                        new KeyValue(right.layoutXProperty(), width.getX(settings) - thickness),
//                        new KeyValue(right.heightProperty(), 0)
//                ),
//                new KeyFrame(
//                        Duration.millis(AlgorithmController.ANIMATION_LENGTH),
//                        new KeyValue(middle.layoutYProperty(), settings.elementWidth() - thickness),
//                        new KeyValue(left.heightProperty(), settings.elementWidth()),
//                        new KeyValue(right.heightProperty(), settings.elementWidth())
//                )
//        );
//    }

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
                        new KeyValue(middle.widthProperty(), width.getX(settings)),
                        new KeyValue(right.layoutXProperty(), width.getX(settings) - settings.elementWidth() * THICKNESS_FRACTION)
                )),
                _ -> {}
        );
    }

    @Override
    public AnimationUpdate changeFill(Paint fill) {
        return new AnimationUpdate(
                new Timeline(new KeyFrame(
                        Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                        new KeyValue(middle.fillProperty(), fill),
                        new KeyValue(left.fillProperty(), fill),
                        new KeyValue(right.fillProperty(), fill)
                )),
                () -> {
                    middle.setFill(fill);
                    left.setFill(fill);
                    right.setFill(fill);
                }
        );
    }

    @Override
    public DisplayUpdate setFill(Paint fill) {
        return _ -> {
            middle.setFill(fill);
            left.setFill(fill);
            right.setFill(fill);
        };
    }
}
