package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;

import java.util.List;

// TODO: Still some bugs
public class TimSort extends ActionSortingAlgorithm {

    private final int RUN_SIZE = 32;

    public TimSort(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);

        for (int i = 0; i < list.size(); i += RUN_SIZE) {
            int end = Math.min(i + RUN_SIZE, list.size());
            // TODO: Technically could be out of bounds
            int first = list.get(i);
            int second = list.get(i + 1);
            boolean ascending = first <= second;
            addToStart(new TimSortInsertion(ascending, i, end));
            if (!ascending) {
                addToStart(new Flip(i, end - 1));
            }
        }

        for (int size = RUN_SIZE; size < list.size(); size *= 2) {
            for (int left = 0; left < list.size(); left += 2 * size) {
                addToStart(new Merge(left, left + size, Math.min(left + 2 * size, list.size())));
            }
        }

        catchUpActions();
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {
        for (int i = 0; i < list.size(); i += RUN_SIZE) {
            insertionSort(i, Math.min(i + RUN_SIZE - 1, list.size() - 1));
        }

        // Keep combining blocks of the same size
        for (int size = RUN_SIZE; size < list.size(); size *= 2) {
            // Merge all blocks of the same size
            for (int left = 0; left < list.size(); left += 2 * size) {
                inPlaceMerge(left, left + size, left + 2 * size);
            }
        }
    }

    // TODO: Make this accept nicer inputs
    private void insertionSort(int left, int right) {
        int i = left + 1;
        while (i <= right) {
            int tmp = list.get(i);
            int j = i - 1;

            while (j >= left && list.get(j) > tmp) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, tmp);
            i++;
        }
    }

    @Override
    public String getName() {
        return "Tim Sort";
    }

    private int calculateMinrun() {
        for (int i = 32; i < 64; i++) {
            if (isPowerOfTwo(list.size() / i) && !isPowerOfTwo(list.size() / (i + 1))) {
                return i + 1;
            }
        }
        return 64;
    }

    private boolean isPowerOfTwo(int n) {
        return n == (int) (Math.pow(2, (int) (Math.log(n) / Math.log(2))));
    }

    protected static class TimSortInsertion extends AlgorithmAction {
        private final boolean ascending;

        private final int left;
        private final int right;
        private final int index;

        public TimSortInsertion(boolean ascending, int left, int right) {
            this(ascending, left, right, left);
        }

        public TimSortInsertion(boolean ascending, int left, int right, int index) {
            this.left = left;
            this.right = right;
            this.index = index;
            this.ascending = ascending;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            if (ascending) {
                for (int i = index - 1; i >= left; i--) {
                    if (algorithm.list.get(i) < algorithm.list.get(index)) {
                        next(algorithm, i + 1);
                        return;
                    }
                    algorithm.addToStart(new Wait());
                }
                next(algorithm, left);
            } else {
                for (int i = index - 1; i >= left; i--) {
                    if (algorithm.list.get(i) > algorithm.list.get(index)) {
                        next(algorithm, i + 1);
                        return;
                    }
                    algorithm.addToStart(new Wait());
                }
                next(algorithm, left);
            }
        }

        private void next(ActionSortingAlgorithm algorithm, int to) {
            algorithm.addToStart(new Move(index, to));
            if (index + 1 < right) {
                algorithm.addToStart(new TimSortInsertion(ascending, left, right, index + 1));
            }
        }
    }

}
