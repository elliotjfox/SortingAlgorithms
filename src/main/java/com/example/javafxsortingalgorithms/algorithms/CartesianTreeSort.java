package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;

import java.util.List;

public class CartesianTreeSort extends SortingAlgorithm {

//    private CartesianTreeNode rootNode;
//    private AnimatedCartesianTree cartesianTree;

    public CartesianTreeSort(List<Integer> arrayList) {
        super(arrayList);

//        rootNode = new CartesianTreeNode();
//        addToStart(new FindHighest(0, list.size() - 1));

//        for (int i = list.size() - 1; i >= 0; i--) {
//            addToStart(new ExtractMax(i));
//        }
//        catchUpActions();
    }

    @Override
    protected void runAlgorithm() {

    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

    }

//    @Override
//    public void startAnimated(AnimatedArrayDisplay display) {
//        cartesianTree = new AnimatedCartesianTree(display, list);
//        display.addItem(cartesianTree, list.size(), 600);
//    }

    @Override
    public String getName() {
        return null;
    }

//    private abstract static class CartesianTreeAction extends AlgorithmAction {
//
//        @Override
//        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
//            if (algorithm instanceof CartesianTreeSort cartesianTreeSort) {
//                perform(cartesianTreeSort, display);
//            }
//        }
//
//        @Override
//        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
//            if (algorithm instanceof CartesianTreeSort cartesianTreeSort) {
//                performDetailed(cartesianTreeSort, display);
//            }
//        }
//
//        public abstract void perform(CartesianTreeSort cartesianTreeSort, ArrayDisplay display);
//
//        public abstract void performDetailed(CartesianTreeSort cartesianTreeSort, AnimatedArrayDisplay display);
//    }
//
//    private static class FindHighest extends CartesianTreeAction {
//
//        private final int from;
//        private final int to;
//        private int highestIndex;
//        private CartesianTreeNode node;
//        private CartesianTreeNode parent;
//        private FindHighest parentAction;
//
//        // Inclusive, [from,to]
//        public FindHighest(int from, int to) {
//            this.from = from;
//            this.to = to;
//            highestIndex = from;
//            takesStep = false;
//        }
//
//        public FindHighest(int from, int to, CartesianTreeNode parent) {
//            this(from, to);
//            this.parent = parent;
//        }
//
//        public FindHighest(int from, int to, FindHighest parentAction) {
//            this(from, to, parentAction.parent);
//            this.parentAction = parentAction;
//        }
//
//        @Override
//        public void perform(CartesianTreeSort cartesianTreeSort, ArrayDisplay display) {
//            for (int i = from + 1; i <= to; i++) {
//                cartesianTreeSort.addToStart(new Compare(this, i));
//            }
//            cartesianTreeSort.addToStart(
//                    new LaterAction(() -> {
//                        node = new CartesianTreeNode(cartesianTreeSort.list.get(highestIndex));
//
//                        if (parent == null) {
//                            cartesianTreeSort.rootNode = node;
//                        } else if (parent.getLeft() == null) {
//                            parent.setLeft(node);
//                        } else {
//                            parent.setRight(node);
//                        }
//
//                        if (highestIndex > from) {
//                            cartesianTreeSort.addToStart(new FindHighest(from, highestIndex - 1, node));
//                        }
//                        if (highestIndex < to) {
//                            cartesianTreeSort.addToStart(new FindHighest(highestIndex + 1, to, node));
//                        }
//                    })
//            );
//        }
//
//        @Override
//        public void performDetailed(CartesianTreeSort cartesianTreeSort, AnimatedArrayDisplay display) {
//            for (int i = from + 1; i <= to; i++) {
//                cartesianTreeSort.addToStart(new Compare(this, i));
//            }
//            cartesianTreeSort.addToStart(
//                    new LaterAction(() -> {
//                        cartesianTreeSort.cartesianTree.lowerOthers(highestIndex, from, to);
//                        node = new CartesianTreeNode(cartesianTreeSort.list.get(highestIndex));
//
//                        if (parentAction != null) {
//                            cartesianTreeSort.addToStart(
//                                    new Wait(),
//                                    new LaterAction(() -> {
//                                        cartesianTreeSort.cartesianTree.drawLine(parentAction.highestIndex, highestIndex);
//                                    })
//                            );
//                        }
//
//                        if (parent == null) cartesianTreeSort.rootNode = node;
//                        else if (parent.getLeft() == null) parent.setLeft(node);
//                        else parent.setRight(node);
//
//                        if (highestIndex > from) {
//                            cartesianTreeSort.addToStart(new FindHighest(from, highestIndex - 1, this));
//                        }
//                        if (highestIndex < to) {
//                            cartesianTreeSort.addToStart(new FindHighest(highestIndex + 1, to, this));
//                        }
//                    })
//            );
//        }
//    }
//
//    private static class Compare extends CartesianTreeAction {
//
//        private final FindHighest findHighest;
//        private final int i;
//
//        public Compare(FindHighest findHighest, int i) {
//            this.findHighest = findHighest;
//            this.i = i;
//        }
//
//        @Override
//        public void perform(CartesianTreeSort cartesianTreeSort, ArrayDisplay display) {
//            if (cartesianTreeSort.list.get(i) > cartesianTreeSort.list.get(findHighest.highestIndex)) {
//                findHighest.highestIndex = i;
//            }
//        }
//
//        @Override
//        public void performDetailed(CartesianTreeSort cartesianTreeSort, AnimatedArrayDisplay display) {
//            display.reading(findHighest.highestIndex, i);
//            if (cartesianTreeSort.list.get(i) > cartesianTreeSort.list.get(findHighest.highestIndex)) {
//                findHighest.highestIndex = i;
//            }
//        }
//    }
//
//    private static class ExtractMax extends CartesianTreeAction {
//
//        private final int destination;
//
//        public ExtractMax(int destination) {
//            this.destination = destination;
//        }
//
//        @Override
//        public void perform(CartesianTreeSort cartesianTreeSort, ArrayDisplay display) {
//            CartesianTreeNode bottom = cartesianTreeSort.rootNode.getBottom();
//            cartesianTreeSort.addToStart(new Set(destination, cartesianTreeSort.rootNode.getValue()));
//            CartesianTreeNode.swapValues(cartesianTreeSort.rootNode, bottom);
//            cartesianTreeSort.addToStart(new MaxHeapify(cartesianTreeSort.rootNode));
//            if (bottom.getParent() == null) {
//                return;
//            }
//            bottom.getParent().removeChild(bottom);
//        }
//
//        @Override
//        public void performDetailed(CartesianTreeSort cartesianTreeSort, AnimatedArrayDisplay display) {
//
//        }
//    }
//
//    private static class MaxHeapify extends CartesianTreeAction {
//
//        private final CartesianTreeNode node;
//
//        private MaxHeapify(CartesianTreeNode node) {
//            this.node = node;
//        }
//
//        @Override
//        public void perform(CartesianTreeSort cartesianTreeSort, ArrayDisplay display) {
//            CartesianTreeNode largestChild = node.getLargestChild();
//            if (largestChild == null) return;
//
//            if (largestChild.getValue() > node.getValue()) {
//                CartesianTreeNode.swapValues(node, largestChild);
//                cartesianTreeSort.addToStart(new MaxHeapify(largestChild));
//            }
//        }
//
//        @Override
//        public void performDetailed(CartesianTreeSort cartesianTreeSort, AnimatedArrayDisplay display) {
//            CartesianTreeNode largestChild = node.getLargestChild();
//            if (largestChild == null) return;
//
//            if (largestChild.getValue() > node.getValue()) {
//                CartesianTreeNode.swapValues(node, largestChild);
//                cartesianTreeSort.addToStart(new MaxHeapify(largestChild));
//            }
//        }
//    }
}
