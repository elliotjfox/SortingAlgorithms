package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsComboBox;
import javafx.scene.paint.Color;

import java.util.List;

public class QuickSort extends SortingAlgorithm {

    // TODO: Reimplement colours?
    private static final double SECTION_OFFSET = 25 * Math.sin(Math.toRadians(60)) + 10;
    private static final Color FINISHED_COLOUR = Color.rgb(44, 199, 88);
    private static final Color IN_PROGRESS_COLOUR = Color.rgb(110, 110, 110);

    public enum PartitionType {
        HOARE,
        LOMUTO_LEFT,
        LOMUTO_RIGHT
    }

    private final PartitionType partitionType;

    public QuickSort(List<Integer> arrayList, PartitionType partitionType) {
        super(arrayList);

        this.partitionType = partitionType;
    }

    @Override
    protected void runAlgorithm() {
        switch (partitionType) {
            case HOARE -> hoarePartition(0, list.size() - 1);
            case LOMUTO_LEFT -> lomutoLeftPartition(0, list.size() - 1);
            case LOMUTO_RIGHT -> lomutoRightPartition(0, list.size() - 1);
        }
    }

    // Hoare Partition
    private void hoarePartition(int from, int to) {
        if (to <= from) return;
        int i = from - 1;
        int j = to + 1;

        while (true) {
            do {
                i++;
                addFrame();
            } while (list.get(i) < list.get(from));

            do {
                j--;
                addFrame();
            } while (list.get(j) > list.get(from));

            if (i >= j) break;
            swap(i, j);
            addFrame();
        }

        hoarePartition(from, j);
        hoarePartition(j + 1, to);
    }

    private void lomutoLeftPartition(int from, int to) {
        if (to <= from) return;
        int k = to;
        for (int i = to; i > from; i--) {
            if (list.get(i) > list.get(from)) {
                swap(i, k);
                k--;
            }
            addFrame();
        }

        swap(k, from);
        addFrame();

        lomutoLeftPartition(from, k - 1);
        lomutoLeftPartition(k + 1, to);
    }

    private void lomutoRightPartition(int from, int to) {
        if (to <= from) return;
        int pivot = list.get(to);
        int k = from;
        for (int i = from; i < to; i++) {
            if (list.get(i) <= pivot) {
                swap(k, i);
                k++;
            }
            addFrame();
        }
        swap(k, to);
        addFrame();

        lomutoRightPartition(from, k - 1);
        lomutoRightPartition(k + 1, to);
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {
        lomutoPartition(0, list.size() - 1, entry);
    }

    private void lomutoPartition(int from, int to, TestEntry entry) {
        int k = to;
        for (int i = to; i > from; i--) {
            if (list.get(i) > list.get(from)) {
                swap(i, k);
                entry.addWrite(2);
                k--;
            }
            entry.addRead(2);
        }
        swap(k, from);
        entry.addWrite(2);
        if (from < k - 1) {
            lomutoPartition(from, k - 1, entry);
        } else {
            entry.updateProgress((double) k / list.size());
        }
        if (k + 1 < to) lomutoPartition(k + 1, to, entry);
    }

    @Override
    public String getName() {
        return "Quick Sort\n" +
                "Partition: " + partitionType;
    }

    public static AlgorithmSettings<QuickSort> getSettings() {
        AlgorithmSettingsComboBox<PartitionType> partitionSetting = new AlgorithmSettingsComboBox<>("Partition Type", PartitionType.values(), PartitionType.HOARE);

        return new AlgorithmSettings<>(
                "Quick Sort",
                list -> new QuickSort(list, partitionSetting.getValue()),
                partitionSetting
        );
    }

//    void quickSort() {
//        leftPartition(0, list.size() - 1);
//    }
//
//    void leftPartition(int from, int to) {
//        int k = to;
//        for (int i = to; i > from; i--) {
//            if (list.get(i) > list.get(from)) {
//                swap(i, k);
//                k--;
//            }
//        }
//        swap(k, from);
//        if (from < k - 1) leftPartition(from, k - 1);
//        if (k + 1 < to) leftPartition(k + 1, to);
//    }
}
