package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AnimationManager {

    private Pane animationPane;
    private int max = 0;
    private SettingsPane settingsPane;
    private double heightMultiplier;
    private List<Integer> list;
    private List<DetailedElement> elements;
    private List<AnimationGroup> animationGroups;
    private AnimationGroup currentAnimationGroup;

    public AnimationManager(Pane animationPane, List<DetailedElement> elements) {
        this.animationPane = animationPane;
        this.elements = elements;
        animationGroups = new ArrayList<>();
        currentAnimationGroup = new AnimationGroup();
        animationGroups.add(currentAnimationGroup);

    }

    public void addReadAnimation(int index) {
        Polygon arrow = createReadArrow();
        arrow.setLayoutX(getX(settingsPane, index));
        arrow.setLayoutY(max * heightMultiplier);
        int height = list.get(index);
        currentAnimationGroup.addTimelines(
                new Timeline(
                        new KeyFrame(
                                Duration.ZERO,
                                event -> animationPane.getChildren().add(arrow)
                        ),
                        new KeyFrame(
                                Duration.millis((double) height / max * ArrayDetailedDisplay.ANIMATION_LENGTH),
                                new KeyValue(arrow.layoutXProperty(), heightMultiplier * (max - height))
                        ),
                        new KeyFrame(
                                Duration.millis(ArrayDetailedDisplay.ANIMATION_LENGTH + 1),
                                event -> animationPane.getChildren().remove(arrow)
                        )
                )
        );
    }

    public void addCompareAnimation(int firstIndex, int secondIndex) {
        addReadAnimation(firstIndex);
        addReadAnimation(secondIndex);
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public static double getX(SettingsPane settingsPane, int index) {
        return settingsPane.getDisplaySettings().getElementWidth() * index;
    }

    public static Timeline scaleTimeline(Timeline timeline, double scaleFactor) {
        for (int i = 0; i < timeline.getKeyFrames().size(); i++) {
            timeline.getKeyFrames().get(i).getTime().multiply(scaleFactor);
        }
        return timeline;
    }

    public static Polygon createReadArrow() {
        double length = 15;
        return new Polygon(
                0.0, 0.0,
                -length, length / 2,
                -length + length / 4, 0.0,
                -length, -length / 2
        );
    }
}
