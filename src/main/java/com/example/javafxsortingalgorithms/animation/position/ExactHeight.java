package com.example.javafxsortingalgorithms.animation.position;

import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;

public class ExactHeight implements YPosition {

    private final double y;

    public ExactHeight(double y) {
        this.y = y;
    }

    @Override
    public double getY(DisplaySettings settings) {
        return y;
    }
}
