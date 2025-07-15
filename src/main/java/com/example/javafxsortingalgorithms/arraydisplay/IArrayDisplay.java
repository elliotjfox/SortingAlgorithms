package com.example.javafxsortingalgorithms.arraydisplay;

import java.util.List;

public interface IArrayDisplay {
    void initializeSettings(DisplaySettings settings);
    void initializeElements(List<Integer> list);
    void displayList(List<Integer> list);
}
