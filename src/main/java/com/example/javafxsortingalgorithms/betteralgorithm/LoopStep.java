package com.example.javafxsortingalgorithms.betteralgorithm;

import java.util.function.Consumer;

public abstract class LoopStep {

    private final LoopStepCounter counter;

    // [from, to)
    public LoopStep(int from, int to) {
        counter = new LoopStepCounter(from, to);
    }

    public void doStep() {
        if (isDone()) return;
        step(counter);
    }

    public abstract void step(LoopStepCounter counter);

    public boolean isDone() {
        return counter.isDone();
    }
}
