package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;

import java.util.List;

public class ExchangeSort extends SortingAlgorithm {

   private int sorted;
   private int curIndex;

    public ExchangeSort(List<Integer> list, boolean isInstant) {
        super(list, isInstant);

        sorted = 0;
        curIndex = 1;
    }

    @Override
    protected void runAlgorithm(ArrayDisplay display) {
        if (curIndex >= list.size()) {
            sorted++;
            curIndex = sorted + 1;

            if (sorted >= list.size()) {
                isDone = true;
            }

            return;
        }

        if (list.get(curIndex) < list.get(sorted)) {
            swap(curIndex, sorted);
            display.writeIndex(curIndex);
            display.writeIndex(sorted);
        } else {
            display.readIndex(curIndex);
            display.readIndex(sorted);
        }

        curIndex++;
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

    }

    @Override
    public String getName() {
        return null;
    }
}
