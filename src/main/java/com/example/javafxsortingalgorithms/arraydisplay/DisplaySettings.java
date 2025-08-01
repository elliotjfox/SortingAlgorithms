package com.example.javafxsortingalgorithms.arraydisplay;

public record DisplaySettings(int maxValue, int size, double heightMultiplier, double elementWidth) {
    public double height() {
        return maxValue * heightMultiplier;
    }
}
