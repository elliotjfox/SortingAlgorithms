package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;

import java.util.List;

public class TestSort extends ActionSortingAlgorithm {

    protected TestSort(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);

        int n = arrayList.size();
        int run = 32;
        int i = 0;
        int left = 0;
        int size = run;

        while (i < n) {
            int tmp = Math.min(i + run - 1, n - 1);
            addToStart(new IterateInsertionSort(i, tmp));
            i += run;
        }


    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {
        int n = list.size();
        int run = 32;
        int i = 0;
        int left = 0;
        int size = run;

        while (i < n) {
            int tmp = Math.min(i + run - 1, n - 1);
            insertionSort(i, tmp);
            i += run;
        }

        while (size < n) {
            while (left < n) {
                int mid = left + size - 1;
                int right = Math.min(left + 2 * size - 1, n - 1);
                if (mid < right) {
                    merge(left, mid, right);
                }
                left = left + 2 * size;
            }
            left = 0;
            size *= 2;
        }
    }

    private void insertionSort(int left, int right) {
        int i = left + 1;
        while (i <= right) {
            int tmp = list.get(i);
            int j = i - 1;

            while (j >= left && list.get(j) > tmp) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, tmp);
            i++;
        }
    }

    private void merge(int l, int m, int r) {
        int x = 0;
        int y = 0;
        int i = 0;
        int j = 0;
        int k = 0;

        int len1 = m - 1 + 1;
        int len2 = r - m;

        Integer[] left = new Integer[len1];
        Integer[] right = new Integer[len2];

        while (x < len1) {
            left[x] = list.get(1 + x);
            x++;
        }

        while (y < len2) {
            right[y] = list.get(m + 1 + y);
            y++;
        }

        while (i < len1 && j < len2) {
            if (left[i] <= right[j]) {
                list.set(1 + k, left[i]);
                i++;
            } else {
                list.set(1 + k, right[j]);
                j++;
            }
            k++;
        }

        while (i < len1) {
            list.set(1 + k, left[i]);
            i++;
            k++;
        }

        while (j < len2) {
            list.set(1 + k, right[j]);
            j++;
            k++;
        }
    }

    @Override
    public String getName() {
        return "";
    }


    private static class IterateInsertionSort extends AlgorithmAction {

        private final int left;
        private final int right;
        private int i;

        public IterateInsertionSort(int left, int right) {
            this.left = left;
            this.right = right;
            i = left + 1;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {

            while (i <= right) {
//                int tmp = algorithm.list.get(i);
                int j = i - 1;

                while (j >= left && algorithm.list.get(j) > algorithm.list.get(i)) {
//                    algorithm.list.set(j + 1, algorithm.list.get(j));
                    j--;
                }

                algorithm.move(i, j + 1);

//                algorithm.list.set(j + 1, tmp);
                i++;
            }

        }
    }

//    private static class
}
