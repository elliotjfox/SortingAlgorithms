package com.example.javafxsortingalgorithms.algorithms.algorithmsettings;

import com.example.javafxsortingalgorithms.algorithms.PancakeSort;
import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import javafx.scene.control.CheckBox;

import java.util.List;

public class PancakeSortSettings extends AlgorithmSettings {

    private PancakeSort pancakeSort;

    private boolean instantFlips;

    private final CheckBox instantFlipCheckBox;

    public PancakeSortSettings() {
        super("Pancake Sort");

        instantFlipCheckBox = new CheckBox();
        instantFlipCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> instantFlips = instantFlipCheckBox.isSelected());
        addSetting("Instant Flip", instantFlipCheckBox);
        addSetting(buildResetButton());
    }

    @Override
    public void resetSettings() {
        super.resetSettings();

        instantFlips = false;

        if (instantFlipCheckBox != null) instantFlipCheckBox.setSelected(false);
    }

    @Override
    public SortingAlgorithm createAlgorithm(List<Integer> array) {
        return pancakeSort = new PancakeSort(array, false, instantFlips);
    }

    @Override
    public SortingAlgorithm createInstantAlgorithm(List<Integer> array) {
        return pancakeSort = new PancakeSort(array, true, instantFlips);
    }

    @Override
    public SortingAlgorithm getAlgorithm() {
        return pancakeSort;
    }

    @Override
    public void resetAlgorithm() {
        pancakeSort = null;
    }
}
