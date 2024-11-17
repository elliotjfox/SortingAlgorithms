package com.example.javafxsortingalgorithms.betteralgorithm;

public class LoopStepCounter {

    private final int from;
    private final int to;
    private int current;
    private boolean done;

    public LoopStepCounter(int from, int to) {
        this.from = from;
        this.to = to;
        current = from;
        done = false;

        checkIfDone();
    }

    public void increment() {
        current++;
        checkIfDone();
    }

    public void decrement() {
        current--;
        checkIfDone();
    }

    public void set(int stepNumber) {
        current = stepNumber;
        checkIfDone();
    }

    public void finish() {
        done = true;
    }

    private void checkIfDone() {
        if (from >= to || current < from || current >= to) {
            finish();
        }
    }

    public int getCurrent() {
        return current;
    }

    public boolean isDone() {
        return done;
    }

    public int getLower() {
        return from;
    }

    public int getUpper() {
        return to;
    }
}
