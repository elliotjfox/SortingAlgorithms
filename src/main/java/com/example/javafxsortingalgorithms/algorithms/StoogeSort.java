package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithmupdates.CreateItemUpdate;
import com.example.javafxsortingalgorithms.arraydisplay.DisplayMode;
import com.example.javafxsortingalgorithms.newanimation.NewAnimatedSection;
import com.example.javafxsortingalgorithms.newanimation.NewAnimationSectionList;

import java.util.List;

public class StoogeSort extends SortingAlgorithm {

    private NewAnimationSectionList sectionList;

    public StoogeSort(List<Integer> arrayList) {
        super(arrayList);
    }

    @Override
    protected void runAlgorithm() {
        if (mode == DisplayMode.ANIMATED) {
            sectionList = new NewAnimationSectionList();
            currentChanges.add(new CreateItemUpdate(sectionList));
            animation.setItemHeight(sectionList, 0);
            animation.addFrame();
            NewAnimatedSection section = new NewAnimatedSection(list.size());
            currentChanges.add(section.setIndex(0));
            currentChanges.add(sectionList.addSectionsToStart(section));
            animation.addFrame();
        }
        stoogeSort(0, list.size() - 1);
    }

    private void stoogeSort(int from, int to) {
        animation.readIndex(from);
        animation.readIndex(to);
        animation.addFrame();
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
                NewAnimatedSection section1 = new NewAnimatedSection((to - third) - from + 1);
                NewAnimatedSection section2 = new NewAnimatedSection(to - (from + third) + 1);
                NewAnimatedSection section3 = new NewAnimatedSection((to - third) - from + 1);
                currentChanges.add(section1.setIndex(from));
                currentChanges.add(section2.setIndex(from + third));
                currentChanges.add(section3.setIndex(from));
                currentChanges.add(sectionList.addSectionsToStart(section1, section2, section3));
                animation.addFrame();
            }
            stoogeSort(from, to - third);
            stoogeSort(from + third, to);
            stoogeSort(from, to - third);
        }

    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

    }

//    @Override
//    public void startAnimated(AnimatedArrayDisplay display) {
//        oldSectionList = new AnimatedSectionList(display);
//        display.addItem(oldSectionList, 0, 0);
//    }

    @Override
    public String getName() {
        return "Stooge Sort";
    }
}
