package com.example.javafxsortingalgorithms.betteralgorithms;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Pointer extends BoundAlgorithmSpaceObject {

    private String name;
    private int lastValue;
    private int value;
    private Runnable atEnd;
    private HBox hBox;
    private Label label;

    public Pointer(int value, Bounds... bounds) {
        this("Unnamed Pointer", value, bounds);
    }

    public Pointer(String name, int value, Bounds... bounds) {
        super(bounds);
        this.name = name;
        this.value = value;
        hBox = new HBox(5);
        label = new Label();
        hBox.getChildren().addAll(new Label(name), label);
        updateLabel();
    }


    public int getValue() {
        return value;
    }

    public void increment() {
        lastValue = value;
        value++;
        updateLabel();
        if (!isWithinBounds()) {
            if (atEnd != null) {
                atEnd.run();
            } else {
                undo();
            }
        }
    }

    public void decrement() {
        lastValue = value;
        value--;
        updateLabel();
        checkWithinBounds();
    }

    public void setValue(int value) {
        lastValue = this.value;
        this.value = value;
        updateLabel();
        checkWithinBounds();
    }

    public void onEnd(Runnable onEnd) {
        this.atEnd = onEnd;
    }

    public void undo() {
        value = lastValue;
        updateLabel();
    }

    public void checkWithinBounds() {
        if (!isWithinBounds()) {
            undo();
        }
    }

    private void updateLabel() {
        label.setText("Value: " + value);
    }

    @Override
    public boolean isWithinBounds() {
        for (Bounds bound : bounds) {
            if (!bound.isWithinBounds(value)) return false;
        }
        return true;
    }

    @Override
    public Node getVisual() {
        return hBox;
    }

    @Override
    public String toString() {
        return "Pointer{" +
                    "name=" + name +
                    "value=" + value +
                    '}';
    }
}
