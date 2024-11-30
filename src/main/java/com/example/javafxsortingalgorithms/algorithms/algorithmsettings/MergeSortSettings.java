package com.example.javafxsortingalgorithms.algorithms.algorithmsettings;

import com.example.javafxsortingalgorithms.algorithms.MergeSort;
import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import javafx.scene.control.CheckBox;

import java.util.List;

public class MergeSortSettings extends AlgorithmSettings {

    private MergeSort mergeSort;

    private boolean inPlace = true;

    private final CheckBox inPlaceCheckBox;

    public MergeSortSettings() {
        super("Merge Sort");

        inPlaceCheckBox = new CheckBox();
        inPlaceCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> inPlace = newValue);
        inPlaceCheckBox.setSelected(inPlace);
        addSetting("In place", inPlaceCheckBox);

        addSetting(buildResetButton());
    }

    @Override
    public void resetSettings() {
        super.resetSettings();

        inPlace = true;

        if (inPlaceCheckBox != null) inPlaceCheckBox.setSelected(inPlace);
    }

    @Override
    public SortingAlgorithm createAlgorithm(List<Integer> array) {
        return mergeSort = new MergeSort(array, false, inPlace);
    }

    @Override
    public SortingAlgorithm createInstantAlgorithm(List<Integer> array) {
        return mergeSort = new MergeSort(array, true, inPlace);
    }

    @Override
    public SortingAlgorithm getAlgorithm() {
        return mergeSort;
    }

    @Override
    public void resetAlgorithm() {
        mergeSort = null;
    }
}
