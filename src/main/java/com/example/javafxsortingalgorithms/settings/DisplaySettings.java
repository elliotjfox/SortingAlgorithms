package com.example.javafxsortingalgorithms.settings;

import javafx.scene.control.Label;

public class DisplaySettings extends SettingsSection {

    private int numberElements;
    private int borderWidth;
    private int elementWidth;
    private int maxHeight;
    private int minHeight;

    private final IntegerInputBox numberElementsBox;
    private final IntegerInputBox arrayBorderBox;
    private final IntegerInputBox elementWidthBox;
    private final IntegerInputBox maxHeightBox;
    private final IntegerInputBox minHeightBox;

    private static final String numberElementsInfo = "The number of elements (numbers) that the algorithm has to sort.";
    private static final String borderWidthInfo = "The width of the border around the elements.";
    private static final String elementWidthInfo = "The width of each element.";
    private static final String maxHeightInfo = "The maximum height the highest element can be.";
    private static final String minHeightInfo = "The minimum height the highest element can be.";

    public DisplaySettings() {
        super();

        numberElementsBox = new IntegerInputBox(() -> numberElements, (i) -> numberElements = i);
        arrayBorderBox = new IntegerInputBox(() -> borderWidth, (i) -> borderWidth = i);
        elementWidthBox = new IntegerInputBox(() -> elementWidth, (i) -> elementWidth = i);
        maxHeightBox = new IntegerInputBox(() -> maxHeight, (i) -> maxHeight = Math.max(i, minHeight));
        minHeightBox = new IntegerInputBox(() -> minHeight, (i) -> minHeight = Math.min(i, maxHeight));

        addSetting(new Label("Number of Elements"), numberElementsBox, numberElementsInfo);
        addSetting(new Label("Array Border"), arrayBorderBox, borderWidthInfo);
        addSetting(new Label("Element Width"), elementWidthBox, elementWidthInfo);
        addSetting(new Label("Max height"), maxHeightBox, maxHeightInfo);
        addSetting(new Label("Min height"), minHeightBox, minHeightInfo);

        addSetting(buildResetButton());
    }

    @Override
    public void resetSettings() {
        numberElements = Settings.defaultNumberElements;
        borderWidth = Settings.defaultArrayBorder;
        elementWidth = Settings.defaultElementWidth;
        maxHeight = Settings.defaultMaxHeight;
        minHeight = Settings.defaultMinHeight;

        if (numberElementsBox != null) numberElementsBox.updateValue();
        if (arrayBorderBox != null) arrayBorderBox.updateValue();
        if (elementWidthBox != null) elementWidthBox.updateValue();
        if (maxHeightBox != null) maxHeightBox.updateValue();
        if (minHeightBox != null) minHeightBox.updateValue();
    }

    public int getNumberElements() {
        return numberElements;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public int getElementWidth() {
        return elementWidth;
    }

    public int getDisplayMaxHeight() {
        return maxHeight;
    }

    public int getDisplayMinHeight() {
        return minHeight;
    }

//    public ArrayDisplay createArrayDisplay(AlgorithmDisplay algorithmDisplay) {
//        return displayType.createArrayDisplay(algorithmDisplay);
//    }
}
