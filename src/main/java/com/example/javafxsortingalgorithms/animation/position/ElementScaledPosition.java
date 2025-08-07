package com.example.javafxsortingalgorithms.animation.position;

public class ElementScaledPosition extends AnimationPosition {
    public ElementScaledPosition(double xPosition, double yPosition) {
        super(new ElementScaledIndex(xPosition), new ElementScaledHeight(yPosition));
    }
}
