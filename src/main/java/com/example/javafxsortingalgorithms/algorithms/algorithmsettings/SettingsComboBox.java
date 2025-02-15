package com.example.javafxsortingalgorithms.algorithms.algorithmsettings;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class SettingsComboBox<T> extends ComboBox<T> implements AlgorithmSettingObject {

    private final String name;
    private final String description;
    private final T initialValue;

    private T value;

    public SettingsComboBox(String name, T[] values, T initialValue) {
        this(name, "", values, initialValue);
    }

    public SettingsComboBox(String name, String description, T[] values, T initialValue) {
        super(FXCollections.observableArrayList(values));

        this.name = name;
        this.description = description;
        this.initialValue = initialValue;

        this.value = initialValue;

        getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> value = newValue);
        getSelectionModel().select(value);
    }

    @Override
    public void resetSetting() {
        value = initialValue;
        getSelectionModel().select(value);
    }

    @Override
    public void add(AlgorithmSettings settings) {
        if (description.isBlank()) settings.addSetting(name, this);
        else settings.addSetting(new Label(name), this, description);
    }
}
