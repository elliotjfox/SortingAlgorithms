package com.example.javafxsortingalgorithms.animation;

public class ItemIndexPosition implements ItemPosition {

    private int index;
    private double height;

    public ItemIndexPosition(int index, double height) {
        this.index = index;
        this.height = height;
    }

    @Override
    public void moveItem(AnimatedItem item) {
        item.setLayoutX(index * item.display.getElementWidth());
        item.setLayoutY(item.display.getMaxValue() * item.display.getHeightMultiplier() - height);
    }
}
