package com.example.javafxsortingalgorithms.arraydisplay;

import com.example.javafxsortingalgorithms.animation.AnimatedReadArrow;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class HilbertCurveDisplay extends SimpleArrayDisplay<Rectangle> {

    private static final double COLOUR_RANGE = 360;

    private static List<Point2D> positions;

    public HilbertCurveDisplay() {
        super();
    }

    @Override
    public void initializeElements(List<Integer> list) {
        generatePositions();
        super.initializeElements(list);
    }

    private void generatePositions() {
        positions = new ArrayList<>();

        int n = getDimensions();

        for (int i = 0; i < currentSettings.size(); i++) {
            positions.add(getPoint(i, n));
        }
    }

    public Point2D getPoint(int d, int n) {
        Point2D point = new Point2D(0, 0);

        for (int s = 1; s < n; s *= 2) {
            int rx = 1 & (d / 2);
            int ry = 1 & (d ^ rx);

            point = rotatePoint(point, rx, ry, s);

            point = point.add(s * rx, s * ry);

            d /= 4;
        }

        return point;
    }

    private Point2D rotatePoint(Point2D point, int rx, int ry, int s) {
        if (ry == 0) {
            if (rx == 1) {
                return new Point2D(s - 1 - point.getY(), s - 1 - point.getX());
            }
            return new Point2D(point.getY(), point.getX());
        }
        return point;
    }

    @Override
    protected Timeline createReadTimeline(AnimatedReadArrow readArrow, int index, int value) {
        // TODO
        return null;
    }

    @Override
    protected Rectangle createElement() {
        return new Rectangle();
    }

    @Override
    protected void formatElement(int index, int value, Rectangle element) {
        element.setX(currentSettings.height() * getXPos(index) / getDimensions());
        element.setY(currentSettings.height() * (1 - getYPos(index) / getDimensions()));

        element.setHeight(currentSettings.height() / getDimensions());
        element.setWidth(currentSettings.height() / getDimensions());
        element.setFill(Color.hsb((double) value / currentSettings.size() * COLOUR_RANGE, 1.0, 1.0));
    }

    private int getDimensions() {
        return (int) Math.sqrt(currentSettings.size());
    }

    private double getXPos(int index) {
        return positions.get(index).getX();
    }

    private double getYPos(int index) {
        return positions.get(index).getY();
    }

    @Override
    protected Timeline moveElement(Rectangle element, int targetIndex) {
        // TODO
        return null;
    }
}
