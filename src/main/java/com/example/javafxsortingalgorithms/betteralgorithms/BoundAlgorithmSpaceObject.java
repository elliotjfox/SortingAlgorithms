package com.example.javafxsortingalgorithms.betteralgorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BoundAlgorithmSpaceObject extends AlgorithmSpaceObject implements BoundedObject {
    protected final Set<Bounds> bounds;

    public BoundAlgorithmSpaceObject() {
        bounds = new HashSet<>();
    }

    public BoundAlgorithmSpaceObject(Bounds... bounds) {
        this();
        addBounds(bounds);
    }

    public void addBounds(Bounds bound) {
        bounds.add(bound);
    }

    public void addBounds(Bounds... bounds) {
        for (Bounds bound : bounds) {
            addBounds(bound);
        }
    }

    public void addBounds(Set<Bounds> bounds) {
        for (Bounds bound : bounds) {
            addBounds(bound);
        }
    }

    @Override
    public void clearBounds() {
        bounds.clear();
    }

    public abstract boolean isWithinBounds();
}
