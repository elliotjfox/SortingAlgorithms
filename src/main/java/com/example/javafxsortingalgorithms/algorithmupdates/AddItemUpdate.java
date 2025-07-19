package com.example.javafxsortingalgorithms.algorithmupdates;

import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplayBase;
import javafx.scene.Node;

public class AddItemUpdate implements DisplayUpdate {

    private final Node item;

    public AddItemUpdate(Node item) {
        this.item = item;
    }

    @Override
    public void performChange(ArrayDisplayBase display) {
        display.getChildren().add(item);
    }
}
