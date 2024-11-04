package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class ArrayBarDisplay extends ArrayDisplay {

    protected ArrayList<Rectangle> elements;
    protected double colourOffset = 0;
    private final Timeline finishAnimation;

    private final BorderPane borderPane;
    private final Pane centerPane;

    public ArrayBarDisplay(List<Integer> arrayList, SettingsPane settingsPane) {
        super(settingsPane);
        centerPane = new Pane();

        finishAnimation = new Timeline(
                new KeyFrame(
                        Duration.millis(5),
                        event -> {
                            colourOffset += 1;
                            drawArray();
                        }
                )
        );
        finishAnimation.setCycleCount(360);
        finishAnimation.setOnFinished(event -> {
            colourOffset = 0;
            drawArray();
        });

        borderPane = new BorderPane();
        Pane top = new Pane();
        top.setPrefHeight(settingsPane.getDisplaySettings().getBorderWidth());


        borderPane.setTop(top);
        borderPane.setCenter(centerPane);

        getChildren().add(borderPane);

        setArray(arrayList);

        drawArray();
    }

    public void createElements(int count) {
        if (elements == null) elements = new ArrayList<>();
        if (count == elements.size()) return;
        for (Rectangle rect : elements) {
            centerPane.getChildren().remove(rect);
        }
        elements.clear();

        for (int i = 0; i < count; i++) {
            Rectangle rect = new Rectangle();
            centerPane.getChildren().add(rect);
            elements.add(rect);
        }
    }

    public void drawArray() {
        if (elements == null) return;
        // TODO: Figure out how to stop the animation if needed
//        if (finishAnimation != null) finishAnimation.stop();
        setHeightMultiplier();
        for (int i = 0; i < elements.size(); i++) {
            // Apparently the colour range is 0-360 https://stackoverflow.com/questions/22973532/java-creating-a-discrete-rainbow-colour-array
            double k = 360;
            double height = array.get(i) * heightMultiplier;
            double bottom = maxValue * heightMultiplier;
            double hue = k * array.get(i) / maxValue + colourOffset;

            Rectangle rect = elements.get(i);
            if (colourActions.containsKey(i)) {
                if (colourActions.get(i) == ColourAction.READ) {
                    rect.setFill(Color.hsb(hue, 0.2, 1.0));
                } else {
                    rect.setFill(Color.hsb(hue, 0.4, 1.0));
                }
                colourActions.remove(i);
            } else {
                rect.setFill(Color.hsb(hue, 1.0, 1.0));
            }
            rect.setX(settingsPane.getDisplaySettings().getElementWidth() * i);
            rect.setY(bottom - height);
            rect.setWidth(settingsPane.getDisplaySettings().getElementWidth());
            rect.setHeight(height);
        }
        colourActions.clear();
    }

    @Override
    public void onFinish() {
        finishAnimation.playFromStart();
    }
}
