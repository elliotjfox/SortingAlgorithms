package com.example.javafxsortingalgorithms.algorithmupdates;

import java.util.List;

public class MoveUpdate implements ListUpdate {

    private final int index;
    private final int targetIndex;

    public MoveUpdate(int index, int targetIndex) {
        this.index = index;
        this.targetIndex = targetIndex;
    }

    @Override
    public void performChange(List<?> list) {
        ListUpdate.move(list, index, targetIndex);
    }

    @Override
    public String toString() {
        return "move(" + index + ", " + targetIndex + ")";
    }
}
