package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsComboBox;
import com.example.javafxsortingalgorithms.animation.AnimatedArrow;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledIndex;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledPosition;

import java.util.List;

/**
 * A class representing the logic and variables for selection sort
 */
public class SelectionSort extends SortingAlgorithm {

    /**
     * The type of selection sort this is. MIN corresponding to selecting the minimum number, MAX to selecting the maximum numbers,
     * and BOTH to keeping track of both the maximum and minimum at the same time.
     */
    public enum SelectionMode {
        MIN,
        MAX,
        BOTH
    }

    /** The type of selection sort this is */
    private final SelectionMode mode;

    /**
     * Create a selection sort to sort the provided list, with the provided selection type.
     *
     * @param list The list to sort
     * @param mode The selection mode to use
     */
    public SelectionSort(List<Integer> list, SelectionMode mode) {
        super(list);

        this.mode = mode;
    }

    @Override
    protected void runAlgorithm() {
        switch (mode) {
            case MIN -> runMin();
            case MAX -> runMax();
            case BOTH -> runBoth();
        }
    }

    private void runMin() {
        AnimatedArrow minPointer = animation.createArrow();
        animation.setItemPosition(minPointer, new ElementScaledPosition(0, 0));
        AnimatedArrow pointer = animation.createArrow();
        animation.setItemPosition(pointer, new ElementScaledPosition(0, 0));

        for (int i = 0; i < list.size(); i++) {
            trial.setProgress(i, list.size());
            int minIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                animation.changeItemX(pointer, new ElementScaledIndex(j));
                animation.changeItemX(minPointer, new ElementScaledIndex(minIndex));
                animation.addFrame();
                animation.readIndex(j);
                animation.readIndex(minIndex);
                trial.addRead(2);
                trial.addComparison();
                if (list.get(j) < list.get(minIndex)) {
                    animation.addFrame();
                    animation.changeItemX(minPointer, new ElementScaledIndex(j));
                    minIndex = j;
                }
                addFrame();
            }
            swap(minIndex, i);
            addFrame();
        }
    }

    private void runMax() {
        AnimatedArrow maxArrow = animation.createArrow();
        animation.setItemPosition(maxArrow, new ElementScaledPosition(0, 0));
        AnimatedArrow pointer = animation.createArrow();
        animation.setItemPosition(pointer, new ElementScaledPosition(0, 0));

        for (int i = 0; i < list.size(); i++) {
            trial.setProgress(i, list.size());
            int maxIndex = 0;
            for (int j = 0; j < list.size() - i; j++) {
                animation.changeItemX(pointer, new ElementScaledIndex(j));
                animation.changeItemX(maxArrow, new ElementScaledIndex(maxIndex));
                animation.addFrame();
                animation.readIndex(j);
                animation.readIndex(maxIndex);
                trial.addRead(2);
                trial.addComparison();
                if (list.get(j) > list.get(maxIndex)) {
                    animation.addFrame();
                    animation.changeItemX(maxArrow, new ElementScaledIndex(j));
                    maxIndex = j;
                }
                addFrame();
            }
            swap(maxIndex, list.size() - 1 - i);
            addFrame();
        }
    }

    private void runBoth() {
        AnimatedArrow pointer = animation.createArrow();
        animation.setItemPosition(pointer, new ElementScaledPosition(0, 0));
        AnimatedArrow minPointer = animation.createArrow();
        animation.setItemPosition(minPointer, new ElementScaledPosition(0, 0));
        AnimatedArrow maxPointer = animation.createArrow();
        animation.setItemPosition(maxPointer, new ElementScaledPosition(0, 0));

        for (int i = 0; i < list.size() / 2; i++) {
            trial.setProgress(i, list.size() / 2.0);
            int minIndex = i;
            int maxIndex = i;
            for (int j = i + 1; j < list.size() - i; j++) {
                animation.changeItemX(pointer, new ElementScaledIndex(j));
                animation.changeItemX(minPointer, new ElementScaledIndex(minIndex));
                animation.changeItemX(maxPointer, new ElementScaledIndex(maxIndex));
                animation.addFrame();
                animation.readIndex(j);
                animation.readIndex(minIndex);
                trial.addRead(2);
                trial.addComparison();
                if (list.get(j) < list.get(minIndex)) {
                    animation.addFrame();
                    animation.changeItemX(minPointer, new ElementScaledIndex(j));
                    minIndex = j;
                }
                animation.addFrame();
                animation.readIndex(j);
                animation.readIndex(maxIndex);
                trial.addRead(2);
                trial.addComparison();
                if (list.get(j) > list.get(maxIndex)) {
                    animation.addFrame();
                    animation.changeItemX(maxPointer, new ElementScaledIndex(j));
                    maxIndex = j;
                }
                addFrame();
            }

            if (maxIndex == i) {
                swap(maxIndex, list.size() - 1 - i);
                addFrame();
                swap(minIndex, i);
            } else {
                swap(minIndex, i);
                addFrame();
                swap(maxIndex, list.size() - 1 - i);
            }
            addFrame();
        }
    }

    @Override
    public String getName() {
        StringBuilder str = new StringBuilder("Selection Sort");
        switch (mode) {
            case MIN -> str.append("\nMinimum selection mode");
            case MAX -> str.append("\nMaximum selection mode");
            case BOTH -> str.append("\nMin and max selection mode");
        }
        return str.toString();
    }

    public static AlgorithmSettings<SelectionSort> getSettings() {
        AlgorithmSettingsComboBox<SelectionMode> selectionSetting = new AlgorithmSettingsComboBox<>("Selection Mode", SelectionMode.values(), SelectionMode.MIN);

        return new AlgorithmSettings<>(
                "Selection Sort",
                list -> new SelectionSort(list, selectionSetting.getValue()),
                selectionSetting
        );
    }

//    void selectionSort() {
//        for (int i = 0; i < list.size(); i++) {
//            int minIndex = i;
//            for (int j = i + 1; j < list.size(); j++) {
//                if (list.get(j) < list.get(minIndex)) {
//                    minIndex = j;
//                }
//            }
//            swap(minIndex, i);
//        }
//    }
}
