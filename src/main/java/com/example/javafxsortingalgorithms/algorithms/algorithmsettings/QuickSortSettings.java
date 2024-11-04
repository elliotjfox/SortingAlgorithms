package com.example.javafxsortingalgorithms.algorithms.algorithmsettings;

import com.example.javafxsortingalgorithms.algorithms.QuickSort;
import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.util.List;

public class QuickSortSettings extends AlgorithmSettings {

    private QuickSort quickSort;

    private QuickSort.PartitionType partitionType;

    private final ComboBox<QuickSort.PartitionType> partitionTypeSelector;

    public QuickSortSettings() {
        super("Quick Sort");

        partitionTypeSelector = new ComboBox<>(FXCollections.observableArrayList(QuickSort.PartitionType.values()));
        partitionTypeSelector.getSelectionModel().select(partitionType);
        partitionTypeSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> partitionType = newValue);

        addSetting("Partition Type", partitionTypeSelector);
        addSetting(buildResetButton());

    }

    @Override
    public void resetSettings() {
        super.resetSettings();

        partitionType = QuickSort.PartitionType.LEFT;

        if (partitionTypeSelector != null) partitionTypeSelector.getSelectionModel().select(partitionType);
    }

    @Override
    public SortingAlgorithm createAlgorithm(List<Integer> array) {
        return quickSort = new QuickSort(array, false, partitionType);
    }

    @Override
    public SortingAlgorithm createInstantAlgorithm(List<Integer> array) {
        return quickSort = new QuickSort(array, true, partitionType);
    }

    @Override
    public SortingAlgorithm getAlgorithm() {
        return quickSort;
    }

    @Override
    public void resetAlgorithm() {
        quickSort = null;
    }
}
