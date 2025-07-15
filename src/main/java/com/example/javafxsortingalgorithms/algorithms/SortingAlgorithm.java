package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.animation.AnimatedArrayDisplay;
import com.example.javafxsortingalgorithms.TestDisplay;
import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.animation.Pointer;
import com.example.javafxsortingalgorithms.arraydisplay.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

// TODO: Finish replacing ArrayLists with Lists
// TODO: There might still be a few bugs around when an algorithm is finished or not
public abstract class SortingAlgorithm {
    protected List<Integer> list;
    protected boolean isDone;
    protected final boolean isInstant;
    protected DisplayMode mode;

    protected List<DisplayFrame> frames;
    protected List<ListChange> currentChanges;

    public enum ColourAction {
        READ, WRITE
    }

    public SortingAlgorithm(List<Integer> list, boolean isInstant) {
        this.list = list;
        this.isInstant = isInstant;
        isDone = false;

        this.frames = new ArrayList<>();
        currentChanges = new ArrayList<>();
    }

    protected void runAlgorithm(ArrayDisplay display) {}

    public void startAlgorithm(DisplayMode mode) {
        this.mode = mode;
        System.out.println("Running on " + mode);
        runAlgorithm();
    }

    protected void runAlgorithm() {
        System.out.println("TODO: Implement this algorithm!");
    }

    protected void addFrame() {
        frames.add(new DisplayFrame(currentChanges));
        currentChanges.clear();
    }

    protected Pointer createPointer() {
        return new Pointer(this, mode);
    }

    public List<DisplayFrame> getFrames() {
        return frames;
    }

    protected abstract void instantAlgorithm(TestEntry entry);

    public void startAnimated(AnimatedArrayDisplay display) {
        System.out.println("TODO: Implement detailed start for this algorithm!");
    }

    public void iterateAnimated(AnimatedArrayDisplay display) {
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

    public boolean isDone() {
        return isDone;
    }

    protected void swap(int firstIndex, int secondIndex) {
        if (firstIndex < 0 || firstIndex >= list.size() || secondIndex < 0 || secondIndex >= list.size()) return;
        list.set(firstIndex, list.set(secondIndex, list.get(firstIndex)));
        currentChanges.add(new SwapChange(firstIndex, secondIndex));
    }

    protected void move(int index, int targetIndex) {
        if (index < 0 || index >= list.size() || targetIndex < 0 || targetIndex >= list.size() || index == targetIndex) return;
        list.add(targetIndex, list.remove(index));
        currentChanges.add(new MoveChange(index, targetIndex));
    }

    // [left, mid) and [mid, right)
    protected void inPlaceMerge(int left, int mid, int right) {
        // Clamp edges
        if (left < 0) left = 0;
        if (right > list.size()) right = list.size();

        // Make sure left <= mid <= right
        if (!increasing(left, mid, right)) return;


        int l = left;
        int r = mid;

        while (r < right && l < r) {
            if (list.get(l) > list.get(r)) {
                move(r, l);
                r++;
            }
            l++;
        }
    }

    private boolean increasing(int i1, int i2, int i3) {
        return i1 <= i2 && i2 <= i3;
    }

    public List<Integer> getList() {
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

    // An alternate to isListSorted, if it's better to check from the other end (ie something like selection sort leaves the
    // end unsorted until the end, so it's more efficient to check from that direction)
    public static boolean isListSortedReverse(List<Integer> list) {
        for (int i = list.size() - 1; i > 0; i--) {
            if (list.get(i) < list.get(i - 1)) {
                return false;
            }
        }
        return true;
    }
}
