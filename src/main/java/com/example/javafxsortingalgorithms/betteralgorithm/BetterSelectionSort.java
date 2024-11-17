package com.example.javafxsortingalgorithms.betteralgorithm;

public class BetterSelectionSort extends LoopThroughAlgorithm {

    @Override
    protected void initializeLoopStep() {
        loopStep = new OuterSelectionSortStep(this, 0, list.size());
    }

    @Override
    public void initializeAnimated() {

    }

    @Override
    public void initializeInstant() {

    }

    @Override
    public void stepAnimated() {

    }

    @Override
    public void doInstant() {

    }
}

class OuterSelectionSortStep extends LoopStep {

    private int minIndex;
    private InnerSelectionSortStep innerStep;
    private BetterSelectionSort selectionSort;

    public OuterSelectionSortStep(BetterSelectionSort selectionSort, int from, int to) {
        super(from, to);
        this.selectionSort = selectionSort;
    }

    @Override
    public void step(int i) {
        if (innerStep == null) {
            minIndex = i;
            innerStep = new InnerSelectionSortStep(selectionSort, this, i + 1, to);
        } else {
            innerStep.doStep();
            if (innerStep.isDone()) {
                selectionSort.swap(i, minIndex);
                increment();
                innerStep = null;
            }
        }
    }

    public int getMinIndex() {
        return minIndex;
    }

    public void setMinIndex(int minIndex) {
        this.minIndex = minIndex;
    }
}

class InnerSelectionSortStep extends LoopStep {

    private BetterSelectionSort selectionSort;
    private OuterSelectionSortStep outerStep;

    public InnerSelectionSortStep(BetterSelectionSort selectionSort, OuterSelectionSortStep outerStep, int from, int to) {
        super(from, to);
        this.selectionSort = selectionSort;
        this.outerStep = outerStep;
    }

    @Override
    public void step(int i) {
        if (selectionSort.list.get(i) < selectionSort.list.get(outerStep.getMinIndex())) {
            outerStep.setMinIndex(i);
        }
        increment();
    }
}