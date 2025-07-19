package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.AlgorithmController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class BasicArrayDisplay extends SimpleArrayDisplay<Rectangle> {

    private static final double COLOUR_RANGE = 360;

    @Override
    protected Rectangle createElement() {
        return new Rectangle();
    }

    @Override
    protected void formatElement(int index, int value, Rectangle element) {
        double hue = COLOUR_RANGE * value / currentSettings.maxValue();
        element.setX(index * currentSettings.elementWidth());
        element.setY((currentSettings.maxValue() - value) * currentSettings.heightMultiplier());
        element.setHeight(value * currentSettings.heightMultiplier());
        element.setWidth(currentSettings.elementWidth());
        element.setFill(Color.hsb(hue, 1.0 ,1.0));
    }

    @Override
    protected Timeline moveElement(Rectangle element, int targetIndex) {
        return new Timeline(
                new KeyFrame(
                        Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                        new KeyValue(element.xProperty(), currentSettings.elementWidth() * targetIndex)
                )
        );
    }
}
