package com.example.javafxsortingalgorithms.algorithms.algorithmsettings;

import com.example.javafxsortingalgorithms.algorithms.BitonicSort;
import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import javafx.scene.control.CheckBox;

import java.util.List;

public class BitonicSettings extends AlgorithmSettings {

    private BitonicSort bitonicSort;

    private boolean fastMode;

    private final CheckBox fastModeCheckBox;

    public BitonicSettings() {
        super("Bitonic Sort");

        fastModeCheckBox = new CheckBox();
        fastModeCheckBox.selectedProperty().addListener(((observable, oldValue, newValue) -> fastMode = fastModeCheckBox.isSelected()));
        addSetting("Fast Mode", fastModeCheckBox);
        addSetting(buildResetButton());

    }

    @Override
    public void resetSettings() {
        super.resetSettings();

        fastMode = false;

        if (fastModeCheckBox != null) fastModeCheckBox.setSelected(false);
    }

    @Override
    public SortingAlgorithm createAlgorithm(List<Integer> array) {
        return bitonicSort = new BitonicSort(array, false, fastMode);
    }

    @Override
    public SortingAlgorithm createInstantAlgorithm(List<Integer> array) {
        return bitonicSort = new BitonicSort(array, true, fastMode);
    }

    @Override
    public SortingAlgorithm getAlgorithm() {
        return bitonicSort;
    }

    @Override
    public void resetAlgorithm() {
        bitonicSort = null;
    }
}
