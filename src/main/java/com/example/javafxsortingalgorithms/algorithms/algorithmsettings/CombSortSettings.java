package com.example.javafxsortingalgorithms.algorithms.algorithmsettings;

import com.example.javafxsortingalgorithms.algorithms.CombSort;
import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import com.example.javafxsortingalgorithms.settings.GeneralInputBox;

import java.util.List;

public class CombSortSettings extends AlgorithmSettings {

    private CombSort combSort;

    private double shrinkFactor;

    private final GeneralInputBox<Double> shrinkFactorBox;

    public CombSortSettings() {
        super("Comb Sort");

        shrinkFactorBox = new GeneralInputBox<>(() -> shrinkFactor, (s) -> {
            try {
                double num = Double.parseDouble(s);
                if (num <= 1) throw new IllegalArgumentException();
                shrinkFactor = num;
            } catch (Exception e) {
                System.out.println("Please only input numbers larger than 1");
            }
        });
        addSetting("Shrink Factor", shrinkFactorBox);
        addSetting(buildResetButton());

    }

    @Override
    public void resetSettings() {
        super.resetSettings();

        shrinkFactor = 1.3;

        if (shrinkFactorBox != null) shrinkFactorBox.resetValue();
    }

    @Override
    public void resetAlgorithm() {
        combSort = null;
    }

    @Override
    public SortingAlgorithm createAlgorithm(List<Integer> array) {
        return combSort = new CombSort(array, false, shrinkFactor);
    }

    @Override
    public SortingAlgorithm createInstantAlgorithm(List<Integer> array) {
        return combSort = new CombSort(array, true, shrinkFactor);
    }

    @Override
    public SortingAlgorithm getAlgorithm() {
        return combSort;
    }
}
