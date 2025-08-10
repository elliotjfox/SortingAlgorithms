package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsCheckBox;

import java.util.ArrayList;
import java.util.List;

public class PigeonholeSort extends SortingAlgorithm {

    private boolean instantListUpdate;

    public PigeonholeSort(List<Integer> list, boolean instantListUpdate) {
        super(list);

        this.instantListUpdate = instantListUpdate;
    }

    @Override
    protected void runAlgorithm() {
        int maxValue = list.getFirst();
        for (int i : list) {
            if (i > maxValue) maxValue = i;
            addFrame();
        }

        List<Integer> bucketEnd = new ArrayList<>(maxValue);
        for (int i = 0; i < maxValue + 1; i++) bucketEnd.add(0);

        for (int i = 0; i < list.size(); i++) {
            int bucket = list.get(i);

            move(i, bucketEnd.get(bucket));
            addFrame();
            incrementFollowing(bucketEnd, bucket);
        }
    }

    private void incrementFollowing(List<Integer> list, int index) {
        for (int i = index; i < list.size(); i++) {
            increment(list, i);
        }
    }

    private void increment(List<Integer> list, int index) {
        list.set(index, list.get(index) + 1);
        if (!instantListUpdate) addFrame();
    }

    @Override
    public String getName() {
        return "Pigeonhole Sort";
    }

    public static AlgorithmSettings<PigeonholeSort> getSettings() {
        AlgorithmSettingsCheckBox instantListUpdateSetting = new AlgorithmSettingsCheckBox("Instant List Update", false);

        return new AlgorithmSettings<>(
                "Bitonic Sort",
                list -> new PigeonholeSort(list, instantListUpdateSetting.getValue()),
                instantListUpdateSetting
        );
    }
}
