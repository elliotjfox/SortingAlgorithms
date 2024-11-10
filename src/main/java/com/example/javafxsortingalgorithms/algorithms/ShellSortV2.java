package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDetailedDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.DetailedArrow;

import java.util.List;

public class ShellSortV2 extends ActionSortingAlgorithm {

    private final double shrinkFactor;
    private int gapSize;

    private DetailedArrow arrow;

    public ShellSortV2(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);


        this.shrinkFactor = 2.3;
        gapSize = arrayList.size();

        if (!isInstant) {
            do {
                shrinkGap();
                for (int i = gapSize; i < list.size(); i++) {
                    actions.add(new SearchFrom(gapSize, i));
                }
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
    public void startDetailed(ArrayDetailedDisplay display) {
        arrow = new DetailedArrow(display, true);
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

    private static class SearchFrom extends AlgorithmAction {

        private final int gapSize;
        private final int from;

        public SearchFrom(int gapSize, int from) {
            this.gapSize = gapSize;
            this.from = from;
        }

        @Override
        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            if (from - gapSize < 0) return;
            if (algorithm.getArray().get(from - gapSize) > algorithm.getArray().get(from)) {
                algorithm.addToStart(
                        new Swap(from - gapSize, from),
                        new SearchFrom(gapSize, from - gapSize)
                );
            }
        }

        @Override
        public void performDetailed(ActionSortingAlgorithm algorithm, ArrayDetailedDisplay display) {
            if (from - gapSize < 0) return;

            algorithm.addToStart(
                    new AnimationAction(
                            ((ShellSortV2) algorithm).arrow.moveToIndexTimeline(from, 0),
                            display.highlightAnimation(i -> Math.abs(i - from) % gapSize == 0)
                    ),
                    new LaterAction(() -> display.reading(from - gapSize, from), true)
            );
            if (algorithm.getArray().get(from - gapSize) > algorithm.getArray().get(from)) {
                algorithm.addToStart(
                        new Swap(from - gapSize, from),
                        new SearchFrom(gapSize, from - gapSize)
                );
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
        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            if (!(algorithm instanceof ShellSortV2 shellSort) || index - gapSize < 0) {
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
        public void performDetailed(ActionSortingAlgorithm algorithm, ArrayDetailedDisplay display) {
            if (!(algorithm instanceof ShellSortV2 shellSort) || index - gapSize < 0) {
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
}
