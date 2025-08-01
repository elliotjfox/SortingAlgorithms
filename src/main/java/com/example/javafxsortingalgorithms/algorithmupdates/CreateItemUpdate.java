package com.example.javafxsortingalgorithms.algorithmupdates;

import com.example.javafxsortingalgorithms.animation.AnimatedItem;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplayBase;

public class CreateItemUpdate implements DisplayUpdate {

    private final AnimatedItem animatedItem;

    public CreateItemUpdate(AnimatedItem animatedItem) {
        this.animatedItem = animatedItem;
    }

    @Override
    public void performChange(ArrayDisplayBase display) {
        animatedItem.generateVisuals(display.getSettings());
        display.getChildren().add(animatedItem);
    }
}
