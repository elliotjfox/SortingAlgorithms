package com.example.javafxsortingalgorithms.arraydisplay;

import java.util.ArrayList;
import java.util.List;

public record DisplayFrame(List<Integer> list) {
    public DisplayFrame(List<Integer> list) {
        this.list = new ArrayList<>(list);
    }
}
