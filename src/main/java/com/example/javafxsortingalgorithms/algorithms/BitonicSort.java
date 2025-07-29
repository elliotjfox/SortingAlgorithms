package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithnSettingsCheckBox;
import com.example.javafxsortingalgorithms.algorithmupdates.CreateItemUpdate;
import com.example.javafxsortingalgorithms.animation.AnimatedArrayDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;
import com.example.javafxsortingalgorithms.animation.AnimatedSortingNetwork;
import com.example.javafxsortingalgorithms.arraydisplay.DisplayMode;
import com.example.javafxsortingalgorithms.newanimation.NewAnimatedSortingNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BitonicSort extends SortingAlgorithm {

    private final boolean fastMode;

    private NewAnimatedSortingNetwork sortingNetwork;

    public BitonicSort(List<Integer> arrayList, boolean isInstant, boolean fastMode) {
        super(arrayList, isInstant);

        this.fastMode = fastMode;
    }

    @Override
    protected void runAlgorithm() {
        if (mode == DisplayMode.ANIMATED) {
            sortingNetwork = new NewAnimatedSortingNetwork();
            currentChanges.add(new CreateItemUpdate(sortingNetwork));
        }

        checkPairs();
        if (sortingNetwork != null) currentChanges.add(sortingNetwork.startNewSection());
        animation.addFrame();

        // TODO: This works, but it could be better
        for (int width = 4; width < 2 * list.size(); width *= 2) {
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

    private void compareAndSwap(int first, int second, TestEntry testEntry) {
        if (first < 0 || second < 0 || first >= list.size() || second >= list.size()) return;
        testEntry.addRead(2);
        if (list.get(first) > list.get(second)) {
            swap(first, second);
            testEntry.addWrite(2);
        }
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

        for (int i = 0; i < list.size() - 1; i += 2) {
            compareAndSwap(i, i + 1, entry);
        }

        for (int width = 4; width <= roundUpToPowerOf2(list.size()); width *= 2) {
            entry.updateProgress(log2(width / 2.0) / log2(roundUpToPowerOf2(list.size())));
            if (width > 1) {
                for (int i = width; i > 1; i -= 2) {
                    for (int j = (width - i) / 2; j < list.size(); j += width) {
                        compareAndSwap(j, j + i - 1, entry);
                    }
                }
            }

            for (int i = width / 2; i >= 4; i /= 2) {
                int distance = i / 2;
                for (int j = 0; j < distance; j++) {
                    for (int k = j; k < list.size(); k += i) {
                        compareAndSwap(k, k + distance, entry);
                    }
                }
            }

            for (int i = 0; i < list.size() - 1; i += 2) {
                compareAndSwap(i, i + 1, entry);
            }
        }
    }

//    @Override
//    public void startAnimated(AnimatedArrayDisplay display) {
//        sortingNetwork = new AnimatedSortingNetwork(display, list);
//        display.addItem(sortingNetwork, 0, 0);
//
//        // Buffer for the first one
//        sortingNetwork.addComparisons(new ArrayList<>());
//        for (AlgorithmAction action : actions) {
//            if (action instanceof BitonicComparison comparison) {
//                sortingNetwork.addComparisons(comparison.comparisons);
//            }
//        }
//
//        display.animate(sortingNetwork.moveUp());
//    }

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
        AlgorithnSettingsCheckBox fastModeSetting = new AlgorithnSettingsCheckBox("Fast Mode", false);

        return new AlgorithmSettings<>(
                "Bitonic Sort",
                (l, b) -> new BitonicSort(l, b, fastModeSetting.getValue()),
                fastModeSetting
        );
    }
}
