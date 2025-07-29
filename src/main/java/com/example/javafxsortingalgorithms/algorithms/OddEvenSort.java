package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.animation.*;
import com.example.javafxsortingalgorithms.arraydisplay.*;
import com.example.javafxsortingalgorithms.newanimation.NewAnimatedArrow;
import com.example.javafxsortingalgorithms.newanimation.NewAnimatedSection;

import java.util.ArrayList;
import java.util.List;

public class OddEvenSort extends SortingAlgorithm {

    private static final double SECTION_HEIGHT = -7;
    private static final double ARROW_HEIGHT = -15;

    public OddEvenSort(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);
    }

    @Override
    protected void runAlgorithm() {
        NewAnimatedArrow leftArrow = animation.createArrow();
        NewAnimatedArrow rightArrow = animation.createArrow();

        animation.setItemIndex(leftArrow, 0);
        animation.setItemHeight(leftArrow, ARROW_HEIGHT);
        animation.setItemIndex(rightArrow, 1);
        animation.setItemHeight(rightArrow, ARROW_HEIGHT);

        List<NewAnimatedSection> sections = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i += 2) {
            NewAnimatedSection section = animation.createSection(1);
            sections.add(section);
            animation.setItemPosition(section, i + 0.5);
            animation.setItemHeight(section, SECTION_HEIGHT);
        }

        boolean hasMadeSwap;
        do {
            hasMadeSwap = false;

            for (int i = 0; i < sections.size(); i++) {
                animation.moveItem(sections.get(i), i * 2 + 0.5);
            }

            for (int i = 0; i < list.size() - 1; i += 2) {
                animation.moveItem(leftArrow, i);
                animation.moveItem(rightArrow, i + 1);
                animation.addFrame();
                animation.readIndex(i);
                animation.readIndex(i + 1);
                if (list.get(i) > list.get(i + 1)) {
                    animation.addFrame();
                    hasMadeSwap = true;
                    swap(i, i + 1);
                }
                addFrame();
            }

            for (int i = 0; i < sections.size(); i++) {
                animation.moveItem(sections.get(i), i * 2 + 1.5);
            }

            for (int i = 1; i < list.size() - 1; i += 2) {
                animation.moveItem(leftArrow, i);
                animation.moveItem(rightArrow, i + 1);
                animation.addFrame();
                animation.readIndex(i);
                animation.readIndex(i + 1);
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
    protected void instantAlgorithm(TestEntry entry) {
        boolean hasMadeSwap;
        do {
            hasMadeSwap = false;

            for (int i = 0; i < list.size() - 1; i += 2) {
                entry.addRead(2);
                if (list.get(i) > list.get(i + 1)) {
                    hasMadeSwap = true;
                    swap(i, i + 1);
                    entry.addWrite(2);
                }
            }
            for (int i = 1; i < list.size() - 1; i += 2) {
                entry.addRead(2);
                if (list.get(i) > list.get(i + 1)) {
                    hasMadeSwap = true;
                    swap(i, i + 1);
                    entry.addWrite(2);
                }
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
