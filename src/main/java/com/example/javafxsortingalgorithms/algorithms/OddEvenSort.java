package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.animation.*;
import com.example.javafxsortingalgorithms.arraydisplay.*;

import java.util.ArrayList;
import java.util.List;

public class OddEvenSort extends SortingAlgorithm {

    private static final double SECTION_HEIGHT = -7;
    private static final double ARROW_HEIGHT = -15;

    private List<AnimatedSection> sections;
    private AnimatedItem leftArrow;
    private AnimatedItem rightArrow;

    private boolean hasMadeSwap;
    private int currentPos;

    public OddEvenSort(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);
        hasMadeSwap = false;
        currentPos = 0;
    }

    @Override
    protected void runAlgorithm() {
        do {
            hasMadeSwap = false;

            for (int i = 0; i < list.size() - 1; i += 2) {
                if (list.get(i) > list.get(i + 1)) {
                    hasMadeSwap = true;
                    swap(i, i + 1);
                }
                addFrame();
            }
            for (int i = 1; i < list.size() - 1; i += 2) {
                if (list.get(i) > list.get(i + 1)) {
                    hasMadeSwap = true;
                    swap(i, i + 1);
                }
                addFrame();
            }
        } while (hasMadeSwap);
    }

    @Override
    protected void runAlgorithm(ArrayDisplay display) {
        if (currentPos + 1 >= list.size()) {
            if (isListSorted(list)) {
                isDone = true;
                return;
            }
            if (currentPos % 2 == 0) {
                currentPos = 1;
            } else {
                currentPos = 0;

                if (!hasMadeSwap) {
                    isDone = true;
                    return;
                }
                hasMadeSwap = false;
            }
            return;
        }

        if (list.get(currentPos) > list.get(currentPos + 1)) {
            hasMadeSwap = true;
            swap(currentPos, currentPos + 1);
            display.writeIndex(currentPos);
            display.writeIndex(currentPos + 1);
        } else {
            display.readIndex(currentPos);
            display.readIndex(currentPos + 1);
        }

        currentPos += 2;
    }

    @Override
    public void startAnimated(AnimatedArrayDisplay display) {
        leftArrow = new ItemBuilder(display)
                .add(PolygonWrapper.triangle(display))
                .at(0, ARROW_HEIGHT)
                .build();
        display.addItem(leftArrow);

        rightArrow = new ItemBuilder(display)
                .add(PolygonWrapper.triangle(display))
                .at(1, ARROW_HEIGHT)
                .build();
        display.addItem(rightArrow);

        sections = new ArrayList<>();
        for (int i = 0; i < list.size() / 2; i++) {
            AnimatedSection section = new ItemBuilder(display)
                    .at(i * 2 + (currentPos % 2 == 0 ? 0 : 1) + 12.5, SECTION_HEIGHT)
                    .buildSection(1, true);
            sections.add(section);
            display.addItem(section);
        }

        display.setCurrentTask("Checking evens");
        display.getDetailedInfo().updateInfo("Left index", 0);
        display.getDetailedInfo().updateInfo("Left value", list.get(0));
        display.getDetailedInfo().updateInfo("Right index", 1);
        display.getDetailedInfo().updateInfo("Right value", list.get(1));
    }

    @Override
    public void iterateAnimated(AnimatedArrayDisplay display) {
        if (currentPos + 1 >= list.size()) {
            if (isListSorted(list)) {
                isDone = true;
                return;
            }

            if (currentPos % 2 == 0) {
                currentPos = 1;
                display.whenDone(() -> display.setCurrentTask("Checking odds"));
            } else {
                currentPos = 0;
                display.whenDone(() -> display.setCurrentTask("Checking evens"));

                if (!hasMadeSwap) {
                    isDone = true;
                    return;
                }
                hasMadeSwap = false;
            }
            updateInfoWhenDone(display);
            return;
        }

        leftArrow.moveToIndex(currentPos, ARROW_HEIGHT);
        rightArrow.moveToIndex(currentPos + 1, ARROW_HEIGHT);
        for (int i = 0; i < sections.size(); i++) {
            double xPos = display.getElementWidth() * 2 * i;
            if (currentPos % 2 == 0) xPos += display.getElementWidth();
            xPos += display.getElementWidth() / 2;
            sections.get(i).moveToPosition(xPos, SECTION_HEIGHT);
        }
        updateInfoWhenDone(display);
        display.newGroup();
        display.comparing(currentPos, currentPos + 1);

        if (list.get(currentPos) > list.get(currentPos + 1)) {
            hasMadeSwap = true;
            swap(currentPos, currentPos + 1);
            display.swap(currentPos, currentPos + 1);
            updateInfoWhenDone(display);
        }

        currentPos += 2;
    }

    private void updateInfoWhenDone(AnimatedArrayDisplay display) {
        display.updateInfoWhenDone("Left index", currentPos);
        display.updateInfoWhenDone("Left value", list.get(currentPos));
        display.updateInfoWhenDone("Right index", currentPos + 1);
        display.updateInfoWhenDone("Right value", list.get(currentPos + 1));
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {
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
