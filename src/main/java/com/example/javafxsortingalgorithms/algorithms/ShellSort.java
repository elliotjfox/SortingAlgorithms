package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsComboBox;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsInputBox;
import com.example.javafxsortingalgorithms.animation.AnimatedArrayDisplay;
import com.example.javafxsortingalgorithms.animation.AnimatedItem;
import com.example.javafxsortingalgorithms.animation.ItemBuilder;
import com.example.javafxsortingalgorithms.arraydisplay.*;

import java.util.List;

public class ShellSort extends ActionSortingAlgorithm {

    public enum ShellSortMode {
        // This is sorting each element sequentially
        ONE_BY_ONE,
        // This is sorting all the ones that are k apart at once, then the ones 1 above k, etc.
        OFFSET_FIRST
    }

    private final double shrinkFactor;
    private final ShellSortMode mode;

    private AnimatedItem arrow;

    public ShellSort(List<Integer> list, boolean isInstant, double shrinkFactor, ShellSortMode mode) {
        super(list, isInstant);

        this.shrinkFactor = shrinkFactor;
        this.mode = mode;

//        if (!isInstant) {
//            switch (mode) {
//                case ONE_BY_ONE -> addOneByOneActions();
//                case OFFSET_FIRST -> addOffsetFirstActions();
//            }
//        }
    }

    // TODO: Add animations
    @Override
    protected void runAlgorithm() {
        switch (mode) {
            case ONE_BY_ONE -> runOneByOne();
            case OFFSET_FIRST -> runOffset();
        }
    }

    private void runOneByOne() {
        int gapSize = list.size();
        do {
            gapSize = shrinkGap(gapSize);
            for (int i = gapSize; i < list.size(); i++) {
                int j = i - gapSize;
                while (j >= 0 && list.get(j) > list.get(j + gapSize)) {
                    swap(j, j + gapSize);
                    j -= gapSize;
                    addFrame();
                }
            }
        } while (gapSize > 1);
    }

