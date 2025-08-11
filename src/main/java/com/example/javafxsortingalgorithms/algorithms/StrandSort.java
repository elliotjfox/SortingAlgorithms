package com.example.javafxsortingalgorithms.algorithms;

import java.util.List;

public class StrandSort extends SortingAlgorithm {
    public StrandSort(List<Integer> arrayList) {
        super(arrayList);
    }

    @Override
    protected void runAlgorithm() {
        int buffer = 0;

        while (buffer < list.size()) {
            int pos = -1;
            for (int i = buffer; i < list.size(); i++) {
                trial.addRead(2);
                trial.addComparison();
                if (pos < 0 || list.get(i) > list.get(pos)) {
                    pos++;
                    buffer++;
                    move(i, pos);
                }
                addFrame();
            }
            mergeStrand(0, pos + 1, buffer);

        }
    }

    private void mergeStrand(int left, int mid, int right) {
        while (left < mid && mid < right) {
            trial.addRead(2);
            trial.addComparison();
            if (list.get(left) >= list.get(mid)) {
                move(mid, left);
                mid++;
            }
            left++;
            addFrame();
        }
    }

    @Override
    public String getName() {
        return "Strand Sort";
    }
}
