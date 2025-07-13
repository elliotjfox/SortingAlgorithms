package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class HeatMapDisplay extends SimpleArrayDisplay<Rectangle> {

    @Override
    protected Rectangle createElement() {
        return new Rectangle();
    }

    @Override
    protected void formatElement(int index, int value, Rectangle element) {
        // Only works for uniform lists
        double distance = Math.abs(value - index);
        double hue = -120.0 * Math.sqrt(distance / currentSettings.size()) + 120;
        element.setX(index * currentSettings.elementWidth());
        element.setY((currentSettings.maxValue() - value) * currentSettings.heightMultiplier());
        element.setHeight(value * currentSettings.heightMultiplier());
        element.setWidth(currentSettings.elementWidth());
        element.setFill(Color.hsb(hue, 1.0 ,1.0));
    }
}
