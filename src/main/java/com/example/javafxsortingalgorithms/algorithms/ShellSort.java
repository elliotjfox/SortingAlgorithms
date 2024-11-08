package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDetailedDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;

import java.util.List;

public class ShellSort extends ActionSortingAlgorithm {

    private final double shrinkFactor;
    private int gapSize;

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
    public void startDetailed(ArrayDetailedDisplay display) {}

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
        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
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
        public void performDetailed(ActionSortingAlgorithm algorithm, ArrayDetailedDisplay display) {
            if (!(algorithm instanceof  ShellSort shellSort)) {
                return;
            }
            for (int i = 0; i < gap; i++) {
                int finalI = i;
                for (int j = i; j < shellSort.list.size(); j += gap) {
                    algorithm.addToStart(
                            new LaterAction(() -> display.highlightElements(pos -> (pos - finalI) % gap == 0)),
                            new ShellSearch(j, gap)
                    );
                }
            }
        }
    }

    private static abstract class ShellSearchAction extends AlgorithmAction {

        protected final int index;
        protected final int gapSize;

        public ShellSearchAction(int index, int gapSize) {
            this.index = index;
            this.gapSize = gapSize;
        }

        @Override
        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            if (algorithm instanceof ShellSort) {
                search((ShellSort) algorithm, display);
            }
        }

        @Override
        public void performDetailed(ActionSortingAlgorithm algorithm, ArrayDetailedDisplay display) {
            if (algorithm instanceof ShellSort) {
                searchDetailed((ShellSort) algorithm, display);
            }
        }

        protected abstract void search(ShellSort shellSort, ArrayDisplay display);

        protected abstract void searchDetailed(ShellSort shellSort, ArrayDetailedDisplay display);
    }

    private static class ShellSearch extends ShellSearchAction {

        public ShellSearch(int index, int gapSize) {
            super(index, gapSize);
        }

        @Override
        protected void search(ShellSort shellSort, ArrayDisplay display) {
            if (index - gapSize < 0) {
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
        protected void searchDetailed(ShellSort shellSort, ArrayDetailedDisplay display) {
            if (index - gapSize < 0) {
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

//    protected void shellSort() {
//        do {
//            shrinkGap();
//            // For each up to the gap size (make sure each element is looked at)
//            for (int i = 0; i < gapSize; i++) {
//                for (int farRight = i; farRight < list.size(); farRight += gapSize) {
//                    searchFrom(farRight);
//                }
//            }
//        } while (gapSize > 1);
//    }
//
//    private void searchFrom(int i) {
//        int left = i - gapSize;
//        if (left < 0) return;
//        if (list.get(left) > list.get(i)) {
//            swap(left, i);
//            searchFrom(left);
//        }
//    }
}
