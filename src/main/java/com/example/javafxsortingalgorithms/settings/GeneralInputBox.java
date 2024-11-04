package com.example.javafxsortingalgorithms.settings;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GeneralInputBox<T> extends TextField implements InputBox {

    private Supplier<T> startValueSupplier;
    private Consumer<String> setValue;

    public GeneralInputBox(Supplier<T> startValueSupplier, Consumer<String> setValue) {
        super(startValueSupplier.get().toString());

        this.startValueSupplier = startValueSupplier;
        this.setValue = setValue;

        textProperty().addListener((observable, oldValue, newValue) -> setValue.accept(newValue));
    }

    // TODO: Whats the diff?
    @Override
    public void resetValue() {
        setText(startValueSupplier.get().toString());
    }

    @Override
    public void updateValue() {
        setValue.accept(getText());
    }
}
