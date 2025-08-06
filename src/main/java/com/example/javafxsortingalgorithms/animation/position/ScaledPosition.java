package com.example.javafxsortingalgorithms.animation.position;

public class ScaledPosition extends AnimationPosition {
    public ScaledPosition(double xPosition, double yPosition) {
        super(new ScaledIndex(xPosition), new ScaledHeight(yPosition));
    }
}
