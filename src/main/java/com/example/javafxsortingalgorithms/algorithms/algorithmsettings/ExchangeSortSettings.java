package com.example.javafxsortingalgorithms.algorithms.algorithmsettings;

import com.example.javafxsortingalgorithms.algorithms.ExchangeSort;
import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.util.List;

public class ExchangeSortSettings extends AlgorithmSettings {

    private ExchangeSort exchangeSort;

    private ExchangeSort.Direction direction = ExchangeSort.Direction.DOWN;

    private ComboBox<ExchangeSort.Direction> directionComboBox;

    public ExchangeSortSettings() {
        super("Exchange Sort");

        directionComboBox = new ComboBox<>(FXCollections.observableArrayList(ExchangeSort.Direction.values()));
        directionComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> direction = newValue);
        directionComboBox.getSelectionModel().select(direction);

        addSetting("Direction", directionComboBox);
        addSetting(buildResetButton());
    }

    @Override
    public void resetSettings() {
        super.resetSettings();

        direction = ExchangeSort.Direction.DOWN;

        if (directionComboBox != null) directionComboBox.getSelectionModel().select(direction);
    }

    @Override
    public SortingAlgorithm createAlgorithm(List<Integer> array) {
        return exchangeSort = new ExchangeSort(array, false, direction);
    }

    @Override
    public SortingAlgorithm createInstantAlgorithm(List<Integer> array) {
        return exchangeSort = new ExchangeSort(array, true, direction);
    }

    @Override
    public SortingAlgorithm getAlgorithm() {
        return exchangeSort;
    }

    @Override
    public void resetAlgorithm() {
        exchangeSort = null;
    }
}
