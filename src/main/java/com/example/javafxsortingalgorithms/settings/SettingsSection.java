package com.example.javafxsortingalgorithms.settings;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;

public abstract class SettingsSection extends GridPane {

    protected int rows = 0;

    public SettingsSection() {
        super(5, 5);

        resetSettings();
    }

    public Button buildResetButton() {
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(event -> resetSettings());
        return resetButton;
    }

    public void addSetting(Node setting) {
        add(setting, 0, rows, 3, 1);
        rows++;
    }

    public void addSetting(String settingName, Node setting) {
        addSetting(new Label(settingName), setting);
    }

    public void addSetting(Node settingName, Node setting) {
        add(settingName, 0, rows);
        add(setting, 1, rows);
        rows++;
    }

    public void addSetting(Node settingsName, Node setting, String settingInfo) {
        add(settingsName, 0, rows);
        add(setting, 1, rows);
        add(createInfoButton(settingInfo), 2, rows);
        rows++;
    }

    protected Button createInfoButton(String info) {
        Button button = new Button("i");
        button.setBorder(
                new Border(
                        new BorderStroke(
                                Color.rgb(73,192,237),
                                BorderStrokeStyle.SOLID,
                                new CornerRadii(100, true),
                                BorderWidths.DEFAULT
                        )
        ));
        button.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.rgb(27, 179, 255),
                                new CornerRadii(100, true),
                                Insets.EMPTY
                        )
                )
        );
        button.setPrefSize(20, 20);
        button.setTextFill(Color.WHITE);
        button.setFont(Font.font("Verdana", FontWeight.BOLD, 8));
        // TODO: Make this have a popup somehow
        button.setOnAction(event -> {
            System.out.println("Would display info here: \"" + info + "\"");
        });
        return button;
    }

    public abstract void resetSettings();
}
