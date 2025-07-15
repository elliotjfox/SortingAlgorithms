package com.example.javafxsortingalgorithms.animation;

import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import com.example.javafxsortingalgorithms.arraydisplay.DisplayMode;

public class Pointer {

    private final SortingAlgorithm algorithm;
    private final DisplayMode mode;

    private int value;

    public Pointer(SortingAlgorithm algorithm, DisplayMode mode) {
        this.algorithm = algorithm;
        this.mode = mode;
    }

    public void setValue(int newValue) {
        value = newValue;
        if (mode == DisplayMode.ANIMATED) {

        }
    }

    public void increment(int amount) {
        setValue(value + amount);
    }

    public void increment() {
        increment(1);
    }

    public int getValue() {
        return value;
    }
}
