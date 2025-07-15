package com.example.javafxsortingalgorithms.arraydisplay;

import java.util.List;

public class SwapChange implements ListChange {

    private final int firstIndex;
    private final int secondIndex;

    public SwapChange(int firstIndex, int secondIndex) {
        this.firstIndex = firstIndex;
        this.secondIndex = secondIndex;
    }

    @Override
    public void performChange(List<Integer> list) {
        list.set(firstIndex, list.set(secondIndex, list.get(firstIndex)));
    }

    @Override
    public String toString() {
        return "swap(" + firstIndex + ", " + secondIndex + ")";
    }
}
