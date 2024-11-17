package com.example.javafxsortingalgorithms.betteralgorithms;

import java.util.List;

public abstract class AlgorithmBlock {

    protected final List<Integer> list;

    public AlgorithmBlock(List<Integer> list) {
        this.list = list;
    }

    public abstract void execute();
}
