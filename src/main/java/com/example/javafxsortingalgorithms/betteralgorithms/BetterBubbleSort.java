package com.example.javafxsortingalgorithms.betteralgorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class BetterBubbleSort extends SortingAlgorithm {

    private AlgorithmSpace space;
    private BubbleSortSpace bubbleSort;

    public BetterBubbleSort(List<Integer> list, boolean isInstant) {
        super(list, isInstant);

        space = new AlgorithmSpace(list);

        bubbleSort = new BubbleSortSpace(space, new Bounds(0, list.size()));

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
        return bubbleSort.isDone();
    }

    @Override
    public String getName() {
        return "";
    }
}
