package com.example.javafxsortingalgorithms;

import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplayBase;
import com.example.javafxsortingalgorithms.arraydisplay.DisplayMode;
import com.example.javafxsortingalgorithms.arraydisplay.DisplayType;
import com.example.javafxsortingalgorithms.settings.AlgorithmType;
import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class Display extends BorderPane {

    private final AlgorithmController algorithmController;
    private final ButtonPanel buttonPanel;
    private final SettingsPane settingsPane;
    private final FlowPane arrayPane;

    private ArrayDisplayBase arrayDisplay;
    private TrialDisplay trialDisplay;
    private DisplayMode currentMode;
    private DisplayType displayType;

    public Display() {
        this.algorithmController = new AlgorithmController(this);

        this.buttonPanel = new ButtonPanel(this, algorithmController);
        setTop(buttonPanel);

        this.settingsPane = new SettingsPane();

        this.arrayPane = new FlowPane(Orientation.HORIZONTAL);
        arrayPane.setAlignment(Pos.CENTER);
        setCenter(arrayPane);

        setDisplayType(settingsPane.getDisplaySettings().getDisplayType());
        setMode(DisplayMode.NORMAL);
        algorithmSelected(AlgorithmType.SELECTION);
        algorithmController.reset();
    }

    public void setDisplayType(DisplayType type) {
        // Don't need to do anything
        if (displayType == type) return;
        displayType = type;
        if (arrayDisplay != null) {
            arrayPane.getChildren().remove(arrayDisplay);
        }
        arrayDisplay = type.createDisplay();
        arrayPane.getChildren().add(arrayDisplay);
        algorithmController.linkToDisplay(arrayDisplay);
    }

    public void setMode(DisplayMode mode) {
        currentMode = mode;
        algorithmController.setMode(mode);
        buttonPanel.setMode(mode);
        if (mode == DisplayMode.TRIAL) {
            trialDisplay = new TrialDisplay();
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//            FlowPane flowPane = new FlowPane();
//            flowPane.setAlignment(Pos.TOP_CENTER);
//            flowPane.getChildren().add(trialDisplay);
            scrollPane.setContent(trialDisplay);
            setCenter(scrollPane);
        } else {
            setCenter(arrayDisplay);
        }
    }

    public void algorithmSelected(AlgorithmType algorithm) {
        buttonPanel.setSelectedAlgorithm(algorithm);
        settingsPane.setAlgorithmSettings(algorithm.getSettings());
    }

    public void openSettings() {
        settingsPane.show();
    }

    public void onClose() {
        settingsPane.close();
    }

    public SettingsPane getSettings() {
        return settingsPane;
    }

    public DisplayMode getMode() {
        return currentMode;
    }

    public ButtonPanel getButtonPanel() {
        return buttonPanel;
    }

    public TrialDisplay getTrialDisplay() {
        return trialDisplay;
    }
}
