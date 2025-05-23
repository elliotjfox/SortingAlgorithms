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
    private int gapSize;

    private AnimatedItem arrow;

    public ShellSort(List<Integer> arrayList, boolean isInstant, double shrinkFactor, ShellSortMode mode) {
        super(arrayList, isInstant);

        this.shrinkFactor = shrinkFactor;
        gapSize = arrayList.size();

        if (!isInstant) {
            switch (mode) {
                case ONE_BY_ONE -> addOneByOneActions();
                case OFFSET_FIRST -> addOffsetFirstActions();
            }
        }
    }

    private void addOneByOneActions() {
        do {
            shrinkGap();
            // For each element, sort it
            for (int i = gapSize; i < list.size(); i++) {
                addToStart(new SearchFrom(i, gapSize));
            }
        } while (gapSize > 1);
        catchUpActions();
    }

    private void addOffsetFirstActions() {
        do {
            shrinkGap();
            addToStart(new AddSearches(gapSize));
        } while (gapSize > 1);
        catchUpActions();
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {
        gapSize = list.size();
        do {
            shrinkGap();
            // For each up to the gap size (make sure each element is looked at)
            for (int i = 0; i < gapSize; i++) {
                for (int j = i; j < list.size(); j += gapSize) {
                    searchFrom(entry, j);
                }
            }
        } while (gapSize > 1);
    }

    private void searchFrom(TestEntry entry, int i) {
        int left = i - gapSize;
        if (left < 0) return;
        entry.addRead(2);
        if (list.get(left) > list.get(i)) {
            entry.addWrite(2);
            swap(left, i);
            searchFrom(entry, left);
        }
    }

    @Override
    public void startAnimated(AnimatedArrayDisplay display) {
        arrow = ItemBuilder.defaultArrow(display, 0);
        display.addItem(arrow);

        display.updateInfo("Gap Size", (int) (list.size() / shrinkFactor));
        display.updateInfo("Offset", 0);
    }

    @Override
    public String getName() {
        return "Shell Sort\nShrink Factor: " + shrinkFactor;
    }

    public void shrinkGap() {
        gapSize = Math.max(1, (int) (gapSize / shrinkFactor));
    }

    private static class AddSearches extends AlgorithmAction {
        protected final int gap;

        public AddSearches(int gap) {
            this.gap = gap;
            takesStep = false;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            if (!(algorithm instanceof  ShellSort shellSort)) {
                return;
            }
            for (int i = 0; i < gap; i++) {
                for (int j = i; j < shellSort.list.size(); j += gap) {
                    algorithm.addToStart(
                            new SearchFrom(j, gap)
                    );
                }
            }
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
            if (!(algorithm instanceof  ShellSort shellSort)) {
                return;
            }
            for (int i = 0; i < gap; i++) {
                int finalI = i;
                shellSort.addToStart(
                        new AnimationAction(display.highlightAnimation(pos -> (pos - finalI) % gap == 0)),
                        new LaterAction(() -> {
                            display.updateInfoOnPlay("Current task", finalI == 0 ? "Shrinking gap size" : "Increasing offset");
                            display.updateInfoOnPlay("Gap Size", gap);
                        })
                );
                for (int j = i; j < shellSort.list.size(); j += gap) {
                    int finalJ = j;
                    shellSort.addToStart(
                            new AnimationAction(shellSort.arrow.moveToIndexTimeline(j, 0)),
                            new LaterAction(() -> {
                                display.updateInfoOnPlay("Offset", finalI);
                                display.updateInfoWhenDone("Current task", "Searching from " + finalJ);
                            }, true),
                            new SearchFrom(j, gap)
                    );
                }
            }
        }
    }

    private static class SearchFrom extends AlgorithmAction {

        private final int from;
        protected final int gapSize;

        public SearchFrom(int from, int gapSize) {
            this.from = from;
            this.gapSize = gapSize;
            takesStep = false;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            if (from - gapSize < 0) return;

            if (algorithm.list.get(from - gapSize) > algorithm.list.get(from)) {
                display.writeIndex(from, from - gapSize);
                algorithm.addToStart(
                        new Swap(from - gapSize, from),
                        new SearchFrom(from - gapSize, gapSize)
                );
            } else {
                display.readIndex(from, from - gapSize);
            }
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
            if (from - gapSize < 0) {
                return;
            }

            // TODO: Still need to fix this up a bit
            algorithm.addToStart(
                    new AnimationAction(
                            ((ShellSort) algorithm).arrow.moveToIndexTimeline(from, 0),
                            display.highlightAnimation(i -> Math.abs(i - from) % gapSize == 0)
                    ),
                    new Wait(),
                    new LaterAction(() -> display.reading(from - gapSize, from))
            );

            if (algorithm.list.get(from - gapSize) > algorithm.list.get(from)) {
                algorithm.addToStart(
                        new Swap(from - gapSize, from),
                        new SearchFrom(from - gapSize, gapSize)
                );
            }

//            if (from - gapSize < 0) return;
//
             // I think this is for ALL_K mode
//            algorithm.addToStart(
//                    new AnimationAction(
//                            ((ShellSortV2) algorithm).arrow.moveToIndexTimeline(from, 0),
//                            display.highlightAnimation(i -> Math.abs(i - from) % gapSize == 0)
//                    ),
//                    new LaterAction(() -> display.reading(from - gapSize, from), true)
//            );
//            if (algorithm.getArray().get(from - gapSize) > algorithm.getArray().get(from)) {
//                algorithm.addToStart(
//                        new Swap(from - gapSize, from),
//                        new ShellSortV2.SearchFrom(gapSize, from - gapSize)
//                );
//            }
        }
    }

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
