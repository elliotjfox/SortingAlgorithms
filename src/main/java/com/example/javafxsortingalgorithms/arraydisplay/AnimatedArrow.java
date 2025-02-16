package com.example.javafxsortingalgorithms.arraydisplay;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

public class AnimatedArrow extends AnimatedItem {

    private final Polygon triangle;
    private boolean pointingUp;
    private double length;

    public AnimatedArrow(AnimatedArrayDisplay display) {
        this(display, true);
    }

    public AnimatedArrow(AnimatedArrayDisplay display, boolean up) {
        super(display);

        length = display.getElementWidth();
        pointingUp = up;

        triangle = new Polygon();
        getChildren().add(triangle);

        createPoints();
    }

    public void setTriangleLength(double length) {
        this.length = length;
        createPoints();
    }

    public void setFill(Paint paint) {
        triangle.setFill(paint);
    }

    public void setPointingUp(boolean up) {
        pointingUp = up;
        createPoints();
    }

    public void createPoints() {
        triangle.getPoints().clear();
        if (pointingUp) {
            triangle.getPoints().addAll(
                    length / 2, 0.0,
                    length, length * Math.sin(Math.toRadians(60)),
                    0.0, length * Math.sin(Math.toRadians(60))
            );
        } else {
            triangle.getPoints().addAll(
                0.0, 0.0,
                length, 0.0,
                length / 2, length * Math.sin(Math.toRadians(60))
            );
        }
    }
}
