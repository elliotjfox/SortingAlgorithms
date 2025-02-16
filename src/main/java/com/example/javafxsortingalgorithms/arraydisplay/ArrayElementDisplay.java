package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public abstract class ArrayElementDisplay<T extends Node> extends ArrayDisplay {

    protected final List<T> elements;

    private final Pane centerPane;

    public ArrayElementDisplay(List<Integer> list, SettingsPane settingsPane) {
        super(settingsPane, list);

        elements = new ArrayList<>();

        centerPane = new Pane();
        BorderPane borderPane = new BorderPane();
        Pane top = new Pane();
        top.setPrefHeight(settingsPane.getDisplaySettings().getBorderWidth());
        borderPane.setTop(top);
        borderPane.setCenter(centerPane);

        getChildren().add(borderPane);

        initializeFinishTimeline();
        setList(list);
        update();
    }

    protected void initializeFinishTimeline() {}

    protected abstract T createElement();

    private void removeElements() {
        for (Node element : elements) {
            centerPane.getChildren().remove(element);
        }
        elements.clear();
    }

    @Override
    protected void initializeElements(int count) {
        if (elements == null) {
            System.out.println("Elements is null!");
            return;
        }
        if (count == elements.size()) return;

        removeElements();
        for (int i = 0; i < count; i++) {
            T element = createElement();
            centerPane.getChildren().add(element);
            elements.add(element);
        }
    }
}
