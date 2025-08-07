package com.example.javafxsortingalgorithms.animation.position;

import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;

public class ElementScaledIndex implements XPosition{

    private final double index;

    public ElementScaledIndex(double index) {
        this.index = index;
    }

    @Override
    public double getX(DisplaySettings settings) {
        return index * settings.elementWidth();
    }
}
