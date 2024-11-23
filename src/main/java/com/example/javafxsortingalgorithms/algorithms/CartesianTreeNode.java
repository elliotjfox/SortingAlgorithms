package com.example.javafxsortingalgorithms.algorithms;

public class CartesianTreeNode {

    private int value;
    private int index;
    private CartesianTreeNode left;
    private CartesianTreeNode right;
    private CartesianTreeNode parent;

    public CartesianTreeNode() {

    }

    public CartesianTreeNode(int value, int index) {
        this.value = value;
        this.index = index;
    }

    public void setLeft(CartesianTreeNode left) {
        if (this.left != null) this.left.setParent(null);
        this.left = left;
        if (left != null) left.setParent(this);
    }

    public void setRight(CartesianTreeNode right) {
        if (this.right != null) this.right.setParent(null);
        this.right = right;
        if (right != null) right.setParent(this);
    }

    public void setValue(int n) {
        this.value = n;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setParent(CartesianTreeNode parent) {
        this.parent = parent;
    }

    public CartesianTreeNode getLeft() {
        return left;
    }

    public CartesianTreeNode getRight() {
        return right;
    }

    public CartesianTreeNode getRightmost() {
        if (right == null) return this;
        return right.getRightmost();
    }


    public int getIndex() {
        return index;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return STR."CartesianTreeNode{value=\{value}, index=\{index}\{'}'}";
    }

    public static void swapChildren(CartesianTreeNode node1, CartesianTreeNode node2) {
        CartesianTreeNode tmpLeft = node1.getLeft();
        CartesianTreeNode tmpRight = node1.getRight();
        node1.setLeft(node2.getLeft());
        node1.setRight(node2.getRight());
        node2.setLeft(tmpLeft);
        node2.setRight(tmpRight);
    }

    public static void swapIndex(CartesianTreeNode node1, CartesianTreeNode node2) {
        int tmp = node1.getIndex();
        node1.setIndex(node2.index);
        node2.setIndex(tmp);
    }
}
