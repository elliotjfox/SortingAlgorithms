package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.*;
import javafx.scene.paint.Color;

import java.util.List;

public class QuickSort extends ActionSortingAlgorithm {

    private static final double SECTION_OFFSET = 25 * Math.sin(Math.toRadians(60)) + 10;

    public enum PartitionType {
        LEFT,
        RIGHT,
        MIDDLE
    }

    private AnimatedSection section;

    public QuickSort(List<Integer> arrayList, boolean isInstant, PartitionType partitionType) {
        super(arrayList, isInstant);

        switch (partitionType) {
            case LEFT -> actions.add(new LeftPartition(0, arrayList.size() - 1));
            case RIGHT -> actions.add(new RightPartition(0, arrayList.size() - 1));
            case MIDDLE -> System.out.println("Need to implement still!");
        }
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

    }

    @Override
    public void startAnimated(ArrayAnimatedDisplay display) {
        section = new AnimatedSection(display, list.size(), true);
        display.addItem(section, 0, -SECTION_OFFSET);
        section.setFill(Color.rgb(44, 199, 88));
        actions.add(new AnimationAction(display.recolourTimeline()));
    }

    @Override
    public String getName() {
        return null;
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
        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
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
        public void performDetailed(ActionSortingAlgorithm algorithm, ArrayAnimatedDisplay display) {
            AnimatedArrow kArrow = new AnimatedArrow(display, true);
            display.addItem(kArrow, end, 0);

            AnimatedArrow iArrow = new AnimatedArrow(display, true);
            iArrow.setFill(Color.rgb(25, 53, 145));
            display.addItem(iArrow, end, 0);

            AnimatedArrow minArrow = new AnimatedArrow(display, true);
            minArrow.setFill(Color.LIGHTGREEN);
            display.addItem(minArrow, start, 0);

            display.animate(display.highlightAnimation(i -> i >= start && i <= end));

            int k = end;
            for (int i = end; i > start; i--) {
                int finalI = i;
                int finalK = k;
                algorithm.addToStart(
                        new LaterAction(() -> {
                            iArrow.moveToIndex(finalI, 0);
                            kArrow.moveToIndex(finalK, 0);
                            display.newGroup();
                            display.comparing(finalI, start);
                        }, true)
                );
                if (algorithm.list.get(i) > algorithm.list.get(start)) {
                    // Don't swap if i == k
                    if (i != k) algorithm.addToStart(new Swap(i, k));
                    k--;
                }
            }

            algorithm.addToStart(
                    new Swap(k, start),
                    new LaterAction(() -> {
                        display.removeItem(kArrow);
                        display.removeItem(iArrow);
                        display.removeItem(minArrow);
                    })
            );

            boolean makingLeft = start < k - 1;
            boolean makingRight = k + 1 < end;

            AnimatedSection leftSection;
            AnimatedSection rightSection;

            // Initialize
            if (makingLeft) {
                leftSection = new AnimatedSection(display, end - start + 1, true);
                leftSection.setFill(Color.rgb(44, 199, 88));
                algorithm.addToStart(new LaterAction(() -> display.addItem(leftSection, start, -depth * 15 - SECTION_OFFSET)));
            } else {
                leftSection = null;
            }

            if (makingRight) {
                rightSection = new AnimatedSection(display, end - start + 1, true);
                rightSection.setFill(Color.rgb(44, 199, 88));
                algorithm.addToStart(new LaterAction(() -> display.addItem(rightSection, start, -depth * 15 - SECTION_OFFSET)));
            } else {
                rightSection = null;
            }

            // Animations
            if (makingLeft && makingRight) {
                algorithm.addToStart(new AnimationAction(
                        leftSection.moveToIndexTimeline(start, -(depth + 1) * 15 - SECTION_OFFSET),
                        rightSection.moveToIndexTimeline(k + 1, -(depth + 1) * 15 - SECTION_OFFSET),
                        leftSection.resizeTimeline(k - start),
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
                        new LeftPartition(start, k - 1, depth + 1),
                        new LaterAction(() -> leftSection.setFill(Color.rgb(52, 52, 52)))
                );
            }

            if (makingRight) {
                algorithm.addToStart(
                        new LeftPartition(k + 1, end, depth + 1),
                        new LaterAction(() -> rightSection.setFill(Color.rgb(52, 52, 52)))
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
        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
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
}
