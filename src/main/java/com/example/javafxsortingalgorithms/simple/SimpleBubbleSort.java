package com.example.javafxsortingalgorithms.simple;

import com.example.javafxsortingalgorithms.animation.AnimatedArrayDisplay;
import com.example.javafxsortingalgorithms.animation.AnimatedItem;
import com.example.javafxsortingalgorithms.animation.ItemBuilder;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;

import java.util.List;

public class SimpleBubbleSort extends SimpleAlgorithm {

    private boolean done;

    private AnimatedItem leftArrow;
    private AnimatedItem rightArrow;

    public SimpleBubbleSort(List<Integer> list) {
        super(list);

        done = false;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void iterate() {
        boolean hasSwapped;
        for (int i = 0; i < list.size(); i++) {
            hasSwapped = false;
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    swap(j, j + 1);
                    hasSwapped = true;
                }
                doneStep();
            }
            if (!hasSwapped) {
                done = true;
                return;
            }
        }
    }

    @Override
    public void startAnimations(AnimatedArrayDisplay animatedArrayDisplay) {
        super.startAnimations(animatedArrayDisplay);

        leftArrow = ItemBuilder.defaultArrow(animatedArrayDisplay, 0);
        animatedArrayDisplay.addItem(leftArrow);
        rightArrow = ItemBuilder.defaultArrow(animatedArrayDisplay, 1);
        animatedArrayDisplay.addItem(rightArrow);
    }

    @Override
    public void iterateAnimated() {
        boolean hasSwapped;
        for (int i = 0; i < list.size(); i++) {
            hasSwapped = false;
            for (int j = 0; j < list.size() - 1 - i; j++) {
                leftArrow.moveToIndex(j, 0);
                rightArrow.moveToIndex(j + 1, 0);
                animatedArrayDisplay.newGroup();
                animatedArrayDisplay.reading(j, j + 1);
                if (list.get(j) > list.get(j + 1)) {
                    swap(j, j + 1);
                    hasSwapped = true;
                }
                doneStep();
            }
            if (!hasSwapped) {
                done = true;
                return;
            }
        }
    }
}
