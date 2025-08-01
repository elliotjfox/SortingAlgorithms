package com.example.javafxsortingalgorithms.algorithmupdates;

import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplayBase;
import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;
import javafx.animation.Timeline;

import java.util.function.Consumer;
import java.util.function.Function;

public class GenerateAnimationUpdate implements DisplayUpdate {

    private final Function<DisplaySettings, Timeline> createTimeline;
    private final Consumer<DisplaySettings> whenInterrupted;

    public GenerateAnimationUpdate(Function<DisplaySettings, Timeline> createTimeline, Consumer<DisplaySettings> whenInterrupted) {
        this.createTimeline = createTimeline;
        this.whenInterrupted = whenInterrupted;
    }

    @Override
    public void performChange(ArrayDisplayBase display) {
        Timeline timeline = createTimeline.apply(display.getSettings());
        AnimationUpdate animation = new AnimationUpdate(timeline, () -> whenInterrupted.accept(display.getSettings()));

        // This adds the animation to the
        animation.performChange(display);
    }
}
