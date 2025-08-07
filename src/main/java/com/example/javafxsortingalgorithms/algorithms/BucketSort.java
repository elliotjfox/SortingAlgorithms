package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsComboBox;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsInputBox;
import com.example.javafxsortingalgorithms.algorithmupdates.CreateItemUpdate;
import com.example.javafxsortingalgorithms.animation.AnimatedArrow;
import com.example.javafxsortingalgorithms.animation.AnimatedBucket;
import com.example.javafxsortingalgorithms.animation.position.AnimationPosition;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledHeight;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledIndex;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledPosition;
import com.example.javafxsortingalgorithms.arraydisplay.DisplayMode;

import java.util.ArrayList;
import java.util.List;

public class BucketSort extends SortingAlgorithm {

    private static final int DEFAULT_NUMBER_OF_BUCKETS = 4;
    private static final BucketSortSubAlgorithm DEFAULT_SUB_ALGORITHM = BucketSortSubAlgorithm.INSERTION;

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
            if (mode != DisplayMode.ANIMATED) addFrame();
        }

        int bucketSize = (int) Math.ceil((double) (maxValue + 1) / numberOfBuckets);
        List<Integer> bucketEnd = new ArrayList<>(numberOfBuckets);
        List<AnimatedBucket> buckets = new ArrayList<>();
        for (int i = 0; i < numberOfBuckets; i++) {
            bucketEnd.add(0);
            if (mode == DisplayMode.ANIMATED) {
                AnimatedBucket bucket = new AnimatedBucket(new ElementScaledIndex(0));
                buckets.add(bucket);
                currentChanges.add(new CreateItemUpdate(bucket));
                animation.setItemPosition(bucket, new ElementScaledPosition(0, 0));
            }
        }
        AnimatedArrow arrow = animation.createArrow();
        animation.setItemPosition(arrow, new ElementScaledPosition(0, -1));

        for (int i = 0; i < list.size(); i++) {
            int bucket = list.get(i) / bucketSize;

            animation.changeItemX(arrow, new ElementScaledIndex(i));
            animation.addFrame();
            animation.readIndex(i);
            animation.addFrame();

            move(i, bucketEnd.get(bucket));
            incrementFollowing(bucketEnd, bucket);
            for (int j = 0; j < buckets.size(); j++) {
                int lower = j == 0 ? 0 : bucketEnd.get(j - 1);
                currentChanges.add(buckets.get(j).changeWidth(new ElementScaledIndex(bucketEnd.get(j) - lower)));
                currentChanges.add(buckets.get(j).changeXPosition(new ElementScaledIndex(lower)));
            }
            addFrame();
        }

        animation.removeItem(arrow);

        for (int i = 0; i < bucketEnd.size(); i++) {
            AnimatedBucket bucket = buckets.isEmpty() ? null : buckets.removeFirst();
            animation.changeItemY(bucket, new ElementScaledHeight(-1));
            animation.addFrame();
            int lower = i == 0 ? 0 : bucketEnd.get(i - 1);
            switch (subAlgorithm) {
                case INSERTION -> insertionSort(lower, bucketEnd.get(i));
                case SELECTION -> selectionSort(lower, bucketEnd.get(i));
            }
            animation.removeItem(bucket);
        }
    }

    private void incrementFollowing(List<Integer> list, int index) {
        for (int i = index; i < list.size(); i++) {
            increment(list, i);
        }
    }

    private void increment(List<Integer> list, int index) {
        list.set(index, list.get(index) + 1);
        if (mode != DisplayMode.ANIMATED) addFrame();
    }

    // [from, to)
    private void insertionSort(int from, int to) {
        AnimatedArrow iArrow = animation.createArrow();
        animation.setItemPosition(iArrow, new ElementScaledPosition(from + 1, 0));
        AnimatedArrow jArrow = animation.createArrow();
        animation.setItemPosition(jArrow, new ElementScaledPosition(from, 0));

        for (int i = from + 1; i < to && i < list.size(); i++) {
            int j = i - 1;
            animation.changeItemX(iArrow, new ElementScaledIndex(i));
            animation.changeItemX(jArrow, new ElementScaledIndex(j));
            animation.addFrame();
            while (j >= from) {
                animation.readIndex(j);
                animation.readIndex(i);
                animation.addFrame();
                if (list.get(j) <= list.get(i)) break;
                j--;
                animation.changeItemX(jArrow, new ElementScaledIndex(j));
                addFrame();
            }
            move(i, j + 1);
            addFrame();
        }

        animation.removeItem(iArrow);
        animation.removeItem(jArrow);
    }

    // [from, to)
    private void selectionSort(int from, int to) {
        AnimatedArrow minArrow = animation.createArrow();
        animation.setItemPosition(minArrow, new ElementScaledPosition(from, 0));
        AnimatedArrow jArrow = animation.createArrow();
        animation.setItemPosition(jArrow, new ElementScaledPosition(from + 1, 0));
        for (int i = from; i < to && i < list.size(); i++) {
            int min = i;
            for (int j = i + 1; j < to; j++) {
                animation.changeItemX(jArrow, new ElementScaledIndex(j));
                animation.changeItemX(minArrow, new ElementScaledIndex(min));
                animation.addFrame();
                animation.readIndex(j);
                animation.readIndex(min);
                if (list.get(j) < list.get(min)) {
                    animation.addFrame();
                    animation.changeItemX(minArrow, new ElementScaledIndex(j));
                    min = j;
                }
                addFrame();
            }
            swap(i, min);
            addFrame();
        }

        animation.removeItem(minArrow);
        animation.removeItem(jArrow);
    }

    @Override
    public String getName() {
        return "Bucket Sort";
    }

    public static AlgorithmSettings<BucketSort> getSettings() {
        AlgorithmSettingsInputBox<Integer> numberOfBucketsSetting = new AlgorithmSettingsInputBox<>(
                "Number of Buckets", DEFAULT_NUMBER_OF_BUCKETS,
                Integer::parseInt, i -> i > 0
        );

        AlgorithmSettingsComboBox<BucketSortSubAlgorithm> subAlgorithmSetting = new AlgorithmSettingsComboBox<>(
                "Sub Algorithm", BucketSortSubAlgorithm.values(), DEFAULT_SUB_ALGORITHM
        );

        return new AlgorithmSettings<>(
                "Bucket Sort",
                list -> new BucketSort(list, numberOfBucketsSetting.getValue(), subAlgorithmSetting.getValue()),
                numberOfBucketsSetting, subAlgorithmSetting
        );
    }
}
