package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsComboBox;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsInputBox;

import java.util.ArrayList;
import java.util.List;

public class BucketSort extends SortingAlgorithm {

    public enum BucketSortSubAlgorithm {
        INSERTION,
        SELECTION
    }

    private final int numberOfBuckets;
    private final BucketSortSubAlgorithm subAlgorithm;

    public BucketSort(List<Integer> list, int numberOfBuckets, BucketSortSubAlgorithm subAlgorithm) {
        super(list);

        this.numberOfBuckets = numberOfBuckets;
        this.subAlgorithm = subAlgorithm;
    }

    @Override
    protected void runAlgorithm() {
        int maxValue = list.getFirst();
        for (int i : list) {
            if (i > maxValue) maxValue = i;
            addFrame();
        }

        int bucketSize = (int) Math.ceil((double) (maxValue + 1) / numberOfBuckets);
        List<Integer> bucketEnd = new ArrayList<>(numberOfBuckets);
        for (int i = 0; i < numberOfBuckets; i++) bucketEnd.add(0);

        for (int i = 0; i < list.size(); i++) {
            int bucket = list.get(i) / bucketSize;

            move(i, bucketEnd.get(bucket));
            incrementFollowing(bucketEnd, bucket);
            addFrame();
        }

        for (int i = 0; i < bucketEnd.size(); i++) {
            int lower = i == 0 ? 0 : bucketEnd.get(i - 1);
            switch (subAlgorithm) {
                case INSERTION -> insertionSort(lower, bucketEnd.get(i));
                case SELECTION -> selectionSort(lower, bucketEnd.get(i));
            }
        }
    }

    private void incrementFollowing(List<Integer> list, int index) {
        for (int i = index; i < list.size(); i++) {
            increment(list, i);
        }
    }

    private void increment(List<Integer> list, int index) {
        list.set(index, list.get(index) + 1);
        addFrame();
    }

    // [from, to)
    private void insertionSort(int from, int to) {
        for (int i = from + 1; i < to && i < list.size(); i++) {
            int j = i - 1;
            while (j >= from) {
                if (list.get(j) <= list.get(i)) break;
                j--;
                addFrame();
            }
            move(i, j + 1);
            addFrame();
        }
    }

    // [from, to)
    private void selectionSort(int from, int to) {
        for (int i = from; i < to && i < list.size(); i++) {
            int min = i;
            for (int j = i + 1; j < to; j++) {
                if (list.get(j) < list.get(min)) {
                    min = j;
                }
                addFrame();
            }
            swap(i, min);
            addFrame();
        }
    }

    @Override
    public String getName() {
        return "Bucket Sort";
    }

    public static AlgorithmSettings<BucketSort> getSettings() {
        AlgorithmSettingsInputBox<Integer> numberOfBucketsSetting = new AlgorithmSettingsInputBox<>(
                "Number of Buckets", 4,
                Integer::parseInt, i -> i > 0
        );

        AlgorithmSettingsComboBox<BucketSortSubAlgorithm> subAlgorithmSetting = new AlgorithmSettingsComboBox<>(
                "Sub Algorithm", BucketSortSubAlgorithm.values(), BucketSortSubAlgorithm.INSERTION
        );

        return new AlgorithmSettings<>(
                "Bucket Sort",
                list -> new BucketSort(list, numberOfBucketsSetting.getValue(), subAlgorithmSetting.getValue()),
                numberOfBucketsSetting, subAlgorithmSetting
        );
    }
}
