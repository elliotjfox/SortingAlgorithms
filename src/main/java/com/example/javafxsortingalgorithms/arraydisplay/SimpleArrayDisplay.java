package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.algorithmupdates.AnimationUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.ListUpdate;
import javafx.animation.Timeline;
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
        getChildren().clear();
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

    @Override
    public Timeline moveElement(int index, int targetIndex) {
        return moveElement(elements.get(index), targetIndex);
    }

    @Override
    public void animateItems(List<Integer> list, List<ListUpdate> changes) {
        for (ListUpdate change : changes) {
            change.performChange(elements);
        }

        for (int i = 0; i < elements.size(); i++) {
            int finalI = i;
            AnimationUpdate animation = new AnimationUpdate(
                    moveElement(elements.get(i), i),
                    () -> formatElement(finalI, list.get(finalI), elements.get(finalI))
            );
            animation.performChange(this);
        }
    }

    protected abstract T createElement();
    protected abstract void formatElement(int index, int value, T element);
    protected abstract Timeline moveElement(T element, int targetIndex);
}
