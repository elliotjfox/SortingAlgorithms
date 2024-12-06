package com.example.javafxsortingalgorithms.betteralgorithms;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class Ticker extends AlgorithmSpaceObject {

    private final Runnable runnable;
    private final Label label;

    public Ticker(Runnable runnable) {
        this.runnable = runnable;
        label = new Label("Unnamed Ticker");
    }

    public void run() {
        runnable.run();
    }

    @Override
    public Node getVisual() {
        return label;
    }
}
