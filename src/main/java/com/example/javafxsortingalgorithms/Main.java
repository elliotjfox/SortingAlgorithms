package com.example.javafxsortingalgorithms;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) {
        Main.stage = stage;
        AlgorithmDisplay algorithmDisplay = new AlgorithmDisplay();
        Scene scene = new Scene(algorithmDisplay, 1050, 650);
//        stage.setOnCloseRequest(windowEvent -> algorithmDisplay.close());

        stage.setTitle("Sorting Algorithms");
        stage.setScene(scene);
        stage.show();

    }

    public static void close() {
        if (stage != null) {
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}