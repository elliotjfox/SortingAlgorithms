package com.example.javafxsortingalgorithms.betteralgorithms;

public class BubbleSortSpace extends Algorithm {

    private final Pointer count;
    private final Pointer i;
    private final Iterator iterator;

    public BubbleSortSpace(AlgorithmSpace space, Bounds bounds) {
        super("Bubble Sort", space, bounds);

        count = new Pointer("Limit", bounds.getUpper() - 1, bounds);
        i = new Pointer("Position", 0, new Bounds(0, count.getValue()));

        i.onEnd(() -> {
            i.setValue(0);
            count.decrement();
            i.setBounds(new Bounds(0, count.getValue()));
        });

        count.onEnd(this::finish);

        iterator = new Iterator(() -> {
            if (space.get(i.getValue()) > space.get(i.getValue() + 1)) {
                swap(i.getValue(), i.getValue() + 1);
            }
            i.increment();
        });

        space.addAlgorithmSpaceObject(count, i);
        space.addIterator(iterator);
    }
}
