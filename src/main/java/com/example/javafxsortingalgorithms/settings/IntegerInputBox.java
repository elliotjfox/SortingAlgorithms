package com.example.javafxsortingalgorithms.settings;

import javafx.scene.control.TextField;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class IntegerInputBox extends TextField implements InputBox {

    private final Supplier<Integer> startValueSupplier;

    public IntegerInputBox(Supplier<Integer> startValueSupplier, Consumer<Integer> setValue) {
        super(startValueSupplier.get().toString());

        this.startValueSupplier = startValueSupplier;

        textProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.equals(oldValue, newValue)) return;
            try {
                int value = Integer.parseInt(newValue);
                setValue.accept(value);
                setStyle("-fx-text-fill: black");
            } catch (Exception e) {
                setStyle("-fx-text-fill: red");
            }
        });
    }

    @Override
    public void resetValue() {
        setText(startValueSupplier.get().toString());
    }

    // TODO: Huh?
    @Override
    public void updateValue() {
        setText(startValueSupplier.get().toString());
    }
}
