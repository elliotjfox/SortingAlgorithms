package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayAnimatedDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.AnimatedArrow;

import java.util.List;

public class ShellSort extends ActionSortingAlgorithm {

    private final double shrinkFactor;
    private int gapSize;

    private AnimatedArrow arrow;

    public ShellSort(List<Integer> arrayList, boolean isInstant, double shrinkFactor) {
        super(arrayList, isInstant);

        this.shrinkFactor = shrinkFactor;
        gapSize = arrayList.size();

        if (!isInstant) {
            do {
                shrinkGap();
                actions.add(new AddSearches(gapSize));
            } while (gapSize > 1);
        }
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
    public void startAnimated(ArrayAnimatedDisplay display) {
        arrow = new AnimatedArrow(display, true);
        display.addItem(arrow, 0, 0);

        display.updateInfo("Gap Size", (int) (list.size() / shrinkFactor));
        display.updateInfo("Offset", 0);
    }

    @Override
    public String getName() {
        return STR."Shell Sort\nShrink Factor: \{shrinkFactor}";
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
                            new ShellSearch(j, gap)
                    );
                }
            }
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, ArrayAnimatedDisplay display) {
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
                                display.updateInfoWhenDone("Current task", STR."Searching from \{finalJ}");
                            }, true),
                            new ShellSearch(j, gap)
                    );
                }
            }
        }
    }

    private static class ShellSearch extends AlgorithmAction {

        private final int index;
        protected final int gapSize;

        public ShellSearch(int index, int gapSize) {
            this.index = index;
            this.gapSize = gapSize;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            if (!(algorithm instanceof ShellSort shellSort) || index - gapSize < 0) {
                return;
            }

            if (shellSort.list.get(index - gapSize) > shellSort.list.get(index)) {
                display.writeIndex(index - gapSize);
                display.writeIndex(index);
                shellSort.swap(index - gapSize, index);
                shellSort.addToStart(
                        new ShellSearch(index - gapSize, gapSize)
                );
            } else {
                display.readIndex(index - gapSize);
                display.readIndex(index);
            }
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, ArrayAnimatedDisplay display) {
            if (!(algorithm instanceof ShellSort shellSort) || index - gapSize < 0) {
                return;
            }

            display.comparing(index, index - gapSize);
            if (shellSort.list.get(index - gapSize) > shellSort.list.get(index)) {
                shellSort.swap(index - gapSize, index);
                display.swap(index - gapSize, index);
                shellSort.addToStart(
                        new ShellSearch(index - gapSize, gapSize)
                );
            }
        }
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
