package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.AlgorithmController;
import com.example.javafxsortingalgorithms.newanimation.NewAnimatedReadArrow;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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

    @Override
    protected Timeline moveElement(Rectangle element, int targetIndex) {
        return new Timeline(
                new KeyFrame(
                        Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                        new KeyValue(element.xProperty(), currentSettings.elementWidth() * targetIndex)
                )
        );
    }

    @Override
    public void createReadAnimation(int index, int value) {
    }

    @Override
    protected Timeline createReadTimeline(NewAnimatedReadArrow readArrow, int index, int value) {
        return null;
    }
}
