package com.example.javafxsortingalgorithms.arraydisplay;

import javafx.scene.control.Label;

import java.util.LinkedHashMap;
import java.util.Map;

public class AnimatedInfo extends Label {

    public static final String OUT_OF_BOUNDS_INDEX = "N/A";
    public static final String OUT_OF_BOUND_VALUE = "N/A";

    private final Map<String, String> info;

    public AnimatedInfo() {
        info = new LinkedHashMap<>();
        setWrapText(true);
        setPrefWidth(300);
    }

    public void updateInfo(String key, Object value) {
        updateInfo(key, value.toString());
    }

    public void updateInfo(String key, String value) {
        if (!info.containsKey(key)) {
            System.out.println(STR."Adding \"\{key}\" to the map");
        }
        info.put(key, value);
        updateText();
    }

    public void finish() {
        clear();
        setText("Finished!");
    }

    public void clear() {
        info.clear();
        updateText();
    }

    public void updateText() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : info.entrySet()) {
            builder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        setText(builder.toString());
    }
}
