package com.example.javafxsortingalgorithms.animation.position;

import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;

public class ExactIndex implements XPosition {

    private final double x;

    public ExactIndex(double x) {
        this.x = x;
    }

    @Override
    public double getX(DisplaySettings settings) {
        return x;
    }
}
