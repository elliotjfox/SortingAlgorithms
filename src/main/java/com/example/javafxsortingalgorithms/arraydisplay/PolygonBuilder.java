package com.example.javafxsortingalgorithms.arraydisplay;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;

public class PolygonBuilder {

    private Paint fill;
    private final List<Double> points;

    public PolygonBuilder() {
        fill = Color.BLACK;
        points = new ArrayList<>();
    }

    public PolygonBuilder painted(Paint fill) {
        this.fill = fill;
        return this;
    }

    public PolygonBuilder addPoints(Double... points) {
        // Must be an even number of points
        if (points.length % 2 != 0) return this;
        this.points.addAll(List.of(points));
        return this;
    }

    public Polygon build() {
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(points);
        polygon.setFill(fill);
        return polygon;
    }

    public static PolygonBuilder triangle(double length) {
        return triangle(length, true);
    }

    public static PolygonBuilder triangle(AnimatedArrayDisplay display) {
        return triangle(display.getElementWidth(), true);
    }

    public static PolygonBuilder triangle(AnimatedArrayDisplay display, boolean up) {
        return triangle(display.getElementWidth(), up);
    }

    public static PolygonBuilder triangle(double length, boolean up) {
        double sinLength = length * Math.sin(Math.toRadians(60));

        PolygonBuilder builder = new PolygonBuilder();

        if (up) {
            return builder.addPoints(
                    length / 2, 0.0,
                    length, sinLength,
                    0.0, sinLength
            );
        } else {
            return builder.addPoints(
                    0.0, 0.0,
                    length, 0.0,
                    length / 2, sinLength
            );
        }
    }
}
