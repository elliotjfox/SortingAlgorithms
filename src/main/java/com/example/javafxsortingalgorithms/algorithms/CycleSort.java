package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.animation.AnimatedArrow;
import com.example.javafxsortingalgorithms.animation.position.ScaledIndex;
import com.example.javafxsortingalgorithms.animation.position.ScaledPosition;

import java.util.*;

public class CycleSort extends SortingAlgorithm {

    public CycleSort(List<Integer> list) {
        super(list);
    }

    @Override
    protected void runAlgorithm() {

        AnimatedArrow cycleStartArrow = animation.createArrow();
        animation.setItemPosition(cycleStartArrow, new ScaledPosition(0, 0));

        AnimatedArrow targetPosArrow = animation.createArrow();
        animation.setItemPosition(targetPosArrow, new ScaledPosition(0, 0));

        AnimatedArrow iArrow = animation.createArrow();
        animation.setItemPosition(targetPosArrow, new ScaledPosition(1, 0));


        for (int cycleStart = 0; cycleStart < list.size() - 1; cycleStart++) {
            animation.changeItemX(cycleStartArrow, new ScaledIndex(cycleStart));
            while (true) {
                int targetPos = cycleStart;
                for (int i = cycleStart + 1; i < list.size(); i++) {
                    animation.changeItemX(targetPosArrow, new ScaledIndex(targetPos));
                    animation.changeItemX(iArrow, new ScaledIndex(i));
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
