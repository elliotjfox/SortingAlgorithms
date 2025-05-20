package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.arraydisplay.*;
import com.example.javafxsortingalgorithms.TestEntry;

import java.util.List;

public class GnomeSort extends SortingAlgorithm {

    private int currentSpot;

    private AnimatedItem leftArrow;
    private AnimatedItem rightArrow;

    public GnomeSort(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);

        currentSpot = 0;
    }

    @Override
    protected void runAlgorithm(ArrayDisplay display) {
        if (currentSpot + 1 >= list.size()) {
            isDone = true;
            return;
        }

        if (currentSpot < 0) {
            currentSpot++;
            return;
        }

        display.readIndex(currentSpot);
        display.readIndex(currentSpot + 1);
        if (list.get(currentSpot) <= list.get(currentSpot + 1)) {
            currentSpot++;
            return;
        }

        display.writeIndex(currentSpot);
        display.writeIndex(currentSpot + 1);
        swap(currentSpot, currentSpot + 1);
        currentSpot--;
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {
        int highest = 0;
        int i = 0;
        while (true) {
            if (i > highest) highest = i;
            entry.updateProgress((double) highest / list.size());
            if (i + 1 >= list.size()) break;
            else if (i < 0) i++;
            else if (list.get(i) <= list.get(i + 1)) {
                entry.addRead(2);
                i++;
            } else {
                // Because we would have read those values
                entry.addRead(2);
                entry.addWrite(2);
                swap(i, i + 1);
                i--;
            }
        }
    }

    @Override
    public void startAnimated(AnimatedArrayDisplay display) {
        leftArrow = AnimatedItemBuilder.defaultArrow(display, 0);
        display.addItem(leftArrow);

        rightArrow = AnimatedItemBuilder.defaultArrow(display, 1);
        display.addItem(rightArrow);

        display.setCurrentTask("Sorting...");
        display.updateInfo("Left index", 0);
        display.updateInfo("Left value", list.getFirst());
        display.updateInfo("Right index", 1);
        display.updateInfo("Right value", list.get(1));
    }

    @Override
    public void iterateAnimated(AnimatedArrayDisplay display) {
        leftArrow.moveToIndex(currentSpot, 0);
        rightArrow.moveToIndex(currentSpot + 1, 0);

        if (currentSpot + 1 >= list.size()) {
            isDone = true;
            return;
        }

        if (currentSpot < 0) {
            currentSpot++;
            // TODO: Keep this or not?
            display.updateInfoWhenDone("Left index", AnimatedInfo.OUT_OF_BOUNDS_INDEX);
            display.updateInfoWhenDone("Left value", AnimatedInfo.OUT_OF_BOUND_VALUE);
            display.updateInfoWhenDone("Right index", currentSpot);
            display.updateInfoWhenDone("Right value", list.get(currentSpot));
            return;
        }

        display.updateInfoWhenDone("Left index", currentSpot);
        display.updateInfoWhenDone("Left value", list.get(currentSpot));
        display.updateInfoWhenDone("Right index", currentSpot + 1);
        display.updateInfoWhenDone("Right value", list.get(currentSpot + 1));
        display.newGroup();
        display.comparing(currentSpot, currentSpot + 1);

        if (list.get(currentSpot) <= list.get(currentSpot + 1)) {
            currentSpot++;
            return;
        }

        swap(currentSpot, currentSpot + 1);
        display.swap(currentSpot, currentSpot + 1);

        currentSpot--;
    }

    @Override
    public String getName() {
        return "Gnome Sort";
    }


//    void gnomeSort() {
//        int i = 0;
//        while (i + 1 < list.size()) {
//            if (i < 0 || list.get(i) <= list.get(i + 1)) {
//                i++;
//            } else {
//                swap(i, i + 1);
//                i--;
//            }
//        }
//    }

}