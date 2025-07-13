package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.animation.AnimatedArrayDisplay;
import com.example.javafxsortingalgorithms.animation.AnimatedItem;
import com.example.javafxsortingalgorithms.animation.ItemBuilder;
import com.example.javafxsortingalgorithms.arraydisplay.*;
import com.example.javafxsortingalgorithms.TestEntry;

import java.util.List;

/**
 * <h3>Bubble Sort</h3> <br>
 * Checks each pair of elements and swaps them if they are out of order. Once it reaches the end, it restarts from the beginning.
 * <br> <br>
 * <h4>Time Complexity</h4>
 * Best: n <br>
 * Average: n<sup>2</sup> <br>
 * Worst: n<sup>2</sup>
 */
public class BubbleSort extends SortingAlgorithm {

    private int sorted;
    private int lastPos;
    private boolean hasMadeSwap;

    private AnimatedItem leftArrow;
    private AnimatedItem rightArrow;

    public BubbleSort(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);

        sorted = 0;
        lastPos = 0;
        hasMadeSwap = false;
    }

    protected void runAlgorithm() {
        boolean hasSwapped;
        for (int i = 0; i < list.size(); i++) {
            hasSwapped = false;
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    swap(j, j + 1);
                    hasSwapped = true;
                }
                addFrame();
            }
            if (!hasSwapped) return;
        }
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {
        boolean hasSwapped;
        for (int i = 0; i < list.size(); i++) {
            hasSwapped = false;
            for (int j = 0; j < list.size() - 1 - i; j++) {
                entry.addRead(2);
                if (list.get(j) > list.get(j + 1)) {
                    entry.addWrite(2);
                    swap(j, j + 1);
                    hasSwapped = true;
                }
            }
            if (!hasSwapped) return;

            entry.updateProgress((double) i / list.size());
        }
    }

    @Override
    public void startAnimated(AnimatedArrayDisplay display) {
        leftArrow = ItemBuilder.defaultArrow(display, 0);
        display.addItem(leftArrow);

        rightArrow = ItemBuilder.defaultArrow(display, 1);
        display.addItem(rightArrow);

        display.updateInfo("Number sorted", 0);
        display.updateInfo("Left index", 0);
        display.updateInfo("Left value", list.get(0));
        display.updateInfo("Right index", 1);
        display.updateInfo("Right value", list.get(1));
    }

    @Override
    public void iterateAnimated(AnimatedArrayDisplay display) {
        // Check if the next position would be outside the array, and reset if we need to.
        if (lastPos + 1 >= list.size() - sorted) {
            sorted++;
            lastPos = 0;

            if (!hasMadeSwap) {
                isDone = true;
                return;
            }
            hasMadeSwap = false;

            leftArrow.moveToIndex(lastPos, 0);
            rightArrow.moveToIndex(lastPos + 1, 0);
            updateInfo(display);
        }

        leftArrow.moveToIndex(lastPos, 0);
        rightArrow.moveToIndex(lastPos + 1, 0);
        updateInfo(display);
        display.newGroup();
        display.comparing(lastPos, lastPos + 1);

        if (list.get(lastPos) > list.get(lastPos + 1)) {
            hasMadeSwap = true;
            swap(lastPos, lastPos + 1);
            display.swap(lastPos, lastPos + 1);
            updateInfo(display);
        }

        // Increase the position
        lastPos++;
    }

    private void updateInfo(AnimatedArrayDisplay display) {
        display.updateInfoWhenDone("Number sorted", sorted);
        display.updateInfoWhenDone("Left index", lastPos);
        display.updateInfoWhenDone("Left value", list.get(lastPos));
        display.updateInfoWhenDone("Right index", lastPos + 1);
        display.updateInfoWhenDone("Right value", list.get(lastPos + 1));
    }

    @Override
    public String getName() {
        return "Bubble Sort";
    }


//    void bubbleSort() {
//        boolean hasSwapped;
//        for (int i = 0; i < list.size(); i++) {
//            hasSwapped = false;
//            for (int j = 0; j < list.size() - 1 - i; j++) {
//                if (list.get(j) > list.get(j + 1)) {
//                    swap(j, j + 1);
//                    hasSwapped = true;
//                }
//            }
//            if (!hasSwapped) return;
//        }
//    }
}
