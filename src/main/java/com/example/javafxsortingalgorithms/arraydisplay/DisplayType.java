package com.example.javafxsortingalgorithms.arraydisplay;

import java.util.function.Supplier;

public enum DisplayType {
    COLOURFUL(BasicArrayDisplay::new),
    HEATMAP(HeatMapDisplay::new);

    private final Supplier<? extends ArrayDisplayBase> createDisplay;

    DisplayType(Supplier<? extends ArrayDisplayBase> createDisplay) {
        this.createDisplay = createDisplay;
    }

    public ArrayDisplayBase createDisplay() {
        return createDisplay.get();
    }
}
