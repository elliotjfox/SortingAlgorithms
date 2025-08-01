package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithnSettingsCheckBox;
import com.example.javafxsortingalgorithms.animation.AnimatedArrow;
import com.example.javafxsortingalgorithms.animation.AnimatedSection;
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
        AnimatedSection section = animation.createSection(list.size());
        animation.setItemIndex(section, 0);
        animation.setItemHeight(section, -SECTION_OFFSET);
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
            leftSection = animation.createSection(half - from);
            rightSection = animation.createSection(to - half);
            animation.setItemIndex(leftSection, from);
            animation.setItemHeight(leftSection, height);
            animation.setItemIndex(rightSection, from);
            animation.setItemHeight(rightSection, height);

            animation.moveItem(leftSection, from);
            animation.moveItemHeight(leftSection, height - SECTION_DISTANCE);
            animation.moveItem(rightSection, half);
            animation.moveItemHeight(rightSection, height - SECTION_DISTANCE);
            animation.addFrame();

            divide(from, half, height - SECTION_DISTANCE, leftSection);
            divide(half, to, height - SECTION_DISTANCE, rightSection);
            // Merge adds frames
            merge(from, to);

            animation.changeSectionWidth(leftSection, to - from);
            animation.changeSectionWidth(rightSection, to - from);
            animation.moveItem(rightSection, from);
            animation.addFrame();
            animation.moveItemHeight(leftSection, height);
            animation.moveItemHeight(rightSection, height);
        }
        animation.changeItemFill(parentSection, COMPLETED_COLOUR);
        animation.addFrame();
        animation.removeItem(leftSection);
        animation.removeItem(rightSection);
    }

    // TODO: Add inPlace options
    private void merge(int left, int end) {
        int right = (left + end) / 2;

        AnimatedArrow leftArrow = animation.createArrow();
        AnimatedArrow rightArrow = animation.createArrow();
        animation.setItemIndex(leftArrow, left);
        animation.setItemHeight(leftArrow, 0);
        animation.setItemIndex(rightArrow, right);
        animation.setItemHeight(rightArrow, 0);

        while (right < end && left < right) {
            animation.moveItem(leftArrow, left);
            animation.moveItem(rightArrow, right);
            animation.addFrame();
            animation.readIndex(left);
            animation.readIndex(right);

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
    protected void instantAlgorithm(TestEntry entry) {
        divide(0, list.size(), entry);
    }

    // [from, to)
    private void divide(int from, int to, TestEntry entry) {
        if (to - from >= 2) {
            int half = (from + to) / 2;
            divide(from, half, entry);
            divide(half, to, entry);
            merge(from, to, entry);
        }
    }

    private void merge(int left, int end, TestEntry entry) {
        int right = (left + end) / 2;
        while (right < end && left < right) {
            entry.addRead(2);
            if (list.get(left) < list.get(right)) {
                left++;
            } else {
                entry.addWrite(1);
                move(right, left);
                right++;
            }
        }
    }

//    @Override
//    public void startAnimated(AnimatedArrayDisplay display) {
//        AnimatedSection currentSection = new ItemBuilder(display)
//                .at(0, -SECTION_OFFSET)
//                .buildSection(list.size());
//        display.addItem(currentSection);
//    }

    @Override
    public String getName() {
        return "Merge Sort";
    }

    public static AlgorithmSettings<MergeSort> getSettings() {
        AlgorithnSettingsCheckBox inPlaceSetting = new AlgorithnSettingsCheckBox("Merge In Place", true);

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
