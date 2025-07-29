package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.algorithmupdates.*;
import com.example.javafxsortingalgorithms.TestDisplay;
import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.*;
import com.example.javafxsortingalgorithms.newanimation.NewAnimatedArrow;
import com.example.javafxsortingalgorithms.newanimation.NewAnimatedItem;
import com.example.javafxsortingalgorithms.newanimation.NewAnimatedSection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class SortingAlgorithm {
    protected List<Integer> list;
    protected boolean isDone;
    protected DisplayMode mode;

    protected List<DisplayFrame> frames;
    protected List<AlgorithmUpdate> currentChanges;
    protected List<AlgorithmUpdate> nextChanges;
    protected SortingAlgorithmAnimation animation;

    public enum ColourAction {
        READ, WRITE
    }

    public SortingAlgorithm(List<Integer> list) {
        this.list = list;
        isDone = false;

        this.frames = new ArrayList<>();
        currentChanges = new ArrayList<>();
        nextChanges = new ArrayList<>();
        animation = new SortingAlgorithmAnimation(this);
    }

    public void startAlgorithm(DisplayMode mode) {
        this.mode = mode;
        runAlgorithm();
        addFrame();
        currentChanges.add(new FinishUpdate());
        addFrame();
    }

    protected abstract void runAlgorithm();

    public List<DisplayFrame> getFrames() {
        return frames;
    }

    protected void instantAlgorithm(TestEntry entry) {}

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
        currentChanges.add(new SwapUpdate(firstIndex, secondIndex));
    }

    protected void move(int index, int targetIndex) {
        if (index < 0 || index >= list.size() || targetIndex < 0 || targetIndex >= list.size() || index == targetIndex) return;
        list.add(targetIndex, list.remove(index));
        currentChanges.add(new MoveUpdate(index, targetIndex));
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

    protected void addFrame() {
        frames.add(new DisplayFrame(currentChanges));
        currentChanges.clear();
        currentChanges.addAll(nextChanges);
        nextChanges.clear();
    }

    protected static class SortingAlgorithmAnimation {
        private final SortingAlgorithm algorithm;

        public SortingAlgorithmAnimation(SortingAlgorithm algorithm) {
            this.algorithm = algorithm;
        }

        public void addFrame() {
            if (algorithm.mode == DisplayMode.ANIMATED) {
                algorithm.addFrame();
            }
        }

        protected NewAnimatedArrow createArrow() {
            if (algorithm.mode == DisplayMode.ANIMATED) {
                NewAnimatedArrow tmp = new NewAnimatedArrow();
                algorithm.currentChanges.add(new CreateItemUpdate(tmp));
                return tmp;
            }
            return null;
        }

        protected NewAnimatedSection createSection(double width) {
            if (algorithm.mode == DisplayMode.ANIMATED) {
                NewAnimatedSection section = new NewAnimatedSection(width);
                algorithm.currentChanges.add(new CreateItemUpdate(section));
                return section;
            }
            return null;
        }

        protected NewAnimatedSection createSection(int width) {
            if (algorithm.mode == DisplayMode.ANIMATED) {
                NewAnimatedSection section = new NewAnimatedSection(width);
                algorithm.currentChanges.add(new CreateItemUpdate(section));
                return section;
            }
            return null;
        }

        protected void removeItem(NewAnimatedItem item) {
            if (algorithm.mode == DisplayMode.ANIMATED) {
                algorithm.currentChanges.add(new RemoveItemUpdate(item));
            }
        }

        protected void readIndex(int index) {
            if (index < 0 || index >= algorithm.list.size()) return;
            if (algorithm.mode == DisplayMode.ANIMATED) {
                algorithm.currentChanges.add(new ReadUpdate(index, algorithm.list.get(index)));
            }
        }

        protected void setItemIndex(NewAnimatedItem item, int index) {
            if (item == null) return;

            algorithm.currentChanges.add(item.setIndex(index));
        }

        protected void setItemPosition(NewAnimatedItem item, double position) {
            if (item == null) return;

            algorithm.currentChanges.add(item.setPosition(position));
        }

        protected void moveItem(NewAnimatedItem item, int index) {
            if (item == null) return;

            algorithm.currentChanges.add(item.moveToIndex(index));
        }

        protected void moveItem(NewAnimatedItem item, double position) {
            if (item == null) return;

            algorithm.currentChanges.add(item.moveToPosition(position));
        }

        protected void setItemHeight(NewAnimatedItem item, double height) {
            if (item == null) return;

            algorithm.currentChanges.add(item.setHeight(height));
        }

        protected void moveItemHeight(NewAnimatedItem item, double height) {
            if (item == null) return;

            algorithm.currentChanges.add(item.moveToHeight(height));
        }

        protected void changeItemFill(NewAnimatedItem item, Paint fill) {
            if (item == null) return;

            algorithm.currentChanges.add(item.changeFill(fill));
        }

        protected void setSectionWidth(NewAnimatedSection section, double width) {
            if (section == null) return;

            algorithm.currentChanges.add(section.setWidth(width));
        }

        protected void changeSectionWidth(NewAnimatedSection section, double width) {
            if (section == null) return;

            algorithm.currentChanges.add(section.changeWidth(width));
        }

        protected void changeSectionWidth(NewAnimatedSection section, int width) {
            if (section == null) return;

            algorithm.currentChanges.add(section.changeWidth(width));
        }

        protected void animateItem(Supplier<AlgorithmUpdate> createUpdate) {
            try {
                algorithm.currentChanges.add(createUpdate.get());
            } catch (NullPointerException ignored) {}
        }
    }
}
