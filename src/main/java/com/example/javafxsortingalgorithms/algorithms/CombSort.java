package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsInputBox;
import com.example.javafxsortingalgorithms.animation.AnimatedArrow;
import com.example.javafxsortingalgorithms.animation.AnimatedSection;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledIndex;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledPosition;

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
        animation.setItemPosition(left, new ElementScaledPosition(0, ARROW_HEIGHT));

        AnimatedArrow right = animation.createArrow();
        animation.setItemPosition(right, new ElementScaledPosition(gapSize, ARROW_HEIGHT));

        AnimatedSection section = animation.createSection(new ElementScaledIndex(gapSize + 1));
        animation.setItemPosition(section, new ElementScaledPosition(0, SECTION_HEIGHT));

        while (!isListSorted(list)) {
            for (int i = 0; i + gapSize < list.size(); i++) {
                animation.changeItemX(left, new ElementScaledIndex(i));
                animation.changeItemX(right, new ElementScaledIndex(i + gapSize));
                animation.changeItemX(section, new ElementScaledIndex(i));
                animation.addFrame();
                animation.readIndex(i);
                animation.readIndex(i + gapSize);
                if (list.get(i) > list.get(i + gapSize)) {
                    animation.addFrame();
                    swap(i, i + gapSize);
                }
                addFrame();
            }
            animation.changeItemX(left, new ElementScaledIndex(0));
            animation.changeItemX(right, new ElementScaledIndex(gapSize));
            animation.changeItemX(section, new ElementScaledIndex(0));
            animation.addFrame();
            if (gapSize != 1) gapSize = (int) (gapSize / shrinkFactor);
            animation.changeSectionWidth(section, new ElementScaledIndex(gapSize + 1));
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
