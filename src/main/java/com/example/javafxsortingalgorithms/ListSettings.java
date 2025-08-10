package com.example.javafxsortingalgorithms;

import com.example.javafxsortingalgorithms.settings.ListGenerationType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.util.List;

public class ListSettings extends MenuBar {

    private ListGenerationType generationType;

    private final Menu base;

    public ListSettings() {
        base = createMainMenu();
        getMenus().add(base);
        setGenerationType(ListGenerationType.DEFAULT);
    }

    public List<Integer> generateList(int size) {
        return generationType.getGenerator().generate(size);
    }

    private Menu createMainMenu() {
        return new Menu(
                "", null,
                createMenuItem(ListGenerationType.DEFAULT),
                createMenuItem(ListGenerationType.REVERSED),
                createMenuItem(ListGenerationType.RANDOM),
                createFormulaMenu()
        );
    }

    private Menu createFormulaMenu() {
        return new Menu(
                "Formula", null,
                createMenuItem(ListGenerationType.EXPONENTIAL),
                createMenuItem(ListGenerationType.SQUARE_ROOT),
                createMenuItem(ListGenerationType.LOGARITHMIC)
        );
    }

    private void setGenerationType(ListGenerationType generationType) {
        this.generationType = generationType;
        base.setText(generationType.getName());
    }

    private MenuItem createMenuItem(ListGenerationType generationType) {
        MenuItem item = new MenuItem();
        item.setText(generationType.getName());
        item.setOnAction(_ -> setGenerationType(generationType));
        return item;
    }
}
