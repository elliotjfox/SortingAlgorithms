package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.AlgorithmController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;

public class CircularDisplay extends SimpleArrayDisplay<Arc> {

    private static final double COLOUR_RANGE = 360;

    private double radius;

    @Override
    public void initializeSettings(DisplaySettings settings) {
        super.initializeSettings(settings);
        radius = Math.min(settings.size() * settings.elementWidth(), settings.maxValue() * settings.heightMultiplier()) / 2;
    }

    @Override
    protected Arc createElement() {
        return new Arc();
    }

    @Override
    protected void formatElement(int index, int value, Arc element) {
        double length = 360.0 / currentSettings.size();
        double hue = COLOUR_RANGE * value / currentSettings.maxValue();
        element.setCenterX(currentSettings.size() * currentSettings.elementWidth() / 2);
        element.setCenterY(currentSettings.maxValue() * currentSettings.heightMultiplier() / 2);
        element.setRadiusX(radius * value / currentSettings.maxValue());
        element.setRadiusY(radius * value / currentSettings.maxValue());
        element.setStartAngle(index * length);
        element.setLength(length);
        element.setType(ArcType.ROUND);
        element.setFill(Color.hsb(hue, 1.0 ,1.0));
    }

    @Override
    protected Timeline moveElement(Arc element, int targetIndex) {
        double length = 360.0 / currentSettings.size();
        return new Timeline(
                new KeyFrame(
                        Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                        new KeyValue(element.startAngleProperty(), targetIndex * length)
                )
        );
    }
}
