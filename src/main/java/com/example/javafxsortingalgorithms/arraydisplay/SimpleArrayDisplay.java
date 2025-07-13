package com.example.javafxsortingalgorithms.arraydisplay;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleArrayDisplay<T extends Node> extends ArrayDisplayBase {

    private final List<T> elements;

    public SimpleArrayDisplay() {
        elements = new ArrayList<>();
    }

    @Override
    public void initializeElements(int listSize) {
        getChildren().removeAll(elements);
        elements.clear();
        for (int i = 0; i < listSize; i++) {
            T element = createElement();
            getChildren().add(element);
            elements.add(element);
        }
    }

    @Override
    public void displayFrame(DisplayFrame frame) {
        if (frame.list().size() != elements.size()) {
            System.out.println("Size of frame does not equal number of elements");
            return;
        }

        for (int i = 0; i < frame.list().size(); i++) {
            formatElement(i, frame.list().get(i), elements.get(i));
        }
    }

    protected abstract T createElement();
    protected abstract void formatElement(int index, int value, T element);
}
