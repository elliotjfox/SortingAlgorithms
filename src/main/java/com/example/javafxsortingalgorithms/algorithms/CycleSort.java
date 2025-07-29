package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;
import com.example.javafxsortingalgorithms.newanimation.NewAnimatedArrow;

import java.util.*;

public class CycleSort extends SortingAlgorithm {

    public CycleSort(List<Integer> list, boolean isInstant) {
        super(list, isInstant);
    }

    @Override
    protected void runAlgorithm() {

        NewAnimatedArrow cycleStartArrow = animation.createArrow();
        NewAnimatedArrow targetPosArrow = animation.createArrow();
        NewAnimatedArrow iArrow = animation.createArrow();
        animation.setItemIndex(cycleStartArrow, 0);
        animation.setItemIndex(targetPosArrow, 0);
        animation.setItemIndex(iArrow, 1);
        animation.setItemHeight(cycleStartArrow, 0);
        animation.setItemHeight(targetPosArrow, 0);
        animation.setItemHeight(iArrow, 0);


        for (int cycleStart = 0; cycleStart < list.size() - 1; cycleStart++) {
            animation.moveItem(cycleStartArrow, cycleStart);
            while (true) {
                int targetPos = cycleStart;
                for (int i = cycleStart + 1; i < list.size(); i++) {
                    animation.moveItem(targetPosArrow, targetPos);
                    animation.moveItem(iArrow, i);
                    animation.addFrame();
                    animation.readIndex(i);
                    animation.readIndex(cycleStart);
                    if (list.get(i) < list.get(cycleStart)) {
                        targetPos++;
                    }
                    addFrame();
                }

                if (targetPos == cycleStart) break;

                swap(targetPos, cycleStart);
                addFrame();
            }
        }
    }

    @Override
    public String getName() {
        return "Cycle Sort";
    }
}
