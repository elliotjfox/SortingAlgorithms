package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;

import java.util.List;

/**
 * <h3>Cocktail Shaker Sort</h3> <br>
 * Checks each pair of elements and swaps them if they are out of order. When it reaches the end, it turns around and continues
 * back to the start, mimicking a shaking motion.
 * <br> <br>
 * <h4>Time Complexity</h4> <br>
 * Best: n <br>
 * Average: n<sup>2</sup> <br>
 * Worst: n<sup>2</sup>
 */
public class CocktailShakerSort extends SortingAlgorithm {

    private int lastPos;
    private boolean goingUp;
    private int sortedLeft;
    private int sortedRight;
    private boolean hasMadeSwap;

    public CocktailShakerSort(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);

        lastPos = 0;
        goingUp = true;
        sortedLeft = 0;
        sortedRight = 0;
        hasMadeSwap = false;
    }

    @Override
    protected void runAlgorithm() {
        int start = 0;
        int end = list.size() - 1;
        while (true) {
            boolean swapped = false;
            for (int i = start; i < end; i++) {
                if (list.get(i) > list.get(i + 1)) {
                    swap(i, i + 1);
                    swapped = true;
                }
                addFrame();
            }

            // If we didn't swap anything, we are done sorting
            if (!swapped) break;

            end--;

            swapped = false;
            for (int i = end - 1; i >= start; i--) {
                if (list.get(i) > list.get(i + 1)) {
                    swap(i, i + 1);
                    swapped = true;
                }
                addFrame();
            }

            if (!swapped) break;

            start++;
        }
    }

    @Override
    protected void runAlgorithm(ArrayDisplay display) {
        // Check if we are at the edge, and turn around if needed. If we do, it uses a step to take so.
        if (goingUp) {
            // We have reached the right end
            if (lastPos + 1 >= list.size() - sortedRight) {
                sortedRight++;
                goingUp = false;
                if (!hasMadeSwap) {
                    isDone = true;
                    return;
                }
                hasMadeSwap = false;
                return;
            }
        } else {
            // We have reached the left end
            if (lastPos - 1 < sortedLeft) {
                sortedLeft++;
                goingUp = true;
                if (!hasMadeSwap) {
                    isDone = true;
                    return;
                }
                hasMadeSwap = false;
                return;
            }
        }

        // Check if we need to swap the two elements we are comparing, then move in the correct direction. Colour the elements we looked at
        if (goingUp) {
            if (list.get(lastPos) > list.get(lastPos + 1)) {
                swap(lastPos, lastPos + 1);
                hasMadeSwap = true;
                display.writeIndex(lastPos);
                display.writeIndex(lastPos + 1);
            } else {
                display.readIndex(lastPos);
                display.readIndex(lastPos + 1);
            }
            lastPos++;
        } else {
            if (list.get(lastPos) < list.get(lastPos - 1)) {
                swap(lastPos, lastPos - 1);
                hasMadeSwap = true;
                display.writeIndex(lastPos);
                display.writeIndex(lastPos - 1);
            } else {
                display.readIndex(lastPos);
                display.readIndex(lastPos - 1);
            }
            lastPos--;
        }
    }

    // TODO: Figure out how to calculate percentage
    @Override
    protected void instantAlgorithm(TestEntry entry) {
        int start = 0;
        int end = list.size() - 1;
        while (true) {
            boolean swapped = false;
            for (int i = start; i < end; i++) {
                entry.addRead();
                if (list.get(i) > list.get(i + 1)) {
                    entry.addWrite();
                    swap(i, i + 1);
                    swapped = true;
                }
            }

            // If we didn't swap anything, we are done sorting
            if (!swapped) break;

            end--;

            swapped = false;
            for (int i = end - 1; i >= start; i--) {
                entry.addRead();
                if (list.get(i) > list.get(i + 1)) {
                    entry.addWrite();
                    swap(i, i + 1);
                    swapped = true;
                }
            }

            if (!swapped) break;

            start++;
        }
        entry.done();
    }

    @Override
    public String getName() {
        return "Cocktail Shaker Sort";
    }



//    void cocktailShakerSort() {
//        int start = 0;
//        int end = list.size() - 1;
//        while (true) {
//            boolean swapped = false;
//            for (int i = start; i < end; i++) {
//                if (list.get(i) > list.get(i + 1)) {
//                    swap(i, i + 1);
//                    swapped = true;
//                }
//            }
//
//            if (!swapped) break;
//            end--;
//            swapped = false;
//
//            for (int i = end - 1; i >= start; i--) {
//                if (list.get(i) > list.get(i + 1)) {
//                    swap(i, i + 1);
//                    swapped = true;
//                }
//            }
//
//            if (!swapped) break;
//            start++;
//        }
//    }
}
