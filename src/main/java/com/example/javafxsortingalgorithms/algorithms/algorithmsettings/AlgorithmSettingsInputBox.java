package com.example.javafxsortingalgorithms.algorithms.algorithmsettings;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Objects;
import java.util.function.Function;

public class AlgorithmSettingsInputBox<T> extends TextField implements AlgorithmSettingObject {

    private final String name;
    private final String description;
    private final T initialValue;
    private final Function<String, T> parseInput;
    private final Function<T, Boolean> validateInput;

    private T value;

    public AlgorithmSettingsInputBox(String name, T initialValue,
                                     Function<String, T> parseInput, Function<T, Boolean> validateInput) {
        this(name, "", initialValue, parseInput, validateInput);
    }

    public AlgorithmSettingsInputBox(String name, String description, T initialValue,
                                     Function<String, T> parseInput, Function<T, Boolean> validateInput) {
        super(initialValue.toString());

        this.name = name;
        this.description = description;
        this.initialValue = initialValue;
        this.parseInput = parseInput;
        this.validateInput = validateInput;

        this.value = initialValue;

        textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (Objects.equals(oldValue, newValue)) return;
                    try {
                        T tmp = parseInput.apply(newValue);
                        if (validateInput.apply(tmp)) {
                            value = tmp;
                            setStyle("-fx-text-fill: black");
                        } else {
                            setStyle("-fx-text-fill: red");
                        }
                    } catch (Exception e) {
                        setStyle("-fx-text-fill: red");
                    }
                }
        );
    }

    @Override
    public void resetSetting() {
        value = initialValue;
        setText(initialValue.toString());
    }

    @Override
    public void add(AlgorithmSettings<?> settings) {
        if (description.isBlank()) settings.addSetting(name, this);
        else settings.addSetting(new Label(name), this, description);
    }

    public T getValue() {
        return value;
    }
}
