package com.example.javafxsortingalgorithms.betteralgorithms;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Algorithm extends BoundAlgorithmSpaceObject {

    protected final AlgorithmSpace space;
    protected final HBox visual;

    public Algorithm(String name, AlgorithmSpace space, Bounds bounds) {
        super(bounds);

        this.space = space;

        visual = new HBox(5);
        visual.getChildren().addAll(new Label(name), new Label(bounds.toString()));

        space.addAlgorithmSpaceObject(this);
    }

    protected void swap(int firstIndex, int secondIndex) {
        space.swap(firstIndex, secondIndex);
    }

    protected void move(int index, int targetIndex) {
        space.move(index, targetIndex);
    }

    @Override
    public Node getVisual() {
        return visual;
    }
}
