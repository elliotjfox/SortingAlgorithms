package com.example.javafxsortingalgorithms.animation.position;

import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;

public class ElementScaledHeight implements YPosition {

    private final double height;

    public ElementScaledHeight(double height) {
        this.height = height;
    }

    @Override
    public double getY(DisplaySettings settings) {
        return settings.maxValue() * settings.heightMultiplier() - height * settings.elementWidth();
    }
}
