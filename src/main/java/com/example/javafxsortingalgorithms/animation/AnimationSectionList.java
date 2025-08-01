package com.example.javafxsortingalgorithms.animation;

import com.example.javafxsortingalgorithms.AlgorithmController;
import com.example.javafxsortingalgorithms.algorithmupdates.AnimationUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.GenerateAnimationUpdate;
import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnimationSectionList extends AnimatedItem {

    private static final double SPACING = 10;

    private final List<AnimatedSection> sections;
    private final Pane pane;

    public AnimationSectionList() {
        sections = new ArrayList<>();
        pane = new VBox(SPACING);
        ((VBox) pane).setFillWidth(false);
        getChildren().add(pane);
    }

    @Override
    public void generateVisuals(DisplaySettings settings) {
        for (AnimatedSection section : sections) {
            section.generateVisuals(settings);
        }
    }

    public GenerateAnimationUpdate addSectionsToStart(AnimatedSection... newSections) {
        KeyValue[] keyValues = new KeyValue[newSections.length];
        for (int i = 0; i < newSections.length; i++) {
            keyValues[i] = new KeyValue(newSections[i].scaleYProperty(), 1);
            sections.add(newSections[i]);
        }

        return new GenerateAnimationUpdate(
                settings -> new Timeline(
                        new KeyFrame(
                                Duration.ZERO,
                                _ -> {
                                    for (int i = newSections.length - 1; i >= 0; i--) {
                                        Pane tmp = new Pane(newSections[i]);
                                        newSections[i].setLayoutY(settings.elementWidth() / 5);
                                        pane.getChildren().addFirst(tmp);
                                    }
                                    sections.addAll(Arrays.asList(newSections));
                                    for (AnimatedSection section : newSections) {
                                        section.setScaleY(0);
                                    }
                                }
                        ),
                        new KeyFrame(
                                Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                                keyValues
                        )
                ),
                _ -> {}
        );
    }

    public GenerateAnimationUpdate removeFirstSection() {
        return new GenerateAnimationUpdate(
                _ -> new Timeline(
//                        new KeyFrame(
//                                Duration.ZERO,
//                                _ -> pane.getChildren().getFirst().setVisible(false)
//                        ),
                        new KeyFrame(
                                Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                                _ -> pane.getChildren().removeFirst(),
                                new KeyValue(pane.getChildren().getFirst().scaleYProperty(), 0)
                        )
                ),
                _ -> {}
        );
    }

    @Override
    public AnimationUpdate changeFill(Paint fill) {
        return null;
    }
}
