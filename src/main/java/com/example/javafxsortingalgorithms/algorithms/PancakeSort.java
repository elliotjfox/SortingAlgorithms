package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.*;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;

import java.util.List;

public class PancakeSort extends ActionSortingAlgorithm {

    private int count;
    private final boolean instantFlips;

    public PancakeSort(List<Integer> arrayList, boolean isInstant, boolean instantFlips) {
        super(arrayList, isInstant);

        this.instantFlips = instantFlips;
        count = 0;

        if (!isInstant) {
            for (int i = 0; i < arrayList.size(); i++) {
                actions.add(new FindSmallest(i));
            }
        }
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {
        for (int i = 0; i < list.size(); i++) {
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
        for (int i = 0; i < (to - from) / 2; i++) {
            swap(from + i, to - i);
            testEntry.addWrite(2);
        }
    }

    @Override
    public String getName() {
        return "Pancake Sort";
    }

    private static class FindSmallest extends AlgorithmAction {

        private int i;
        private int smallestIndex;

        public FindSmallest(int from) {
            i = from;
            smallestIndex = from;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            for (int j = i; j < algorithm.getList().size(); j++) {
                algorithm.addToStart(
                        new LaterAction(() -> {
                            if (algorithm.getList().get(i) < algorithm.getList().get(smallestIndex)) {
                                smallestIndex = i;
                            }
                            i++;
                        })
                );
            }

            algorithm.addToStart(
                    new LaterAction(() -> {
                        algorithm.addToStart(
                            new Flip(smallestIndex, algorithm.getList().size() - 1, !((PancakeSort) algorithm).instantFlips),
                            new Flip(((PancakeSort) algorithm).count, algorithm.getList().size() - 1, !((PancakeSort) algorithm).instantFlips)
                        );
                        ((PancakeSort) algorithm).count++;
                    }, false)
            );
        }
    }

    public static AlgorithmSettings getSettings() {
        SettingsCheckBox instantFlipsSetting = new SettingsCheckBox("Instant Flip", false);

        return new FunctionalAlgorithmSettings<>(
                "Pancake Sort",
                (l, b) -> new PancakeSort(l, b, instantFlipsSetting.getValue()),
                instantFlipsSetting
        );

    }
}
