package com.example.javafxsortingalgorithms.betteralgorithm;

public abstract class LoopThroughAlgorithm extends BetterAlgorithm {

    protected LoopStep loopStep;
    private int currentStep;

    @Override
    public void initializeNormal() {
        currentStep = 0;
        initializeLoopStep();
    }

    protected abstract void initializeLoopStep();

    @Override
    public void stepNormal() {
        loopStep.doStep();
        if (loopStep.isDone()) {
            finish();
        }
    }
}
