package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;

import java.util.Arrays;
import java.util.List;

public class GravitySort extends SortingAlgorithm {

    private boolean checkingArray;
    private int counter;
    private final boolean[][] booleanArray;

    public GravitySort(List<Integer> list, boolean isInstant) {
        super(list, isInstant);

        // We are assuming the max value is <= list.size();
        booleanArray = new boolean[list.size()][list.size()];

        for (int row = 0; row < list.size(); row++) {
            for (int col = 0; col < list.size(); col++) {
                if (list.get(col) > row) {
                    booleanArray[list.size() - 1 - row][col] = true;
                }
            }
        }
        checkingArray = true;
        counter = 0;
    }

    // TODO: Doesn't sort properly, overwrote some values wrong
    @Override
    protected void runAlgorithm(ArrayDisplay display) {
        if (isListSorted(list)) {
            isDone = true;
            return;
        }

        if (checkingArray) {
            for (int row = 0; row < booleanArray.length; row++) {
                for (int i = booleanArray[row].length - 1; i > 0; i--) {
                    if (!booleanArray[row][i] && booleanArray[row][i - 1]) {
                        booleanArray[row][i - 1] = false;
                        booleanArray[row][i] = true;
                    }
                }
            }
            checkingArray = false;
            counter = 0;
        } else {
            int c = 0;
            for (boolean[] booleans : booleanArray) {
                if (booleans[counter]) {
                    c++;
                }
            }
            list.set(counter, c);
            counter++;
            if (counter >= list.size()) {
                checkingArray = true;
            }
        }
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

    }

    @Override
    public String getName() {
        return null;
    }

    public void printBooleanArray() {
        for (int i = 0; i < booleanArray.length; i++) {
            for (int j = 0; j < booleanArray[i].length; j++) {
                System.out.print(booleanArray[i][j] ? "#" : " ");
            }
            System.out.println();
        }
    }
}
