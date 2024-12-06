package com.example.javafxsortingalgorithms.betteralgorithms;

public abstract class BoundAlgorithmSpaceObject extends AlgorithmSpaceObject {
    protected Bounds bounds;
    protected Runnable runnable;

    public BoundAlgorithmSpaceObject(Bounds bounds) {
        setBounds(bounds);
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public void clearBounds() {
        bounds = null;
    }

    public void onEnd(Runnable runnable) {
        this.runnable = runnable;
    }

    public Bounds getBounds() {
        return bounds;
    }
}
