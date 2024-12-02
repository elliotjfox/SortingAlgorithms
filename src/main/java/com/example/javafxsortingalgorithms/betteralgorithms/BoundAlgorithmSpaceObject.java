package com.example.javafxsortingalgorithms.betteralgorithms;

public abstract class BoundAlgorithmSpaceObject extends AlgorithmSpaceObject {
    protected Bounds bounds;

    public BoundAlgorithmSpaceObject(Bounds bounds) {
        setBounds(bounds);
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public void clearBounds() {
        bounds = null;
    }

    public Bounds getBounds() {
        return bounds;
    }
}
