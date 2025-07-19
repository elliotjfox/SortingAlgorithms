package com.example.javafxsortingalgorithms.animation;

import com.example.javafxsortingalgorithms.AlgorithmController;
import com.example.javafxsortingalgorithms.algorithmupdates.GenerateAnimationUpdate;
import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class NewAnimatedItem extends Group {

    private DisplaySettings settings;

    public NewAnimatedItem() {}

    public void applySettings(DisplaySettings settings) {
        this.settings = settings;
        getChildren().clear();

        double length = settings.elementWidth();
        double sineLength = Math.sin(Math.toRadians(60)) * length;
        getChildren().add(new Polygon(
                length / 2, 0,
                length, sineLength,
                0, sineLength
        ));
    }

    public GenerateAnimationUpdate moveToIndex(int index) {
        return new GenerateAnimationUpdate(
                settings -> {
                    return new Timeline(
                            new KeyFrame(
                                    Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                                    new KeyValue(layoutXProperty(), settings.elementWidth() * index)
                            )
                    );
                },
                settings -> setLayoutX(settings.elementWidth() * index)
        );
    }
}
