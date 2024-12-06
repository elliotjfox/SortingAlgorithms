package com.example.javafxsortingalgorithms.betteralgorithms;

import java.util.Objects;

public class BubbleSortSpace extends Algorithm {

    private final Pointer count;
    private final Pointer i;
    private final Ticker ticker;

    public BubbleSortSpace(AlgorithmSpace space, Bounds bounds) {
        super("Bubble Sort", space, bounds);

        count = new Pointer("Limit", bounds.getUpper() - 1, bounds);
        i = new Pointer("Position", 0, new Bounds(0, count.getValue()));

        i.onEnd(() -> {
            i.setValue(0);
            count.decrement();
            i.setBounds(new Bounds(0, count.getValue()));
        });

        count.onEnd(space::finish);

        ticker = new Ticker(() -> {
            if (space.compare(i.getValue(), i.getValue() + 1) instanceof Result.GreaterThan) {
                swap(i.getValue(), i.getValue() + 1);
            }
            i.increment();
        });

        space.addAlgorithmSpaceObject(count, i);
        space.addTicker(ticker);
    }
}
