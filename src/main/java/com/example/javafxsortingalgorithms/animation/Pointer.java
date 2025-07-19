package com.example.javafxsortingalgorithms.animation;

import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import com.example.javafxsortingalgorithms.arraydisplay.DisplayMode;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Pointer extends Group {

    private final SortingAlgorithm algorithm;
    private final DisplayMode mode;

    public Pointer(SortingAlgorithm algorithm, DisplayMode mode) {
        this.algorithm = algorithm;
        this.mode = mode;



//        getChildren().add(PolygonWrapper.triangle(null, Color.BLACK, true));
    }
}
