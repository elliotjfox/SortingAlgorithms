package com.example.javafxsortingalgorithms.arraydisplay;

import java.util.ArrayList;
import java.util.List;

public record DisplayFrame(List<ListChange> list) {
    public DisplayFrame(List<ListChange> list) {
        this.list = new ArrayList<>(list);
    }

}
