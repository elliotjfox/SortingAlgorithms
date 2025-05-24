package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.animation.AnimatedArrayDisplay;
import com.example.javafxsortingalgorithms.animation.ItemBuilder;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;
import com.example.javafxsortingalgorithms.animation.AnimatedBinaryTree;
import javafx.animation.Timeline;

import java.util.List;

public class HeapSort extends ActionSortingAlgorithm {

    private int length;

    private AnimatedBinaryTree tree;

    public HeapSort(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);

        length = arrayList.size();
        setInitialActions(new BuildMaxHeap(), new ExtractMax());
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {
        // Build the heap
        for (int i = list.size() / 2; i >= 0; i--) {
            maxHeapify(i, entry);
        }
        for (int i = 0; i < list.size(); i++) {
            swap(0, length - 1);
            length--;
            entry.addWrite(2);
            maxHeapify(0, entry);
            entry.updateProgress((double) i / list.size());
        }
    }

    // A recursive method that fixes the heap from a given point
    private void maxHeapify(int i, TestEntry entry) {
        // If both children are in range
        if (i * 2 + 2 < length) {
            // If the current is larger than both children, we're done
            if (list.get(i) > list.get(i * 2 + 1) && list.get(i) > list.get(i * 2 + 2)) {
                entry.addRead(3);
            } else {
                // Otherwise, swap with the larger child
                int largestChild = list.get(i * 2 + 1) > list.get(i * 2 + 2) ? i * 2 + 1 : i * 2 + 2;
                swap(i, largestChild);
                entry.addRead(3);
                entry.addWrite(2);
                maxHeapify(largestChild, entry);
            }
        } else if (i * 2 + 1 < length) {
            // One of the children is in range, check if it is bigger
            if (list.get(i) < list.get(i * 2 + 1)) {
                swap(i, i * 2 + 1);
                entry.addWrite(2);
                entry.addRead(2);
                // Don't need to call, since the child was at the edge of the
            }
        }
    }

    @Override
    public void startAnimated(AnimatedArrayDisplay display) {
        tree = new ItemBuilder(display)
                .at(list.size(), 600)
                .buildTree(list);
        display.addItem(tree);
    }

    @Override
    public String getName() {
        return "Heap Sort";
    }

    private static class BuildMaxHeap extends AlgorithmAction {

        public BuildMaxHeap() {
            takesStep = false;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            for (int i = algorithm.list.size() / 2; i >= 0; i--) {
                algorithm.addToStart(new MaxHeapify(i));
            }
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
            display.setCurrentTask("Creating binary tree");
            for (int i = algorithm.list.size() / 2; i >= 0; i--) {
                algorithm.addToStart(new MaxHeapify(i));
            }
        }
    }

    private static class MaxHeapify extends AlgorithmAction {

        private final int i;

        public MaxHeapify(int i) {
            this.i = i;
            takesStep = false;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            if (algorithm instanceof HeapSort) {
                perform((HeapSort) algorithm);
            }
        }

        private void perform(HeapSort heapSort) {
            // If both children are in range
            if (i * 2 + 2 < heapSort.length) {
                // If the current one is less than one of its children
                if (heapSort.getList().get(i) < heapSort.getList().get(i * 2 + 1) || heapSort.getList().get(i) < heapSort.getList().get(i * 2 + 2)) {
                    // Swap is with the larger child
                    int largestChild = heapSort.getList().get(i * 2 + 1) > heapSort.getList().get(i * 2 + 2) ? i * 2 + 1 : i * 2 + 2;
                    heapSort.addToStart(
                            new Swap(i, largestChild),
                            new MaxHeapify(largestChild)
                    );
                }
            } else if (i * 2 + 1 < heapSort.length) {
                // One of the children is in range, check if it is bigger
                if (heapSort.getList().get(i) < heapSort.getList().get(i * 2 + 1)) {
                    heapSort.addToStart(new Swap(i, i * 2 + 1));
                    // Don't need to call max heapify, since the child was at the edge of the
                }
            }
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
            if (algorithm instanceof HeapSort) {
                performDetailed((HeapSort) algorithm, display);
            }
        }

