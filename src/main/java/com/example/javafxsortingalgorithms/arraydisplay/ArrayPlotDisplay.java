package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.AlgorithmController;
import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;

public class ArrayPlotDisplay extends SimpleArrayDisplay<Rectangle> {

    @Override
    protected Rectangle createElement() {
        return new Rectangle();
    }

    @Override
    protected void formatElement(int index, int value, Rectangle element) {
        element.setFill(Color.hsb(0, 0, 0));
        element.setX(index * currentSettings.elementWidth());
        element.setY((currentSettings.maxValue() - value) * currentSettings.heightMultiplier());
        element.setWidth(currentSettings.elementWidth());
        element.setHeight(currentSettings.elementWidth());
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
