package com.example.javafxsortingalgorithms.betteralgorithm;

import java.util.List;

public class BetterSelectionSort extends LoopThroughAlgorithm {

    @Override
    protected void initializeLoopStep() {

        // This is NOT better
        loopStep = new LoopStep(0, list.size()) {
            LoopStep subStep;
            int minIndex;

            @Override
            public void step(int i) {
                if (subStep == null) {
                    minIndex = i;
                    subStep = new LoopStep(i, list.size()) {
                        @Override
                        public void step(int i) {
                            if (list.get(i) < list.get(minIndex)) {
                                minIndex = i;
                            }
                            increment();
                        }
                    };
                    subStep.setStep(i + 1);
                } else {
                    subStep.doStep();
                    if (subStep.isDone()) {
                        swap(i, minIndex);
                        increment();
                        subStep = null;
                    }
                }
            }
        };
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
