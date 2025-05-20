package com.example.javafxsortingalgorithms.arraydisplay;

public class AnimatedItemExactPosition implements AnimatedItemPosition {

    private double x;
    private double y;

    public AnimatedItemExactPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void moveItem(AnimatedItem item) {
        item.setLayoutX(x);
        item.setLayoutY(item.display.getMaxValue() * item.display.getHeightMultiplier() - y);
    }
}
