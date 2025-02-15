package com.example.javafxsortingalgorithms.algorithms.algorithmsettings;

import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class FunctionalAlgorithmSettings<T extends SortingAlgorithm> extends AlgorithmSettings {

    // TODO: Need integer and double input boxes
    private final BiFunction<List<Integer>, Boolean, T> createAlgorithm;
    private T algorithm;
    private final List<AlgorithmSettingObject> settings;

    public FunctionalAlgorithmSettings(String algorithmName, BiFunction<List<Integer>, Boolean, T> createAlgorithm, AlgorithmSettingObject... settings) {
        super(algorithmName);

        this.createAlgorithm = createAlgorithm;
        this.settings = new ArrayList<>();

        for (AlgorithmSettingObject setting : settings) {
            this.settings.add(setting);
            setting.add(this);
        }

        addSetting(buildResetButton());

        resetSettings();
    }

    @Override
    public void resetSettings() {
        super.resetSettings();

        if (settings == null) return;
        for (AlgorithmSettingObject setting : settings) {
            setting.resetSetting();
        }
    }

    @Override
    public T createAlgorithm(List<Integer> array) {
        return algorithm = createAlgorithm.apply(array, false);
    }

    @Override
    public T createInstantAlgorithm(List<Integer> array) {
        return algorithm = createAlgorithm.apply(array, true);
    }

    @Override
    public T getAlgorithm() {
        return algorithm;
    }

    @Override
    public void resetAlgorithm() {
        algorithm = null;
    }
}
