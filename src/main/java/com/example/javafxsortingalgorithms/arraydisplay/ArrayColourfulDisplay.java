package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;

public class ArrayColourfulDisplay extends ArrayElementDisplay<Rectangle> {

    private double colourOffset;

    public ArrayColourfulDisplay(List<Integer> list, SettingsPane settingsPane) {
        super(list, settingsPane);
        colourOffset = 0;
    }

    @Override
    protected void initializeFinishTimeline() {
        finishTimeline = new Timeline(
                new KeyFrame(
                        Duration.millis(5),
                        event -> {
                            colourOffset += 1;
                            update();
                        }
                )
        );
        finishTimeline.setCycleCount(360);
        finishTimeline.setOnFinished(event -> {
            colourOffset = 0;
            update();
        });
    }

    @Override
    protected Rectangle createElement() {
        return new Rectangle();
    }

    @Override
    public void update() {
        if (elements == null) return;

        for (int i = 0; i < elements.size(); i++) {
            // Apparently the colour range is 0-360 https://stackoverflow.com/questions/22973532/java-creating-a-discrete-rainbow-colour-array
            double k = 360;
            double height = list.get(i) * getHeightMultiplier();
            double bottom = maxValue * getHeightMultiplier();
            double hue = k * list.get(i) / maxValue + colourOffset;

            Rectangle rect = elements.get(i);
//            if (colourActions.containsKey(i)) {
//                if (colourActions.get(i) == ColourAction.READ) {
//                    rect.setFill(Color.hsb(hue, 0.2, 1.0));
//                } else {
//                    rect.setFill(Color.hsb(hue, 0.4, 1.0));
//                }
//                colourActions.remove(i);
//            } else {
//                rect.setFill(Color.hsb(hue, 1.0, 1.0));
//            }

            rect.setFill(Color.hsb(hue, 1.0, 1.0));
            rect.setX(getElementWidth() * i);
            rect.setY(bottom - height);
            rect.setWidth(getElementWidth());
            rect.setHeight(height);
        }
        colourActions.clear();
    }
}
