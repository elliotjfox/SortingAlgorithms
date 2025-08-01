package com.example.javafxsortingalgorithms.algorithmupdates;

import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplayBase;
import javafx.animation.Timeline;

public class AnimationUpdate implements DisplayUpdate {

    private final Timeline timeline;
    private final Runnable whenInterrupted;

    public AnimationUpdate(Timeline timeline, Runnable whenInterrupted) {
        this.timeline = timeline;
        this.whenInterrupted = whenInterrupted;
    }

    public void play() {
        if (timeline != null) timeline.play();
    }

    public void interrupt() {
        whenInterrupted.run();
    }

    @Override
    public void performChange(ArrayDisplayBase display) {
        timeline.setOnFinished(_ -> display.removeAnimation(this));
        display.addAnimation(this);
    }
}
