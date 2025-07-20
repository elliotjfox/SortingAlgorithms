package com.example.javafxsortingalgorithms.newanimation;

import com.example.javafxsortingalgorithms.AlgorithmController;
import com.example.javafxsortingalgorithms.algorithmupdates.DisplayUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.GenerateAnimationUpdate;
import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;

public abstract class NewAnimatedItem extends Group {

    public abstract void generateVisuals(DisplaySettings settings);

    public DisplayUpdate setIndex(int index) {
        return display -> setLayoutX(display.getSettings().elementWidth() * index);
    }

    public DisplayUpdate setHeight(double height) {
        return display -> setLayoutY(display.getSettings().maxValue() * display.getSettings().heightMultiplier() - height);
    }

    public GenerateAnimationUpdate moveToIndex(int index) {
        return new GenerateAnimationUpdate(
                settings -> new Timeline(new KeyFrame(
                        Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                        new KeyValue(layoutXProperty(), settings.elementWidth() * index)
                )),
                settings -> setLayoutX(settings.elementWidth() * index)
        );
    }

    public GenerateAnimationUpdate moveToHeight(double height) {
        return new GenerateAnimationUpdate(
                settings -> new Timeline(new KeyFrame(
                        Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                        new KeyValue(layoutYProperty(), settings.maxValue() * settings.heightMultiplier() - height)
                )),
                settings -> setLayoutY(settings.maxValue() * settings.heightMultiplier() - height)
        );
    }
}
