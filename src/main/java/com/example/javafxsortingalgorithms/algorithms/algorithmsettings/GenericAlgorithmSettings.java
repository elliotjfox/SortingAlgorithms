package com.example.javafxsortingalgorithms.algorithms.algorithmsettings;

import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;

import java.util.List;
import java.util.function.BiFunction;

public class GenericAlgorithmSettings<T extends SortingAlgorithm> extends AlgorithmSettings {

    private final BiFunction<List<Integer>, Boolean, T> createAlgorithm;

    private T algorithm;

    public GenericAlgorithmSettings(String algorithmName, BiFunction<List<Integer>, Boolean, T>  createAlgorithm) {
        super(algorithmName);

        addSetting(buildResetButton());

        this.createAlgorithm = createAlgorithm;
    }

    public SortingAlgorithm createAlgorithm(List<Integer> array) {
        return algorithm = createAlgorithm.apply(array, false);
    }

    @Override
    public SortingAlgorithm createInstantAlgorithm(List<Integer> array) {
        return algorithm = createAlgorithm.apply(array, true);
    }

    public SortingAlgorithm getAlgorithm() {
        return algorithm;
    }

    @Override
    public void resetAlgorithm() {
        algorithm = null;
    }
}
