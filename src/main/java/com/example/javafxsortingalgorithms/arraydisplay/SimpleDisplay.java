package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class SimpleDisplay extends Display {

    protected List<Rectangle> nodes;

    public SimpleDisplay(SettingsPane settingsPane, List<Integer> list) {
        this(settingsPane);
        setList(list);
    }

    public SimpleDisplay(SettingsPane settingsPane) {
        super(settingsPane);

        nodes = new ArrayList<>();
    }

    @Override
    protected void showFrame(DisplayFrame frame) {
        if (!isFrameValid(frame)) return;

        List<Integer> list = frame.getList();
        for (int i = 0; i < list.size(); i++) {
            double k = 360;
            double height = list.get(i) * getHeightMultiplier();
            double bottom = maxValue * getHeightMultiplier();
            double hue = k * list.get(i) / maxValue;

            Rectangle rect = nodes.get(i);

            rect.setFill(Color.hsb(hue, 1.0, 1.0));
            rect.setX(getElementWidth() * i);
            rect.setY(bottom - height);
            rect.setWidth(getElementWidth());
            rect.setHeight(height);
        }
    }

    @Override
    public void setList(List<Integer> list) {
        super.setList(list);

        getChildren().removeAll(nodes);

        nodes.clear();
        for (int i = 0; i < list.size(); i++) {
            nodes.add(new Rectangle());
        }
        getChildren().addAll(nodes);

        resetMax();

        showFrame(list);
    }
}
