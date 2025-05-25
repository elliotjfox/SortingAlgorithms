package com.example.javafxsortingalgorithms.arraydisplay;

import java.util.List;
import java.util.Map;

public class DisplayFrame {

    private final List<Integer> list;
    private final Map<Integer, ArrayDisplay.ColourAction> colourActions;

    public DisplayFrame(List<Integer> list, Map<Integer, ArrayDisplay.ColourAction> colourActions) {
        this.list = list;
        this.colourActions = colourActions;
    }

    public List<Integer> getList() {
        return list;
    }

    public Map<Integer, ArrayDisplay.ColourAction> getColourActions() {
        return colourActions;
    }
}
