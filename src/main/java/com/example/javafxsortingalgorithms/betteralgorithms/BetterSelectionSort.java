package com.example.javafxsortingalgorithms.betteralgorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;

import java.util.List;

public class BetterSelectionSort extends SortingAlgorithm {


    public BetterSelectionSort(List<Integer> list, boolean isInstant) {
        super(list, isInstant);
//        LoopBlock loopBlock = new LoopBlock(list, 0, list.size(),
//                new IndexedAlgorithmBlock(list) {
//                    @Override
//                    public void executeAt(int i) {
//
//                    }
//                }
//        );
    }

    @Override
    protected void runAlgorithm(ArrayDisplay display) {

    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

    }

    @Override
    public String getName() {
        return null;
    }
}
