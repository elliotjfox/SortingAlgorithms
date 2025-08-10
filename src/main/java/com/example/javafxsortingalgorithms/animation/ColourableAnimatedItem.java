package com.example.javafxsortingalgorithms.animation;

import com.example.javafxsortingalgorithms.algorithmupdates.AnimationUpdate;
import javafx.scene.paint.Paint;

public interface ColourableAnimatedItem {
    AnimationUpdate changeFill(Paint fill);
}
