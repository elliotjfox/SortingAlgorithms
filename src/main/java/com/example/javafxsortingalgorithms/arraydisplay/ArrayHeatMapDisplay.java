package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class ArrayHeatMapDisplay extends ArrayBarDisplay {

    public ArrayHeatMapDisplay(List<Integer> arrayList, SettingsPane settingsPane) {
        super(arrayList, settingsPane);
    }

    @Override
    public void drawArray() {
        if (elements == null) return;
        // TODO: Figure out how to stop the animation if needed
//        if (finishAnimation != null) finishAnimation.stop();
        setHeightMultiplier();
        for (int i = 0; i < elements.size(); i++) {
            double height = maxValue * heightMultiplier;
            int distance = Math.abs(array.get(i) - i);
            // https://www.desmos.com/calculator/jkaxaniaz8
            double hue = -120.0 * Math.sqrt((double) distance / array.size()) + 120 + colourOffset;

            Rectangle rect = elements.get(i);
            if (colourActions.containsKey(i)) {
                if (colourActions.get(i) == ColourAction.READ) {
                    rect.setFill(Color.hsb(hue, 0.2, 1.0));
                } else {
                    rect.setFill(Color.hsb(hue, 0.4, 1.0));
                }
                colourActions.remove(i);
            } else {
                rect.setFill(Color.hsb(hue, 1.0, 0.75));
            }
            rect.setX(settingsPane.getDisplaySettings().getElementWidth() * i);
            rect.setY(0);
            rect.setWidth(settingsPane.getDisplaySettings().getElementWidth());
            rect.setHeight(height);
        }
        colourActions.clear();
    }
}
