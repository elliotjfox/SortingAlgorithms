package com.example.javafxsortingalgorithms;

public enum TrialInfo {
    NAME(0),
    NUMBER(1),
    SIZE(2),
    READ_COUNT(3),
    WRITE_COUNT(4),
    COMPARISON_COUNT(5),
    PROGRESS(6),
    TIME(7);

    private final int col;

    TrialInfo(int col) {
        this.col = col;
    }

    public int getCol() {
        return col;
    }
}
