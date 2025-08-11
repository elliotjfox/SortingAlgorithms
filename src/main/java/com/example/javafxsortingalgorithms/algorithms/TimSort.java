package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsCheckBox;

import java.util.List;

public class TimSort extends SortingAlgorithm {

    private final int RUN_SIZE = 32;

    private final boolean alwaysAscending;

    public TimSort(List<Integer> arrayList, boolean alwaysAscending) {
        super(arrayList);

        this.alwaysAscending = alwaysAscending;
    }

    @Override
    protected void runAlgorithm() {
        // Insertion sort step
        for (int i = 0; i < list.size(); i += RUN_SIZE) {
            // If the list has only one element left
            if (i + 1 >= list.size()) {
                addFrame();
                continue;
            }

            if (alwaysAscending || list.get(i) <= list.get(i + 1)) {
                ascendingInsertionSort(i, i + RUN_SIZE - 1);
            } else {
                descendingInsertionSort(i, i + RUN_SIZE - 1);
            }
        }

        // Merge Step
        for (int size = RUN_SIZE; size < list.size(); size *= 2) {
            for (int left = 0; left < list.size(); left += 2 * size) {
                merge(left, left + size, left + 2 * size);
            }
        }
    }

    // [from, to]
    private void ascendingInsertionSort(int from, int to) {
        if (to >= list.size()) to = list.size() - 1;
        int i = from + 1;
        while (i <= to) {
            int j = i - 1;

            trial.addRead(2);
            trial.addComparison();
            while (j >= from && list.get(j) > list.get(i)) {
                j--;
                addFrame();
            }
            move(i, j + 1);
            addFrame();
            i++;
        }
    }

    private void descendingInsertionSort(int from, int to) {
        if (to >= list.size()) to = list.size() - 1;
        int i = from + 1;
        while (i <= to) {
            int j = i - 1;

            trial.addRead(2);
            trial.addComparison();
            while (j >= from && list.get(j) < list.get(i)) {
                j--;
                addFrame();
            }
            move(i, j + 1);
            addFrame();
            i++;
        }

        // Flip
        for (int j = 0; j <= (to - from) / 2; j++) {
            swap(from + j, to - j);
            addFrame();
        }
    }

    private void merge(int left, int right, int end) {
        if (end > list.size()) end = list.size();
        while (right < end && left < right) {
            trial.addRead(2);
            trial.addComparison();
            if (list.get(left) >= list.get(right)) {
                move(right, left);
                right++;
            }
            left++;
            addFrame();
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

    public static AlgorithmSettings<TimSort> getSettings() {
        AlgorithmSettingsCheckBox alwaysAscendingSetting = new AlgorithmSettingsCheckBox("Always Ascending", false);

        return new AlgorithmSettings<>(
                "Tim Sort",
                list -> new TimSort(list, alwaysAscendingSetting.getValue()),
                alwaysAscendingSetting
        );
    }

}
