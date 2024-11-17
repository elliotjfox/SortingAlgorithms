package com.example.javafxsortingalgorithms.betteralgorithms;

import java.util.ArrayList;
import java.util.List;

public class LoopBlock extends AlgorithmBlock {

    private final int from;
    private final int to;
    List<IndexedAlgorithmBlock> blocks;

    public LoopBlock(List<Integer> list, int from, int to, IndexedAlgorithmBlock... blocks) {
        super(list);
        this.from = from;
        this.to = to;
        this.blocks = new ArrayList<>(List.of(blocks));
    }

    @Override
    public void execute() {
        for (int i = to; i < from; i++) {
            for (IndexedAlgorithmBlock block : blocks) {
                block.executeAt(i);
            }
        }
    }
}
