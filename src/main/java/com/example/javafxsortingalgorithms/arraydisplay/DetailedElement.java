package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class DetailedElement extends DetailedItem {

    private final SettingsPane settingsPane;
    private int index;
    private final Rectangle rectangle;

    public DetailedElement(ArrayDetailedDisplay display, int index) {
        super(display);
        this.settingsPane = display.getSettings();
        this.index = index;

        rectangle = new Rectangle();
        getChildren().add(rectangle);

        setLayoutX(ArrayDetailedDisplay.getX(settingsPane, index));
        rectangle.setWidth(settingsPane.getDisplaySettings().getElementWidth());
        rectangle.yProperty().bind(Bindings.multiply(-1, rectangle.heightProperty()));
    }

    public void setColour(double height, double max) {
        rectangle.setFill(Color.hsb(height / max * 360, 1.0, 1.0));
    }

    public void setColour(Color colour) {
        rectangle.setFill(colour);
    }

    public void setElementHeight(double height, double max) {
        setColour(height, max);
        setLayoutY(max);
//        rectangle.setHeight(height);
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(rectangle.heightProperty(), 0)
                ),
                new KeyFrame(
                        Duration.millis(250),
                        new KeyValue(rectangle.heightProperty(), height)
                )
        );
        timeline.play();
    }

    public void moveToIndex(int i) {
        if (index == i) return;
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(ArrayDetailedDisplay.ANIMATION_LENGTH),
                        new KeyValue(layoutXProperty(), ArrayDetailedDisplay.getX(settingsPane, i))
                )
        );
        index = i;
//        timeline.setOnFinished(event -> index = i);
        timeline.setCycleCount(1);
        timeline.play();
    }

    public int getIndex() {
        return index;
    }
}
