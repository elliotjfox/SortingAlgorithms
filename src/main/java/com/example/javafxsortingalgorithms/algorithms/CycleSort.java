package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.animation.AnimatedArrow;

import java.util.*;

public class CycleSort extends SortingAlgorithm {

    public CycleSort(List<Integer> list) {
        super(list);
    }

    @Override
    protected void runAlgorithm() {

        AnimatedArrow cycleStartArrow = animation.createArrow();
        AnimatedArrow targetPosArrow = animation.createArrow();
        AnimatedArrow iArrow = animation.createArrow();
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
