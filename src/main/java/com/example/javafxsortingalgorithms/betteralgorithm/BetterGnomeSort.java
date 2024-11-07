package com.example.javafxsortingalgorithms.betteralgorithm;

public class BetterGnomeSort extends BetterAlgorithm {

    private int currentSpot;
    private boolean isDone;

    @Override
    public void initializeNormal() {

    }

    @Override
    public void initializeAnimated() {

    }

    @Override
    public void initializeInstant() {

    }

    @Override
    public void stepNormal() {
        if (currentSpot + 1 >= list.size()) {
            isDone = true;
            return;
        }

        if (currentSpot < 0) {
            currentSpot++;
            return;
        }

        if (list.get(currentSpot) <= list.get(currentSpot + 1)) {
            currentSpot++;
            return;
        }

        swap(currentSpot, currentSpot + 1);
        currentSpot--;
    }

    @Override
    public void stepAnimated() {

    }

    @Override
    public void doInstant() {

    }
}
