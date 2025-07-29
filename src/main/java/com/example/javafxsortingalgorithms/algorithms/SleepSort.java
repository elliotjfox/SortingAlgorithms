package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;

import java.util.*;

// TODO: Get other modes in
public class SleepSort extends SortingAlgorithm {

    private static final int TIME_MULTIPLIER = 100;

    private int sorted;
    // We won't actually be using threads for the iterate mode
    private HashMap<Integer, Integer> sleepThreads;

    public SleepSort(List<Integer> arrayList) {
        super(arrayList);

        sorted = 0;
    }

    // TODO
    @Override
    protected void runAlgorithm() {

    }

    //    @Override
//    protected void runAlgorithm(ArrayDisplay display) {
//        if (sleepThreads == null) {
//            sleepThreads = new HashMap<>();
//            for (Integer integer : list) {
//                sleepThreads.put(integer, integer * TIME_MULTIPLIER);
//            }
//        }
//
//        if (sleepThreads.isEmpty()) {
//            isDone = true;
//            return;
//        }
//        ArrayList<Integer> toRemove = new ArrayList<>();
//        for (Map.Entry<Integer, Integer> entry : sleepThreads.entrySet()) {
//            sleepThreads.put(entry.getKey(), entry.getValue() - 1);
//            if (entry.getValue() <= 0) {
//                toRemove.add(entry.getKey());
//            }
//        }
//        for (Integer i : toRemove) {
//            list.set(sorted, i);
//            sorted++;
//            sleepThreads.remove(i);
//        }
//    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

    }

    @Override
    public String getName() {
        return null;
    }
}
