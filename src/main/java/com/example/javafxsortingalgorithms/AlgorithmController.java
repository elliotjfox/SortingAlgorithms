package com.example.javafxsortingalgorithms;

import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplayBase;
import com.example.javafxsortingalgorithms.arraydisplay.DisplayFrame;
import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;
import com.example.javafxsortingalgorithms.settings.Settings;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.List;

public class AlgorithmController {

    private MainDisplay display;
    private ArrayDisplayBase arrayDisplay;
    private SortingAlgorithm algorithm;
    private List<DisplayFrame> frames;
    private int currentFrame;
    private boolean hasRunAlgorithm;

    private final Timeline normalTimeline;

    private List<Integer> list;

    public AlgorithmController(MainDisplay display) {
        this.display = display;
        normalTimeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1),
                        _ -> showNextFrame()
                )
        );
        normalTimeline.setCycleCount(Animation.INDEFINITE);
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

        algorithm.runAlgorithm(display.getMode());
        hasRunAlgorithm = true;
        frames = algorithm.getFrames();
        System.out.println("# frames: " + frames.size());
        currentFrame = 0;
    }

    public void play() {
        runAlgorithm();
        normalTimeline.play();
    }

    public void resume() {
        normalTimeline.play();
    }

    public void stop() {
        normalTimeline.stop();
    }

    public void step() {
        normalTimeline.stop();
        if (!hasRunAlgorithm) {
            runAlgorithm();
        }
        showNextFrame();
    }

    public void reset() {
        normalTimeline.stop();
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
        arrayDisplay.initializeElements(listSize);

        showFrame(new DisplayFrame(list));
    }

    private void showNextFrame() {
        currentFrame++;

        if (currentFrame >= frames.size()) {
            currentFrame = frames.size() - 1;
        }

        if (currentFrame == -1) {
            System.out.println("No frames");
            return;
        }

        showFrame(frames.get(currentFrame));
    }

    private void showFrame(DisplayFrame frame) {
        if (frame == null) {
            System.out.println("Frame is null");
            return;
        }

        if (arrayDisplay == null) {
            System.out.println("Display is null");
            return;
        }

        arrayDisplay.displayFrame(frame);
    }

    public List<Integer> getList() {
        return list;
    }
}
