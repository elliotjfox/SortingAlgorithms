package com.example.javafxsortingalgorithms.algorithmupdates;

import com.example.javafxsortingalgorithms.animation.AnimatedCreation;
import com.example.javafxsortingalgorithms.animation.AnimatedItem;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplayBase;
import javafx.animation.Timeline;

public class CreateItemUpdate implements DisplayUpdate {

    private final AnimatedItem animatedItem;

    public CreateItemUpdate(AnimatedItem animatedItem) {
        this.animatedItem = animatedItem;
    }

    @Override
    public void performChange(ArrayDisplayBase display) {
        display.getChildren().add(animatedItem);
        if (animatedItem instanceof AnimatedCreation animatedCreation) {
            Timeline creationTimeline = animatedCreation.createCreationTimeline(display.getSettings());
            AnimationUpdate animationUpdate = new AnimationUpdate(creationTimeline, () -> animatedItem.generateVisuals(display.getSettings()));
            animationUpdate.performChange(display);
        } else {
            animatedItem.generateVisuals(display.getSettings());
        }
    }
}
