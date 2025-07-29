package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsComboBox;
import com.example.javafxsortingalgorithms.newanimation.NewAnimatedArrow;
import com.example.javafxsortingalgorithms.newanimation.NewAnimatedSection;

import java.util.List;
import java.util.Objects;

public class InsertionSort extends SortingAlgorithm {

    private static final double SECTION_HEIGHT = -10;
    private static final double ARROW_HEIGHT = 0;

    public enum SearchType {
        LEFT_LINEAR("Left-most index", "Left-most value"),
        RIGHT_LINEAR("Right-most index", "Right-most value"),
        BINARY("Index", "Value");

        private static final String description = "Select which search algorithm this insertion sort will use to insert each element.";

        private final String index;
        private final String value;

        SearchType(String index, String value) {
            this.index = index;
            this.value = value;
        }
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
        NewAnimatedSection searchSection = animation.createSection(0);
        NewAnimatedArrow arrow = animation.createArrow();
        animation.setItemIndex(searchSection, 0);
        animation.setItemIndex(arrow, 0);
        animation.setItemHeight(searchSection, SECTION_HEIGHT);
        animation.setItemHeight(arrow, ARROW_HEIGHT);
        for (int i = 1; i < list.size(); i++) {
            int j = i - 1;
            animation.moveItem(arrow, i);
            animation.changeSectionWidth(searchSection, j + 1);
            animation.addFrame();

            while (j >= 0) {
                // Make sure we play the animations
                animation.readIndex(j);
                animation.readIndex(i);
                animation.addFrame();
                if (list.get(j) <= list.get(i)) break;
                j--;
                animation.changeSectionWidth(searchSection, j + 1);
                addFrame();
            }
            move(i, j + 1);
            if (i != j + 1) addFrame();
        }
    }

    private void leftSearch() {
        NewAnimatedSection searchSection = animation.createSection(0);
        NewAnimatedArrow arrow = animation.createArrow();
        animation.setItemIndex(searchSection, 0);
        animation.setItemIndex(arrow, 0);
        animation.setItemHeight(searchSection, SECTION_HEIGHT);
        animation.setItemHeight(arrow, ARROW_HEIGHT);
        for (int i = 1; i < list.size(); i++) {
            int j = 0;
            animation.moveItem(arrow, i);
            animation.changeSectionWidth(searchSection, i);
            animation.moveItem(searchSection, 0);
            animation.addFrame();
            while (j < i) {
                animation.readIndex(j);
                animation.readIndex(i);
                animation.addFrame();
                if (list.get(j) >= list.get(i)) break;
                j++;
                animation.changeSectionWidth(searchSection, i - j);
                animation.moveItem(searchSection, j);
                addFrame();
            }
            move(i, j);
            if (i != j) addFrame();
        }
    }

    private void binarySearch() {
        NewAnimatedSection searchSection = animation.createSection(0);
        NewAnimatedArrow arrow = animation.createArrow();
        animation.setItemIndex(searchSection, 0);
        animation.setItemIndex(arrow, 0);
        animation.setItemHeight(searchSection, SECTION_HEIGHT);
        animation.setItemHeight(arrow, ARROW_HEIGHT);
        for (int i = 1; i < list.size(); i++) {
            int left = 0;
            int right = i - 1;
            animation.moveItem(arrow, i);
            animation.moveItem(searchSection, left);
            animation.changeSectionWidth(searchSection, right - left + 1);
            animation.addFrame();
            int position = left;
            while (left <= right) {
                int mid = (left + right) / 2;
                animation.readIndex(i);
                animation.readIndex(mid);
                animation.addFrame();
                if (Objects.equals(list.get(i), list.get(mid))) {
                    position = mid + 1;
                    break;
                } else if (list.get(i) > list.get(mid)) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
                animation.changeSectionWidth(searchSection, right - left + 1);
                animation.moveItem(searchSection, left);
                position = left;
                addFrame();
            }

            move(i, position);
            if (i != position) addFrame();
        }
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {
        switch (searchType) {
            case RIGHT_LINEAR -> instantRight(entry);
            case LEFT_LINEAR -> instantLeft(entry);
            case BINARY -> System.out.println("Binary instant not implemented yet");
        }
    }

    private void instantRight(TestEntry entry) {
        // Technically not necessary to start at 0, we could start at 1
        for (int i = 0; i < list.size(); i++) {
            int j = i - 1;
            // I think the > instead of >= makes this stable?
            while (j >= 0 && list.get(j) > list.get(i)) {
                entry.addRead(2);
                j--;
            }
            move(i, j + 1);
            entry.addWrite();
            entry.updateProgress((double) i / list.size());
        }
    }

    private void instantLeft(TestEntry entry) {
        // Technically not necessary to start at 0, we could start at 1
        for (int i = 0; i < list.size(); i++) {
            int j = 0;
            // I think the <= over the < makes this stable?
            while (j < i && list.get(j) <= list.get(i)) {
                entry.addRead(2);
                j++;
            }
            move(i, j);
            entry.addWrite();
            entry.updateProgress((double) i / list.size());
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
        AlgorithmSettingsComboBox<SearchType> searchSetting = new AlgorithmSettingsComboBox<>("Search Type", SearchType.description, SearchType.values(), SearchType.RIGHT_LINEAR);

        return new AlgorithmSettings<>(
                "Insertion Sort",
                (l, b) -> new InsertionSort(l, searchSetting.getValue()),
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
