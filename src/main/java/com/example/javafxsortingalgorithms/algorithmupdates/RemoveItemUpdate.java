package com.example.javafxsortingalgorithms.algorithmupdates;

import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplayBase;
import com.example.javafxsortingalgorithms.newanimation.NewAnimatedItem;

public class RemoveItemUpdate implements DisplayUpdate {

    private final NewAnimatedItem animatedItem;

    public RemoveItemUpdate(NewAnimatedItem animatedItem) {
        this.animatedItem = animatedItem;
    }

    @Override
    public void performChange(ArrayDisplayBase display) {
        display.getChildren().remove(animatedItem);
    }
}
