package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.*;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledIndex;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledPosition;
import com.example.javafxsortingalgorithms.arraydisplay.*;
import com.example.javafxsortingalgorithms.animation.AnimatedArrow;

import java.util.List;

public class PancakeSort extends SortingAlgorithm {

    private final boolean instantFlips;

    public PancakeSort(List<Integer> arrayList, boolean instantFlips) {
        super(arrayList);

        this.instantFlips = instantFlips;
    }

    @Override
    protected void runAlgorithm() {
        AnimatedArrow arrow = animation.createArrow();
        animation.setItemPosition(arrow, new ElementScaledPosition(0, 0));
        AnimatedArrow minArrow = animation.createArrow();
        animation.setItemPosition(minArrow, new ElementScaledPosition(0, 0));

        for (int i = 0; i < list.size() - 1; i++) {
            trial.setProgress(i, list.size());
            int smallestIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                animation.changeItemX(arrow, new ElementScaledIndex(j));
                animation.changeItemX(minArrow, new ElementScaledIndex(smallestIndex));
                animation.addFrame();
                animation.readIndex(j);
                animation.readIndex(smallestIndex);
                trial.addRead(2);
                trial.addComparison();
                if (list.get(j) < list.get(smallestIndex)) {
                    smallestIndex = j;
                    animation.addFrame();
                    animation.changeItemX(minArrow, new ElementScaledIndex(smallestIndex));
                }
                addFrame();
            }

            flip(smallestIndex, list.size() - 1);
            flip(i, list.size() - 1);
        }
    }

    private void flip(int from, int to) {
        for (int i = 0; i <= (to - from) / 2; i++) {
            swap(from + i, to - i);
            if (!instantFlips && mode != DisplayMode.ANIMATED) addFrame();
        }
        if (instantFlips || mode == DisplayMode.ANIMATED) addFrame();
    }

    @Override
    public String getName() {
        return "Pancake Sort";
    }

    public static AlgorithmSettings<PancakeSort> getSettings() {
        AlgorithmSettingsCheckBox instantFlipsSetting = new AlgorithmSettingsCheckBox("Instant Flip", false);

        return new AlgorithmSettings<>(
                "Pancake Sort",
                list -> new PancakeSort(list, instantFlipsSetting.getValue()),
                instantFlipsSetting
        );
    }

//    void pancakeSort() {
//        for (int i = 0; i < list.size() - 1; i++) {
//            int min = i;
//            for (int j = i + 1; j < list.size(); j++) {
//                if (list.get(j) < list.get(min)) {
//                    min = j;
//                }
//            }
//            flip(min);
//            flip(i);
//        }
//    }
//
//    void flip(int lower) {
//        int upper = list.size() - 1;
//        for (int i = 0; i <= (upper - lower) / 2; i++) {
//            swap(lower + i, upper - i);
//        }
//    }
}
