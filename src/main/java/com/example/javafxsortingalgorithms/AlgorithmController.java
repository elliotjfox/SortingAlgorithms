package com.example.javafxsortingalgorithms;

import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import com.example.javafxsortingalgorithms.algorithmupdates.AlgorithmUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.DisplayUpdate;
import com.example.javafxsortingalgorithms.arraydisplay.*;
import com.example.javafxsortingalgorithms.algorithmupdates.ListUpdate;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmController implements ControlResponder {

    public static final double ANIMATION_LENGTH = 400;
    public static final double ANIMATION_COOLDOWN = 100;
    
    private final Display display;
    private ArrayDisplayBase arrayDisplay;
    private SortingAlgorithm algorithm;
    private List<DisplayFrame> frames;
    private int currentFrame;
    private boolean hasRunAlgorithm;

    private final Timeline normalTimeline;
    private final Timeline animatedTimeline;
    private final Timeline trialTimeline;

    private boolean createdAlgorithm;

    private Timeline currentTimeline;

    private List<Integer> list;

    private List<ListUpdate> currentListUpdates;

    public AlgorithmController(Display display) {
        this.display = display;

        normalTimeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1),
                        _ -> createNextFrame()
                )
        );
        normalTimeline.setCycleCount(Animation.INDEFINITE);

        animatedTimeline = new Timeline(
                new KeyFrame(
                        Duration.millis(ANIMATION_LENGTH + ANIMATION_COOLDOWN),
                        _ -> createNextFrame()
                )
        );
        animatedTimeline.setCycleCount(Animation.INDEFINITE);

        // TODO: Do I need this?
        trialTimeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1),
                        _ -> {}
                )
        );

        currentTimeline = normalTimeline;
    }

    public void linkToDisplay(ArrayDisplayBase arrayDisplay) {
        this.arrayDisplay = arrayDisplay;
    }

    private void runAlgorithm() {
        if (algorithm == null) {
            System.out.println("ERROR: Algorithm is null");
            return;
        }

        if (arrayDisplay == null) {
            System.out.println("ERROR: Display is null");
            return;
        }

        frames = new ArrayList<>();
        hasRunAlgorithm = true;
        currentFrame = -1;
        algorithm.startAlgorithm(display.getMode(), this::acceptFrames);
    }

    private void acceptFrames(List<DisplayFrame> newFrames) {
        frames.addAll(newFrames);
    }

    public void setMode(DisplayMode mode) {
        System.out.println("Switching to " + mode);
        switch (mode) {
            case NORMAL -> currentTimeline = normalTimeline;
            case ANIMATED -> currentTimeline = animatedTimeline;
            case TRIAL -> currentTimeline = trialTimeline;
            default -> currentTimeline = normalTimeline;
        }
    }

    @Override
    public void play() {
        if (display.getMode() == DisplayMode.TRIAL) {
            createList();
            createAlgorithm();
            algorithm.linkTrialRow(display.getTrialDisplay().startTrial(algorithm.getName(), list.size()));
            runAlgorithm();
            currentTimeline.play();
            return;
        }

        if (hasRunAlgorithm) {
            resume();
            return;
        }

        if (!createdAlgorithm) {
            createAlgorithm();
        }

        if (!hasRunAlgorithm) {
            runAlgorithm();
        }

        currentTimeline.play();
    }

    public void resume() {
        currentTimeline.play();
    }

    @Override
    public void stop() {
        currentTimeline.stop();
        algorithm.stop();
    }

    @Override
    public void step() {
        if (!createdAlgorithm) {
            createAlgorithm();
        }
        if (!hasRunAlgorithm) {
            runAlgorithm();
        }
        currentTimeline.stop();
        createNextFrame();
    }

    @Override
    public void reset() {
        display.setDisplayType(display.getSettings().getDisplaySettings().getDisplayType());
        hasRunAlgorithm = false;
        createdAlgorithm = false;
        currentTimeline.stop();
        createList();
    }

    private void createAlgorithm() {
        List<Integer> listCopy = new ArrayList<>(list);
        algorithm = display.getSettings().getAlgorithmSettings().createAlgorithm(listCopy);
        createdAlgorithm = true;
    }

    private void createList() {
        int listSize = display.getSettings().getDisplaySettings().getNumberElements();
        list = display.getButtonPanel().getListSettings().generateList(listSize);
        int maxValue = list.getFirst();
        for (Integer i : list) maxValue = Math.max(maxValue, i);

        arrayDisplay.initializeSettings(
                new DisplaySettings(
                        maxValue,
                        listSize,
                        (double) display.getSettings().getDisplaySettings().getDisplayHeight() / maxValue,
                        display.getSettings().getDisplaySettings().getElementWidth()
                )
        );
        arrayDisplay.initializeElements(list);
    }

    private void createNextFrame() {
        if (currentFrame + 1 < frames.size()) {
            currentFrame++;

            currentListUpdates = new ArrayList<>();

            // Apply changes
            for (int i = 0; i < frames.get(currentFrame).list().size(); i++) {
                AlgorithmUpdate currentUpdate = frames.get(currentFrame).list().get(i);

                if (currentUpdate instanceof ListUpdate listUpdate) {
                    listUpdate.performChange(list);
                    currentListUpdates.add(listUpdate);
                }

                if (currentUpdate instanceof DisplayUpdate displayUpdate) {
                    displayUpdate.performChange(arrayDisplay);
                }
            }

            showFrame();
        }
    }

    private void showFrame() {
        if (arrayDisplay == null) {
            System.out.println("Display is null");
            return;
        }

        if (display.getMode() != DisplayMode.ANIMATED) {
            arrayDisplay.displayList(list);
        } else {
            arrayDisplay.animateItems(list, currentListUpdates);
            arrayDisplay.playAnimations();
        }
    }

    public List<Integer> getList() {
        return list;
    }
}
