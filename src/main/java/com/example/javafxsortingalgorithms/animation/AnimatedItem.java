package com.example.javafxsortingalgorithms.animation;

import com.example.javafxsortingalgorithms.AlgorithmController;
import com.example.javafxsortingalgorithms.algorithmupdates.AnimationUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.DisplayUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.GenerateAnimationUpdate;
import com.example.javafxsortingalgorithms.animation.position.AnimationPosition;
import com.example.javafxsortingalgorithms.animation.position.XPosition;
import com.example.javafxsortingalgorithms.animation.position.YPosition;
import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

public abstract class AnimatedItem extends Group {

    public abstract void generateVisuals(DisplaySettings settings);

    public DisplayUpdate setPosition(AnimationPosition position) {
        return display -> {
            setLayoutX(position.getX(display.getSettings()));
            setLayoutY(position.getY(display.getSettings()));
        };
    }

    public GenerateAnimationUpdate changePosition(AnimationPosition position) {
        return new GenerateAnimationUpdate(
                settings -> new Timeline(new KeyFrame(
                        Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                        new KeyValue(layoutYProperty(), position.getX(settings)),
                        new KeyValue(layoutYProperty(), position.getY(settings))
                )),
                _ -> {}
        );
    }

    public DisplayUpdate setXPosition(XPosition xPosition) {
        return display -> setLayoutX(xPosition.getX(display.getSettings()));
    }

    public DisplayUpdate setYPosition(YPosition yPosition) {
        return display -> setLayoutY(yPosition.getY(display.getSettings()));
    }

    public GenerateAnimationUpdate changeXPosition(XPosition xPosition) {
        return new GenerateAnimationUpdate(
                settings -> new Timeline(new KeyFrame(
                        Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                        new KeyValue(layoutXProperty(), xPosition.getX(settings))
                )),
                _ -> {}
        );
    }

    public GenerateAnimationUpdate changeYPosition(YPosition yPosition) {
        return new GenerateAnimationUpdate(
                settings -> new Timeline(new KeyFrame(
                        Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                        new KeyValue(layoutYProperty(), yPosition.getY(settings))
                )),
                _ -> {}
        );
    }
}
