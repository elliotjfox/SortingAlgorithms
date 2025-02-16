package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;

public class ArrayHeatMapDisplay extends ArrayElementDisplay<Rectangle> {

    private double colourOffset;

    public ArrayHeatMapDisplay(List<Integer> arrayList, SettingsPane settingsPane) {
        super(arrayList, settingsPane);
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
        // TODO: Figure out how to stop the animation if needed
//        if (finishAnimation != null) finishAnimation.stop();
        for (int i = 0; i < elements.size(); i++) {
            double height = maxValue * getHeightMultiplier();
            int distance = Math.abs(list.get(i) - i);
            // https://www.desmos.com/calculator/jkaxaniaz8
            double hue = -120.0 * Math.sqrt((double) distance / list.size()) + 120 + colourOffset;

            Rectangle rect = elements.get(i);
//            if (colourActions.containsKey(i)) {
//                if (colourActions.get(i) == ColourAction.READ) {
//                    rect.setFill(Color.hsb(hue, 0.2, 1.0));
//                } else {
//                    rect.setFill(Color.hsb(hue, 0.4, 1.0));
//                }
//                colourActions.remove(i);
//            } else {
//                rect.setFill(Color.hsb(hue, 1.0, 0.75));
//            }

            rect.setFill(Color.hsb(hue, 1.0, 0.75));
            rect.setX(getElementWidth() * i);
            rect.setY(0);
            rect.setWidth(getElementWidth());
            rect.setHeight(height);
        }
        colourActions.clear();
    }
}
