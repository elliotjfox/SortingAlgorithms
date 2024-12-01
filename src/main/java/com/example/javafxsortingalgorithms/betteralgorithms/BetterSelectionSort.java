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
    private Pointer outerPointer;
    private Pointer innerPointer;
    private Pointer minIndex;
    private Iterator iterator;

    public BetterSelectionSort(List<Integer> list, boolean isInstant) {
        super(list, isInstant);
        space = new AlgorithmSpace(list, new Bounds(0, list.size()));
        // Don't need to go all the way there
        outerPointer = new Pointer("Count", 0, new Bounds(0, list.size() - 1));
        innerPointer = new Pointer("Inner", 0, new Bounds(0, list.size()));
        minIndex = new Pointer("Min value", 0, new Bounds(0, list.size()));
        iterator = new Iterator(() -> {
            if (list.get(innerPointer.getValue()) < list.get(minIndex.getValue())) {
                minIndex.setValue(innerPointer.getValue());
            }
            innerPointer.increment();
        });

        outerPointer.onEnd(() -> {
            isDone = true;
        });

        innerPointer.onEnd(() -> {
            swap(outerPointer.getValue(), minIndex.getValue());
            outerPointer.increment();
            minIndex.setValue(outerPointer.getValue());
            innerPointer.setValue(outerPointer.getValue() + 1);
        });

        space.addAlgorithmSpaceObject(outerPointer, innerPointer, minIndex);
        space.addIterator(iterator);

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
    public String getName() {
        return null;
    }
}
