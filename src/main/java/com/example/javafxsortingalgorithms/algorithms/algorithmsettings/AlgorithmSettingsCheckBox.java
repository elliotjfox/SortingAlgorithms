package com.example.javafxsortingalgorithms.algorithms.algorithmsettings;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class AlgorithmSettingsCheckBox extends CheckBox implements AlgorithmSettingObject {

    private final String name;
    private final String description;
    private final boolean initialValue;

    private boolean value;

    public AlgorithmSettingsCheckBox(String settingName, boolean initialValue) {
        this(settingName, "", initialValue);
    }

    public AlgorithmSettingsCheckBox(String settingsName, String description, boolean initialValue) {
        super();

        this.name = settingsName;
        this.description = description;
        this.initialValue = initialValue;

        value = initialValue;

        selectedProperty().addListener((_, _, newValue) -> value = newValue);
    }

    @Override
    public void resetSetting() {
        value = initialValue;
        setSelected(value);
    }

    @Override
    public void add(AlgorithmSettings<?> settings) {
        if (description.isBlank()) settings.addSetting(name, this);
        else settings.addSetting(new Label(name), this, description);
    }

    public boolean getValue() {
        return value;
    }
}
