package com.example.javafxsortingalgorithms.animation.position;

import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;

public interface YPosition {
    YPosition TOP = _ -> 0;
    YPosition BOTTOM = DisplaySettings::height;

    double getY(DisplaySettings settings);

//    default YPosition plus(XPosition xPosition) {
//        return settings -> getY(settings) + xPosition.getX(settings);
//    }

    static YPosition add(YPosition position1, YPosition position2) {
        return settings -> position1.getY(settings) + position2.getY(settings);
    }
}
