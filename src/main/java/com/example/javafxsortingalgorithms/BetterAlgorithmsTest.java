package com.example.javafxsortingalgorithms;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BetterAlgorithmsTest extends Application {
    @Override
    public void start(Stage stage) {
        BetterAlgorithmDisplay betterAlgorithmDisplay = new BetterAlgorithmDisplay();
        Scene scene = new Scene(betterAlgorithmDisplay, 1050, 650);

        stage.setTitle("Sorting Algorithms");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
