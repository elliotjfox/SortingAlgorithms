package com.example.javafxsortingalgorithms.settings;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SettingsEntry extends HBox {

    private String name;
    private Pane entry;
    private String description;

    public SettingsEntry(String name, Pane entry, String description) {
        this.name = name;
        this.entry = entry;
        this.description = description;

        getChildren().add(new Label(name));
        getChildren().add(entry);
        getChildren().add(getDescriptionButton());
    }

    private Button getDescriptionButton() {
        Button button = new Button("i");
        button.setOnAction(event -> {
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setTitle(name);
            stage.setScene(new Scene(new Label(description)));
            stage.show();
        });
        return button;
    }
}