    private void runOffset() {
        int gapSize = list.size();
        do {
            gapSize = shrinkGap(gapSize);
            for (int offset = 0; offset < gapSize; offset++) {
                for (int i = offset + gapSize; i < list.size(); i += gapSize) {
                    int j = i - gapSize;
                    while (j >= 0 && list.get(j) > list.get(j + gapSize)) {
                        swap(j, j + gapSize);
                        j -= gapSize;
                        addFrame();
                    }

                }
            }
        } while (gapSize > 1);

    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {
        int gapSize = list.size();
        do {
            gapSize = shrinkGap(gapSize);
            // For each up to the gap size (make sure each element is looked at)
            for (int i = 0; i < gapSize; i++) {
                for (int j = i; j < list.size(); j += gapSize) {
                    while (j >= 0 && list.get(j) > list.get(j + gapSize)) {
                        swap(j, j + gapSize);
                        j -= gapSize;
                    }
                }
            }
        } while (gapSize > 1);
    }

    @Override
    public String getName() {
        return "Shell Sort\nShrink Factor: " + shrinkFactor;
    }


    private int shrinkGap(int gap) {
        return Math.max(1, (int) (gap / shrinkFactor));
    }

//    private static class AddSearches extends AlgorithmAction {
//        protected final int gap;
//
//        public AddSearches(int gap) {
//            this.gap = gap;
//            takesStep = false;
//        }
//
//        @Override
//        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
//            if (!(algorithm instanceof  ShellSort shellSort)) {
//                return;
//            }
//            for (int i = 0; i < gap; i++) {
//                for (int j = i; j < shellSort.list.size(); j += gap) {
//                    algorithm.addToStart(
//                            new SearchFrom(j, gap)
//                    );
//                }
//            }
//        }
//
//        @Override
//        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
//            if (!(algorithm instanceof  ShellSort shellSort)) {
//                return;
//            }
//            for (int i = 0; i < gap; i++) {
//                int finalI = i;
//
//                // TODO: Fix this animation
//                display.highlight(pos -> (pos - finalI) % gap == 0);
//                if (i == 0) {
//                    display.onPlay(() -> {
//                        display.setCurrentTask("Shrinking gap size");
//                        display.updateInfo("Gap Size", gap);
//                    });
//                } else {
//                    display.onPlay(() -> {
//                        display.setCurrentTask("Increasing offset");
//                        display.updateInfo("Gap Size", gap);
//                    });
//                }
//                display.newGroup();
//
//                for (int j = i; j < shellSort.list.size(); j += gap) {
//                    int finalJ = j;
//                    shellSort.addToStart(
//                            new AnimationAction(shellSort.arrow.moveToIndexTimeline(j, 0)),
//                            new LaterAction(() -> {
//                                display.updateInfoOnPlay("Offset", finalI);
//                                display.updateInfoWhenDone("Current task", "Searching from " + finalJ);
//                            }, true),
//                            new SearchFrom(j, gap)
//                    );
//                }
//            }
//        }
//    }
//
//    private static class SearchFrom extends AlgorithmAction {
//
//        private final int from;
//        protected final int gapSize;
//
//        public SearchFrom(int from, int gapSize) {
//            this.from = from;
//            this.gapSize = gapSize;
//            takesStep = false;
//        }
//
//        @Override
//        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
//            if (from - gapSize < 0) return;
//
//            if (algorithm.list.get(from - gapSize) > algorithm.list.get(from)) {
//                display.writeIndex(from, from - gapSize);
//                algorithm.addToStart(
//                        new Swap(from - gapSize, from),
//                        new SearchFrom(from - gapSize, gapSize)
//                );
//            } else {
//                display.readIndex(from, from - gapSize);
//            }
//        }
//
//        @Override
//        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
//            if (from - gapSize < 0) {
//                return;
//            }
//
//            // TODO: Still need to fix this up a bit
//            algorithm.addToStart(
//                    new AnimationAction(
//                            ((ShellSort) algorithm).arrow.moveToIndexTimeline(from, 0),
//                            display.highlightTimeline(i -> Math.abs(i - from) % gapSize == 0)
//                    ),
//                    new Wait(),
//                    new LaterAction(() -> display.reading(from - gapSize, from))
//            );
//
//            if (algorithm.list.get(from - gapSize) > algorithm.list.get(from)) {
//                algorithm.addToStart(
//                        new Swap(from - gapSize, from),
//                        new SearchFrom(from - gapSize, gapSize)
//                );
//            }
//
////            if (from - gapSize < 0) return;
////
//             // I think this is for ALL_K mode
////            algorithm.addToStart(
////                    new AnimationAction(
////                            ((ShellSortV2) algorithm).arrow.moveToIndexTimeline(from, 0),
////                            display.highlightAnimation(i -> Math.abs(i - from) % gapSize == 0)
////                    ),
////                    new LaterAction(() -> display.reading(from - gapSize, from), true)
////            );
////            if (algorithm.getArray().get(from - gapSize) > algorithm.getArray().get(from)) {
////                algorithm.addToStart(
////                        new Swap(from - gapSize, from),
////                        new ShellSortV2.SearchFrom(gapSize, from - gapSize)
////                );
////            }
//        }
//    }

    public static AlgorithmSettings<ShellSort> getSettings() {
        AlgorithmSettingsInputBox<Double> shrinkFactorSetting = new AlgorithmSettingsInputBox<>(
                "Shrink Factor", 2.3,
                Double::parseDouble, d -> d > 1
        );
        AlgorithmSettingsComboBox<ShellSortMode> modeSetting = new AlgorithmSettingsComboBox<>(
                "Shell Sort Mode", ShellSortMode.values(), ShellSortMode.ONE_BY_ONE
        );

        return new AlgorithmSettings<>(
                "Shell Sort",
                (l, b) -> new ShellSort(l, b, shrinkFactorSetting.getValue(), modeSetting.getValue()),
                modeSetting,
                shrinkFactorSetting
        );
    }

//    void shellSort() {
//        do {
//            gapSize = Math.max(1, (int) (gapSize / shrinkFactor));
//            // For each up to the gap size (make sure each set of elements is looked at)
//            for (int i = 0; i < gapSize; i++) {
//                for (int farRight = i; farRight < list.size(); farRight += gapSize) {
//                    searchFrom(farRight);
//                }
//            }
//        } while (gapSize > 1);
//    }
//
//    void searchFrom(int i) {
//        int left = i - gapSize;
//        if (left < 0) return;
//        if (list.get(left) > list.get(i)) {
//            swap(left, i);
//            searchFrom(left);
//        }
//    }

}
