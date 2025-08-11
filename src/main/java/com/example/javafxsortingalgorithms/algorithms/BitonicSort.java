package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsCheckBox;
import com.example.javafxsortingalgorithms.algorithmupdates.CreateItemUpdate;
import com.example.javafxsortingalgorithms.arraydisplay.DisplayMode;
import com.example.javafxsortingalgorithms.animation.AnimatedSortingNetwork;

import java.util.List;

public class BitonicSort extends SortingAlgorithm {

    private final boolean fastMode;

    private AnimatedSortingNetwork sortingNetwork;

    public BitonicSort(List<Integer> arrayList, boolean fastMode) {
        super(arrayList);

        this.fastMode = fastMode;
    }

    @Override
    protected void runAlgorithm() {
        if (mode == DisplayMode.ANIMATED) {
            sortingNetwork = new AnimatedSortingNetwork();
            currentChanges.add(new CreateItemUpdate(sortingNetwork));
        }

        checkPairs();
        if (sortingNetwork != null) currentChanges.add(sortingNetwork.startNewSection());
        animation.addFrame();

        // TODO: This works, but it could be better
        for (int width = 4; width < 2 * list.size(); width *= 2) {
            trial.setProgress(log2(width / 2.0), log2(roundUpToPowerOf2(list.size())));
            for (int comparisonWidth = width; comparisonWidth > 1; comparisonWidth -= 2) {
                for (int j = (width - comparisonWidth) / 2; j < list.size(); j += width) {
                    safeCompare(j, j + comparisonWidth - 1);
                }
                if (sortingNetwork != null) currentChanges.add(sortingNetwork.startNewSection());
                animation.addFrame();
            }

            for (int i = width / 2; i >= 2; i /= 2) {
                int distance = i / 2;
                for (int j = 0; j < distance; j++) {
                    for (int k = j; k < list.size(); k += i) {
                        safeCompare(k, k + distance);
                    }
                    if (sortingNetwork != null) currentChanges.add(sortingNetwork.startNewSection());
                    animation.addFrame();
                }
            }
        }
    }

    private void safeCompare(int index1, int index2) {
        if (sortingNetwork != null) {
            sortingNetwork.addComparison(index1, index2);
        }

        animation.readIndex(index1);
        animation.readIndex(index2);
        animation.addFrame();
        trial.addRead(2);
        trial.addComparison();
        if (index1 < list.size() && index2 < list.size() && list.get(index1) > list.get(index2)) {
            swap(index1, index2);
        }
        addFrame();
    }

    private void checkPairs() {
        for (int i = 0; i < list.size() - 1; i += 2) {
            safeCompare(i, i + 1);
        }
    }

    @Override
    public String getName() {
        return "Bitonic Sort";
    }

    public static int roundUpToPowerOf2(int i) {
        return (int) Math.pow(2, Math.ceil(log2(i)));
    }

    public static double log2(double a) {
        return Math.log(a) / Math.log(2);
    }

    public static AlgorithmSettings<BitonicSort> getSettings() {
        AlgorithmSettingsCheckBox fastModeSetting = new AlgorithmSettingsCheckBox("Fast Mode", false);

        return new AlgorithmSettings<>(
                "Bitonic Sort",
                list -> new BitonicSort(list, fastModeSetting.getValue()),
                fastModeSetting
        );
    }
}
