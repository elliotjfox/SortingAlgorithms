package com.example.javafxsortingalgorithms.simple;

import com.example.javafxsortingalgorithms.AlgorithmDisplay;
import com.example.javafxsortingalgorithms.animation.AnimatedArrayDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.Display;
import com.example.javafxsortingalgorithms.arraydisplay.DisplayFrame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class SimpleAlgorithm {

    protected final List<Integer> list;
    protected Display display;
    protected AnimatedArrayDisplay animatedArrayDisplay;
    protected AlgorithmDisplay.Mode mode;

    public SimpleAlgorithm(List<Integer> list) {
        this.list = list;
        mode = AlgorithmDisplay.Mode.NORMAL;
    }

    public void link(Display display) {
        this.display = display;
    }

    public void startAnimations(AnimatedArrayDisplay animatedArrayDisplay) {
        this.animatedArrayDisplay = animatedArrayDisplay;
        mode = AlgorithmDisplay.Mode.ANIMATED;
    }

    protected void swap(int firstIndex, int secondIndex) {
        if (firstIndex < 0 || firstIndex >= list.size() || secondIndex < 0 || secondIndex >= list.size()) return;
        list.set(firstIndex, list.set(secondIndex, list.get(firstIndex)));

        if (mode == AlgorithmDisplay.Mode.ANIMATED) {
            animatedArrayDisplay.swap(firstIndex, secondIndex);
        }
    }

    protected void move(int index, int targetIndex) {
        if (index < 0 || index >= list.size() || targetIndex < 0 || targetIndex >= list.size() || index == targetIndex) return;
        list.add(targetIndex, list.remove(index));

        if (mode == AlgorithmDisplay.Mode.ANIMATED) {
            animatedArrayDisplay.move(index, targetIndex);
        }
    }

    protected void doneStep() {
        switch (mode) {
            case ANIMATED -> {
                animatedArrayDisplay.newGroup();
//                animatedArrayDisplay.
            }
            case NORMAL, COMPARING -> display.addFrame(new DisplayFrame(new ArrayList<>(list), new HashMap<>()));
            case TESTING -> {}
        }
    }

    public abstract boolean isDone();
    public abstract void iterate();
    public abstract void iterateAnimated();
}
