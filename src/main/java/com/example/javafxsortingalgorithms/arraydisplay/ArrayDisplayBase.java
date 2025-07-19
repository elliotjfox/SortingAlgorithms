package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.algorithmupdates.AnimationUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.ListUpdate;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public abstract class ArrayDisplayBase extends Pane {

    protected DisplaySettings currentSettings;
    protected List<AnimationUpdate> currentAnimations;

    public ArrayDisplayBase() {
        currentAnimations = new ArrayList<>();
    }

    public void initializeSettings(DisplaySettings settings) {
        this.currentSettings = settings;
    }
    public abstract void initializeElements(List<Integer> list);
    public abstract void displayList(List<Integer> list);
    public abstract void animateItems(List<Integer> list, List<ListUpdate> changes);
    public abstract Timeline moveElement(int index, int targetIndex);

    public void playAnimations() {
        for (AnimationUpdate animation : currentAnimations) {
            animation.play();
        }
    }

    public void addAnimation(AnimationUpdate animation) {
        currentAnimations.add(animation);
    }

    public void removeAnimation(AnimationUpdate animation) {
        currentAnimations.remove(animation);
    }

    public DisplaySettings getSettings() {
        return currentSettings;
    }
}
