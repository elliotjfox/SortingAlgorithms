package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.animation.AnimatedArrow;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledIndex;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledPosition;

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

    public CocktailShakerSort(List<Integer> arrayList) {
        super(arrayList);
    }

    @Override
    protected void runAlgorithm() {
        // Initialize animation items
        AnimatedArrow leftArrow = animation.createArrow();
        AnimatedArrow rightArrow = animation.createArrow();
        animation.setItemPosition(leftArrow, new ElementScaledPosition(0, 0));
        animation.setItemPosition(rightArrow, new ElementScaledPosition(1, 0));

        int start = 0;
        int end = list.size() - 1;
        while (true) {
            boolean swapped = false;
            for (int i = start; i < end; i++) {
                animation.changeItemX(leftArrow, new ElementScaledIndex(i));
                animation.changeItemX(rightArrow, new ElementScaledIndex(i + 1));
                animation.addFrame();
                animation.readIndex(i);
                animation.readIndex(i + 1);
                if (list.get(i) > list.get(i + 1)) {
                    animation.addFrame();
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
                animation.changeItemX(leftArrow, new ElementScaledIndex(i));
                animation.changeItemX(rightArrow, new ElementScaledIndex(i + 1));
                animation.addFrame();
                animation.readIndex(i);
                animation.readIndex(i + 1);
                if (list.get(i) > list.get(i + 1)) {
                    animation.addFrame();
                    swap(i, i + 1);
                    swapped = true;
                }
                addFrame();
            }

            if (!swapped) break;

            start++;
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
