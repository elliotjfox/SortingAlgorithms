package com.example.javafxsortingalgorithms.algorithms.algorithmsettings;

import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import com.example.javafxsortingalgorithms.settings.SettingsSection;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AlgorithmSettings<T extends SortingAlgorithm> extends SettingsSection {

    private T algorithm;
    protected final Function<List<Integer>, T> createAlgorithm;
    protected final List<AlgorithmSettingObject> settings;

    private AlgorithmSettingsInputBox<Integer> speedSetting;

    private static final String speedInfo = "This determines how many steps the algorithm takes per millisecond";

    public AlgorithmSettings(String algorithmName, Function<List<Integer>, T> createAlgorithm, AlgorithmSettingObject... settings) {
        super();

        this.createAlgorithm = createAlgorithm;
        this.settings = new ArrayList<>();

        addSetting(new Label(algorithmName));

        createSpeedSetting();
        for (AlgorithmSettingObject setting : settings) {
            this.settings.add(setting);
            setting.add(this);
        }

        addSetting(buildResetButton());
        resetSettings();
    }

    protected void createSpeedSetting() {
        speedSetting = new AlgorithmSettingsInputBox<>(
                "Speed", speedInfo, 1, Integer::parseInt, i -> i > 0
        );

        settings.add(speedSetting);
        speedSetting.add(this);
    }

    public void resetSettings() {
        if (settings == null) return;
        for (AlgorithmSettingObject setting : settings) {
            setting.resetSetting();
        }
    }

    public int getSpeed() {
        return speedSetting.getValue();
    }

    public T createAlgorithm(List<Integer> array) {
        return algorithm = createAlgorithm.apply(array);
    }

    public T createInstantAlgorithm(List<Integer> array) {
        return algorithm = createAlgorithm.apply(array);
    }

    public T getAlgorithm() {
        return algorithm;
    }

    public void resetAlgorithm() {
        algorithm = null;
    }
}
