package com.example.javafxsortingalgorithms.betteralgorithms;

public class QuickSortSpace extends Algorithm {

    private final Pointer i;
    private final Pointer k;
    private final Ticker ticker;

    public QuickSortSpace(AlgorithmSpace space, Bounds bounds) {
        this(space, bounds, 0);
    }

    public QuickSortSpace(AlgorithmSpace space, Bounds bounds, int index) {
        super("Quick Sort", space, bounds);

        i = new Pointer("I", bounds.getHighestValue(), bounds);
        k = new Pointer("K", bounds.getHighestValue(), bounds);

        ticker = new Ticker(() -> {
            if (space.get(i.getValue()) > space.get(bounds.getLowestValue())) {
                swap(i.getValue(), k.getValue());
                k.decrement();
            }
            i.decrement();
        });

        i.onEnd(this::finishThis);

        space.addAlgorithmSpaceObject(i, k);
        space.addTicker(index, ticker);
    }

    private void finishThis() {
        swap(bounds.getLowestValue(), k.getValue());
        space.removeTicker(ticker);
        space.removeAlgorithmSpaceObject(this, i, k);

        boolean left = bounds.getLowestValue() < k.getValue() - 1;
        boolean right = k.getValue() + 1 < bounds.getHighestValue();

        if (right) {
            new QuickSortSpace(space, new Bounds(k.getValue() + 1, bounds.getHighestValue(), true, true), 0);
        }

        if (left) {
            new QuickSortSpace(space, new Bounds(bounds.getLowestValue(), k.getValue() - 1, true, true), 0);
        }

        if (!left && !right) {
            if (space.getTickers().isEmpty()) {
                space.finish();
            }
        }
    }
}
