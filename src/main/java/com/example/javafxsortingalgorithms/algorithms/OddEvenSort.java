package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.animation.AnimatedArrow;
import com.example.javafxsortingalgorithms.animation.AnimatedSection;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledIndex;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledPosition;

import java.util.ArrayList;
import java.util.List;

public class OddEvenSort extends SortingAlgorithm {

    private static final double SECTION_HEIGHT = -7;
    private static final double ARROW_HEIGHT = -15;

    public OddEvenSort(List<Integer> arrayList) {
        super(arrayList);
    }

    @Override
    protected void runAlgorithm() {
        AnimatedArrow leftArrow = animation.createArrow();
        animation.setItemPosition(leftArrow, new ElementScaledPosition(0, ARROW_HEIGHT));
        AnimatedArrow rightArrow = animation.createArrow();
        animation.setItemPosition(rightArrow, new ElementScaledPosition(1, ARROW_HEIGHT));

        List<AnimatedSection> sections = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i += 2) {
            AnimatedSection section = animation.createSection(new ElementScaledIndex(1));
            sections.add(section);
            animation.setItemPosition(section, new ElementScaledPosition(i + 0.5, SECTION_HEIGHT));
        }

        boolean hasMadeSwap;
        do {
            hasMadeSwap = false;

            for (int i = 0; i < sections.size(); i++) {
                animation.changeItemX(sections.get(i), new ElementScaledIndex(i * 2 + 0.5));
            }

            for (int i = 0; i < list.size() - 1; i += 2) {
                animation.changeItemX(leftArrow, new ElementScaledIndex(i));
                animation.changeItemX(rightArrow, new ElementScaledIndex(i + 1));
                animation.addFrame();
                animation.readIndex(i);
                animation.readIndex(i + 1);
                trial.addRead(2);
                trial.addComparison();
                if (list.get(i) > list.get(i + 1)) {
                    animation.addFrame();
                    hasMadeSwap = true;
                    swap(i, i + 1);
                }
                addFrame();
            }

            for (int i = 0; i < sections.size(); i++) {
                animation.changeItemX(sections.get(i), new ElementScaledIndex(i * 2 + 1.5));
            }

            for (int i = 1; i < list.size() - 1; i += 2) {
                animation.changeItemX(leftArrow, new ElementScaledIndex(i));
                animation.changeItemX(rightArrow, new ElementScaledIndex(i + 1));
                animation.addFrame();
                animation.readIndex(i);
                animation.readIndex(i + 1);
                trial.addRead(2);
                trial.addComparison();
                if (list.get(i) > list.get(i + 1)) {
                    animation.addFrame();
                    hasMadeSwap = true;
                    swap(i, i + 1);
                }
                addFrame();
            }
        } while (hasMadeSwap);
    }

    @Override
    public String getName() {
        return "Odd-Even Sort";
    }

//    void oddEvenSort() {
//        boolean hasMadeSwap;
//        do {
//            hasMadeSwap = false;
//            for (int i = 0; i < list.size() - 1; i += 2) {
//                if (list.get(i) > list.get(i + 1)) {
//                    hasMadeSwap = true;
//                    swap(i, i + 1);
//                }
//            }
//
//            for (int i = 1; i < list.size() - 1; i += 2) {
//                if (list.get(i) > list.get(i + 1)) {
//                    hasMadeSwap = true;
//                    swap(i, i + 1);
//                }
//            }
//        } while (hasMadeSwap);
//    }
}
