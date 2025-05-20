package com.example.javafxsortingalgorithms.animation;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

public class PolygonWrapper extends Polygon {

    public PolygonWrapper(Paint fill, double... points) {
        super(points);
        setFill(fill);
    }

    public static Polygon triangle(AnimatedArrayDisplay display, Paint fill, boolean up) {
        return triangle(display.getElementWidth(), fill, up);
    }

    public static Polygon triangle(AnimatedArrayDisplay display, Paint fill) {
        return triangle(display.getElementWidth(), fill, true);
    }

    public static Polygon triangle(AnimatedArrayDisplay display) {
        return triangle(display.getElementWidth(), Color.BLACK, true);
    }

    public static Polygon triangle(double length, Paint fill, boolean up) {
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
        polygon.setFill(fill);

        return polygon;
    }
}
