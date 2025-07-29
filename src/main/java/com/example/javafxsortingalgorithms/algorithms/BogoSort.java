package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;

import java.util.List;
import java.util.Random;

public class BogoSort extends SortingAlgorithm {

    public BogoSort(List<Integer> arrayList) {
        super(arrayList);
    }

    // TODO: Make a way to run this on a thread, so you aren't just waiting forever
    //  And generically, if the algorithm is taking too long, start a thread for it
    @Override
    protected void runAlgorithm() {
        Random r = new Random();
        while (!isListSorted(list)) {
            for (int i = list.size(); i > 1; i--) {
                swap(i - 1, r.nextInt(i));
                addFrame();
            }
        }
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {
//        Random random = new Random();
//        while (!isListSorted(list)) {
//            for (int i = list.size(); i > 1; i--) {
//                entry.addWrite(2);
//                swap(i - 1, random.nextInt(i));
//            }
//        }
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public String getName() {
        return "Bogo Sort";
    }
}
