package com.example.javafxsortingalgorithms.arraydisplay;

import javafx.scene.layout.Pane;

import java.util.List;

public abstract class ArrayDisplayBase extends Pane implements IArrayDisplay {

    protected DisplaySettings currentSettings;

    @Override
    public void initializeSettings(DisplaySettings settings) {
        this.currentSettings = settings;
    }
}
