package com.example.javafxsortingalgorithms.betteralgorithms;

import java.util.List;

public abstract class IndexedAlgorithmBlock extends AlgorithmBlock {
    public IndexedAlgorithmBlock(List<Integer> list) {
        super(list);
    }

    @Override
    public void execute() {}

    public abstract void executeAt(int i);
}
