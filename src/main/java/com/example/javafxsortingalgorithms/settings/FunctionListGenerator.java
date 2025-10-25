package com.example.javafxsortingalgorithms.settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunctionListGenerator implements ListGenerator {

    private final GenerateListInteger generator;
    private boolean shufflesList;
    private boolean scalesIntegers;

    public FunctionListGenerator(GenerateListInteger generator) {
        this.generator = generator;
        shufflesList = true;
        scalesIntegers = false;
    }

    public FunctionListGenerator shuffles(boolean shuffles) {
        this.shufflesList = shuffles;
        return this;
    }

    public FunctionListGenerator scales(boolean scales) {
        this.scalesIntegers = scales;
        return this;
    }

    @Override
    public List<Integer> generate(int size) {
        double max = 0;
        List<Double> rawList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            double num = generator.get(i, size);
            if (num < 0) num = 0;
            if (num > max) max = num;
            rawList.add(num);
        }
        if (shufflesList) {
            Collections.shuffle(rawList);
        }
        if (scalesIntegers) {
            double scaleFactor = (double) size / max;
            return new ArrayList<>(rawList.stream().map(i -> (int) Math.round(i * scaleFactor)).toList());
        } else {
            return new ArrayList<>(rawList.stream().map(i -> (int) Math.round(i)).toList());
        }
    }
}
