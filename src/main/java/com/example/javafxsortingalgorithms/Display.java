package com.example.javafxsortingalgorithms;

import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplayBase;
import com.example.javafxsortingalgorithms.arraydisplay.DisplayMode;
import com.example.javafxsortingalgorithms.arraydisplay.DisplayType;
import com.example.javafxsortingalgorithms.settings.AlgorithmType;
import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

public class Display extends BorderPane {

    private AlgorithmController algorithmController;
    private ButtonPanel buttonPanel;
    private ArrayDisplayBase arrayDisplay;
    private SettingsPane settingsPane;
    private FlowPane arrayPane;

    private DisplayMode currentMode;
    private DisplayType displayType;
    private boolean createdAlgorithm;

    public Display() {
        this.algorithmController = new AlgorithmController(this);

        this.buttonPanel = new ButtonPanel(this);
        setTop(buttonPanel);

        this.arrayPane = new FlowPane(Orientation.HORIZONTAL);
        arrayPane.setAlignment(Pos.CENTER);
        setCenter(arrayPane);

        settingsPane = new SettingsPane();

        setDisplayType(settingsPane.getDisplaySettings().getDisplayType());
        setMode(DisplayMode.NORMAL);
        algorithmSelected(AlgorithmType.SELECTION);
        reset();
    }

    private void setDisplayType(DisplayType type) {
        System.out.println("Current: " + displayType + ", next: " + type);
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
    }

    private void createAlgorithm() {
        List<Integer> listCopy = new ArrayList<>(algorithmController.getList());
        algorithmController.setAlgorithm(settingsPane.getAlgorithmSettings().createAlgorithm(listCopy));
        createdAlgorithm = true;
    }

    public void play() {
        createAlgorithm();
        algorithmController.play();
    }

    public void reset() {
        setDisplayType(settingsPane.getDisplaySettings().getDisplayType());
        algorithmController.reset();
        createdAlgorithm = false;
    }

    public void step() {
        if (!createdAlgorithm) createAlgorithm();
        algorithmController.step();
    }

    public void stop() {
        algorithmController.stop();
    }

    public void resume() {
        algorithmController.resume();
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
}
