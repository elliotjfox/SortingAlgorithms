package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.algorithmupdates.AlgorithmUpdate;

import java.util.ArrayList;
import java.util.List;

public record DisplayFrame(List<AlgorithmUpdate> list) {
    public DisplayFrame(List<AlgorithmUpdate> list) {
        this.list = new ArrayList<>(list);
    }
}
