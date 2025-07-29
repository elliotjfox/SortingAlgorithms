package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsComboBox;
import com.example.javafxsortingalgorithms.animation.*;
import com.example.javafxsortingalgorithms.arraydisplay.*;
import javafx.scene.paint.Color;

import java.util.List;

public class QuickSort extends ActionSortingAlgorithm {

    private static final double SECTION_OFFSET = 25 * Math.sin(Math.toRadians(60)) + 10;
    private static final Color FINISHED_COLOUR = Color.rgb(44, 199, 88);
    private static final Color IN_PROGRESS_COLOUR = Color.rgb(110, 110, 110);

    public enum PartitionType {
        HOARE,
        LOMUTO_LEFT,
        LOMUTO_RIGHT
    }

    private AnimatedSection section;

    private final PartitionType partitionType;

    public QuickSort(List<Integer> arrayList, boolean isInstant, PartitionType partitionType) {
        super(arrayList, isInstant);

        this.partitionType = partitionType;
        switch (partitionType) {
            case HOARE -> setInitialActions(new LeftPartition(0, arrayList.size() - 1));
            case LOMUTO_LEFT -> setInitialActions(new RightPartition(0, arrayList.size() - 1));
        }
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
    public void startAnimated(AnimatedArrayDisplay display) {
        section = new ItemBuilder(display)
                .at(0, -SECTION_OFFSET)
                .buildSection(list.size());
        section.setSectionFill(IN_PROGRESS_COLOUR);
        display.addItem(section);

        // Reset actions, and add two visual ones after the sorting algorithm
        actions.clear();
        switch (partitionType) {
            case HOARE -> setInitialActions(
                    new LeftPartition(0, list.size() - 1),
                    new LaterAction(() -> section.setSectionFill(FINISHED_COLOUR)),
                    new AnimationAction(display.recolourTimeline())
            );
            case LOMUTO_LEFT -> setInitialActions(
                    new RightPartition(0, list.size() - 1),
                    new LaterAction(() -> section.setSectionFill(FINISHED_COLOUR)),
                    new AnimationAction(display.recolourTimeline())
            );
        }
    }

    @Override
    public String getName() {
        return "Quick Sort\n" +
                "Partition: " + partitionType;
    }

    protected static class LeftPartition extends AlgorithmAction {
        private final int start;
        private final int end;
        private final int depth;

        // [left, right]
        public LeftPartition(int left, int right) {
            this(left, right, 0);
        }

        public LeftPartition(int left, int right, int depth) {
            this.start = left;
            this.end = right;
            this.depth = depth;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            int k = end;
            for (int i = end; i > start; i--) {
                if (algorithm.list.get(i) > algorithm.list.get(start)) {
                    algorithm.addToStart(new Swap(i, k));
                    k--;
                }
            }
            algorithm.addToStart(new Swap(k, start));

            if (start < k - 1) algorithm.addToStart(new LeftPartition(start, k - 1, depth + 1));
            if (k + 1 < end) algorithm.addToStart(new LeftPartition(k + 1, end, depth + 1));
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
            AnimatedItem kArrow = ItemBuilder.defaultArrow(display, end);
            display.addItem(kArrow);

            AnimatedItem iArrow = new ItemBuilder(display)
                    .add(PolygonWrapper.triangle(display, Color.rgb(25, 53, 145)))
                    .at(end, 0)
                    .build();
            display.addItem(iArrow);

            AnimatedItem minArrow = new ItemBuilder(display)
                    .add(PolygonWrapper.triangle(display, Color.LIGHTGREEN))
                    .at(start, 0)
                    .build();
            display.addItem(minArrow);

            AnimatedSection partitionLimit = new ItemBuilder(display)
                    .at(start, algorithm.list.get(start) * display.getHeightMultiplier())
                    .buildSection(end - start + 1, false);
            display.addItem(partitionLimit);

            display.highlight(i -> i >= start && i <= end);
            display.onPlay(() -> display.setCurrentTask("Partitioning [" + start + ", " + end + "]"));

            int k = end;
            for (int i = end; i > start; i--) {
                int finalI = i;
                boolean needsToSwap = algorithm.list.get(i) > algorithm.list.get(start);
                algorithm.addToStart(
                        new AnimationAction(
                                iArrow.moveToIndexTimeline(i, 0),
                                kArrow.moveToIndexTimeline(k, 0)
                        ),
                        new Wait(),
                        new LaterAction(() -> display.comparing(finalI, start), !needsToSwap || i == k) // Don't ask me how this logic is working
                );
                if (needsToSwap) {
                    // Don't swap if i == k
                    if (i != k) algorithm.addToStart(new Swap(i, k));
                    k--;
                }
            }

            algorithm.addToStart(
                    new AnimationAction(
                            iArrow.moveToIndexTimeline(start, 0),
                            kArrow.moveToIndexTimeline(k, 0)
                    ),
                    new Swap(k, start),
                    new RemoveItem(kArrow, iArrow, minArrow, partitionLimit)
            );

            boolean makingLeft = start < k - 1;
            boolean makingRight = k + 1 < end;

            AnimatedSection leftSection;
            AnimatedSection rightSection;

            // Initialize
            if (makingLeft) {
                leftSection = new ItemBuilder(display)
                        .at(start, -depth * 15 - SECTION_OFFSET)
                        .buildSection(end - start + 1);
                algorithm.addToStart(new AddItem(leftSection));
            } else {
                leftSection = null;
            }

            if (makingRight) {
                rightSection = new ItemBuilder(display)
                        .at(start, -depth * 15 - SECTION_OFFSET)
                        .buildSection(end - start + 1);
                algorithm.addToStart(new AddItem(rightSection));
            } else {
                rightSection = null;
            }

            // Animations
            if (makingLeft && makingRight) {
                algorithm.addToStart(new AnimationAction(
                        leftSection.moveToIndexTimeline(start, -(depth + 1) * 15 - SECTION_OFFSET),
                        leftSection.resizeTimeline(k - start),
                        rightSection.moveToIndexTimeline(k + 1, -(depth + 1) * 15 - SECTION_OFFSET),
                        rightSection.resizeTimeline(end - k)
                ));
            } else if (makingLeft) {
                algorithm.addToStart(new AnimationAction(
                        leftSection.moveToIndexTimeline(start, -(depth + 1) * 15 - SECTION_OFFSET),
                        leftSection.resizeTimeline(k - start)
                ));
            } else if (makingRight) {
                algorithm.addToStart(new AnimationAction(
                        rightSection.moveToIndexTimeline(k + 1, -(depth + 1) * 15 - SECTION_OFFSET),
                        rightSection.resizeTimeline(end - k)
                ));
            }

            if (makingLeft) {
                algorithm.addToStart(
                        new LaterAction(() -> leftSection.setSectionFill(IN_PROGRESS_COLOUR)),
                        new LeftPartition(start, k - 1, depth + 1),
                        new LaterAction(() -> leftSection.setSectionFill(FINISHED_COLOUR))
                );
            }

            if (makingRight) {
                algorithm.addToStart(
                        new LaterAction(() -> rightSection.setSectionFill(IN_PROGRESS_COLOUR)),
                        new LeftPartition(k + 1, end, depth + 1),
                        new LaterAction(() -> rightSection.setSectionFill(FINISHED_COLOUR))
                );
            }
        }
    }

    protected static class RightPartition extends AlgorithmAction {
        private final int start;
        private final int end;

        // [left, right]
        public RightPartition(int left, int right) {
            start = left;
            end = right;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            int k = start;
            for (int i = start; i < end; i++) {
                if (algorithm.list.get(i) < algorithm.list.get(end)) {
                    algorithm.addToStart(new Swap(i, k));
                    k++;
                }
            }
            algorithm.addToStart(new Swap(k, end));

            if (start < k - 1) algorithm.addToStart(new RightPartition(start, k - 1));
            if (k + 1 < end) algorithm.addToStart(new RightPartition(k + 1, end));
        }
    }

    // TODO: Implement
//    protected static class MiddlePartition extends AlgorithmAction {
//        private final int start;
//        private final int end;
//
//        // [left, right]
//        public MiddlePartition(int left, int right) {
//            start = left;
//            end = right;
//        }
//
//        @Override
//        void perform(ActionSortingAlgorithm algorithm) {
//            int pivotValue = algorithm.arrayList.get((start + end) / 2);
//            int k = start;
//            for (int i = start; i < end; i++) {
//                if (algorithm.arrayList.get(i) < algorithm.arrayList.get(end)) {
//                    algorithm.addToStart(new Swap(i, k));
//                    k++;
//                }
//            }
//            algorithm.addToStart(new Swap(k, end));
//
//            if (start < k - 1) algorithm.addToStart(new MiddlePartition(start, k - 1));
//            if (k + 1 < end) algorithm.addToStart(new MiddlePartition(k + 1, end));
//        }
//    }

    public static AlgorithmSettings<QuickSort> getSettings() {
        AlgorithmSettingsComboBox<PartitionType> partitionSetting = new AlgorithmSettingsComboBox<>("Partition Type", PartitionType.values(), PartitionType.HOARE);

        return new AlgorithmSettings<>(
                "Quick Sort",
                (l, b) -> new QuickSort(l, b, partitionSetting.getValue()),
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
