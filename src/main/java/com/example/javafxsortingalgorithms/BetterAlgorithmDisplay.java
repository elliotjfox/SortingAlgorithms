package com.example.javafxsortingalgorithms;

import com.example.javafxsortingalgorithms.betteralgorithm.BetterAlgorithm;
import com.example.javafxsortingalgorithms.betteralgorithm.BetterGnomeSort;
import com.example.javafxsortingalgorithms.betteralgorithm.BetterSelectionSort;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BetterAlgorithmDisplay extends VBox {

    private BetterAlgorithm algorithm;
    private Timeline stepTimeline;

    private List<Integer> list;
    private List<Rectangle> rectangles;

    public BetterAlgorithmDisplay() {
        algorithm = new BetterSelectionSort();
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

        algorithm.setMode(BetterAlgorithm.Mode.NORMAL);
        algorithm.setWrapper(new BetterAlgorithm.Wrapper());
        algorithm.setList(list);
    }

    private void start() {
        stepTimeline.play();
        algorithm.initializeNormal();
    }

    private void step() {
        System.out.println("Step");
        algorithm.stepNormal();
        draw();
        if (algorithm.isDone()) {
            finish();
        }
    }

    private void finish() {
        stepTimeline.pause();
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
