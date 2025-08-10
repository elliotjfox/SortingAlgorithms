package com.example.javafxsortingalgorithms.settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public class FormulaListGenerator implements ListGenerator {

    private BiFunction<Integer, Integer, Integer> function;

    public FormulaListGenerator(BiFunction<Integer, Integer, Integer> function) {
        this.function = function;
    }

    // TODO: Fix this (expand the input range in some way?)
    @Override
    public List<Integer> generate(int size) {
        int max = 0;
        List<Integer> rawList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int num = function.apply(i, size);
            if (num < 0) num = 0;
            if (num > max) max = num;
            rawList.add(num);
        }
        Collections.shuffle(rawList);
        if (max <= size) {
            return rawList;
        }
        double scaleFactor = (double) size / max;
        // List has already been shuffled
        return new ArrayList<>(rawList.stream().map(i -> (int) Math.round(i * scaleFactor)).toList());
    }
}
