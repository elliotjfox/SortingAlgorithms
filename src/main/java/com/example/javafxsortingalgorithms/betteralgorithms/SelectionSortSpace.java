package com.example.javafxsortingalgorithms.betteralgorithms;

public class SelectionSortSpace extends Algorithm {

    private final Pointer outerPointer;
    private final Pointer innerPointer;
    private final Pointer minIndex;
    private final Iterator iterator;

    public SelectionSortSpace(AlgorithmSpace space, Bounds bounds) {
        super("Selection Sort", space, bounds);

        // Don't need outer to go all the way there
        outerPointer = new Pointer("Count", 0, bounds.shrink(0, 1));
        innerPointer = new Pointer("Inner", 0, bounds);
        minIndex = new Pointer("Min value", 0, bounds);
        iterator = new Iterator(() -> {
            if (space.get(innerPointer.getValue()) < space.get(minIndex.getValue())) {
                minIndex.setValue(innerPointer.getValue());
            }
            innerPointer.increment();
        });

        System.out.println(outerPointer.getBounds());

        outerPointer.onEnd(this::finish);

        innerPointer.onEnd(this::reset);

        space.addAlgorithmSpaceObject(outerPointer, innerPointer, minIndex);
        space.addIterator(iterator);
    }

    private void reset() {
        swap(outerPointer.getValue(), minIndex.getValue());
        outerPointer.increment();
        minIndex.setValue(outerPointer.getValue());
        innerPointer.setValue(outerPointer.getValue() + 1);
    }
}
