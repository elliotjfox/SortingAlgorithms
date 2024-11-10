package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;

import java.util.*;

public class CycleSort extends SortingAlgorithm {

    private int cycleStart;
    private int currentCycleStart;
    private int item;
    private int pos;
    private int step;

    public CycleSort(List<Integer> list, boolean isInstant) {
        super(list, isInstant);

        cycleStart = 0;
        currentCycleStart = -1;
    }

    @Override
    protected void runAlgorithm(ArrayDisplay display) {
        isDone = true;
//
//        if (currentCycleStart == -1) {
//            if (cycleStart >= list.size()) {
//                isDone = true;
//                return;
//            }
//
//            currentCycleStart = cycleStart;
//            cycleStart++;
//            item = list.get(currentCycleStart);
//            pos = cycleStart;
//            step = cycleStart;
//            return;
//        }
//
//        if (step >= list.size()) {
//            if (pos == cycleStart) {
//                currentCycleStart = -1;
//                return;
//            }
//            item = list.set(pos, item);
//
//            return;
//        }
//
//        if (list.get(step) < item) {
//            pos++;
//        }
//
//        step++;

//        for (int cycleStart = 0; cycleStart < list.size(); cycleStart++) {
//            int item = list.get(cycleStart);
//
//            int pos = cycleStart;
//            for (int i = cycleStart + 1; i < list.size(); i++) {
//                if (list.get(i) < item) {
//                    pos++;
//                }
//            }
//
//            if (pos == cycleStart) continue;
//
//            while (item == list.get(pos)) {
//                pos++;
//            }
//
//            item = list.set(pos, item);
//
//            while (pos != cycleStart) {
//                pos = cycleStart;
//                for (int i = cycleStart + 1; i < list.size(); i++) {
//                    if (list.get(i) < item) {
//                        pos++;
//                    }
//                }
//
//                while (item == list.get(pos)) {
//                    pos++;
//                }
//
//                item = list.set(pos, item);
//            }
//
//        }

    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

    }

    @Override
    public String getName() {
        return null;
    }
}
