package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsComboBox;
import com.example.javafxsortingalgorithms.newanimation.NewAnimatedArrow;

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

    /** The index of the minimum number */
    private int minIndex;
    /** The index of the maximum number */
    private int maxIndex;
    /** The index we are currently looking at */
    private int curIndex;
    /** The number of sorted elements */
    private int sorted;

    /**
     * Create a selection sort to sort the provided list, with the provided selection type.
     *
     * @param list The list to sort
     * @param mode The selection mode to use
     */
    public SelectionSort(List<Integer> list, SelectionMode mode) {
        super(list);

        this.mode = mode;

        minIndex = 0;
        maxIndex = 0;
        curIndex = 0;
        sorted = 0;
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
        NewAnimatedArrow minPointer = animation.createArrow();
        NewAnimatedArrow pointer = animation.createArrow();
        animation.setItemHeight(minPointer, 0);
        animation.setItemHeight(pointer, 0);
        animation.setItemIndex(minPointer, 0);
        animation.setItemIndex(pointer, 0);

        for (int i = 0; i < list.size(); i++) {
            int minIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                animation.moveItem(pointer, j);
                animation.moveItem(minPointer, minIndex);
                animation.addFrame();
                animation.readIndex(j);
                animation.readIndex(minIndex);
                if (list.get(j) < list.get(minIndex)) {
                    animation.addFrame();
                    animation.moveItem(minPointer, j);
                    minIndex = j;
                }
                addFrame();
            }
            swap(minIndex, i);
            addFrame();
        }
    }

    private void runMax() {
        NewAnimatedArrow maxArrow = animation.createArrow();
        NewAnimatedArrow pointer = animation.createArrow();
        animation.setItemHeight(maxArrow, 0);
        animation.setItemHeight(pointer, 0);
        animation.setItemIndex(maxArrow, 0);
        animation.setItemIndex(pointer, 0);

        for (int i = 0; i < list.size(); i++) {
            int maxIndex = 0;
            for (int j = 0; j < list.size() - i; j++) {
                animation.moveItem(pointer, j);
                animation.moveItem(maxArrow, maxIndex);
                animation.addFrame();
                animation.readIndex(j);
                animation.readIndex(maxIndex);
                if (list.get(j) > list.get(maxIndex)) {
                    animation.addFrame();
                    animation.moveItem(maxArrow, j);
                    maxIndex = j;
                }
                addFrame();
            }
            swap(maxIndex, list.size() - 1 - i);
            addFrame();
        }
    }

    private void runBoth() {
        NewAnimatedArrow pointer = animation.createArrow();
        NewAnimatedArrow minPointer = animation.createArrow();
        NewAnimatedArrow maxPointer = animation.createArrow();
        animation.setItemHeight(pointer, 0);
        animation.setItemHeight(minPointer, 0);
        animation.setItemHeight(maxPointer, 0);
        animation.setItemIndex(pointer, 0);
        animation.setItemIndex(minPointer, 0);
        animation.setItemIndex(maxPointer, 0);

        for (int i = 0; i < list.size() / 2; i++) {
            int minIndex = i;
            int maxIndex = i;
            for (int j = i + 1; j < list.size() - i; j++) {
                animation.moveItem(pointer, j);
                animation.moveItem(minPointer, minIndex);
                animation.moveItem(maxPointer, maxIndex);
                animation.addFrame();
                animation.readIndex(j);
                animation.readIndex(minIndex);
                if (list.get(j) < list.get(minIndex)) {
                    animation.addFrame();
                    animation.moveItem(minPointer, j);
                    minIndex = j;
                }
                animation.addFrame();
                animation.readIndex(j);
                animation.readIndex(maxIndex);
                if (list.get(j) > list.get(maxIndex)) {
                    animation.addFrame();
                    animation.moveItem(maxPointer, j);
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


    /**
     * Solve the list at top speed (this will be called inside a thread). Currently only uses the min selection type
     * @param entry The entry to keep track of how many read and write ops we've done
     */
    @Override
    protected void instantAlgorithm(TestEntry entry) {
        for (int i = 0; i < list.size(); i++) {
            int minIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                entry.addRead(2);
                if (list.get(j) < list.get(minIndex)) minIndex = j;
            }
            entry.addWrite(2);
            swap(minIndex, i);
            entry.updateProgress((float) i / list.size());
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
                (l, b) -> new SelectionSort(l, selectionSetting.getValue()),
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
