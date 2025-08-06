package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.algorithmupdates.*;
import com.example.javafxsortingalgorithms.TestDisplay;
import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.animation.position.*;
import com.example.javafxsortingalgorithms.arraydisplay.*;
import com.example.javafxsortingalgorithms.animation.AnimatedArrow;
import com.example.javafxsortingalgorithms.animation.AnimatedItem;
import com.example.javafxsortingalgorithms.animation.AnimatedSection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class SortingAlgorithm {

    private static final int MIN_FRAME_LIST_SIZE = 50_000;
    private static Thread runningThread;
    private static final boolean usingThread = true;

    protected List<Integer> list;
    protected boolean isDone;
    protected DisplayMode mode;

    protected List<DisplayFrame> frames;
    protected List<AlgorithmUpdate> currentChanges;
    protected List<AlgorithmUpdate> nextChanges;
    protected SortingAlgorithmAnimation animation;

    private Consumer<List<DisplayFrame>> frameAcceptor;

    public SortingAlgorithm(List<Integer> list) {
        this.list = list;
        isDone = false;

        this.frames = new ArrayList<>();
        currentChanges = new ArrayList<>();
        nextChanges = new ArrayList<>();
        animation = new SortingAlgorithmAnimation(this);
    }

    public void startAlgorithm(DisplayMode mode, Consumer<List<DisplayFrame>> frameAcceptor) {
        this.mode = mode;
        this.frameAcceptor = frameAcceptor;

        if (usingThread) {
            stop();

            runningThread = new Thread(() -> {
                runAlgorithm();
                addFrame();
                currentChanges.add(new FinishUpdate());
                addFrame();
                sendFrames();
                runningThread = null;
            });
            runningThread.start();
        } else {
            runAlgorithm();
            addFrame();
            currentChanges.add(new FinishUpdate());
            addFrame();
            sendFrames();
        }
    }

    public void stop() {
        if (runningThread != null) {
            runningThread.interrupt();
        }
    }

    protected abstract void runAlgorithm();

    protected void addFrame() {
        frames.add(new DisplayFrame(currentChanges));
        currentChanges.clear();
        currentChanges.addAll(nextChanges);
        nextChanges.clear();

        if (usingThread && frames.size() >= MIN_FRAME_LIST_SIZE) {
            sendFrames();
        }
    }

    private void sendFrames() {
        System.out.println("Sending " + frames.size());
        List<DisplayFrame> framesPackage = frames;
        frames = new ArrayList<>();
        frameAcceptor.accept(framesPackage);
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

        protected AnimatedArrow createArrow() {
            if (algorithm.mode == DisplayMode.ANIMATED) {
                AnimatedArrow tmp = new AnimatedArrow();
                algorithm.currentChanges.add(new CreateItemUpdate(tmp));
                return tmp;
            }
            return null;
        }

        protected AnimatedSection createSection(XPosition width) {
            if (algorithm.mode == DisplayMode.ANIMATED) {
                AnimatedSection section = new AnimatedSection(width);
                algorithm.currentChanges.add(new CreateItemUpdate(section));
                return section;
            }
            return null;
        }

        protected void removeItem(AnimatedItem item) {
            if (item == null) return;

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

        protected void setItemX(AnimatedItem item, XPosition xPosition) {
            if (item == null) return;

            algorithm.currentChanges.add(item.setXPosition(xPosition));
        }

        protected void setItemY(AnimatedItem item, YPosition yPosition) {
            if (item == null) return;

            algorithm.currentChanges.add(item.setYPosition(yPosition));
        }

        protected void setItemPosition(AnimatedItem item, AnimationPosition position) {
            if (item == null) return;

            algorithm.currentChanges.add(item.setPosition(position));
        }

        protected void changeItemX(AnimatedItem item, XPosition xPosition) {
            if (item == null) return;

            algorithm.currentChanges.add(item.changeXPosition(xPosition));
        }

        protected void changeItemY(AnimatedItem item, YPosition yPosition) {
            if (item == null) return;

            algorithm.currentChanges.add(item.changeYPosition(yPosition));
        }

        protected void changeItemPosition(AnimatedItem item, AnimationPosition position) {
            if (item == null) return;

            algorithm.currentChanges.add(item.changePosition(position));
        }

        protected void changeItemFill(AnimatedItem item, Paint fill) {
            if (item == null) return;

            algorithm.currentChanges.add(item.changeFill(fill));
        }

        protected void setSectionWidth(AnimatedSection section, XPosition width) {
            if (section == null) return;

            algorithm.currentChanges.add(section.setWidth(width));
        }

        protected void changeSectionWidth(AnimatedSection section, XPosition width) {
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
