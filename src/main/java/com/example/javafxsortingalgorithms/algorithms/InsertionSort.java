package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsComboBox;
import com.example.javafxsortingalgorithms.animation.AnimatedArrow;
import com.example.javafxsortingalgorithms.animation.AnimatedSection;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledIndex;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledPosition;

import java.util.List;
import java.util.Objects;

public class InsertionSort extends SortingAlgorithm {

    private static final double SECTION_HEIGHT = -10;
    private static final double ARROW_HEIGHT = 0;

    public enum SearchType {
        LEFT_LINEAR,
        RIGHT_LINEAR,
        BINARY;

        private static final String DESCRIPTION = "Select which search algorithm this insertion sort will use to insert each element.";
    }

    private final SearchType searchType;

    public InsertionSort(List<Integer> arrayList, SearchType searchType) {
        super(arrayList);

        this.searchType = searchType;
    }

    @Override
    protected void runAlgorithm() {
        switch (searchType) {
            case RIGHT_LINEAR -> rightSearch();
            case LEFT_LINEAR -> leftSearch();
            case BINARY -> binarySearch();
        }
    }

    private void rightSearch() {
        AnimatedSection searchSection = animation.createSection(new ElementScaledIndex(0));
        animation.setItemPosition(searchSection, new ElementScaledPosition(0, SECTION_HEIGHT));

        AnimatedArrow arrow = animation.createArrow();
        animation.setItemPosition(arrow, new ElementScaledPosition(0, ARROW_HEIGHT));

        for (int i = 1; i < list.size(); i++) {
            trial.setProgress(i, list.size());
            int j = i - 1;
            animation.changeItemX(arrow, new ElementScaledIndex(i));
            animation.changeSectionWidth(searchSection, new ElementScaledIndex(j + 1));
            animation.addFrame();

            while (j >= 0) {
                // Make sure we play the animations
                animation.readIndex(j);
                animation.readIndex(i);
                animation.addFrame();
                trial.addRead(2);
                trial.addComparison();
                if (list.get(j) <= list.get(i)) break;
                j--;
                animation.changeSectionWidth(searchSection, new ElementScaledIndex(j + 1));
                addFrame();
            }
            move(i, j + 1);
            if (i != j + 1) addFrame();
        }
    }

    private void leftSearch() {
        AnimatedSection searchSection = animation.createSection(new ElementScaledIndex(0));
        animation.setItemPosition(searchSection, new ElementScaledPosition(0, SECTION_HEIGHT));

        AnimatedArrow arrow = animation.createArrow();
        animation.setItemPosition(arrow, new ElementScaledPosition(0, ARROW_HEIGHT));

        for (int i = 1; i < list.size(); i++) {
            trial.setProgress(i, list.size());
            int j = 0;
            animation.changeItemX(arrow, new ElementScaledIndex(i));
            animation.changeItemX(searchSection, new ElementScaledIndex(0));
            animation.changeSectionWidth(searchSection, new ElementScaledIndex(i));
            animation.addFrame();
            while (j < i) {
                animation.readIndex(j);
                animation.readIndex(i);
                animation.addFrame();
                trial.addRead(2);
                trial.addComparison();
                if (list.get(j) >= list.get(i)) break;
                j++;
                animation.changeSectionWidth(searchSection, new ElementScaledIndex(i - j));
                animation.changeItemX(searchSection, new ElementScaledIndex(j));
                addFrame();
            }
            move(i, j);
            if (i != j) addFrame();
        }
    }

    private void binarySearch() {
        AnimatedSection searchSection = animation.createSection(new ElementScaledIndex(0));
        animation.setItemPosition(searchSection, new ElementScaledPosition(0, SECTION_HEIGHT));

        AnimatedArrow arrow = animation.createArrow();
        animation.setItemPosition(arrow, new ElementScaledPosition(0, ARROW_HEIGHT));

        for (int i = 1; i < list.size(); i++) {
            trial.setProgress(i, list.size());
            int left = 0;
            int right = i - 1;
            animation.changeItemX(arrow, new ElementScaledIndex(i));
            animation.changeItemX(searchSection, new ElementScaledIndex(left));
            animation.changeSectionWidth(searchSection, new ElementScaledIndex(right - left + 1));
            animation.addFrame();
            int position = left;
            while (left <= right) {
                int mid = (left + right) / 2;
                animation.readIndex(i);
                animation.readIndex(mid);
                animation.addFrame();
                trial.addRead(2);
                trial.addComparison();
                if (Objects.equals(list.get(i), list.get(mid))) {
                    position = mid + 1;
                    break;
                } else if (list.get(i) > list.get(mid)) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
                animation.changeSectionWidth(searchSection, new ElementScaledIndex(right - left + 1));
                animation.changeItemX(searchSection, new ElementScaledIndex(left));
                position = left;
                addFrame();
            }

            move(i, position);
            if (i != position) addFrame();
        }
    }

    @Override
    public String getName() {
        return switch (searchType) {
            case LEFT_LINEAR -> "Insertion Sort\nLeft Search";
            case RIGHT_LINEAR -> "Insertion Sort\nRight Search";
            case BINARY -> "Insertion Sort\nBinary Search";
        };
    }

    public static AlgorithmSettings<InsertionSort> getSettings() {
        AlgorithmSettingsComboBox<SearchType> searchSetting = new AlgorithmSettingsComboBox<>("Search Type", SearchType.DESCRIPTION, SearchType.values(), SearchType.RIGHT_LINEAR);

        return new AlgorithmSettings<>(
                "Insertion Sort",
                list -> new InsertionSort(list, searchSetting.getValue()),
                searchSetting
        );
    }

    // Right linear search
//    void insertionSort() {
//        for (int i = 1; i < list.size(); i++) {
//            int j = i - 1;
//            while (j >= 0 && list.get(j) > list.get(i)) {
//                j--;
//            }
//            move(i, j + 1);
//        }
//    }

}
