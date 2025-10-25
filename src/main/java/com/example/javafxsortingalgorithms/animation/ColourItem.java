package com.example.javafxsortingalgorithms.animation;

import com.example.javafxsortingalgorithms.algorithmupdates.AnimationUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.DisplayUpdate;
import javafx.scene.paint.Paint;

public interface ColourItem {
    AnimationUpdate changeFill(Paint fill);
    DisplayUpdate setFill(Paint fill);
}
