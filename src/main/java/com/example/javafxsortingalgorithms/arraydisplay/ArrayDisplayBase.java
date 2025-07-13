package com.example.javafxsortingalgorithms.arraydisplay;

import javafx.scene.layout.Pane;

public abstract class ArrayDisplayBase extends Pane implements IArrayDisplay {

    protected DisplaySettings currentSettings;

    @Override
    public void initializeSettings(DisplaySettings settings) {
        this.currentSettings = settings;
    }
}
