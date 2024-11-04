package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.*;

import java.util.List;

public class MergeSort extends ActionSortingAlgorithm {

    private static final double SECTION_OFFSET = 25 * Math.sin(Math.toRadians(60)) + 10;

    public MergeSort(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);

        actions.add(new Divide(0, arrayList.size()));
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

    }

    @Override
    public void startDetailed(ArrayDetailedDisplay display) {
        DetailedSection section = new DetailedSection(list.size() * 25, true);
        display.addItem(section, 0, -SECTION_OFFSET);
    }

    @Override
    public String getName() {
        return null;
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
        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            if (max - min >= 2) {
                int half = (min + max) / 2;
                algorithm.addToStart(
                        new Divide(min, half),
                        new Divide(half, max),
                        new Merge(min, max)
                );
            }
        }

        // TODO: Create animations for this
        @Override
        public void performDetailed(ActionSortingAlgorithm algorithm, ArrayDetailedDisplay display) {
//            takesStep = true;
            if (max - min >= 2) {
                int half = (min + max) / 2;
//                DetailedSection leftSection = new DetailedSection((max - min ) * 25, true);
//                DetailedSection rightSection = new DetailedSection((max - min) * 25, true);
                algorithm.addToStart(
//                        new LaterAction(() -> {
//                                display.addItem(leftSection, min, -(depth) * 15 - SECTION_OFFSET);
//                        }),
//                        new AnimationAction(
//                                display.moveItemToElementAnimation(leftSection, min, -(depth + 1) * 15 - SECTION_OFFSET),
//                                leftSection.resizeAnimation((half - min) * 25)
//                        ),
//                        new LaterAction(() -> leftSection.setFill(Color.LIGHTGREEN)),
                        new Divide(min, half, depth + 1),
//                        new LaterAction(() -> {
//                            display.addItem(rightSection, min, -(depth) * 15 - SECTION_OFFSET);
//                            leftSection.setFill(Color.BLACK);
//                            rightSection.setFill(Color.LIGHTGREEN);
//                        }),
//                        new AnimationAction(
//                                display.moveItemToElementAnimation(rightSection, half, -(depth + 1) * 15 - SECTION_OFFSET),
//                                rightSection.resizeAnimation((max - half) * 25)
//                        ),
                        new Divide(half, max, depth + 1),
//                        new LaterAction(() -> rightSection.setFill(Color.BLACK)),
                        new Merge(min, max)
                );
            }
        }
    }

    private static class Merge extends AlgorithmAction {
        private int leftSide;
        private int rightSide;
        private final int end;
        private int i;

        public Merge(int leftSide, int end) {
            this.leftSide = leftSide;
            this.rightSide = (leftSide + end) / 2;
            this.end = end;
            i = leftSide;
            takesStep = false;
        }

        @Override
        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            while (rightSide < end && leftSide < rightSide) {
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

        // TODO: Create animations for this
        @Override
        public void performDetailed(ActionSortingAlgorithm algorithm, ArrayDetailedDisplay display) {
            DetailedArrow leftArrow = new DetailedArrow(25, true);
            DetailedArrow rightArrow = new DetailedArrow(25, true);

            int finalRight = rightSide;
            int finalLeft = leftSide;
//            algorithm.addToStart(
//                    new LaterAction(() -> {
//                        display.addItem(rightArrow, finalRight, 0);
//                        display.addItem(leftArrow, finalLeft, 0);
//                    })
//            );
            while (rightSide < end && leftSide < rightSide) {
//                algorithm.addToStart(
//                        new AnimationAction(
//                                display.moveItemToElementAnimation(leftArrow, leftSide, 0),
//                                display.moveItemToElementAnimation(rightArrow, rightSide, 0)
//                        ),
//                        new LaterAction(() -> {
//                            display.addAnimations(new AnimationGroup(
//                                    display.readAnimation(leftSide),
//                                    display.readAnimation(rightSide)
//                            ));
//                        })
//                );
                if (algorithm.list.get(leftSide) < algorithm.list.get(rightSide)) {
                    algorithm.addToStart(new Wait());
                    leftSide++;
                } else {
                    algorithm.addToStart(new Move(rightSide, i));
                    rightSide++;
                }
                i++;
            }

//            algorithm.addToStart(
//                    new LaterAction(() -> {
//                        display.removeItem(leftArrow);
//                        display.removeItem(rightArrow);
//                    })
//            );
        }
    }
}
