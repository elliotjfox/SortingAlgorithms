package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.animation.AnimatedArrow;
import com.example.javafxsortingalgorithms.animation.position.ScaledIndex;
import com.example.javafxsortingalgorithms.animation.position.ScaledPosition;

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

    public BubbleSort(List<Integer> list) {
        super(list);
    }

    protected void runAlgorithm() {
        boolean hasSwapped;

        // Initialize animation items
        AnimatedArrow leftPointer = animation.createArrow();
        AnimatedArrow rightPointer = animation.createArrow();
        animation.setItemPosition(leftPointer, new ScaledPosition(0, 0));
        animation.setItemPosition(rightPointer, new ScaledPosition(1, 0));

        for (int i = 0; i < list.size(); i++) {
            hasSwapped = false;
            for (int j = 0; j < list.size() - 1 - i; j++) {
                // Move animation items
                animation.changeItemX(leftPointer, new ScaledIndex(j));
                animation.changeItemX(rightPointer, new ScaledIndex(j + 1));
                animation.addFrame();
                animation.readIndex(j);
                animation.readIndex(j + 1);

                if (list.get(j) > list.get(j + 1)) {
                    // If we are going to swap, make an animated frame right before
                    animation.addFrame();
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
