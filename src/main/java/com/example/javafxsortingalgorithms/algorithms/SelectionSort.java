package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsComboBox;
import com.example.javafxsortingalgorithms.animation.AnimatedArrayDisplay;
import com.example.javafxsortingalgorithms.animation.AnimatedItem;
import com.example.javafxsortingalgorithms.animation.ItemBuilder;
import com.example.javafxsortingalgorithms.animation.PolygonWrapper;
import com.example.javafxsortingalgorithms.arraydisplay.*;
import javafx.scene.paint.Color;

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

    /** The arrow pointing to the minimum number */
    private AnimatedItem minArrow;
    /** The arrow pointing to the maximum number */
    private AnimatedItem maxArrow;
    /** The arrow pointing to the current index */
    private AnimatedItem arrow;

    /**
     * Create a selection sort to sort the provided list, with the provided selection type.
     *
     * @param list The list to sort
     * @param mode The selection mode to use
     */
    public SelectionSort(List<Integer> list, boolean isInstant, SelectionMode mode) {
        super(list, isInstant);

        this.mode = mode;

        minIndex = 0;
        maxIndex = 0;
        curIndex = 0;
        sorted = 0;
    }

    @Override
    protected void runAlgorithm(ArrayDisplay display) {
        switch (mode) {
            case MIN -> iterateMin(display);
            case MAX -> iterateMax(display);
            case BOTH -> iterateBoth(display);
        }
    }

    /**
     * Iterate the selection sort using the min selection type by first checking if we have reached the end of the
     * unsorted elements, which is also just the end of the list.
     * <br>
     * If we have, swap the min to its proper place, increase the number of sorted, reset
     * the min index, and check if we are done sorting. This action does take a step.
     * <br>
     * If we haven't reached the end, simply check if the value at the current index is smaller than the current minimum,
     * and update it if needed. Finally, increase the current index for the next step.
     * @param display The display this algorithm is displaying to
     */
    private void iterateMin(ArrayDisplay display) {
        // If we are at the end of the array
        if (curIndex >= list.size()) {
            // Move the smallest to the start
            swap(minIndex, sorted);
            display.writeIndex(sorted);

            // Reset for finding the next smallest
            sorted++;
            curIndex = sorted;
            minIndex = curIndex;

            if (sorted >= list.size()) {
                isDone = true;
            }

            return;
        }

        display.readIndex(curIndex);
        display.readIndex(minIndex);

        // If the current one is smaller, set it to the min
        if (list.get(curIndex) < list.get(minIndex)) {
            minIndex = curIndex;
        }

        // Keep moving through the array
        curIndex++;
    }

    /**
     * Iterate the selection sort using the max selection type by first checking if we have reached the end of the
     * unsorted elements.
     * <br>
     * If we have, swap the max to its proper place, increase the number of sorted, reset
     * the max index, and check if we are done sorting. This action does take a step.
     * <br>
     * If we haven't reached the end, simply check if the value at the current index is bigger than the current maximum,
     * and update it if needed. Finally, increase the current index for the next step.
     * @param display The display this algorithm is displaying to
     */
    private void iterateMax(ArrayDisplay display) {
        // If we are at the end of the array
        if (curIndex >= list.size() - sorted) {
            // Move the smallest to the start
            swap(maxIndex, list.size() - 1 - sorted);
            display.writeIndex(list.size() - 1 - sorted);

            // Reset for finding the next smallest
            sorted++;
            curIndex = 0;
            maxIndex = curIndex;

            if (sorted >= list.size()) {
                isDone = true;
            }

            return;
        }

        display.readIndex(curIndex);
        display.readIndex(maxIndex);

        // If the current one is smaller, set it to the min
        if (list.get(curIndex) > list.get(maxIndex)) {
            maxIndex = curIndex;
        }

        // Keep moving through the array
        curIndex++;
    }

    /**
     * Iterate the selection sort using the both selection type by first checking if we have reached the end of the
     * unsorted elements.
     * <br>
     * If we have, swap the min and max to their proper places, increase the number of sorted, reset
     * the min and max indices, and check if we are done sorting. This action takes a step.
     * <br>
     * If we haven't reached the end, simply check if the value at the current index is smaller or bigger than the current minimum
     * and maximum, and update them if needed. Finally, increase the current index for the next step.
     * @param display The display this algorithm is displaying to
     */
    private void iterateBoth(ArrayDisplay display) {
        // If we have reached the end of the unsorted things
        if (curIndex >= list.size() - sorted) {

            int maxDestination = list.size() - 1 - sorted;
            int minDestination = sorted;

            if (maxDestination == minIndex && minDestination == maxIndex) {
                swap(minIndex, maxIndex);
                display.writeIndex(minIndex);
                display.writeIndex(maxIndex);
            } else if (maxDestination == minIndex) {
                swap(minIndex, minDestination);
                swap(maxIndex, maxDestination);
                display.writeIndex(minIndex);
                display.writeIndex(maxIndex);
                display.writeIndex(minDestination);
                display.writeIndex(maxDestination);
            } else {
                swap(maxIndex, maxDestination);
                swap(minIndex, minDestination);
                display.writeIndex(minIndex);
                display.writeIndex(maxIndex);
                display.writeIndex(minDestination);
                display.writeIndex(maxDestination);
            }

            sorted++;
            curIndex = sorted;
            maxIndex = curIndex;
            minIndex = curIndex;

            // Sorted only increases by 1 for each pair of sorted items, so we have to double it to check properly
            if (sorted * 2 >= list.size()) {
                isDone = true;
            }
            return;
        }

        display.readIndex(minIndex);
        display.readIndex(maxIndex);
        display.readIndex(curIndex);

        if (list.get(curIndex) < list.get(minIndex)) {
            minIndex = curIndex;
        }

        if (list.get(curIndex) > list.get(maxIndex)) {
            maxIndex = curIndex;
        }

        curIndex++;
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
    public void startAnimated(AnimatedArrayDisplay display) {
        curIndex++;

        arrow = ItemBuilder.defaultArrow(display, curIndex);
        display.addItem(arrow);

        display.getDetailedInfo().updateInfo("Sorted", 0);
        display.getDetailedInfo().updateInfo("Current index", curIndex);
        display.getDetailedInfo().updateInfo("Current value",  list.get(curIndex));


        if (mode != SelectionMode.MAX) {
            minArrow = new ItemBuilder(display)
                    .with(PolygonWrapper.triangle(display, Color.LIGHTGREEN))
                    .at(minIndex, 0)
                    .build();
            display.addItem(minArrow);

            display.getDetailedInfo().updateInfo("Min index", minIndex);
            display.getDetailedInfo().updateInfo("Min value", list.get(minIndex));
        }

        if (mode != SelectionMode.MIN) {
            maxArrow = new ItemBuilder(display)
                    .with(PolygonWrapper.triangle(display, Color.LIGHTGREEN))
                    .at(maxIndex, 0)
                    .build();
            display.addItem(maxArrow);

            display.getDetailedInfo().updateInfo("Max index", maxIndex);
            display.getDetailedInfo().updateInfo("Max value", list.get(maxIndex));
        }
    }

    @Override
    public void iterateAnimated(AnimatedArrayDisplay display) {
        switch (mode) {
            case MIN -> minDetailed(display);
            case MAX -> maxDetailed(display);
            case BOTH -> bothDetailed(display);
        }
    }

    /**
     * Does the same thing as {@link #iterateMin(ArrayDisplay)}, except it also adds animations onto the provided detailed display.
     * @param display The detailed display
     */
    private void minDetailed(AnimatedArrayDisplay display) {
        // If we are at the end of the array
        if (curIndex >= list.size()) {
            System.out.println("resetting");

            // Move the smallest to the start
            swap(minIndex, sorted);
            display.swap(minIndex, sorted);
            display.getElementAnimationGroup().addOnPlay(() -> {
                display.setCurrentTask("Swapping smallest");
                display.getDetailedInfo().updateInfo("Sorted", sorted);
            });

            // Reset for finding the next smallest
            sorted++;
            curIndex = sorted;
            minIndex = curIndex;
            curIndex++;

            if (sorted >= list.size()) isDone = true;
            return;
        }

        int finalCur = curIndex;
        display.setCurrentTask("Searching for smallest");
        arrow.moveToIndex(curIndex, 0);
        minArrow.moveToIndex(minIndex, 0);
        display.onPlay(() -> {
            display.getDetailedInfo().updateInfo("Current index", finalCur);
            display.getDetailedInfo().updateInfo("Current value", list.get(finalCur));
        });
        display.newGroup();
        display.comparing(curIndex, minIndex);

        // If the current one is smaller, set it to the min
        if (list.get(curIndex) < list.get(minIndex)) {
            minIndex = curIndex;
            display.newGroup();
            minArrow.moveToIndex(minIndex, 0);
            display.onPlay(() -> {
                display.getDetailedInfo().updateInfo("Min index", Integer.toString(minIndex));
                display.getDetailedInfo().updateInfo("Min value", Integer.toString(list.get(minIndex)));
            });
        }

        // Keep moving through the array
        curIndex++;
    }

    /**
     * Does the same thing as {@link #iterateMax(ArrayDisplay)}, except it also adds animations onto the provided detailed display.
     * @param display The detailed display
     */
    private void maxDetailed(AnimatedArrayDisplay display) {
        // If we are at the end of the array
        if (curIndex >= list.size() - sorted) {
            // Move the smallest to the start
            swap(maxIndex, list.size() - 1 - sorted);
            display.swap(maxIndex, list.size() - 1 - sorted);
            display.getElementAnimationGroup().addOnPlay(() -> display.setCurrentTask("Swapping biggest"));

            // Reset for finding the next smallest
            sorted++;
            curIndex = 0;
            maxIndex = curIndex;
            curIndex++;

            if (sorted >= list.size()) {
                isDone = true;
            }

            return;
        }

        display.setCurrentTask("Searching for biggest");
        arrow.moveToIndex(curIndex, 0);
        maxArrow.moveToIndex(maxIndex, 0);
        display.newGroup();
        display.comparing(curIndex, maxIndex);

        // If the current one is smaller, set it to the min
        if (list.get(curIndex) > list.get(maxIndex)) {
            maxIndex = curIndex;
        }

        // Keep moving through the array
        curIndex++;
    }

    /**
     * Does the same thing as {@link #iterateBoth(ArrayDisplay)}, except it also adds animations onto the provided detailed display.
     * @param display The detailed display
     */
    private void bothDetailed(AnimatedArrayDisplay display) {
        // If we have reached the end of the unsorted things
        if (curIndex >= list.size() - sorted) {

            int maxDestination = list.size() - 1 - sorted;
            int minDestination = sorted;

            // TODO: Maybe make a way to swap these in sequence (it's a bit hard to understand what's happening)
            if (maxDestination == minIndex && minDestination == maxIndex) {
                swap(minIndex, maxIndex);
                display.swap(minIndex, maxIndex);
            } else if (maxDestination == minIndex) {
                swap(minIndex, minDestination);
                swap(maxIndex, maxDestination);
                display.swap(minIndex, minDestination);
                display.swap(maxIndex, maxDestination);
            } else {
                swap(maxIndex, maxDestination);
                swap(minIndex, minDestination);
                display.swap(maxIndex, maxDestination);
                display.swap(minIndex, minDestination);
            }
            display.getElementAnimationGroup().addOnPlay(() -> display.setCurrentTask("Moving smallest and biggest to edges"));

            sorted++;
            curIndex = sorted;
            maxIndex = curIndex;
            minIndex = curIndex;
            curIndex++;

            // Sorted only increases by 1 for each pair of sorted items, so we have to double it to check properly
            if (sorted * 2 >= list.size()) {
                isDone = true;
            }
            return;
        }

        arrow.moveToIndex(curIndex, 0);
        minArrow.moveToIndex(minIndex, 0);
        maxArrow.moveToIndex(maxIndex, 0);
        display.newGroup();
        display.comparing(curIndex, minIndex);

        if (list.get(curIndex) < list.get(minIndex)) {
            minIndex = curIndex;
            display.newGroup();
            minArrow.moveToIndex(minIndex, 0);
        }

        display.newGroup();
        display.comparing(curIndex, maxIndex);

        if (list.get(curIndex) > list.get(maxIndex)) {
            maxIndex = curIndex;
            display.newGroup();
            maxArrow.moveToIndex(maxIndex, 0);
        }

        curIndex++;
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
                (l, b) -> new SelectionSort(l, b, selectionSetting.getValue()),
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
