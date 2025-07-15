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
    public void initializeElements(List<Integer> list) {
        getChildren().removeAll(elements);
        elements.clear();
        for (int i = 0; i < list.size(); i++) {
            T element = createElement();
            getChildren().add(element);
            elements.add(element);
        }

        displayList(list);
    }

    @Override
    public void displayList(List<Integer> list) {
        if (list.size() != elements.size()) {
            System.out.println("List size does not equal number of elements");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            formatElement(i, list.get(i), elements.get(i));
        }
    }

    protected abstract T createElement();
    protected abstract void formatElement(int index, int value, T element);
}
