package com.example.javafxsortingalgorithms.betteralgorithms;

import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlgorithmSpace {

    private final List<Integer> list;
    private final List<AlgorithmSpaceObject> objects;
    private final List<Ticker> tickers;
    private final Set<Bounds> bounds;

    private final VBox vBox;

    private boolean isDone;

    public AlgorithmSpace(List<Integer> list, Bounds... bounds) {
        this.list = list;
        objects = new ArrayList<>();
        tickers = new ArrayList<>();
        this.bounds = new HashSet<>();
        this.bounds.addAll(List.of(bounds));
        vBox = new VBox(5);
        isDone = false;
    }

    public void addAlgorithmSpaceObject(AlgorithmSpaceObject object) {
        objects.add(object);
        vBox.getChildren().add(object.getVisual());
    }

    public void removeAlgorithmSpaceObject(AlgorithmSpaceObject object) {
        objects.remove(object);
        vBox.getChildren().remove(object.getVisual());
    }

    public void addAlgorithmSpaceObject(AlgorithmSpaceObject... objects) {
        for (AlgorithmSpaceObject object : objects) {
            addAlgorithmSpaceObject(object);
        }
    }

    public void removeAlgorithmSpaceObject(AlgorithmSpaceObject... objects) {
        for (AlgorithmSpaceObject object : objects) {
            removeAlgorithmSpaceObject(object);
        }
    }

    protected void swap(int firstIndex, int secondIndex) {
        if (firstIndex < 0 || firstIndex >= list.size() || secondIndex < 0 || secondIndex >= list.size() || firstIndex == secondIndex) return;
        list.set(firstIndex, list.set(secondIndex, list.get(firstIndex)));
    }

    protected void move(int index, int targetIndex) {
        if (index < 0 || index >= list.size() || targetIndex < 0 || targetIndex >= list.size() || index == targetIndex) return;
        list.add(targetIndex, list.remove(index));
    }


    public void addTicker(Ticker ticker) {
        tickers.add(ticker);
    }

    public void addTicker(int index, Ticker ticker) {
        if (tickers.size() < index) {
            addTicker(ticker);
        } else {
            tickers.add(index, ticker);
        }
    }

    public void removeTicker(Ticker ticker) {
        tickers.remove(ticker);
    }

    public void iterate() {
        if (tickers.isEmpty()) return;
        tickers.getFirst().run();
//        for (Ticker ticker : tickers) {
//            ticker.run();
//        }
    }

    public void finish() {
        isDone = true;
    }

    public List<Ticker> getTickers() {
        return tickers;
    }

    protected Result compare(int firstIndex, int secondIndex) {
        if (firstIndex < 0 || firstIndex >= list.size() || secondIndex < 0 || secondIndex >= list.size()) {
            return new Result.OutsideArray();
        } else if (list.get(firstIndex) < list.get(secondIndex)) {
            return new Result.LessThan();
        } else if (list.get(firstIndex) > list.get(secondIndex)) {
            return new Result.GreaterThan();
        } else {
            return new Result.Equal();
        }
    }

    public List<Integer> getList() {
        return list;
    }

    public int get(int i) {
        if (i < 0 || i >= list.size()) {
            System.out.println("Tried to access outside element! (" + i + ")");
            return -1;
        }
        return list.get(i);
    }

    public VBox getVisuals() {
        return vBox;
    }

    public boolean isDone() {
        return isDone;
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
