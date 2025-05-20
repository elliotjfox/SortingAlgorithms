package com.example.javafxsortingalgorithms.arraydisplay;

import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class AnimatedItemBuilder {

    private final AnimatedArrayDisplay display;

    private AnimatedItemPosition position;
    private final List<Node> nodes;

    public AnimatedItemBuilder(AnimatedArrayDisplay display) {
        this.display = display;
        this.nodes = new ArrayList<>();
    }

    public AnimatedItemBuilder at(AnimatedItemPosition position) {
        this.position = position;
        return this;
    }

    public AnimatedItemBuilder at(int index, double height) {
        this.position = new AnimatedItemIndexPosition(index, height);
        return this;
    }

    public AnimatedItemBuilder at(double x, double y) {
        this.position = new AnimatedItemExactPosition(x, y);
        return this;
    }

    public AnimatedItemBuilder with(Node... nodes) {
        this.nodes.addAll(List.of(nodes));
        return this;
    }

    public AnimatedItem build() {
        return new AnimatedItem(display, position, nodes);
    }

    public static AnimatedItem defaultArrow(AnimatedArrayDisplay display, int index) {
        return new AnimatedItemBuilder(display).with(PolygonWrapper.triangle(display, Color.BLACK, true)).at(index, 0).build();
    }
}
