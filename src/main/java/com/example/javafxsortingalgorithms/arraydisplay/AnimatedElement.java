package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class AnimatedElement extends AnimatedItem {

    private final SettingsPane settingsPane;
    private int index;
    private final Rectangle rectangle;
    private final ObjectProperty<Paint> colourProperty;

    public AnimatedElement(ArrayAnimatedDisplay display, int index) {
        super(display);
        this.settingsPane = display.getSettings();
        this.index = index;
        colourProperty = new ObjectPropertyBase<>() {
            @Override
            public Object getBean() {
                return this;
            }

            @Override
            public String getName() {
                return "Colour";
            }
        };

        rectangle = new Rectangle();
        getChildren().add(rectangle);

        setLayoutX(ArrayAnimatedDisplay.getX(settingsPane, index));
        rectangle.setWidth(settingsPane.getDisplaySettings().getElementWidth());
        rectangle.yProperty().bind(Bindings.multiply(-1, rectangle.heightProperty()));
        rectangle.fillProperty().bind(colourProperty);
    }

    public void setColour(double height, double max) {
        colourProperty.setValue(calculateColour(height, max));
//        rectangle.setFill(Color.hsb(height / max * 360, 1.0, 1.0));
    }

    public void setColour(Color colour) {
        colourProperty.setValue(colour);
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
                        Duration.millis(ArrayAnimatedDisplay.ANIMATION_LENGTH),
                        new KeyValue(layoutXProperty(), ArrayAnimatedDisplay.getX(settingsPane, i))
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

    public ObjectProperty<Paint> colourProperty() {
        return colourProperty;
    }

    public static Color calculateColour(double height, double max) {
        return Color.hsb(height / max * 360, 1.0, 1.0);
    }
}
