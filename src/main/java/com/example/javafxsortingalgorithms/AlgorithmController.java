package com.example.javafxsortingalgorithms;

import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import com.example.javafxsortingalgorithms.algorithmupdates.AlgorithmUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.DisplayUpdate;
import com.example.javafxsortingalgorithms.arraydisplay.*;
import com.example.javafxsortingalgorithms.algorithmupdates.ListUpdate;
import com.example.javafxsortingalgorithms.settings.Settings;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmController {

    public static final int ANIMATION_LENGTH = 400;
    public static final int ANIMATION_COOLDOWN = 75;


    private MainDisplay display;
    private ArrayDisplayBase arrayDisplay;
    private SortingAlgorithm algorithm;
    private List<DisplayFrame> frames;
    private int currentFrame;
    private boolean hasRunAlgorithm;

    private final Timeline normalTimeline;
    private final Timeline animatedTimeline;

    private Timeline currentTimeline;

    private List<Integer> list;

    private List<ListUpdate> currentListUpdates;

    public AlgorithmController(MainDisplay display) {
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

        currentTimeline = normalTimeline;
    }

    public void linkToDisplay(ArrayDisplayBase arrayDisplay) {
        this.arrayDisplay = arrayDisplay;
    }

    public void setAlgorithm(SortingAlgorithm algorithm) {
        this.algorithm = algorithm;
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

        algorithm.startAlgorithm(display.getMode());
        hasRunAlgorithm = true;
        frames = algorithm.getFrames();
        System.out.println("# frames: " + frames.size());
        currentFrame = -1;
    }

    public void setMode(DisplayMode mode) {
        switch (mode) {
            case NORMAL -> currentTimeline = normalTimeline;
            case ANIMATED -> currentTimeline = animatedTimeline;
            default -> currentTimeline = normalTimeline;
        }
    }

    public void play() {
        runAlgorithm();
        currentTimeline.play();
    }

    public void resume() {
        currentTimeline.play();
    }

    public void stop() {
        currentTimeline.stop();
    }

    public void step() {
        currentTimeline.stop();
        if (!hasRunAlgorithm) {
            runAlgorithm();
        }
        createNextFrame();
    }

    public void reset() {
        currentTimeline.stop();
        hasRunAlgorithm = false;
        int listSize = display.getSettings().getDisplaySettings().getNumberElements();
        list = Settings.getRandomUniformList(listSize);

        int maxValue = list.getFirst();
        for (Integer i : list) {
            maxValue = Math.max(maxValue, i);
        }

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
