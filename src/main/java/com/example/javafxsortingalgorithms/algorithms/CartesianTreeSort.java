package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayAnimatedDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.AnimatedCartesianTree;

import java.util.List;

public class CartesianTreeSort extends ActionSortingAlgorithm {

    private CartesianTreeNode rootNode;
    private AnimatedCartesianTree cartesianTree;

    public CartesianTreeSort(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);

//        rootNode = new CartesianTreeNode();
        actions.add(new FindHighest(0, list.size() - 1));
        actions.add(new LaterAction(() -> {
            System.out.println("Root: " + rootNode);
            System.out.println("Left: " + rootNode.getLeft());
            System.out.println("Right: " + rootNode.getRight());
        }));
        actions.add(new ExtractMax());
        actions.add(new LaterAction(() -> {
            System.out.println("Root: " + rootNode);
            System.out.println("Left: " + rootNode.getLeft());
            System.out.println("Right: " + rootNode.getRight());
        }));
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

    }

    @Override
    public void startAnimated(ArrayAnimatedDisplay display) {
        cartesianTree = new AnimatedCartesianTree(display, list);
        display.addItem(cartesianTree, list.size(), 600);
    }

    @Override
    public String getName() {
        return null;
    }

    private abstract static class CartesianTreeAction extends AlgorithmAction {

        @Override
        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            if (algorithm instanceof CartesianTreeSort cartesianTreeSort) {
                perform(cartesianTreeSort, display);
            }
        }

        @Override
        public void performDetailed(ActionSortingAlgorithm algorithm, ArrayAnimatedDisplay display) {
            if (algorithm instanceof CartesianTreeSort cartesianTreeSort) {
                performDetailed(cartesianTreeSort, display);
            }
        }

        public abstract void perform(CartesianTreeSort cartesianTreeSort, ArrayDisplay display);

        public abstract void performDetailed(CartesianTreeSort cartesianTreeSort, ArrayAnimatedDisplay display);
    }

    private static class FindHighest extends CartesianTreeAction {

        private final int from;
        private final int to;
        private int highestIndex;
        private CartesianTreeNode node;
        private CartesianTreeNode parent;

        // Inclusive, [from,to]
        public FindHighest(int from, int to) {
            this.from = from;
            this.to = to;
            highestIndex = from;
            takesStep = false;
        }

        public FindHighest(int from, int to, CartesianTreeNode parent) {
            this(from, to);
            this.parent = parent;
        }

        @Override
        public void perform(CartesianTreeSort cartesianTreeSort, ArrayDisplay display) {
            for (int i = from + 1; i <= to; i++) {
                cartesianTreeSort.addToStart(new Compare(this, i));
            }
            cartesianTreeSort.addToStart(
                    new LaterAction(() -> {
                        node = new CartesianTreeNode(cartesianTreeSort.list.get(highestIndex), highestIndex);
                        if (parent == null) {
                            cartesianTreeSort.rootNode = node;
                        } else if (parent.getLeft() == null) {
                            parent.setLeft(node);
                        } else {
                            parent.setRight(node);
                        }

                        if (highestIndex > from) {
                            cartesianTreeSort.addToStart(new FindHighest(from, highestIndex - 1, node));
                        }
                        if (highestIndex < to) {
                            cartesianTreeSort.addToStart(new FindHighest(highestIndex + 1, to, node));
                        }
                    })
            );
        }

        @Override
        public void performDetailed(CartesianTreeSort cartesianTreeSort, ArrayAnimatedDisplay display) {
            for (int i = from + 1; i <= to; i++) {
                cartesianTreeSort.addToStart(new Compare(this, i));
            }
            cartesianTreeSort.addToStart(
                    new LaterAction(() -> {
                        cartesianTreeSort.cartesianTree.lowerOthers(highestIndex, from, to);
                        node = new CartesianTreeNode(cartesianTreeSort.list.get(highestIndex), highestIndex);
                        if (parent == null) {
                            cartesianTreeSort.rootNode = node;
                        } else if (parent.getLeft() == null) {
                            parent.setLeft(node);
                        } else {
                            parent.setRight(node);
                        }
                        if (highestIndex > from) {
                            cartesianTreeSort.addToStart(new FindHighest(from, highestIndex - 1, node));
                        }
                        if (highestIndex < to) {
                            cartesianTreeSort.addToStart(new FindHighest(highestIndex + 1, to, node));
                        }
                    })
            );
        }
    }

    private static class Compare extends CartesianTreeAction {

        private final FindHighest findHighest;
        private final int i;

        public Compare(FindHighest findHighest, int i) {
            this.findHighest = findHighest;
            this.i = i;
        }

        @Override
        public void perform(CartesianTreeSort cartesianTreeSort, ArrayDisplay display) {
            if (cartesianTreeSort.list.get(i) > cartesianTreeSort.list.get(findHighest.highestIndex)) {
                findHighest.highestIndex = i;
            }
        }

        @Override
        public void performDetailed(CartesianTreeSort cartesianTreeSort, ArrayAnimatedDisplay display) {
            display.reading(findHighest.highestIndex, i);
            if (cartesianTreeSort.list.get(i) > cartesianTreeSort.list.get(findHighest.highestIndex)) {
                findHighest.highestIndex = i;
            }
        }
    }

    private static class ExtractMax extends CartesianTreeAction {

        @Override
        public void perform(CartesianTreeSort cartesianTreeSort, ArrayDisplay display) {
            CartesianTreeNode rightMost = cartesianTreeSort.rootNode.getRightmost();
            int index = rightMost.getIndex();
            System.out.println(index);
            cartesianTreeSort.addToStart(new Swap(cartesianTreeSort.rootNode.getIndex(), index));

            CartesianTreeNode.swapIndex(cartesianTreeSort.rootNode, rightMost);
            CartesianTreeNode.swapChildren(cartesianTreeSort.rootNode, rightMost);

            CartesianTreeNode tmp = cartesianTreeSort.rootNode;
            cartesianTreeSort.rootNode = rightMost;
            cartesianTreeSort.rootNode.getRightmost().setRight(tmp);
        }

        @Override
        public void performDetailed(CartesianTreeSort cartesianTreeSort, ArrayAnimatedDisplay display) {

        }
    }

    private static class MaxHeapify extends CartesianTreeAction {

        private final CartesianTreeNode node;

        private MaxHeapify(CartesianTreeNode node) {
            this.node = node;
        }

        @Override
        public void perform(CartesianTreeSort cartesianTreeSort, ArrayDisplay display) {
            if (node.getLeft() == null && node.getRight() == null) {
                return;
            }

            if (node.getLeft() != null && node.getRight() != null) {
                if (node.getValue() >= node.getLeft().getValue() && node.getValue() >= node.getRight().getValue()) {
                    return;
                }
                CartesianTreeNode nodeToSwap = node.getLeft().getValue() >= node.getRight().getValue() ? node.getLeft() : node.getRight();
                CartesianTreeNode.swapChildren(node, nodeToSwap);
                cartesianTreeSort.addToStart(new MaxHeapify(nodeToSwap));
            } else if (node.getLeft() != null && node.getLeft().getValue() > node.getValue()) {
                CartesianTreeNode.swapChildren(node, node.getLeft());
            } else if (node.getRight() != null && node.getRight().getValue() > node.getValue()) {
                CartesianTreeNode.swapChildren(node, node.getRight());
            }
        }

        @Override
        public void performDetailed(CartesianTreeSort cartesianTreeSort, ArrayAnimatedDisplay display) {
            if (node.getLeft() == null && node.getRight() == null) {
                return;
            }

            if (node.getLeft() != null && node.getRight() != null) {
                if (node.getValue() >= node.getLeft().getValue() && node.getValue() >= node.getRight().getValue()) {
                    return;
                }
                CartesianTreeNode nodeToSwap = node.getLeft().getValue() >= node.getRight().getValue() ? node.getLeft() : node.getRight();
                CartesianTreeNode.swapChildren(node, nodeToSwap);
                cartesianTreeSort.addToStart(new MaxHeapify(nodeToSwap));
            } else if (node.getLeft() != null && node.getLeft().getValue() > node.getValue()) {
                CartesianTreeNode.swapChildren(node, node.getLeft());
            } else if (node.getRight() != null && node.getRight().getValue() > node.getValue()) {
                CartesianTreeNode.swapChildren(node, node.getRight());
            }
        }
    }
}
