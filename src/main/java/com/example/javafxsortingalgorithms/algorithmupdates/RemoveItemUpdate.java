package com.example.javafxsortingalgorithms.algorithmupdates;

import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplayBase;
import com.example.javafxsortingalgorithms.animation.AnimatedItem;

public class RemoveItemUpdate implements DisplayUpdate {

    private final AnimatedItem animatedItem;

    public RemoveItemUpdate(AnimatedItem animatedItem) {
        this.animatedItem = animatedItem;
    }

    @Override
    public void performChange(ArrayDisplayBase display) {
        display.getChildren().remove(animatedItem);
    }
}
