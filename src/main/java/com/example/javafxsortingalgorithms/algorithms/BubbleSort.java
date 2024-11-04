package com.example.javafxsortingalgorithms.algorithms;

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

    private DetailedArrow leftArrow;
    private DetailedArrow rightArrow;

    public BubbleSort(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);

        sorted = 0;
        lastPos = 0;
        hasMadeSwap = false;
    }

    @Override
    protected void runAlgorithm(ArrayDisplay display) {
        if (sorted + 1 >= list.size()) {
            isDone = true;
            return;
        }

        // Check if the next position would be outside the array, and reset if we need to. This does use a step.
        if (lastPos + 1 >= list.size() - sorted) {
            sorted++;
            lastPos = 0;
            if (!hasMadeSwap) {
                isDone = true;
                return;
            }
            hasMadeSwap = false;
            return;
        }

        // Check if we need to swap the elements
        if (list.get(lastPos) > list.get(lastPos + 1)) {
            hasMadeSwap = true;
            swap(lastPos, lastPos + 1);
            display.writeIndex(lastPos);
            display.writeIndex(lastPos + 1);
        } else {
            display.readIndex(lastPos);
            display.readIndex(lastPos + 1);
        }

        // Increase the position
        lastPos++;
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
    public void startDetailed(ArrayDetailedDisplay display) {
        // TODO: Make this linked to the settings (element width)
        leftArrow = new DetailedArrow(25, true);
        display.addItem(leftArrow, 0, 0);

        rightArrow = new DetailedArrow(25, true);
        display.addItem(rightArrow, 1, 0);

        display.updateInfo("Number sorted", 0);
        display.updateInfo("Left index", 0);
        display.updateInfo("Left value", list.get(0));
        display.updateInfo("Right index", 1);
        display.updateInfo("Right value", list.get(1));
    }

    @Override
    public void iterateDetailed(ArrayDetailedDisplay display) {
        // Check if the next position would be outside the array, and reset if we need to.
        if (lastPos + 1 >= list.size() - sorted) {
            sorted++;
            lastPos = 0;

            if (!hasMadeSwap) {
                isDone = true;
                return;
            }
            hasMadeSwap = false;

            display.moveItem(leftArrow, lastPos, 0);
            display.moveItem(rightArrow, lastPos + 1, 0);
            // TODO: Surely I don't need the same block of code in three different places
            display.updateInfoWhenDone("Number sorted", sorted);
            display.updateInfoWhenDone("Left index", lastPos);
            display.updateInfoWhenDone("Left value", list.get(lastPos));
            display.updateInfoWhenDone("Right index", lastPos + 1);
            display.updateInfoWhenDone("Right value", list.get(lastPos + 1));
        }

        display.moveItem(leftArrow, lastPos, 0);
        display.moveItem(rightArrow, lastPos + 1, 0);
        display.updateInfoWhenDone("Number sorted", sorted);
        display.updateInfoWhenDone("Left index", lastPos);
        display.updateInfoWhenDone("Left value", list.get(lastPos));
        display.updateInfoWhenDone("Right index", lastPos + 1);
        display.updateInfoWhenDone("Right value", list.get(lastPos + 1));
        display.newGroup();
        display.comparing(lastPos, lastPos + 1);

        if (list.get(lastPos) > list.get(lastPos + 1)) {
            hasMadeSwap = true;
            swap(lastPos, lastPos + 1);
            display.swap(lastPos, lastPos + 1);
            display.updateInfoWhenDone("Number sorted", sorted);
            display.updateInfoWhenDone("Left index", lastPos);
            display.updateInfoWhenDone("Left value", list.get(lastPos));
            display.updateInfoWhenDone("Right index", lastPos + 1);
            display.updateInfoWhenDone("Right value", list.get(lastPos + 1));
        }

        // Increase the position
        lastPos++;
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
