package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithnSettingsCheckBox;
import com.example.javafxsortingalgorithms.arraydisplay.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class MergeSort extends ActionSortingAlgorithm {

    private static final double SECTION_OFFSET = 25 * Math.sin(Math.toRadians(60)) + 10;

    private final boolean inPlace;

    public MergeSort(List<Integer> arrayList, boolean isInstant, boolean inPlace) {
        super(arrayList, isInstant);
        this.inPlace = inPlace;

        if (!isInstant) setInitialActions(new Divide(0, arrayList.size()));
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {
        divide(0, list.size(), entry);
    }

    // [from, to)
    private void divide(int from, int to, TestEntry entry) {
        if (to - from >= 2) {
            int half = (from + to) / 2;
            divide(from, half, entry);
            divide(half, to, entry);
            merge(from, to, entry);
        }
    }

    private void merge(int left, int end, TestEntry entry) {
        int right = (left + end) / 2;
        while (right < end && left < right) {
            entry.addRead(2);
            if (list.get(left) < list.get(right)) {
                left++;
            } else {
                entry.addWrite(1);
                move(right, left);
                right++;
            }
        }
    }

    @Override
    public void startAnimated(AnimatedArrayDisplay display) {
        AnimatedSection currentSection = new AnimatedSection(display, list.size(), true);
        display.addItem(currentSection, 0, -SECTION_OFFSET);
    }

    @Override
    public String getName() {
        return "Merge Sort";
    }


    private static class Divide extends AlgorithmAction {
        private final int min;
        private final int max;
        private final int depth;

        public Divide(int min, int max, int depth) {
            this.min = min;
            this.max = max;
            this.depth = depth;
            takesStep = false;
        }

        public Divide(int min, int max) {
            this(min, max, 0);
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            if (max - min >= 2) {
                int half = (min + max) / 2;
                algorithm.addToStart(
                        new Divide(min, half),
                        new Divide(half, max)
                );
                if (((MergeSort) algorithm).inPlace) {
                    algorithm.addToStart(new InPlaceMerge(min, max));
                } else {
                    algorithm.addToStart(new Merge(min, half, max));
                }

            }
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
            if (max - min >= 2) {
                int half = (min + max) / 2;

                AnimatedSection leftSection = new AnimatedSection(display, max - min, true);
                AnimatedSection rightSection = new AnimatedSection(display, max - min, true);
                algorithm.addToStart(
                        new LaterAction(() -> {

                            display.addItem(leftSection, min, -(depth) * 15 - SECTION_OFFSET);
                            display.addItem(rightSection, min, -(depth) * 15 - SECTION_OFFSET);
                            leftSection.moveToIndex(min, -(depth + 1) * 15 - SECTION_OFFSET);
                            rightSection.moveToIndex(half, -(depth + 1) * 15 - SECTION_OFFSET);
                            display.animate(
                                    display.highlightAnimation(i -> i >= min && i < max),
                                    leftSection.resizeTimeline(half - min),
                                    rightSection.resizeTimeline(max - half)
                            );
                            display.onPlay(() -> display.setCurrentTask("Dividing [" + min + ", " + (max - 1) + "]"));
                        }, true),
                        new Divide(min, half, depth + 1),
                        new Divide(half, max, depth + 1),
                        new LaterAction(() -> {
                            leftSection.setFill(Color.LIGHTGREEN);
                            rightSection.setFill(Color.LIGHTGREEN);
                            display.animate(display.highlightAnimation(i -> i >= min && i < max));
                            display.onPlay(() -> display.setCurrentTask("Merging [" + min + ", " + (half - 1) + "] and [" + half + ", " + (max - 1) + "]"));
                        }),
                        new InPlaceMerge(min, max),
                        new LaterAction(() -> {
                            leftSection.setFill(Color.BLACK);
                            rightSection.setFill(Color.BLACK);
                        }),
                        new AnimationAction(
                                leftSection.moveToIndexTimeline(min, -depth * 15 - SECTION_OFFSET),
                                leftSection.resizeTimeline(max - min),
                                rightSection.moveToIndexTimeline(min, -depth * 15 - SECTION_OFFSET),
                                rightSection.resizeTimeline(max - min)
                        ),
                        new Wait(),
                        new LaterAction(() -> {
                            display.removeItem(leftSection);
                            display.removeItem(rightSection);
                        })
                );
            }
        }
    }

    private static class InPlaceMerge extends AlgorithmAction {
        private int leftSide;
        private int rightSide;
        private final int end;
        private int i;

        public InPlaceMerge(int leftSide, int end) {
            this.leftSide = leftSide;
            this.rightSide = (leftSide + end) / 2;
            this.end = end;
            i = leftSide;
            takesStep = false;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            while (rightSide < end && leftSide < rightSide && i < rightSide) {
                if (algorithm.list.get(leftSide) < algorithm.list.get(rightSide)) {
                    // Pause to keep the merge smooth
                    algorithm.addToStart(new Wait());
                    leftSide++;
                } else {
                    algorithm.addToStart(new Move(rightSide, i));
                    rightSide++;
                }
                i++;
            }
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
            AnimatedArrow leftArrow = new AnimatedArrow(display, true);
            AnimatedArrow rightArrow = new AnimatedArrow(display, true);

            int finalRight = rightSide;
            int finalLeft = leftSide;
            algorithm.addToStart(
                    new LaterAction(() -> {
                        display.addItem(rightArrow, finalRight, 0);
                        display.addItem(leftArrow, finalLeft, 0);
                    })
            );
            while (rightSide < end && leftSide < rightSide && i < rightSide) {
                int innerFinalRight = rightSide;
                int finalI = i;
                if (algorithm.list.get(leftSide) < algorithm.list.get(rightSide)) {
                    algorithm.addToStart(
                            new AnimationAction(leftArrow.moveToIndexTimeline(finalI, 0), rightArrow.moveToIndexTimeline(innerFinalRight, 0)),
                            new Wait(),
                            new LaterAction(() -> display.reading(finalI, innerFinalRight), true)
                    );
                    leftSide++;
                } else {
                    algorithm.addToStart(
                            new AnimationAction(leftArrow.moveToIndexTimeline(finalI, 0), rightArrow.moveToIndexTimeline(innerFinalRight, 0)),
                            new Wait(),
                            new LaterAction(() -> display.reading(finalI, innerFinalRight)),
                            new Move(rightSide, i)
                    );
                    rightSide++;
                }
                i++;
            }

            algorithm.addToStart(
                    new LaterAction(() -> {
                        display.removeItem(leftArrow);
                        display.removeItem(rightArrow);
                    })
            );
        }
    }

    private static class Merge extends AlgorithmAction {

        private final int leftSide;
        private final int rightSide;
        private final int end;
        private int pos;

        private final List<Integer> left;
        private final List<Integer> right;

        public Merge(int leftSide, int rightSide, int end) {
            this.leftSide = leftSide;
            this.rightSide = rightSide;
            this.end = end;
            this.pos = leftSide;

            left = new ArrayList<>();
            right = new ArrayList<>();
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            for (int i = leftSide; i < rightSide; i++) {
                left.add(algorithm.list.get(i));
                algorithm.addToStart(new Wait());
            }
            for (int i = rightSide; i < end; i++) {
                right.add(algorithm.list.get(i));
                algorithm.addToStart(new Wait());
            }

            while (!right.isEmpty() && !left.isEmpty()) {
                if (left.getFirst() <= right.getFirst()) {
                    algorithm.addToStart(new Set(pos, left.removeFirst()));
                } else {
                    algorithm.addToStart(new Set(pos, right.removeFirst()));
                }
                pos++;
            }
            List<Integer> emptyList = right.isEmpty() ? left : right;
            while (!emptyList.isEmpty()) {
                algorithm.addToStart(new Set(pos, emptyList.removeFirst()));
                pos++;
            }
        }
    }

    public static AlgorithmSettings<MergeSort> getSettings() {
        AlgorithnSettingsCheckBox inPlaceSetting = new AlgorithnSettingsCheckBox("Merge In Place", true);

        return new AlgorithmSettings<>(
                "Merge Sort",
                (l, b) -> new MergeSort(l, b, inPlaceSetting.getValue()),
                inPlaceSetting
        );
    }

//    void mergeSort() {
//        divide(0, list.size());
//    }
//
//    void divide(int min, int max) {
//        if (max - min >= 2) {
//            int half = (min + max) / 2;
//            divide(min, half);
//            divide(half, max);
//            merge(min, half, max);
//        }
//    }
//
//    void merge(int left, int right, int end) {
//        while (left < right && right < end) {
//            if (list.get(left) <= list.get(right)) {
//                left++;
//            } else {
//                move(right, left);
//                right++;
//            }
//        }
//    }
}
