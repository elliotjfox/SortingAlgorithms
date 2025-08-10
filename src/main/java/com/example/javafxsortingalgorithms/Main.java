package com.example.javafxsortingalgorithms;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) {
        Main.stage = stage;
        Display display = new Display();
        Scene scene = new Scene(display, 1050, 650);
        stage.setOnCloseRequest(_ -> display.onClose());

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