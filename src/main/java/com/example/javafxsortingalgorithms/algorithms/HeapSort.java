package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithmupdates.CreateItemUpdate;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledPosition;
import com.example.javafxsortingalgorithms.arraydisplay.DisplayMode;
import com.example.javafxsortingalgorithms.animation.AnimatedBinaryTree;

import java.util.List;

public class HeapSort extends SortingAlgorithm {

    private int treeSize;

    private AnimatedBinaryTree tree;

    public HeapSort(List<Integer> list) {
        super(list);
        animation = new HeapAnimation(this);

        treeSize = list.size();
    }

    @Override
    public void runAlgorithm() {
        if (mode == DisplayMode.ANIMATED) {
            tree = new AnimatedBinaryTree(list);
            currentChanges.add(new CreateItemUpdate(tree));
            animation.setItemPosition(tree, new ElementScaledPosition(list.size(), 0));
        }

        // Build the heap
        for (int i = list.size() / 2; i >= 0; i--) {
            maxHeapify(i);
        }
        for (int i = 0; i < list.size(); i++) {
            swap(0, treeSize - 1);
            treeSize--;
            addFrame();
            if (tree != null) currentChanges.add(tree.removeLast());
            animation.addFrame();
            maxHeapify(0);
        }
    }

    // A recursive method that fixes the heap from a given point
    private void maxHeapify(int i) {
        // If both children are in range
        if (i * 2 + 2 < treeSize) {
            animation.readIndex(i);
            animation.addFrame();
            animation.readIndex(i * 2 + 1);
            animation.readIndex(i * 2 + 2);
            animation.addFrame();
            // If the current has a child that is bigger than it,
            if (list.get(i) <= list.get(i * 2 + 1) || list.get(i) <= list.get(i * 2 + 2)) {
                // We need to swap it with the larger child
                int largestChild = list.get(i * 2 + 1) > list.get(i * 2 + 2) ? i * 2 + 1 : i * 2 + 2;
                swap(i, largestChild);
                addFrame();
                // And keep recursively doing this process
                maxHeapify(largestChild);
            }
        } else if (i * 2 + 1 < treeSize) {
            animation.readIndex(i);
            animation.addFrame();
            animation.readIndex(i * 2 + 1);
            animation.addFrame();
            // One of the children is in range, check if it is bigger
            if (list.get(i) < list.get(i * 2 + 1)) {
                swap(i, i * 2 + 1);
                addFrame();
                // Don't need to recursively call, since we only had one child, that child can't possibly have a child
            }
        }
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {
        // Build the heap
        for (int i = list.size() / 2; i >= 0; i--) {
            maxHeapify(i, entry);
        }
        for (int i = 0; i < list.size(); i++) {
            swap(0, treeSize - 1);
            treeSize--;
            entry.addWrite(2);
            maxHeapify(0, entry);
            entry.updateProgress((double) i / list.size());
        }
    }

    // A recursive method that fixes the heap from a given point
    private void maxHeapify(int i, TestEntry entry) {
        // If both children are in range
        if (i * 2 + 2 < treeSize) {
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
        } else if (i * 2 + 1 < treeSize) {
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
    public String getName() {
        return "Heap Sort";
    }

    @Override
    protected void swap(int firstIndex, int secondIndex) {
        super.swap(firstIndex, secondIndex);
        if (tree != null) currentChanges.add(tree.swapElement(firstIndex, secondIndex));
    }

    private static class HeapAnimation extends SortingAlgorithmAnimation {

        private final HeapSort algorithm;

        public HeapAnimation(HeapSort algorithm) {
            super(algorithm);
            this.algorithm = algorithm;
        }

        @Override
        protected void readIndex(int index) {
            super.readIndex(index);
            if (algorithm.mode == DisplayMode.ANIMATED && algorithm.tree != null) {
                algorithm.currentChanges.add(algorithm.tree.readIndex(index));
            }
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
