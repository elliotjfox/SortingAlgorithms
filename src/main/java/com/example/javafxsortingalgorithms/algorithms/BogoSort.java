package com.example.javafxsortingalgorithms.algorithms;

import java.util.List;
import java.util.Random;

public class BogoSort extends SortingAlgorithm {

    private static final int BOGO_SORT_MAX_SIZE = 10;

    public BogoSort(List<Integer> arrayList) {
        super(arrayList);
    }

    @Override
    protected void runAlgorithm() {
        if (list.size() > BOGO_SORT_MAX_SIZE) return;
        Random r = new Random();
        while (!isListSorted(list)) {
            for (int i = list.size(); i > 1; i--) {
                swap(i - 1, r.nextInt(i));
            }
            addFrame();
        }
    }

    @Override
    public String getName() {
        return "Bogo Sort";
    }
}
