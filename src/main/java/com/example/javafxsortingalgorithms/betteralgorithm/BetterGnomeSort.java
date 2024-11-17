package com.example.javafxsortingalgorithms.betteralgorithm;

public class BetterGnomeSort extends LoopThroughAlgorithm {

    private int currentSpot;
    private boolean isDone;

    @Override
    public void initializeLoopStep() {
        loopStep = new BasicLoopStep(0, list.size(), counter -> {
            int i = counter.getCurrent();
            if (i + 1 >= list.size()) {
                counter.finish();
            } else if (i < 0) {
                counter.increment();
            } else if (list.get(i) <= list.get(i + 1)) {
                counter.increment();
            } else {
                swap(i, i + 1);
                counter.decrement();
            }
        });
//        loopStep = new BasicLoopStep(0, list.size()) {
//            @Override
//            public void step(int i) {
//                if (i + 1 >= list.size()) {
//                    finish();
//                } else if (i < 0) {
//                    increment();
//                } else if (list.get(i) <= list.get(i + 1)) {
//                    increment();
//                } else {
//                    swap(i, i + 1);
//                    decrement();
//                }
//            }
//        };
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
