package com.example.javafxsortingalgorithms.betteralgorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Scanner;

public class BetterSelectionSort extends SortingAlgorithm {

    private AlgorithmSpace space;
    private SelectionSortSpace selectionSort;

    public BetterSelectionSort(List<Integer> list, boolean isInstant) {
        super(list, isInstant);
        space = new AlgorithmSpace(list, new Bounds(0, list.size()));

        selectionSort = new SelectionSortSpace(space, new Bounds(0, list.size()));

        Stage dialog = new Stage();
        dialog.initModality(Modality.NONE);
        Scene scene = new Scene(space.getVisuals(), 400, 400);
        dialog.setScene(scene);
        dialog.show();
    }

    @Override
    protected void runAlgorithm(ArrayDisplay display) {
        space.iterate();
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

    }

    @Override
    public boolean isDone() {
        return space.isDone();
    }

    @Override
    public String getName() {
        return null;
    }
}
