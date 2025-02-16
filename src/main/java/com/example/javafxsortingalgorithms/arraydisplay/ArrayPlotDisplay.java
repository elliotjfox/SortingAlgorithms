package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class ArrayPlotDisplay extends ArrayElementDisplay<Rectangle> {
    public ArrayPlotDisplay(List<Integer> arrayList, SettingsPane settingsPane) {
        super(arrayList, settingsPane);
    }

    @Override
    protected Rectangle createElement() {
        return new Rectangle();
    }

    @Override
    public void update() {
        if (elements == null) return;

        for (int i = 0; i < elements.size(); i++) {
            double height = list.get(i) * getHeightMultiplier();
            double bottom = maxValue * getHeightMultiplier();

            Rectangle rect = elements.get(i);
//            if (colourActions.containsKey(i)) {
//                if (colourActions.get(i) == ColourAction.READ) {
//                    rect.setFill(Color.hsb(0, 0, 0.25));
//                } else if (colourActions.get(i) == ColourAction.WRITE) {
//                    rect.setFill(Color.hsb(0, 0, 0.5));
//                }
//            } else {
//                rect.setFill(Color.hsb(0, 0, 0));
//            }

            rect.setFill(Color.hsb(0, 0, 0));
            rect.setX(getElementWidth() * i);
            rect.setY(bottom - height);
            rect.setWidth(getElementWidth());
            rect.setHeight(getElementWidth());
        }
        colourActions.clear();
    }
}
