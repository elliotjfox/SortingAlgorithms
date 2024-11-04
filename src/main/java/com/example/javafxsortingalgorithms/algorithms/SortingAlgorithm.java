package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.arraydisplay.ArrayDetailedDisplay;
import com.example.javafxsortingalgorithms.TestDisplay;
import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

import java.util.List;

// TODO: Finish replacing ArrayLists with Lists
// TODO: There might still be a few bugs around when an algorithm is finished or not
public abstract class SortingAlgorithm {
    protected List<Integer> list;
    protected boolean isDone;
    protected final boolean isInstant;

    public enum ColourAction {
        READ, WRITE
    }

    public SortingAlgorithm(List<Integer> list, boolean isInstant) {
        this.list = list;
        this.isInstant = isInstant;
        isDone = false;
    }

    protected abstract void runAlgorithm(ArrayDisplay display);

    protected abstract void instantAlgorithm(TestEntry entry);

    public void startDetailed(ArrayDetailedDisplay display) {
        System.out.println("TODO: Implement detailed start for this algorithm!");
    }

    public void iterateDetailed(ArrayDetailedDisplay display) {
        System.out.println("TODO: Implement detailed for this algorithm!");
    }

    public void solveInstant(TestDisplay display) {
        solveInstant(display, () -> {});
    }

    public void solveInstant(TestDisplay display, Runnable whenDone) {
        display.startTest(getName(), list.size());

        Timeline updateTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> display.getCurrentTest().updateTestInfo()));
        updateTimeline.setCycleCount(Animation.INDEFINITE);

        Thread thread = new Thread(() -> {
            instantAlgorithm(display.getCurrentTest());
            display.getCurrentTest().done();
            if (!isListSorted(list)) {
                System.out.println("The algorithm has failed to sort list of size " + list.size());
            }
            Platform.runLater(() -> display.getCurrentTest().updateTestInfo());
            updateTimeline.stop();
            whenDone.run();
        });

        thread.start();
        updateTimeline.play();
    }

    // TODO: Probably just delete this
    public void iterate(ArrayDisplay display) {
        runAlgorithm(display);
    }

    public boolean isDone() {
        return isDone;
    }

    protected void swap(int firstIndex, int secondIndex) {
        if (firstIndex < 0 || firstIndex >= list.size() || secondIndex < 0 || secondIndex >= list.size()) return;
        list.set(firstIndex, list.set(secondIndex, list.get(firstIndex)));
    }

    protected void move(int index, int targetIndex) {
        if (index < 0 || index >= list.size() || targetIndex < 0 || targetIndex >= list.size() || index == targetIndex) return;
        list.add(targetIndex, list.remove(index));
    }

    public List<Integer> getArray() {
        return list;
    }

    public abstract String getName();

    public static boolean isListSorted(List<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    // An alternate to isListSorted, if it's better to check from the other end (ie something like selection sort leaved the
    // end unsorted until the end, so it's more efficient to check there, for some algorithms
    public static boolean isListSortedReverse(List<Integer> list) {
        for (int i = list.size() - 1; i > 0; i--) {
            if (list.get(i) < list.get(i - 1)) {
                return false;
            }
        }
        return true;
    }
}
