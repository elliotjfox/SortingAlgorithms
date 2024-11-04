package com.example.javafxsortingalgorithms.algorithms.algorithmsettings;

import com.example.javafxsortingalgorithms.algorithms.InsertionSort;
import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.List;

public class InsertionSortSettings extends AlgorithmSettings {

    private InsertionSort insertionSort;

    private InsertionSort.SearchType searchType;

    private final ComboBox<InsertionSort.SearchType> searchTypeSelector;

    private static final String searchTypeInfo = "Select which search algorithm this insertion sort will use to insert each element.";

    public InsertionSortSettings() {
        super("Insertion Sort");

        searchTypeSelector = new ComboBox<>(FXCollections.observableArrayList(InsertionSort.SearchType.values()));
        searchTypeSelector.getSelectionModel().select(searchType);
        searchTypeSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            searchType = newValue;
        });

        addSetting(new Label("Search Type"), searchTypeSelector, searchTypeInfo);
        addSetting(buildResetButton());

    }

    @Override
    public void resetSettings() {
        super.resetSettings();

        searchType = InsertionSort.SearchType.RIGHT_LINEAR;

        if (searchTypeSelector != null) searchTypeSelector.getSelectionModel().select(searchType);
    }

    @Override
    public SortingAlgorithm createAlgorithm(List<Integer> array) {
        return insertionSort = new InsertionSort(array, false, searchType);
    }

    @Override
    public SortingAlgorithm createInstantAlgorithm(List<Integer> array) {
        return insertionSort = new InsertionSort(array, true, searchType);
    }

    @Override
    public SortingAlgorithm getAlgorithm() {
        return insertionSort;
    }

    @Override
    public void resetAlgorithm() {
        insertionSort = null;
    }
}
