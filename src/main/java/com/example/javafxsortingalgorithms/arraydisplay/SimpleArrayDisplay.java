package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.AlgorithmController;
import com.example.javafxsortingalgorithms.algorithmupdates.AnimationUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.ListUpdate;
import com.example.javafxsortingalgorithms.animation.AnimatedReadArrow;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleArrayDisplay<T extends Node> extends ArrayDisplayBase {

    private final List<T> elements;
    private final List<AnimatedReadArrow> arrows;

    public SimpleArrayDisplay() {
        elements = new ArrayList<>();
        arrows = new ArrayList<>();
    }

    @Override
    public void initializeElements(List<Integer> list) {
        getChildren().clear();
        elements.clear();
        for (int i = 0; i < list.size(); i++) {
            T element = createElement();
            getChildren().add(element);
            elements.add(element);
        }

        displayList(list);
    }

    @Override
    public void displayList(List<Integer> list) {
        if (list.size() != elements.size()) {
            System.out.println("List size does not equal number of elements");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            formatElement(i, list.get(i), elements.get(i));
        }
    }

    @Override
    public void animateItems(List<Integer> list, List<ListUpdate> changes) {
        // Clean up read arrows
        getChildren().removeAll(arrows);
        arrows.clear();

        for (ListUpdate change : changes) {
            change.performChange(elements);
        }

        for (int i = 0; i < elements.size(); i++) {
            int finalI = i;
            AnimationUpdate animation = new AnimationUpdate(
                    moveElement(elements.get(i), i),
                    () -> formatElement(finalI, list.get(finalI), elements.get(finalI))
            );
            animation.performChange(this);
        }
    }

    @Override
    public void createReadAnimation(int index, int value) {
        AnimatedReadArrow readArrow = new AnimatedReadArrow();
        getChildren().add(readArrow);
        readArrow.generateVisuals(currentSettings);
        Timeline timeline = createReadTimeline(readArrow, index, value);

        if (timeline.getKeyFrames().getLast().getTime().equals(Duration.ZERO)) {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(AlgorithmController.ANIMATION_LENGTH)));
        }
    }

    protected abstract Timeline createReadTimeline(AnimatedReadArrow readArrow, int index, int value);
    protected abstract T createElement();
    protected abstract void formatElement(int index, int value, T element);
    protected abstract Timeline moveElement(T element, int targetIndex);
}
