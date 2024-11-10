package com.example.javafxsortingalgorithms.betteralgorithm;

import java.util.function.Consumer;

public abstract class LoopStep {

    protected final int from;
    protected final int to;
    private int current;
    private boolean done;

    // [from, to)
    public LoopStep(int from, int to) {
        this.from = from;
        this.to = to;
        current = from;
    }

    public void doStep() {
        if (done) return;
        step(current);
    }

    public abstract void step(int i);

    public void increment() {
        current++;
        if (current >= to) {
            finish();
        }
    }

    public void decrement() {
        current--;
        if (current < from) {
            current = from;
        }
    }

    public void setStep(int stepNumber) {
        if (stepNumber < from || stepNumber >= to) return;
        current = stepNumber;
    }

    public void finish() {
        done = true;
    }

    public boolean isDone() {
        return done;
    }
}
