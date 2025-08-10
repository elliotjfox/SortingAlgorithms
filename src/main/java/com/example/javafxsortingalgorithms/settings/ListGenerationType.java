package com.example.javafxsortingalgorithms.settings;

import java.util.ArrayList;
import java.util.List;

public enum ListGenerationType {
    DEFAULT("Default", new FormulaListGenerator((i, _) -> i)),
    REVERSED("Reversed", size -> {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        return list.reversed();
    }),
    RANDOM("Random", new FormulaListGenerator((_, size) -> Settings.random.nextInt(size))),
    EXPONENTIAL("Exponential", new FormulaListGenerator((i, _) -> i * i)),
    SQUARE_ROOT("Square Root", new FormulaListGenerator((i, _) -> (int) Math.round(Math.sqrt(i)))),
    LOGARITHMIC("Logarithmic", new FormulaListGenerator((i, _) -> (int) Math.round(Math.log(i))));

    private final String name;
    private final ListGenerator generator;

    ListGenerationType(String name, ListGenerator generator) {
        this.name = name;
        this.generator = generator;
    }

    public String getName() {
        return name;
    }

    public ListGenerator getGenerator() {
        return generator;
    }
}
