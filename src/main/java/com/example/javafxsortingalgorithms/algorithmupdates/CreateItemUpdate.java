package com.example.javafxsortingalgorithms.algorithmupdates;

import com.example.javafxsortingalgorithms.animation.NewAnimatedItem;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplayBase;

public class CreateItemUpdate implements DisplayUpdate {

    private final NewAnimatedItem animatedItem;

    public CreateItemUpdate(NewAnimatedItem animatedItem) {
        this.animatedItem = animatedItem;
    }

    @Override
    public void performChange(ArrayDisplayBase display) {
        animatedItem.applySettings(display.getSettings());
        display.getChildren().add(animatedItem);
    }
}
