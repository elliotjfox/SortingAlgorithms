package com.example.javafxsortingalgorithms.settings;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.IntegerPropertyBase;
import javafx.scene.control.Label;

public class DisplaySettings extends SettingsSection {

    private final IntegerPropertyBase numberElements;
    private final IntegerPropertyBase borderWidth;
    private final IntegerPropertyBase elementWidth;
    private final IntegerPropertyBase height;

    private final IntegerInputBox numberElementsBox;
    private final IntegerInputBox arrayBorderBox;
    private final IntegerInputBox elementWidthBox;
    private final IntegerInputBox heightBox;

    private static final String numberElementsInfo = "The number of elements (numbers) that the algorithm has to sort.";
    private static final String borderWidthInfo = "The width of the border around the elements.";
    private static final String elementWidthInfo = "The width of each element.";
    private static final String heightInfo = "The height of the array display.";

    public DisplaySettings() {
        super();

        numberElements = createProperty("Number Elements");
        borderWidth = createProperty("Border Width");
        elementWidth = createProperty("Element Width");
        height = createProperty("Height");

        numberElementsBox = new IntegerInputBox(numberElements::getValue, numberElements::setValue);
        arrayBorderBox = new IntegerInputBox(borderWidth::getValue, borderWidth::setValue);
        elementWidthBox = new IntegerInputBox(elementWidth::getValue, elementWidth::setValue);
        heightBox = new IntegerInputBox(height::getValue, height::setValue);

        addSetting(new Label("Number of Elements"), numberElementsBox, numberElementsInfo);
        addSetting(new Label("Array Border"), arrayBorderBox, borderWidthInfo);
        addSetting(new Label("Element Width"), elementWidthBox, elementWidthInfo);
        addSetting(new Label("Height"), heightBox, heightInfo);

        addSetting(buildResetButton());

        resetSettings();
    }

    @Override
    public void resetSettings() {
        if (numberElements != null) numberElements.setValue(Settings.defaultNumberElements);
        if (borderWidth != null) borderWidth.setValue(Settings.defaultArrayBorder);
        if (elementWidth != null) elementWidth.setValue(Settings.defaultElementWidth);
        if (height != null) height.setValue(Settings.defaultHeight);

        if (numberElementsBox != null) numberElementsBox.updateValue();
        if (arrayBorderBox != null) arrayBorderBox.updateValue();
        if (elementWidthBox != null) elementWidthBox.updateValue();
        if (heightBox != null) heightBox.updateValue();
    }

    public int getNumberElements() {
        return numberElements.getValue();
    }

    public int getBorderWidth() {
        return borderWidth.getValue();
    }

    public int getElementWidth() {
        return elementWidth.getValue();
    }

    public int getDisplayHeight() {
        return height.getValue();
    }

    public IntegerProperty numberElements() {
        return numberElements;
    }

    public IntegerProperty borderWidth() {
        return borderWidth;
    }

    public IntegerProperty elementWidth() {
        return elementWidth;
    }

    public IntegerProperty height() {
        return height;
    }

    private IntegerPropertyBase createProperty(String name) {
        return new IntegerPropertyBase() {
            @Override
            public Object getBean() {
                return this;
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }
}
