package com.example.javafxsortingalgorithms.animation.position;

import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;

public interface XPosition {
    XPosition LEFT = _ -> 0;
    XPosition RIGHT = settings -> settings.elementWidth() * settings.size();

    double getX(DisplaySettings settings);

//    default XPosition plus(XPosition xPosition) {
//        return settings -> getX(settings) + xPosition.getX(settings);
//    }

    static XPosition add(XPosition position1, XPosition position2) {
        return settings -> position1.getX(settings) + position2.getX(settings);
    }
}
