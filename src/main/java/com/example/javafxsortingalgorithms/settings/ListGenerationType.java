package com.example.javafxsortingalgorithms.settings;

import java.util.ArrayList;
import java.util.List;

public enum ListGenerationType {
    DEFAULT("Default", new FunctionListGenerator((i, _) -> i)),
    REVERSED("Reversed", size -> {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        return list.reversed();
    }),
    RANDOM("Random",
            new FunctionListGenerator((_, size) -> Settings.random.nextInt(size))
                    .shuffles(false)
    ),
    PYRAMID("Pyramid", new FunctionListGenerator(
            (i, size) -> {
                if (i < size / 2) return i;
                else return size - i;
            })
            .shuffles(false)
    ),
    EXPONENTIAL("Exponential",
            new FunctionListGenerator((i, _) -> i * i)
                    .scales(true)
    ),
    SQUARE_ROOT("Square Root",
            new FunctionListGenerator((i, _) -> Math.sqrt(i))
                    .scales(true)
    ),
    LOGARITHMIC("Logarithmic",
            new FunctionListGenerator((i, _) -> Math.log(i))
                    .scales(true)
    );

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
