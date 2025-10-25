package com.example.javafxsortingalgorithms.animation;

import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;
import javafx.animation.Timeline;

public interface AnimatedCreation {
    Timeline createCreationTimeline(DisplaySettings settings);
}
