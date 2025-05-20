package com.example.javafxsortingalgorithms.animation;

public class ItemExactPosition implements ItemPosition {

    private double x;
    private double y;

    public ItemExactPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void moveItem(AnimatedItem item) {
        item.setLayoutX(x);
        item.setLayoutY(item.display.getMaxValue() * item.display.getHeightMultiplier() - y);
    }
}