        private void performDetailed(HeapSort heapSort, AnimatedArrayDisplay display) {
            // If both children are in range
            if (i * 2 + 2 < heapSort.length) {
                // Read the current and its children
                display.reading(i);
                heapSort.tree.read(i);
                display.newGroup();
                display.comparing(i * 2 + 1, i * 2 + 2);
                heapSort.tree.read(i *  2 + 1);
                heapSort.tree.read(i *  2 + 2);
                // If the current one is less than one of its children
                if (heapSort.getList().get(i) < heapSort.getList().get(i * 2 + 1) || heapSort.getList().get(i) < heapSort.getList().get(i * 2 + 2)) {
                    // Swap is with the larger child
                    int largestChild = heapSort.getList().get(i * 2 + 1) > heapSort.getList().get(i * 2 + 2) ? i * 2 + 1 : i * 2 + 2;
                    heapSort.addToStart(
                            new LaterAction(() -> display.getElementAnimationGroup().addTimelines(heapSort.tree.swapTimeline(i, largestChild))),
                            new Swap(i, largestChild),
                            new MaxHeapify(largestChild)
                    );
                } else {
                    heapSort.addToStart(new Wait());
                }
            } else if (i * 2 + 1 < heapSort.length) {
                display.reading(i);
                heapSort.tree.read(i);
                display.newGroup();
                display.reading(i * 2 + 1);
                heapSort.tree.read(i * 2 + 1);

                // One of the children is in range, check if it is bigger
                if (heapSort.getList().get(i) < heapSort.getList().get(i * 2 + 1)) {
                    heapSort.addToStart(
                            new LaterAction(() -> display.getElementAnimationGroup().addTimelines(heapSort.tree.swapTimeline(i, i * 2 + 1))),
                            new Swap(i, i * 2 + 1)
                    );
                    // Don't need to call max heapify, since the child was at the edge of the
                } else {
                    heapSort.addToStart(new Wait());
                }
            }
        }
    }

    private static class ExtractMax extends AlgorithmAction {

        public ExtractMax() {
            takesStep = false;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            if (algorithm instanceof HeapSort) {
                perform((HeapSort) algorithm);
            }
        }

        private void perform(HeapSort heapSort) {
            if (heapSort.length <= 1) {
                return;
            }

            heapSort.addToStart(new Swap(0, heapSort.length - 1));
            heapSort.length -= 1;
            heapSort.addToStart(new MaxHeapify(0));
            if (heapSort.length > 1) heapSort.addToStart(new ExtractMax());
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
            if (algorithm instanceof HeapSort) {
                performDetailed((HeapSort) algorithm, display);
            }
        }

        private void performDetailed(HeapSort heapSort, AnimatedArrayDisplay display) {
            if (heapSort.length <= 1) {
                return;
            }

            Timeline extractAnimation = heapSort.tree.extractItemTimeline(0, heapSort.length - 1);
            heapSort.addToStart(
                    new LaterAction(() -> {
                        display.onPlay(() -> display.setCurrentTask("Extracting max"));
                        display.getElementAnimationGroup().addTimelines(extractAnimation);
                    }),
                    new Swap(0, heapSort.length - 1),
                    new LaterAction(() -> display.setCurrentTask("Fixing binary tree")),
                    new MaxHeapify(0)
            );
            heapSort.length -= 1;
            if (heapSort.length > 1) heapSort.addToStart(new ExtractMax());
        }
    }

//    void heapSort() {
//        // Build the heap
//        for (int i = list.size() / 2; i >= 0; i--) {
//            maxHeapify(i);
//        }
//        // Extract max
//        for (int i = 0; i < list.size(); i++) {
//            swap(0, length - 1);
//            length--;
//            maxHeapify(0);
//        }
//    }
//
//    void maxHeapify(int i) {
//        // Make sure both children are in range
//        if (i * 2 + 2 < length) {
//            // If the current one is less than one of its children
//            if (list.get(i) < list.get(i * 2 + 1) || list.get(i) < list.get(i * 2 + 2)) {
//                // Swap it with the larger child
//                int largestChild = list.get(i * 2 + 1) > list.get(i * 2 + 2) ? i * 2 + 1 : i * 2 + 2;
//                swap(i, largestChild);
//                // Continue calling maxHeapify
//                maxHeapify(largestChild);
//            }
//        } else if (i * 2 + 1 < length) {
//            // One of the children is in range, check that it is bigger
//            if (list.get(i) < list.get(i * 2 + 1)) {
//                swap(i, i * 2 + 1);
//                // Don't need to call maxHeapify again, since the child was at the end of the heap
//            }
//        }
//    }
}
