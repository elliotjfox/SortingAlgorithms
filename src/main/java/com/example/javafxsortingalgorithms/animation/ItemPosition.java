package com.example.javafxsortingalgorithms.animation;

public interface ItemPosition {
    void moveItem(AnimatedItem item);
    double getLayoutX(AnimatedArrayDisplay display);
    double getLayoutY(AnimatedArrayDisplay display);
}
