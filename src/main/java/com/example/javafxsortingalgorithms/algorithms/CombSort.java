package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsInputBox;
import com.example.javafxsortingalgorithms.animation.*;
import com.example.javafxsortingalgorithms.arraydisplay.*;

import java.util.List;

public class CombSort extends SortingAlgorithm {

    private final double DEFAULT_SHRINK_FACTOR = 1.3;

    private int lastPos;
    private int gapSize;
    private final double shrinkFactor;

    private AnimatedItem leftArrow;
    private AnimatedItem rightArrow;
    private AnimatedSection section;

    private final double sectionHeight = -10;
    private final double arrowHeight = -20;

    public CombSort(List<Integer> arrayList, boolean isInstant, double shrinkFactor) {
        super(arrayList, isInstant);

        lastPos = 0;
        gapSize = (int) (arrayList.size() / shrinkFactor);
        this.shrinkFactor = shrinkFactor;
    }

    @Override
    protected void runAlgorithm(ArrayDisplay display) {
        // Check if we will compare outside the array, and reset if we need to. This uses a step.
        if (lastPos + gapSize >= list.size()) {
            gapSize = Math.max(1, (int) (gapSize / shrinkFactor));
            lastPos = 0;
            if (isListSorted(list)) {
                isDone = true;
            }
            return;
        }

        // Check if we need to swap the two elements we are looking at
        if (list.get(lastPos) > list.get(lastPos + gapSize)) {
            swap(lastPos, lastPos + gapSize);
            display.writeIndex(lastPos);
            display.writeIndex(lastPos + 1);
        } else {
            display.readIndex(lastPos);
            display.readIndex(lastPos + 1);
        }

        // Increase position
        lastPos++;
    }

    // TODO: Figure out how to calculate percentage
    @Override
    protected void instantAlgorithm(TestEntry entry) {
        gapSize = (int) (list.size() / shrinkFactor);
        while (!isListSorted(list)) {
            for (int i = 0; i + gapSize < list.size(); i++) {
                if (list.get(i) > list.get(i + gapSize)) {
                    swap(i, i + gapSize);
                    entry.addWrite(2);
                }
                entry.addRead(2);
            }
            if (gapSize != 1) gapSize = (int) (gapSize / shrinkFactor);
        }
    }

    @Override
    public void startAnimated(AnimatedArrayDisplay display) {
        leftArrow = new ItemBuilder(display)
                .at(0, arrowHeight)
                .add(PolygonWrapper.triangle(display))
                .build();
        display.addItem(leftArrow);

        // TODO: This isn't appearing in the right spot?
        rightArrow = new ItemBuilder(display)
                .at(gapSize, arrowHeight)
                .add(PolygonWrapper.triangle(display))
                .build();
        display.addItem(rightArrow);

        section = new ItemBuilder(display)
                .at(0, sectionHeight)
                .buildSection(gapSize + 1);
        display.addItem(section);

        display.updateInfo("Gap size", gapSize);
        display.updateInfo("Left index", 0);
        display.updateInfo("Left value", list.getFirst());
        display.updateInfo("Right index", gapSize);
        display.updateInfo("Right value", list.get(gapSize));
    }

    @Override
    public void iterateAnimated(AnimatedArrayDisplay display) {
        // Check if we will compare outside the array, and reset if we need to. This uses a step.
        if (lastPos + gapSize >= list.size()) {
            if (isListSorted(list)) {
                isDone = true;
                return;
            }

            lastPos = 0;
            int oldGapSize = gapSize;

            display.onPlay(() -> display.setCurrentTask("Resetting"));
            leftArrow.moveToIndex(lastPos, arrowHeight);
            rightArrow.moveToIndex(lastPos + gapSize, arrowHeight);
            section.moveToIndex(lastPos, sectionHeight);
            display.updateInfoWhenDone("Left index", lastPos);
            display.updateInfoWhenDone("Left value", list.get(lastPos));
            display.updateInfoWhenDone("Right index", lastPos + gapSize);
            display.updateInfoWhenDone("Right value", list.get(lastPos + gapSize));
            gapSize = Math.max(1, (int) (gapSize / shrinkFactor));
            if (oldGapSize != gapSize) {
                display.newGroup();
                display.onPlay(() -> display.setCurrentTask("Shrinking gap size"));
                display.updateInfoWhenDone("Gap size", gapSize);
                // Shouldn't actually need to move, but redundancy is good!
                rightArrow.moveToIndex(lastPos + gapSize, arrowHeight);
                section.resize(gapSize + 1);
            }
            return;
        }

        // A little bit of time saving after we reset
        if (lastPos != 0) {
            leftArrow.moveToIndex(lastPos, arrowHeight);
            rightArrow.moveToIndex(lastPos + gapSize, arrowHeight);
            section.moveToIndex(lastPos, sectionHeight);
            display.updateInfoWhenDone("Left index", lastPos);
            display.updateInfoWhenDone("Left value", list.get(lastPos));
            display.updateInfoWhenDone("Right index", lastPos + gapSize);
            display.updateInfoWhenDone("Right value", list.get(lastPos + gapSize));
            display.newGroup();
        } else {
            display.onPlay(() -> display.setCurrentTask("Comparing elements"));
        }

        display.comparing(lastPos, lastPos + gapSize);

        // Check if we need to swap the two elements we are looking at
        if (list.get(lastPos) > list.get(lastPos + gapSize)) {
            swap(lastPos, lastPos + gapSize);
            display.swap(lastPos, lastPos + gapSize);
            display.updateInfoWhenDone("Left value", list.get(lastPos));
            display.updateInfoWhenDone("Right value", list.get(lastPos + gapSize));
        }

        // Increase position
        lastPos++;
    }

    @Override
    public String getName() {
        return "Comb Sort \nShrink Factor: " + shrinkFactor + "}";
    }

    public static AlgorithmSettings<CombSort> getSettings() {
        AlgorithmSettingsInputBox<Double> shrinkFactorSetting = new AlgorithmSettingsInputBox<>(
                "Shrink Factor", 1.3,
                Double::parseDouble, d -> d > 1
        );

        return new AlgorithmSettings<>(
                "Comb Sort",
                (l, b) -> new CombSort(l, b, shrinkFactorSetting.getValue()),
                shrinkFactorSetting
        );
    }

//    void combSort() {
//        int gapSize = (int) (list.size() / shrinkFactor);
//        while (!isListSorted(list)) {
//            for (int i = 0; i + gapSize < list.size(); i++) {
//                if (list.get(i) > list.get(i + gapSize)) {
//                    swap(i, i + gapSize);
//                }
//            }
//            if (gapSize != 1) gapSize = (int) (gapSize / shrinkFactor);
//        }
//    }

}
