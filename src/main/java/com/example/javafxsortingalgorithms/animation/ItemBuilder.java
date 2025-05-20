package com.example.javafxsortingalgorithms.animation;

import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private final AnimatedArrayDisplay display;

    private ItemPosition position;
    private final List<Node> nodes;

    public ItemBuilder(AnimatedArrayDisplay display) {
        this.display = display;
        this.nodes = new ArrayList<>();
    }

    public ItemBuilder at(ItemPosition position) {
        this.position = position;
        return this;
    }

    public ItemBuilder at(int index, double height) {
        this.position = new ItemIndexPosition(index, height);
        return this;
    }

    public ItemBuilder at(double x, double y) {
        this.position = new ItemExactPosition(x, y);
        return this;
    }

    public ItemBuilder with(Node... nodes) {
        this.nodes.addAll(List.of(nodes));
        return this;
    }

    public AnimatedItem build() {
        return new AnimatedItem(display, position, nodes);
    }

    public AnimatedSection buildSection(int width) {
        return buildSection(width * display.getElementWidth(), true);
    }

    public AnimatedSection buildSection(int width, boolean hasEdges) {
        return new AnimatedSection(display, position, nodes, width * display.getElementWidth(), hasEdges);
    }

    public AnimatedSection buildSection(double exactWidth, boolean hasEdges) {
        return new AnimatedSection(display, position, nodes, exactWidth, hasEdges);
    }

    public static AnimatedItem defaultArrow(AnimatedArrayDisplay display, int index) {
        return new ItemBuilder(display).with(PolygonWrapper.triangle(display, Color.BLACK, true)).at(index, 0).build();
    }
}
