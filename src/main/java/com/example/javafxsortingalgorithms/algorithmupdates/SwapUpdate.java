package com.example.javafxsortingalgorithms.algorithmupdates;

import java.util.List;

public class SwapUpdate implements ListUpdate {

    private final int firstIndex;
    private final int secondIndex;

    public SwapUpdate(int firstIndex, int secondIndex) {
        this.firstIndex = firstIndex;
        this.secondIndex = secondIndex;
    }

    @Override
    public void performChange(List<?> list) {
        ListUpdate.swap(list, firstIndex, secondIndex);
    }

    @Override
    public String toString() {
        return "swap(" + firstIndex + ", " + secondIndex + ")";
    }
}
