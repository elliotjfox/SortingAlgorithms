package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.animation.AnimatedArrow;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledIndex;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledPosition;

import java.util.List;

public class GnomeSort extends SortingAlgorithm {

    public GnomeSort(List<Integer> arrayList) {
        super(arrayList);
    }

    @Override
    protected void runAlgorithm() {
        AnimatedArrow arrow = animation.createArrow();
        animation.setItemPosition(arrow, new ElementScaledPosition(0, 0));

        int i = 0;
        while (i + 1 < list.size()) {
            animation.changeItemX(arrow, new ElementScaledIndex(i));
            // We need to read if we are inside the list
            if (i >= 0) {
                animation.addFrame();
                animation.readIndex(i);
                animation.readIndex(i + 1);
            }
            if (i < 0) i++;
            else if (list.get(i) <= list.get(i + 1)) {
                i++;
            } else {
                animation.addFrame();
                swap(i, i + 1);
                i--;
            }
            addFrame();
        }
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