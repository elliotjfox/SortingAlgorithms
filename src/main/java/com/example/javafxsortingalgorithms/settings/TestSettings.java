package com.example.javafxsortingalgorithms.settings;

import javafx.scene.control.CheckBox;

public class TestSettings extends SettingsSection {

    private int numberElements;
    private boolean showRawTime;

    private final IntegerInputBox numberElementsBox;
    private final CheckBox showRawTimeBox;

    public TestSettings() {
        super();

        numberElementsBox = new IntegerInputBox(() -> numberElements, (i) -> numberElements = i);
        addSetting("Number of elements", numberElementsBox);

        // TODO:
        showRawTimeBox = new CheckBox("Show Raw Time");
        showRawTimeBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            showRawTime = newValue;
        });

        addSetting(buildResetButton());
    }

    @Override
    public void resetSettings() {
        numberElements = Settings.defaultTestSize;

        if (numberElementsBox != null) numberElementsBox.updateValue();
    }

    public int getNumberElements() {
        return numberElements;
    }
}
