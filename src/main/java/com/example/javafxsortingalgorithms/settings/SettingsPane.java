package com.example.javafxsortingalgorithms.settings;

import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsPane extends Stage {

    DisplaySettings displaySettings;
    AlgorithmSettings<?> algorithmSettings;
    TitledPane algorithmPane;

    public SettingsPane() {
        displaySettings = new DisplaySettings();

        Accordion accordion = new Accordion();

        TitledPane displayPane = new TitledPane("Display Settings", displaySettings);
        algorithmPane = new TitledPane();
        algorithmPane.setText("Algorithm Settings");

        accordion.getPanes().addAll(displayPane, algorithmPane);

        setTitle("Settings");
        setScene(new Scene(new VBox(accordion), 300, 320));
    }

    public void setAlgorithmSettings(AlgorithmSettings<?> settings) {
        algorithmSettings = settings;
        algorithmPane.setContent(algorithmSettings);
    }

    public DisplaySettings getDisplaySettings() {
        return displaySettings;
    }

    public AlgorithmSettings<?> getAlgorithmSettings() {
        return algorithmSettings;
    }
}
