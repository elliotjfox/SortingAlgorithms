package com.example.javafxsortingalgorithms.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PatienceSort extends SortingAlgorithm {

    public PatienceSort(List<Integer> list) {
        super(list);
    }

    @Override
    protected void runAlgorithm() {
        List<Integer> piles = new ArrayList<>();
        piles.add(0);

        // The first number is already in a pile
        for (int i = 1; i < list.size(); i++) {
            boolean added = false;
            // TODO: Replace with binary search
            for (int j = 0; j < piles.size(); j++) {
                if (list.get(i) < list.get(piles.get(j))) {
                    move(i, piles.get(j) + 1);
                    incrementFollowing(piles, j);
                    added = true;
                    break;
                }
                addFrame();
            }

            // Number is already in the correct position
            if (!added) {
                piles.add(i);
            }
            addFrame();
        }

        for (int i = 0; i < list.size(); i++) {
            int smallest = 0;
            for (int j = 1; j < piles.size(); j++) {
                if (list.get(piles.get(j)) < list.get(piles.get(smallest))) {
                    smallest = j;
                }
                addFrame();
            }
            move(piles.get(smallest), i);
            addFrame();
            incrementPrevious(piles, smallest);
            removeDuplicates(piles, i);
        }
    }

    private void removeDuplicates(List<Integer> list, int limit) {
        for (int i = list.size() - 2; i >= 0; i--) {
            if (Objects.equals(list.get(i), list.get(i + 1)) || list.get(i) <= limit) {
                list.remove(i);
            }
        }
    }

    private void incrementFollowing(List<Integer> list, int index) {
        for (int i = index; i < list.size(); i++) {
            increment(list, i);
        }
    }

    private void incrementPrevious(List<Integer> list, int index) {
        for (int i = 0; i < index; i++) {
            increment(list, i);
        }
    }

    private void decrementFollowing(List<Integer> list, int index) {
        for (int i = index; i < list.size(); i++) {
            decrement(list, i);
        }
    }

    private void increment(List<Integer> list, int index) {
        list.set(index, list.get(index) + 1);
    }

    private void decrement(List<Integer> list, int index) {
        list.set(index, list.get(index) - 1);
    }

    @Override
    public String getName() {
        return "";
    }
}
