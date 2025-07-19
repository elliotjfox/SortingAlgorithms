package com.example.javafxsortingalgorithms.algorithmupdates;

import java.util.List;

public interface ListUpdate extends AlgorithmUpdate {
    void performChange(List<?> list);

    static <T> void swap(List<T> list, int firstIndex, int secondIndex) {
        list.set(firstIndex, list.set(secondIndex, list.get(firstIndex)));
    }

    static <T> void move(List<T> list, int index, int targetIndex) {
        list.add(targetIndex, list.remove(index));
    }
}
