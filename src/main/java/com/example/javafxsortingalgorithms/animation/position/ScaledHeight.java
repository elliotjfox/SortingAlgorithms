package com.example.javafxsortingalgorithms.animation.position;

import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;

public class ScaledHeight implements YPosition {

    private final double height;

    public ScaledHeight(double height) {
        this.height = height;
    }

    @Override
    public double getY(DisplaySettings settings) {
        return height * settings.heightMultiplier();
    }
}
