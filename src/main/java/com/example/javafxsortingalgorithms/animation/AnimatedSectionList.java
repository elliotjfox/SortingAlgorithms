package com.example.javafxsortingalgorithms.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: Finish implementing this properly
public class AnimatedSectionList extends AnimatedItem {

    private final List<AnimatedSection> sections;
    private final VBox vBox;

    public AnimatedSectionList(AnimatedArrayDisplay display) {
        // TODO Fix
        super(display, null, null);
        sections = new ArrayList<>();
        vBox = new VBox(10);

        getChildren().add(vBox);
    }

    public Timeline addSections(AnimatedSection... newSections) {
        KeyValue[] keyValues = new KeyValue[newSections.length];
        for (int i = 0; i < newSections.length; i++) {
            keyValues[i] = new KeyValue(newSections[i].scaleYProperty(), 1);
        }

        return new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        event -> {
                            vBox.getChildren().addAll(newSections);
                            sections.addAll(Arrays.asList(newSections));
                            for (AnimatedSection newSection : newSections) {
                                newSection.setScaleY(0);
                            }
                        }
                ),
                new KeyFrame(
                        Duration.millis(AnimatedArrayDisplay.ANIMATION_LENGTH),
                        keyValues
                )
        );
    }

    public Timeline shrinkHighest() {
        AnimatedSection selectedSection = sections.removeFirst();

        return new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        event -> selectedSection.setSectionVisible(false)
                ),
                new KeyFrame(
                        Duration.millis(AnimatedArrayDisplay.ANIMATION_LENGTH),
                        event -> vBox.getChildren().remove(selectedSection),
                        new KeyValue(selectedSection.scaleYProperty(), 0)
                )
        );
    }

    public boolean hasSection() {
        return !sections.isEmpty();
    }
}
