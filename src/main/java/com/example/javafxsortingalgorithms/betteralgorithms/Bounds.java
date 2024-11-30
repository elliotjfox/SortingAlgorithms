package com.example.javafxsortingalgorithms.betteralgorithms;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class Bounds extends AlgorithmSpaceObject {

    private final int lower;
    private final int upper;

    private final boolean lowerInclusive;
    private final boolean upperInclusive;

    private final Label label;

    public Bounds(int lower, int upper, boolean lowerInclusive, boolean upperInclusive) {
        super();
        this.lower = lower;
        this.upper = upper;
        this.lowerInclusive = lowerInclusive;
        this.upperInclusive = upperInclusive;

        label = new Label(toString());
    }

    public Bounds(int lower, int upper) {
        this(lower, upper, true, false);
    }

    public boolean isWithinBounds(int i) {
        if (i == lower && lowerInclusive) return true;
        else if (i == upper && upperInclusive) return true;
        else return i > lower && i < upper;
    }

    @Override
    public String toString() {
        return "Bounds" + (lowerInclusive ? '[' : '(') + lower + ", " + upper + (upperInclusive ? ']' : ')');
    }

    public static Bounds intersect(Bounds bounds1, Bounds bounds2) {
        int lower;
        int upper;
        boolean lowerInclusive;
        boolean upperInclusive;
        lower = Math.max(bounds1.lower, bounds2.lower);
        upper = Math.min(bounds1.upper, bounds2.upper);
        if (bounds1.lower == bounds2.lower) lowerInclusive = bounds1.lowerInclusive && bounds2.lowerInclusive;
        else lowerInclusive = bounds1.lower > bounds2.lower ? bounds1.lowerInclusive : bounds2.lowerInclusive;

        if (bounds1.upper == bounds2.upper) upperInclusive = bounds1.upperInclusive && bounds2.upperInclusive;
        else upperInclusive = bounds1.upper < bounds2.upper ? bounds1.upperInclusive : bounds2.upperInclusive;

        if (lower > upper) return new Bounds(0, 0, false, false);

        return new Bounds(lower, upper, lowerInclusive, upperInclusive);
    }

    @Override
    public Node getVisual() {
        return label;
    }
}
