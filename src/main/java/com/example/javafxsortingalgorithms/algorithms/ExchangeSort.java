package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.FunctionalAlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.SettingsComboBox;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;

import java.util.List;

public class ExchangeSort extends SortingAlgorithm {

    // TODO: Does this need to be an enum?
    public enum Direction {
        UP,
        DOWN
    }

    private int sorted;
    private int curIndex;
    private final Direction direction;

    public ExchangeSort(List<Integer> list, boolean isInstant, Direction direction) {
        super(list, isInstant);

        sorted = 0;
        curIndex = direction == Direction.UP ? 1 : list.size() - 1;
        this.direction = direction;
    }

    @Override
    protected void runAlgorithm(ArrayDisplay display) {
        if (curIndex >= list.size() || curIndex <= sorted) {
            sorted++;
            curIndex = direction == Direction.UP ? sorted + 1 : list.size() - 1;

            if (sorted >= list.size()) {
                isDone = true;
            }

            return;
        }

        if (list.get(curIndex) < list.get(sorted)) {
            swap(curIndex, sorted);
            display.writeIndex(curIndex);
            display.writeIndex(sorted);
        } else {
            display.readIndex(curIndex);
            display.readIndex(sorted);
        }

        if (direction == Direction.UP) {
            curIndex++;
        } else {
            curIndex--;
        }
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

    }

    @Override
    public String getName() {
        return null;
    }

    public static AlgorithmSettings getSettings() {
        SettingsComboBox<Direction> directionSetting = new SettingsComboBox<>("Direction", Direction.values(), Direction.DOWN);

        return new FunctionalAlgorithmSettings<>(
                "Exchange Sort",
                (l, b) -> new ExchangeSort(l, b, directionSetting.getValue()),
                directionSetting
        );
    }
}
