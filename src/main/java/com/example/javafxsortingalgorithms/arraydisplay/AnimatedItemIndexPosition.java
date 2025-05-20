package com.example.javafxsortingalgorithms.arraydisplay;

public class AnimatedItemIndexPosition implements AnimatedItemPosition {

    private int index;
    private double height;

    public AnimatedItemIndexPosition(int index, double height) {
        this.index = index;
        this.height = height;
    }

    @Override
    public void moveItem(AnimatedItem item) {
        item.setLayoutY(index * item.display.getElementWidth());
        item.setLayoutY(item.display.getMaxValue() * item.display.getHeightMultiplier() - height);
    }
}
