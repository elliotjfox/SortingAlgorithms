package com.example.javafxsortingalgorithms.betteralgorithms;

import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlgorithmSpace {

    private final List<Integer> list;
    private final List<AlgorithmSpaceObject> objects;
    private final List<Iterator> iterators;
    private final Set<Bounds> bounds;

    private final VBox vBox;

    public AlgorithmSpace(List<Integer> list, Bounds... bounds) {
        this.list = list;
        objects = new ArrayList<>();
        iterators = new ArrayList<>();
        this.bounds = new HashSet<>();
        this.bounds.addAll(List.of(bounds));
        vBox = new VBox(5);
    }

    public void addAlgorithmSpaceObject(AlgorithmSpaceObject object) {
        objects.add(object);
        vBox.getChildren().add(object.getVisual());
    }

    public void addAlgorithmSpaceObject(AlgorithmSpaceObject... objects) {
        for (AlgorithmSpaceObject object : objects) {
            addAlgorithmSpaceObject(object);
        }
    }

    public void addIterator(Iterator iterator) {
        iterators.add(iterator);
    }

    public void iterate() {
        for (Iterator iterator : iterators) {
            iterator.run();
        }
    }

    public VBox getVisuals() {
        return vBox;
    }

    @Override
    public String toString() {
        return "AlgorithmSpace{" +
                "list size=" + list.size() +
                ", objects=" + objects +
                ", bounds=" + bounds +
                '}';
    }
}
