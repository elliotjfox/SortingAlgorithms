package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public abstract class ArrayRectangleDisplay extends ArrayDisplay {

    protected final List<Rectangle> elements;

    private final Pane centerPane;

    public ArrayRectangleDisplay(List<Integer> list, SettingsPane settingsPane) {
        super(settingsPane);

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
        drawArray();
    }

    protected void initializeFinishTimeline() {}

    private void removeElements() {
        for (Rectangle rect : elements) {
            centerPane.getChildren().remove(rect);
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
            Rectangle rect = new Rectangle();
            centerPane.getChildren().add(rect);
            elements.add(rect);
        }
    }
}
