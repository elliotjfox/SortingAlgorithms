package com.example.javafxsortingalgorithms.algorithms.algorithmsettings;

import com.example.javafxsortingalgorithms.algorithms.ShellSort;
import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import com.example.javafxsortingalgorithms.settings.GeneralInputBox;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.util.List;

public class ShellSortSettings extends AlgorithmSettings {

    private ShellSort shellSort;

    private double shrinkFactor;
    private ShellSort.ShellSortMode shellSortMode;

    private final GeneralInputBox<Double> shrinkFactorInput;
    private final ComboBox<ShellSort.ShellSortMode> shellSortModeSelector;

    public ShellSortSettings() {
        super("Shell Sort");

        shellSortModeSelector = new ComboBox<>(FXCollections.observableArrayList(ShellSort.ShellSortMode.values()));
        shellSortModeSelector.getSelectionModel().select(shellSortMode);
        shellSortModeSelector.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> shellSortMode = newValue);
        addSetting("Shell Sort Mode", shellSortModeSelector);

//        searchTypeSelector = new ComboBox<>(FXCollections.observableArrayList(ShellSort.SearchType.values()));
//        searchTypeSelector.getSelectionModel().select(searchType);
//        searchTypeSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            searchType = newValue;
//        });

        shrinkFactorInput = new GeneralInputBox<>(
                () -> shrinkFactor,
                (s) -> {
                    try {
                        shrinkFactor = Double.parseDouble(s);
                    } catch (Exception e) {
                        System.out.println("Bad input");
                    }
                }
        );

//        addSetting("Search Type", searchTypeSelector);
        addSetting("Shrink Factor", shrinkFactorInput);
        addSetting(buildResetButton());

    }

    @Override
    public void resetSettings() {
        super.resetSettings();

//        searchType = ShellSort.SearchType.RIGHT_LINEAR;
        shrinkFactor = 2.3;
        shellSortMode = ShellSort.ShellSortMode.ONE_BY_ONE;

//        if (searchTypeSelector != null) searchTypeSelector.getSelectionModel().select(searchType);
        if (shrinkFactorInput != null) shrinkFactorInput.resetValue();
        if (shellSortModeSelector != null) shellSortModeSelector.getSelectionModel().select(shellSortMode);
    }

    @Override
    public SortingAlgorithm createAlgorithm(List<Integer> array) {
        return shellSort = new ShellSort(array, false, shrinkFactor, shellSortMode);
    }

    @Override
    public SortingAlgorithm createInstantAlgorithm(List<Integer> array) {
        return shellSort = new ShellSort(array, true, shrinkFactor, shellSortMode);
    }

    @Override
    public SortingAlgorithm getAlgorithm() {
        return shellSort;
    }

    @Override
    public void resetAlgorithm() {
        shellSort = null;
    }
}
