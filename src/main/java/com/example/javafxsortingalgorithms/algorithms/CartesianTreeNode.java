package com.example.javafxsortingalgorithms.algorithms;

public class CartesianTreeNode {

    private int value;
    private CartesianTreeNode left;
    private CartesianTreeNode right;
    private CartesianTreeNode parent;

    public CartesianTreeNode() {

    }

    public CartesianTreeNode(int value) {
        this.value = value;
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

    public void setParent(CartesianTreeNode parent) {
        this.parent = parent;
    }

    public void removeChild(CartesianTreeNode child) {
        if (left == child) {
            setLeft(null);
        } else if (right == child) {
            setRight(null);
        }
    }

    public CartesianTreeNode getLeft() {
        return left;
    }

    public CartesianTreeNode getRight() {
        return right;
    }

    public CartesianTreeNode getParent() {
        return parent;
    }

    public CartesianTreeNode getBottom() {
        if (right == null && left == null) return this;
        else if (right == null) return left.getBottom();
        else return right.getBottom();
    }

//    public CartesianTreeNode getSmallest() {
//        if (right == null && left == null) return this;
//        else if (right == null) return left;
//        else if (left == null) return right;
//
//    }

    public CartesianTreeNode getLargestChild() {
        if (right == null) return left;
        else if (left == null) return right;
        else return left.getValue() > right.getValue() ? left : right;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "CartesianTreeNode{value=" + value + "}";
    }

    public static void swapChildren(CartesianTreeNode node1, CartesianTreeNode node2) {
        CartesianTreeNode tmpLeft = node1.getLeft();
        CartesianTreeNode tmpRight = node1.getRight();
        node1.setLeft(node2.getLeft());
        node1.setRight(node2.getRight());
        node2.setLeft(tmpLeft);
        node2.setRight(tmpRight);
    }

    public static void swapValues(CartesianTreeNode node1, CartesianTreeNode node2) {
        int tmp = node1.getValue();
        node1.setValue(node2.getValue());
        node2.setValue(tmp);
    }
}
