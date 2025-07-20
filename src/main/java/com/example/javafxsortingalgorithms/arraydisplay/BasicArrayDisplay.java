package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.AlgorithmController;
import com.example.javafxsortingalgorithms.algorithmupdates.AnimationUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.ListUpdate;
import com.example.javafxsortingalgorithms.newanimation.NewAnimatedReadArrow;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class BasicArrayDisplay extends SimpleArrayDisplay<Rectangle> {

    private static final double COLOUR_RANGE = 360;

    private final List<NewAnimatedReadArrow> arrows;

    public BasicArrayDisplay() {
        super();

        arrows = new ArrayList<>();
    }

    @Override
    public void animateItems(List<Integer> list, List<ListUpdate> changes) {
        getChildren().removeAll(arrows);
        arrows.clear();
        super.animateItems(list, changes);
    }

    @Override
    protected Rectangle createElement() {
        return new Rectangle();
    }

    @Override
    protected void formatElement(int index, int value, Rectangle element) {
        double hue = COLOUR_RANGE * value / currentSettings.maxValue();
        element.setX(index * currentSettings.elementWidth());
        element.setY((currentSettings.maxValue() - value) * currentSettings.heightMultiplier());
        element.setHeight(value * currentSettings.heightMultiplier());
        element.setWidth(currentSettings.elementWidth());
        element.setFill(Color.hsb(hue, 1.0 ,1.0));
    }

    @Override
    protected Timeline moveElement(Rectangle element, int targetIndex) {
        return new Timeline(
                new KeyFrame(
                        Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                        new KeyValue(element.xProperty(), currentSettings.elementWidth() * targetIndex)
                )
        );
    }

    @Override
    public void createReadAnimation(int index, int value) {
        NewAnimatedReadArrow readArrow = new NewAnimatedReadArrow();
        getChildren().add(readArrow);
        readArrow.generateVisuals(currentSettings);
        readArrow.setLayoutX(currentSettings.elementWidth() * index);
        readArrow.setLayoutY(currentSettings.maxValue() * currentSettings.heightMultiplier());

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        _ -> arrows.add(readArrow)
                ),
                new KeyFrame(
                        Duration.millis((double) value / currentSettings.maxValue() * AlgorithmController.ANIMATION_LENGTH),
                        new KeyValue(readArrow.layoutYProperty(), (currentSettings.maxValue() - value) * currentSettings.heightMultiplier())
                ),
                new KeyFrame(
                        Duration.millis(AlgorithmController.ANIMATION_LENGTH)
                )
        );

        AnimationUpdate animation = new AnimationUpdate(timeline, () -> {});
        animation.performChange(this);

    }

    @Override
    protected Timeline createReadTimeline(NewAnimatedReadArrow readArrow, int index, int value) {
        return new Timeline(
                new KeyFrame(
                        Duration.millis((double) value / currentSettings.maxValue() * AlgorithmController.ANIMATION_LENGTH),
                        new KeyValue(readArrow.layoutYProperty(), (currentSettings.maxValue() - value) * currentSettings.heightMultiplier())
                )
        );
    }
}
