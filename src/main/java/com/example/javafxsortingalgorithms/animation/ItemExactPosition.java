package com.example.javafxsortingalgorithms.animation;

public class ItemExactPosition implements ItemPosition {

    private final double x;
    private final double y;

    public ItemExactPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void moveItem(AnimatedItem item) {
        item.setLayoutX(x);
        item.setLayoutY(item.display.getMaxValue() * item.display.getHeightMultiplier() - y);
    }

    @Override
    public double getLayoutX(AnimatedArrayDisplay display) {
        return x;
    }

    @Override
    public double getLayoutY(AnimatedArrayDisplay display) {
        return display.getMaxValue() * display.getHeightMultiplier() - y;
    }
}
