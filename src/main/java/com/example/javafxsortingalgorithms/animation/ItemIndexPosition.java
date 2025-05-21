package com.example.javafxsortingalgorithms.animation;

public class ItemIndexPosition implements ItemPosition {

    private final int index;
    private final double height;

    public ItemIndexPosition(int index, double height) {
        this.index = index;
        this.height = height;
    }

    @Override
    public void moveItem(AnimatedItem item) {
        item.setLayoutX(index * item.display.getElementWidth());
        item.setLayoutY(item.display.getMaxValue() * item.display.getHeightMultiplier() - height);
    }

    @Override
    public double getLayoutX(AnimatedArrayDisplay display) {
        return index * display.getElementWidth();
    }

    @Override
    public double getLayoutY(AnimatedArrayDisplay display) {
        return display.getMaxValue() * display.getHeightMultiplier() - height;
    }
}
