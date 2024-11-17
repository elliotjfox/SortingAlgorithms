package com.example.javafxsortingalgorithms.betteralgorithm;

import java.util.function.Consumer;

public class BasicLoopStep extends LoopStep {

    private final Consumer<LoopStepCounter> step;

    public BasicLoopStep(int from, int to, Consumer<LoopStepCounter> step) {
        super(from, to);
        this.step = step;
    }

    @Override
    public void step(LoopStepCounter counter) {
        step.accept(counter);
    }
}
