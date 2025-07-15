package com.example.javafxsortingalgorithms.arraydisplay;

import java.util.List;

public class MoveChange implements ListChange {

    private final int index;
    private final int targetIndex;

    public MoveChange(int index, int targetIndex) {
        this.index = index;
        this.targetIndex = targetIndex;
    }

    @Override
    public void performChange(List<Integer> list) {
        list.add(targetIndex, list.remove(index));
    }

    @Override
    public String toString() {
        return "move(" + index + ", " + targetIndex + ")";
    }
}
