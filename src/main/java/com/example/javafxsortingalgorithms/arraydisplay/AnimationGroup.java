package com.example.javafxsortingalgorithms.arraydisplay;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AnimationGroup {

    private final List<Runnable> onPlayActions;
    private final List<Runnable> whenDoneActions;
    private final List<Timeline> timelines;

    public AnimationGroup(Timeline... timelines) {
        this.timelines = new ArrayList<>();
        this.onPlayActions = new ArrayList<>();
        this.whenDoneActions = new ArrayList<>();
        addTimelines(timelines);
    }

    public void addTimelines(Timeline... timelines) {
        this.timelines.addAll(List.of(timelines));
    }

    public void addOnPlay(Runnable... onPlayActions) {
        this.onPlayActions.addAll(List.of(onPlayActions));
    }

    public void addWhenDone(Runnable... whenDoneActions) {
        this.whenDoneActions.addAll(List.of(whenDoneActions));
    }

    public void play() {
        if (!whenDoneActions.isEmpty()) {
            addTimelines(new Timeline(new KeyFrame(
                    Duration.millis(ArrayDetailedDisplay.ANIMATION_LENGTH),
                    event -> {
                        for (Runnable r : whenDoneActions) r.run();
                    }
            )));
        }
        for (Runnable r : onPlayActions) {
            r.run();
        }
        for (Timeline t : timelines) {
            t.play();
        }
    }

    public void clear() {
        timelines.clear();
        onPlayActions.clear();
    }

    public boolean hasAnimations() {
        return !timelines.isEmpty();
    }
}
