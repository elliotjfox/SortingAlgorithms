package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.animation.AnimatedArrow;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledIndex;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledPosition;

import java.util.*;

public class CycleSort extends SortingAlgorithm {

    public CycleSort(List<Integer> list) {
        super(list);
    }

    @Override
    protected void runAlgorithm() {

        AnimatedArrow cycleStartArrow = animation.createArrow();
        animation.setItemPosition(cycleStartArrow, new ElementScaledPosition(0, 0));

        AnimatedArrow targetPosArrow = animation.createArrow();
        animation.setItemPosition(targetPosArrow, new ElementScaledPosition(0, 0));

        AnimatedArrow iArrow = animation.createArrow();
        animation.setItemPosition(targetPosArrow, new ElementScaledPosition(1, 0));


        for (int cycleStart = 0; cycleStart < list.size() - 1; cycleStart++) {
            animation.changeItemX(cycleStartArrow, new ElementScaledIndex(cycleStart));
            while (true) {
                int targetPos = cycleStart;
                for (int i = cycleStart + 1; i < list.size(); i++) {
                    animation.changeItemX(targetPosArrow, new ElementScaledIndex(targetPos));
                    animation.changeItemX(iArrow, new ElementScaledIndex(i));
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
