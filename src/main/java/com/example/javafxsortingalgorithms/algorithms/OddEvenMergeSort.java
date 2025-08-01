package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.algorithmupdates.CreateItemUpdate;
import com.example.javafxsortingalgorithms.arraydisplay.DisplayMode;
import com.example.javafxsortingalgorithms.animation.AnimatedSortingNetwork;

import java.util.List;

public class OddEvenMergeSort extends SortingAlgorithm {

    public OddEvenMergeSort(List<Integer> arrayList) {
        super(arrayList);
    }

    @Override
    protected void runAlgorithm() {
        AnimatedSortingNetwork sortingNetwork = null;
        if (mode == DisplayMode.ANIMATED) {
            sortingNetwork = new AnimatedSortingNetwork();
            currentChanges.add(new CreateItemUpdate(sortingNetwork));
        }

        for (int p = 1; p < list.size(); p *= 2) {
            for (int gapSize = p; gapSize >= 1; gapSize /= 2) {
                for (int j = gapSize % p; j <= list.size() - 1 - gapSize; j += 2 * gapSize) {
                    int end = Math.min(gapSize - 1, list.size() - j - gapSize - 1);
                    for (int i = 0; i <= end; i++) {
                        int left = i + j;
                        int right = i + j + gapSize;
                        if ((int) ((double) left / (p * 2)) == (int) ((double) right / (p * 2))) {
                            if (list.get(left) > list.get(right)) {
                                swap(left, right);
                            }
                            if (sortingNetwork != null) sortingNetwork.addComparison(left, right);
                        }

                        addFrame();
                    }
                    if (sortingNetwork != null) currentChanges.add(sortingNetwork.startNewSection());
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Odd Even Merge Sort";
    }
}
