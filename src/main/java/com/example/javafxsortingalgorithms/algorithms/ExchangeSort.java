package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsComboBox;
import com.example.javafxsortingalgorithms.animation.AnimatedArrow;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledIndex;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledPosition;

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
        AnimatedArrow outerArrow = animation.createArrow();
        animation.setItemPosition(outerArrow, new ElementScaledPosition(0, 0));
        AnimatedArrow innerArrow = animation.createArrow();
        animation.setItemPosition(innerArrow, new ElementScaledPosition(1, 0));

        for (int i = 0; i < list.size(); i++) {
            trial.setProgress(i, list.size());
            for (int j = i + 1; j < list.size(); j++) {
                animation.changeItemX(outerArrow, new ElementScaledIndex(i));
                animation.changeItemX(innerArrow, new ElementScaledIndex(j));
                animation.addFrame();
                animation.readIndex(j);
                animation.readIndex(i);
                trial.addRead(2);
                trial.addComparison();
                if (list.get(j) < list.get(i)) {
                    animation.addFrame();
                    swap(i, j);
                }
                addFrame();
            }
        }
    }

    private void runDown() {
        AnimatedArrow outerArrow = animation.createArrow();
        animation.setItemPosition(outerArrow, new ElementScaledPosition(0, 0));
        AnimatedArrow innerArrow = animation.createArrow();
        animation.setItemPosition(innerArrow, new ElementScaledPosition(list.size() - 1, 0));

        for (int i = 0; i < list.size(); i++) {
            trial.setProgress(i, list.size());
            for (int j = list.size() - 1; j > i; j--) {
                animation.changeItemX(outerArrow, new ElementScaledIndex(i));
                animation.changeItemX(innerArrow, new ElementScaledIndex(j));
                animation.addFrame();
                animation.readIndex(j);
                animation.readIndex(i);
                trial.addRead(2);
                trial.addComparison();
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
                list -> new ExchangeSort(list, directionSetting.getValue()),
                directionSetting
        );
    }
}
