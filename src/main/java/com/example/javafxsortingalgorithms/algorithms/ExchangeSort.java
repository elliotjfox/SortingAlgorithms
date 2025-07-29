package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsComboBox;
import com.example.javafxsortingalgorithms.newanimation.NewAnimatedArrow;

import java.util.List;

public class ExchangeSort extends SortingAlgorithm {

    // TODO: Does this need to be an enum?
    public enum Direction {
        UP,
        DOWN
    }

    private final Direction direction;

    public ExchangeSort(List<Integer> list, Direction direction) {
        super(list);

        this.direction = direction;
    }

    @Override
    protected void runAlgorithm() {
        switch (direction) {
            case UP -> runUp();
            case DOWN -> runDown();
        }
    }

    private void runUp() {
        NewAnimatedArrow outerArrow = animation.createArrow();
        NewAnimatedArrow innerArrow = animation.createArrow();
        animation.setItemIndex(outerArrow, 0);
        animation.setItemIndex(innerArrow, 1);
        animation.setItemHeight(outerArrow, 0);
        animation.setItemHeight(innerArrow, 0);

        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                animation.moveItem(outerArrow, i);
                animation.moveItem(innerArrow, j);
                animation.addFrame();
                animation.readIndex(j);
                animation.readIndex(i);
                if (list.get(j) < list.get(i)) {
                    animation.addFrame();
                    swap(i, j);
                }
                addFrame();
            }
        }
    }

    private void runDown() {
        NewAnimatedArrow outerArrow = animation.createArrow();
        NewAnimatedArrow innerArrow = animation.createArrow();
        animation.setItemIndex(outerArrow, 0);
        animation.setItemIndex(innerArrow, 1);
        animation.setItemHeight(outerArrow, 0);
        animation.setItemHeight(innerArrow, list.size() - 1);

        for (int i = 0; i < list.size(); i++) {
            for (int j = list.size() - 1; j > i; j--) {
                animation.moveItem(outerArrow, i);
                animation.moveItem(innerArrow, j);
                animation.addFrame();
                animation.readIndex(j);
                animation.readIndex(i);
                if (list.get(j) < list.get(i)) {
                    animation.addFrame();
                    swap(i, j);
                }
                addFrame();
            }
        }
    }

    @Override
    public String getName() {
        return "Exchange Sort\nDirection: " + direction;
    }

    public static AlgorithmSettings<ExchangeSort> getSettings() {
        AlgorithmSettingsComboBox<Direction> directionSetting = new AlgorithmSettingsComboBox<>("Direction", Direction.values(), Direction.DOWN);

        return new AlgorithmSettings<>(
                "Exchange Sort",
                (l, b) -> new ExchangeSort(l, directionSetting.getValue()),
                directionSetting
        );
    }
}
