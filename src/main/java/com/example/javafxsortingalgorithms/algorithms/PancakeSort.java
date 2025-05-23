package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.*;
import com.example.javafxsortingalgorithms.animation.AnimatedArrayDisplay;
import com.example.javafxsortingalgorithms.animation.AnimatedItem;
import com.example.javafxsortingalgorithms.animation.ItemBuilder;
import com.example.javafxsortingalgorithms.arraydisplay.*;

import java.util.List;

public class PancakeSort extends ActionSortingAlgorithm {

    private int count;
    private final boolean instantFlips;
    private AnimatedItem arrow;
    private AnimatedItem smallestArrow;

    public PancakeSort(List<Integer> arrayList, boolean isInstant, boolean instantFlips) {
        super(arrayList, isInstant);

        this.instantFlips = instantFlips;
        count = 0;

        if (!isInstant) {
            for (int i = 0; i < arrayList.size() - 1; i++) {
                addToStart(new FindSmallest(i));
            }
        }
        catchUpActions();
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {
        for (int i = 0; i < list.size() - 1; i++) {
            // Find smallest
            int smallest = i;
            for (int j = i + 1; j < list.size(); j++) {
                entry.addRead(2);
                if (list.get(j) < list.get(smallest)) {
                    smallest = j;
                }
            }
            flip(smallest, list.size() - 1, entry);
            flip(i, list.size() - 1, entry);
        }
    }

    private void flip(int from, int to, TestEntry testEntry) {
        for (int i = 0; i <= (to - from) / 2; i++) {
            swap(from + i, to - i);
            testEntry.addWrite(2);
        }
    }

    @Override
    public void startAnimated(AnimatedArrayDisplay display) {
        arrow = ItemBuilder.defaultArrow(display, 0);
        display.addItem(arrow);

        smallestArrow = ItemBuilder.defaultArrow(display, 0);
        display.addItem(smallestArrow);
    }

    @Override
    public String getName() {
        return "Pancake Sort";
    }

    private static class FindSmallest extends AlgorithmAction {

        private int from;
        private int smallestIndex;

        public FindSmallest(int from) {
            this.from = from;
            this.smallestIndex = from;

            takesStep = false;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            PancakeSort pancakeSort = (PancakeSort) algorithm;

            for (int i = from + 1; i < algorithm.getList().size(); i++) {
                int finalI = i;
                algorithm.addToStart(
                        new LaterAction(() -> {
                            display.readIndex(finalI, smallestIndex);
                            if (algorithm.getList().get(finalI) < algorithm.getList().get(smallestIndex)) {
                                smallestIndex = finalI;
                            }
                        }, true)
                );
            }

            algorithm.addToStart(
                    new LaterAction(() -> {
                        algorithm.addToStart(
                            new Flip(smallestIndex, algorithm.getList().size() - 1, !pancakeSort.instantFlips),
                            new Flip(pancakeSort.count, algorithm.getList().size() - 1, !pancakeSort.instantFlips)
                        );
                        pancakeSort.count++;
                    }, false)
            );
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
            PancakeSort pancakeSort = (PancakeSort) algorithm;

            display.setCurrentTask("Finding smallest");
            for (int i = from + 1; i < algorithm.getList().size(); i++) {
                display.comparing(i, smallestIndex);
                if (algorithm.getList().get(i) < algorithm.getList().get(smallestIndex)) {
                    smallestIndex = i;
                    display.newGroup();
                    pancakeSort.smallestArrow.moveToIndex(smallestIndex, 0);
                }
                display.newGroup();
            }

            algorithm.addToStart(
                    new LaterAction(() -> display.onPlay(() -> display.setCurrentTask("Flipping to top"))),
                    new Flip(smallestIndex, algorithm.getList().size() - 1, false),
                    new Wait(),
                    new LaterAction(() -> display.onPlay(() -> display.setCurrentTask("Flipping to position"))),
                    new Flip(pancakeSort.count, algorithm.getList().size() - 1, false),
                    new Wait()
            );
            pancakeSort.count++;
            algorithm.addToStart(
                    new AnimationAction(
                            pancakeSort.arrow.moveToIndexTimeline(pancakeSort.count, 0),
                            pancakeSort.smallestArrow.moveToIndexTimeline(pancakeSort.count, 0)
                    ),
                    new Wait()
            );
        }
    }

    public static AlgorithmSettings<PancakeSort> getSettings() {
        AlgorithnSettingsCheckBox instantFlipsSetting = new AlgorithnSettingsCheckBox("Instant Flip", false);

        return new AlgorithmSettings<>(
                "Pancake Sort",
                (l, b) -> new PancakeSort(l, b, instantFlipsSetting.getValue()),
                instantFlipsSetting
        );
    }

//    void pancakeSort() {
//        for (int i = 0; i < list.size() - 1; i++) {
//            int min = i;
//            for (int j = i + 1; j < list.size(); j++) {
//                if (list.get(j) < list.get(min)) {
//                    min = j;
//                }
//            }
//            flip(min);
//            flip(i);
//        }
//    }
//
//    void flip(int lower) {
//        int upper = list.size() - 1;
//        for (int i = 0; i <= (upper - lower) / 2; i++) {
//            swap(lower + i, upper - i);
//        }
//    }
}
