package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.algorithmupdates.CreateItemUpdate;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledIndex;
import com.example.javafxsortingalgorithms.animation.position.ElementScaledPosition;
import com.example.javafxsortingalgorithms.arraydisplay.DisplayMode;
import com.example.javafxsortingalgorithms.animation.AnimatedSection;
import com.example.javafxsortingalgorithms.animation.AnimationSectionList;

import java.util.List;

public class StoogeSort extends SortingAlgorithm {

    private AnimationSectionList sectionList;

    public StoogeSort(List<Integer> arrayList) {
        super(arrayList);
    }

    @Override
    protected void runAlgorithm() {
        if (mode == DisplayMode.ANIMATED) {
            sectionList = new AnimationSectionList();
            currentChanges.add(new CreateItemUpdate(sectionList));
            animation.setItemPosition(sectionList, new ElementScaledPosition(0, 0));
            animation.addFrame();
            AnimatedSection section = new AnimatedSection(new ElementScaledIndex(list.size()));
            currentChanges.add(section.setXPosition(new ElementScaledIndex(0)));
            currentChanges.add(sectionList.addSectionsToStart(section));
            animation.addFrame();
        }
        stoogeSort(0, list.size() - 1);
    }

    private void stoogeSort(int from, int to) {
        animation.readIndex(from);
        animation.readIndex(to);
        animation.addFrame();
        trial.addRead(2);
        trial.addComparison();
        if (list.get(from) > list.get(to)) {
            swap(from, to);
            addFrame();
        }
        if (sectionList != null) {
            currentChanges.add(sectionList.removeFirstSection());
            animation.addFrame();
        }

        if (to - from + 1 > 2) {
            int third = (to - from + 1) / 3;
            if (sectionList != null) {
                AnimatedSection section1 = new AnimatedSection(new ElementScaledIndex((to - third) - from + 1));
                AnimatedSection section2 = new AnimatedSection(new ElementScaledIndex(to - (from + third) + 1));
                AnimatedSection section3 = new AnimatedSection(new ElementScaledIndex((to - third) - from + 1));
                currentChanges.add(section1.setXPosition(new ElementScaledIndex(from)));
                currentChanges.add(section2.setXPosition(new ElementScaledIndex(from + third)));
                currentChanges.add(section3.setXPosition(new ElementScaledIndex(from)));
                currentChanges.add(sectionList.addSectionsToStart(section1, section2, section3));
                animation.addFrame();
            }
            stoogeSort(from, to - third);
            stoogeSort(from + third, to);
            stoogeSort(from, to - third);
        }

    }

    @Override
    public String getName() {
        return "Stooge Sort";
    }
}
