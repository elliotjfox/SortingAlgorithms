package com.example.javafxsortingalgorithms.algorithms.algorithmsettings;

import com.example.javafxsortingalgorithms.algorithms.SelectionSort;
import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.util.List;

public class SelectionSortSettings extends AlgorithmSettings {

    private SelectionSort selectionSort;

    private SelectionSort.SelectionMode mode;

    private final ComboBox<SelectionSort.SelectionMode> selectionModeSelector;

    public SelectionSortSettings() {
        super("Selection Sort");

        selectionModeSelector = new ComboBox<>(FXCollections.observableArrayList(SelectionSort.SelectionMode.values()));
        selectionModeSelector.getSelectionModel().select(mode);
        selectionModeSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> mode = newValue);
        addSetting("Selection Mode", selectionModeSelector);
        addSetting(buildResetButton());

    }

    @Override
    public void resetSettings() {
        super.resetSettings();

        mode = SelectionSort.SelectionMode.MIN;

        if (selectionModeSelector != null) selectionModeSelector.getSelectionModel().select(mode);
    }

    @Override
    public SortingAlgorithm createAlgorithm(List<Integer> array) {
        return selectionSort = new SelectionSort(array, false, mode);
    }

    @Override
    public SortingAlgorithm createInstantAlgorithm(List<Integer> array) {
        return selectionSort = new SelectionSort(array, false, mode);
    }

    @Override
    public SortingAlgorithm getAlgorithm() {
        return selectionSort;
    }

    @Override
    public void resetAlgorithm() {
        selectionSort = null;
    }
}
