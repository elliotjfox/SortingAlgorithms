package com.example.javafxsortingalgorithms.arraydisplay;

import javafx.scene.Node;
import javafx.scene.shape.Polygon;

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
        return new AnimatedItemBuilder(display).with(PolygonBuilder.triangle(display).build()).at(index, 0).build();
    }

    public static AnimatedItemBuilder triangle(AnimatedArrayDisplay display) {
        return triangle(display, true);
    }

    public static AnimatedItemBuilder triangle(AnimatedArrayDisplay display, boolean up) {
        double length = display.getElementWidth();
        double sinLength = length * Math.sin(Math.toRadians(60));

        Polygon polygon = new Polygon();

        if (up) {
            polygon.getPoints().addAll(
                    length / 2, 0.0,
                    length, sinLength,
                    0.0, sinLength
            );
        } else {
            polygon.getPoints().addAll(
                    0.0, 0.0,
                    length, 0.0,
                    length / 2, sinLength
            );
        }

        return new AnimatedItemBuilder(display).with(polygon);
    }
}
