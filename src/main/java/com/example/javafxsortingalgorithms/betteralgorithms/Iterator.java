package com.example.javafxsortingalgorithms.betteralgorithms;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class Iterator extends AlgorithmSpaceObject {

    private final Runnable runnable;
    private final Label label;

    public Iterator(Runnable runnable) {
        this.runnable = runnable;
        label = new Label("Iterator");
    }

    public void run() {
        runnable.run();
    }

    @Override
    public Node getVisual() {
        return label;
    }
}
