package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class ArrayPlotDisplay extends ArrayBarDisplay {
    public ArrayPlotDisplay(List<Integer> arrayList, SettingsPane settingsPane) {
        super(arrayList, settingsPane);
    }

    @Override
    public void drawArray() {
        if (elements == null) return;

        setHeightMultiplier();
        for (int i = 0; i < elements.size(); i++) {
            double height = array.get(i) * heightMultiplier;
            double bottom = maxValue * heightMultiplier;

            Rectangle rect = elements.get(i);
            if (colourActions.containsKey(i)) {
                if (colourActions.get(i) == ColourAction.READ) {
                    rect.setFill(Color.hsb(0, 0, 0.25));
                } else if (colourActions.get(i) == ColourAction.WRITE) {
                    rect.setFill(Color.hsb(0, 0, 0.5));
                }
            } else {
                rect.setFill(Color.hsb(0, 0, 0));
            }
            rect.setX(settingsPane.getDisplaySettings().getElementWidth() * i);
            rect.setY(bottom - height);
            rect.setWidth(settingsPane.getDisplaySettings().getElementWidth());
            rect.setHeight(settingsPane.getDisplaySettings().getElementWidth());
        }
        colourActions.clear();
    }
}
