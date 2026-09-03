package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.animation.AnimatedReadArrow;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LinesDisplay extends SimpleArrayDisplay<Rectangle> {

    private static final double COLOUR_RANGE = 360;

    public LinesDisplay() {
        super();
    }

    @Override
    protected Timeline createReadTimeline(AnimatedReadArrow readArrow, int index, int value) {
        // TODO
        return null;
    }

    @Override
    protected Rectangle createElement() {
        return new Rectangle();
    }

    @Override
    protected void formatElement(int index, int value, Rectangle element) {

        element.setX(getXPos(index) / getDimensions() * currentSettings.height());
        element.setY(currentSettings.height() * (1 - getYPos(index) / getDimensions()));

        element.setHeight(currentSettings.height() / getDimensions());
        element.setWidth(currentSettings.height() / getDimensions());
        element.setFill(Color.hsb(getXPos(value) / getDimensions() * COLOUR_RANGE, 0.3 + 0.7 * getYPos(value) / getDimensions(), 1.0));
    }

    private int getDimensions() {
        return (int) Math.sqrt(currentSettings.size());
    }

    private double getXPos(int index) {
        return index % (int) Math.sqrt(currentSettings.size());
    }

    private double getYPos(int index) {
        int elementCount = currentSettings.size();
        int dimensions = (int) Math.sqrt(elementCount);
        return index / dimensions;
    }

    @Override
    protected Timeline moveElement(Rectangle element, int targetIndex) {
        // TODO
        return null;
    }
}
