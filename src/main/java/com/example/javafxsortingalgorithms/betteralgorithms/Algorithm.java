package com.example.javafxsortingalgorithms.betteralgorithms;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Algorithm extends BoundAlgorithmSpaceObject {

    private final AlgorithmSpace space;
    protected final HBox visual;
    protected boolean isDone;

    public Algorithm(String name, AlgorithmSpace space, Bounds bounds) {
        super(bounds);

        this.space = space;
        isDone = false;

        visual = new HBox(5);
        visual.getChildren().addAll(new Label(name));

        space.addAlgorithmSpaceObject(this);
    }

    protected void swap(int firstIndex, int secondIndex) {
        space.swap(firstIndex, secondIndex);
    }

    protected void move(int index, int targetIndex) {
        space.move(index, targetIndex);
    }

    protected void finish() {
        isDone = true;
    }

    public boolean isDone() {
        return isDone;
    }

    @Override
    public Node getVisual() {
        return visual;
    }
}
