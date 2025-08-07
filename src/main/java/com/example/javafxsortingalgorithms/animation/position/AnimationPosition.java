package com.example.javafxsortingalgorithms.animation.position;

import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;

public class AnimationPosition implements XPosition, YPosition {

    private final XPosition xPosition;
    private final YPosition yPosition;

    public AnimationPosition(XPosition xPosition, YPosition yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public double getX(DisplaySettings settings) {
        return xPosition.getX(settings);
    }

    public double getY(DisplaySettings settings) {
        return yPosition.getY(settings);
    }
}
