package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsCheckBox;
import com.example.javafxsortingalgorithms.animation.AnimatedArrow;
import com.example.javafxsortingalgorithms.animation.AnimatedSection;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledHeight;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledIndex;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledPosition;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.List;

public class MergeSort extends SortingAlgorithm {

    private static final double SECTION_OFFSET = 25 * Math.sin(Math.toRadians(60)) + 10;
    private static final double SECTION_DISTANCE = 15;
    private static final Paint COMPLETED_COLOUR = Color.rgb(44, 199, 88);

    public MergeSort(List<Integer> arrayList, boolean inPlace) {
        super(arrayList);
    }

    @Override
    protected void runAlgorithm() {
        AnimatedSection section = animation.createSection(new ElementScaledIndex(list.size()));
        animation.setItemPosition(section, new ElementScaledPosition(0, -SECTION_OFFSET));
        divide(0, list.size(), -SECTION_OFFSET, section);
        animation.removeItem(section);
        animation.addFrame();
    }

    // [from, to)
    private void divide(int from, int to, double height, AnimatedSection parentSection) {
        AnimatedSection leftSection = null;
        AnimatedSection rightSection = null;
        if (to - from >= 2) {
            int half = (from + to) / 2;
            leftSection = animation.createSection(new ElementScaledIndex(half - from));
            rightSection = animation.createSection(new ElementScaledIndex(to - half));
            animation.setItemPosition(leftSection, new ElementScaledPosition(from, height));
            animation.setItemPosition(rightSection, new ElementScaledPosition(from, height));

            animation.changeItemPosition(leftSection, new ElementScaledPosition(from, height - SECTION_DISTANCE));
            animation.changeItemPosition(rightSection, new ElementScaledPosition(half, height - SECTION_DISTANCE));
            animation.addFrame();

            divide(from, half, height - SECTION_DISTANCE, leftSection);
            divide(half, to, height - SECTION_DISTANCE, rightSection);
            merge(from, to);

            animation.changeSectionWidth(leftSection, new ElementScaledIndex(to - from));
            animation.changeSectionWidth(rightSection, new ElementScaledIndex(to - from));
            animation.changeItemX(rightSection, new ElementScaledIndex(from));
            animation.addFrame();
            animation.changeItemY(leftSection, new ElementScaledHeight(height));
            animation.changeItemY(rightSection, new ElementScaledHeight(height));
        }
        animation.changeItemFill(parentSection, COMPLETED_COLOUR);
        animation.addFrame();
        animation.removeItem(leftSection);
        animation.removeItem(rightSection);
    }

    private void merge(int left, int end) {
        int right = (left + end) / 2;

        AnimatedArrow leftArrow = animation.createArrow();
        animation.setItemPosition(leftArrow, new ElementScaledPosition(left, 0));
        AnimatedArrow rightArrow = animation.createArrow();
        animation.setItemPosition(rightArrow, new ElementScaledPosition(right, 0));

        while (right < end && left < right) {
            animation.changeItemX(leftArrow, new ElementScaledIndex(left));
            animation.changeItemX(rightArrow, new ElementScaledIndex(right));
            animation.addFrame();
            animation.readIndex(left);
            animation.readIndex(right);
            trial.addRead(2);
            trial.addComparison();
            if (list.get(left) >= list.get(right)) {
                animation.addFrame();
                move(right, left);
                right++;
            }
            left++;
            addFrame();
        }

        animation.removeItem(leftArrow);
        animation.removeItem(rightArrow);
    }

    @Override
    public String getName() {
        return "Merge Sort";
    }

    public static AlgorithmSettings<MergeSort> getSettings() {
        AlgorithmSettingsCheckBox inPlaceSetting = new AlgorithmSettingsCheckBox("Merge In Place", true);

        return new AlgorithmSettings<>(
                "Merge Sort",
                list -> new MergeSort(list, inPlaceSetting.getValue()),
                inPlaceSetting
        );
    }

//    void mergeSort() {
//        divide(0, list.size());
//    }
//
//    void divide(int min, int max) {
//        if (max - min >= 2) {
//            int half = (min + max) / 2;
//            divide(min, half);
//            divide(half, max);
//            merge(min, half, max);
//        }
//    }
//
//    void merge(int left, int right, int end) {
//        while (left < right && right < end) {
//            if (list.get(left) <= list.get(right)) {
//                left++;
//            } else {
//                move(right, left);
//                right++;
//            }
//        }
//    }
}
