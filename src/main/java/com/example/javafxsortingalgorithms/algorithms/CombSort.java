package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsInputBox;
import com.example.javafxsortingalgorithms.animation.AnimatedArrow;
import com.example.javafxsortingalgorithms.animation.AnimatedSection;

import java.util.List;

public class CombSort extends SortingAlgorithm {

    private static final double SECTION_HEIGHT = -10;
    private static final double ARROW_HEIGHT = -20;

    private final double shrinkFactor;

    public CombSort(List<Integer> arrayList, double shrinkFactor) {
        super(arrayList);

        this.shrinkFactor = shrinkFactor;
    }

    @Override
    protected void runAlgorithm() {
        int gapSize = (int) (list.size() / shrinkFactor);

        AnimatedArrow left = animation.createArrow();
        animation.setItemHeight(left, ARROW_HEIGHT);
        animation.setItemIndex(left, 0);

        AnimatedArrow right = animation.createArrow();
        animation.setItemHeight(right, ARROW_HEIGHT);
        animation.setItemIndex(right, gapSize);

        AnimatedSection section = animation.createSection(gapSize + 1);
        animation.setItemHeight(section, SECTION_HEIGHT);
        animation.setItemIndex(section, 0);

        while (!isListSorted(list)) {
            for (int i = 0; i + gapSize < list.size(); i++) {
                animation.moveItem(left, i);
                animation.moveItem(right, i + gapSize);
                animation.moveItem(section, i);
                animation.addFrame();
                animation.readIndex(i);
                animation.readIndex(i + gapSize);
                if (list.get(i) > list.get(i + gapSize)) {
                    animation.addFrame();
                    swap(i, i + gapSize);
                }
                addFrame();
            }
            animation.moveItem(left, 0);
            animation.moveItem(right, gapSize);
            animation.moveItem(section, 0);
            animation.addFrame();
            if (gapSize != 1) gapSize = (int) (gapSize / shrinkFactor);
            animation.changeSectionWidth(section, gapSize + 1);
        }

    }

    // TODO: Figure out how to calculate percentage
    @Override
    protected void instantAlgorithm(TestEntry entry) {
        int gapSize = (int) (list.size() / shrinkFactor);
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
                list -> new CombSort(list, shrinkFactorSetting.getValue()),
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
