package com.example.javafxsortingalgorithms.algorithms.algorithmsettings;

import com.example.javafxsortingalgorithms.TestDisplay;
import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import com.example.javafxsortingalgorithms.settings.IntegerInputBox;
import com.example.javafxsortingalgorithms.settings.SettingsSection;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.List;

public abstract class AlgorithmSettings extends SettingsSection {

    protected int speed;

    private final IntegerInputBox speedBox;

    private static final String speedInfo = "This determines how many steps the algorithm takes per millisecond";

    public AlgorithmSettings(String algorithmName) {
        super();

        speedBox = new IntegerInputBox(() -> speed, (i) -> speed = i);

        addSetting(new Label(algorithmName));
        addSetting(new Label("Speed"), speedBox, speedInfo);
    }

    public void resetSettings() {
        speed = 1;

        if (speedBox != null) speedBox.updateValue();
    }

    public int getSpeed() {
        return speed;
    }

    public abstract SortingAlgorithm createAlgorithm(List<Integer> array);

    public abstract SortingAlgorithm createInstantAlgorithm(List<Integer> array);

    public abstract SortingAlgorithm getAlgorithm();

    public abstract void resetAlgorithm();
}
