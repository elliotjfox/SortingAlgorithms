package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;

import java.util.List;

public class OddEvenMergeSort extends ActionSortingAlgorithm {

    public OddEvenMergeSort(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);

        if (isInstant) return;

        addComparisons();
    }

    private void addComparisons() {
        // Copied from https://en.wikipedia.org/wiki/Batcher_odd%E2%80%93even_mergesort
        for (int p = 1; p < list.size(); p *= 2) {
            for (int gapSize = p; gapSize >= 1; gapSize /= 2) {
                for (int j = gapSize % p; j <= list.size() - 1 - gapSize; j += 2 * gapSize) {
                    int end = Math.min(gapSize - 1, list.size() - j - gapSize - 1);
                    for (int i = 0; i <= end; i++) {
                        int left = i + j;
                        int right = i + j + gapSize;
                        if ((int) ((double) left / (p * 2)) == (int) ((double) right / (p * 2))) {
                            addToStart(new Comparison(left, right));
                        }
                    }
                }
            }
        }
        catchUpActions();
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

    }

    @Override
    public String getName() {
        return "";
    }

    protected static class Comparison extends AlgorithmAction {

        private final int left;
        private final int right;

        public Comparison(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            if (algorithm.getList().get(left) > algorithm.getList().get(right)) {
                algorithm.swap(left, right);
            }
        }
    }
}
