package com.example.javafxsortingalgorithms;

import com.example.javafxsortingalgorithms.algorithms.GnomeSort;
import com.example.javafxsortingalgorithms.betteralgorithm.BetterAlgorithm;
import com.example.javafxsortingalgorithms.betteralgorithm.BetterGnomeSort;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BetterAlgorithmDisplay extends VBox {

    private BetterGnomeSort gnomeSort;
    private Timeline stepTimeline;

    private List<Integer> list;
    private List<Rectangle> rectangles;

    public BetterAlgorithmDisplay() {
        gnomeSort = new BetterGnomeSort();
        stepTimeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1),
                        event -> step()
                )
        );
        stepTimeline.setCycleCount(Animation.INDEFINITE);

        Button start = new Button("Start");
        start.setOnAction(event -> start());
        getChildren().add(start);

        list = new ArrayList<>();
        rectangles = new ArrayList<>();

        Pane pane = new HBox(1);
        for (int i = 0; i < 250; i++) {
            list.add(i);
            Rectangle rectangle = new Rectangle(i, 4, Color.LIGHTGRAY);
            pane.getChildren().add(rectangle);
            rectangles.add(rectangle);
        }
        Collections.shuffle(list);
        getChildren().add(pane);
        draw();

        gnomeSort.setMode(BetterAlgorithm.Mode.NORMAL);
        gnomeSort.setWrapper(new BetterAlgorithm.Wrapper());
        gnomeSort.setList(list);
    }

    private void start() {
        stepTimeline.play();
        gnomeSort.initializeNormal();
    }

    private void step() {
        System.out.println("Step");
        gnomeSort.stepNormal();
        draw();
    }

    private void draw() {
        System.out.println("Draw");
        for (int i = 0; i < rectangles.size(); i++) {
            Rectangle rectangle = rectangles.get(i);
//            rectangle.setX(i * 5);
//            rectangle.setY(0);
            rectangle.setWidth(4);
            rectangle.setHeight(list.get(i));
        }
    }
}
