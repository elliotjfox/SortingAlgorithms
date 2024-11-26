package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayAnimatedDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.AnimatedSection;
import com.example.javafxsortingalgorithms.arraydisplay.AnimatedSectionList;

import java.util.List;

public class StoogeSort extends ActionSortingAlgorithm {

    private AnimatedSectionList sectionList;

    public StoogeSort(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);

        actions.add(new StoogeSortAction(0, list.size() - 1));
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

    }

    @Override
    public void startAnimated(ArrayAnimatedDisplay display) {
        sectionList = new AnimatedSectionList(display);
        display.addItem(sectionList, 0, 0);
    }

    @Override
    public String getName() {
        return null;
    }

    private static class StoogeSortAction extends AlgorithmAction {
        private final int from;
        private final int to;

        public StoogeSortAction(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
//            System.out.println(STR."[\{from}, \{to}]");
            if (from >= to) return;

            if (algorithm.list.get(from) > algorithm.list.get(to)) {
                algorithm.swap(from, to);
                display.writeIndex(from);
                display.writeIndex(to);
            } else {
                display.readIndex(from);
                display.readIndex(to);
            }

            if (to - from + 1 > 2) {
                int third = (to - from + 1) / 3;
                algorithm.addToStart(
                        new StoogeSortAction(from, to - third),
                        new StoogeSortAction(from + third, to),
                        new StoogeSortAction(from, to - third)
                );
            }
        }

        @Override
        public void performDetailed(ActionSortingAlgorithm algorithm, ArrayAnimatedDisplay display) {
            if (from >= to) return;

            if (algorithm instanceof StoogeSort stoogeSort) {
                if (stoogeSort.sectionList.hasSection()) {
                    display.animate(stoogeSort.sectionList.shrinkHighest());
                    display.newGroup();
                }
                display.comparing(from, to);
                if (algorithm.list.get(from) > algorithm.list.get(to)) {
                    algorithm.swap(from, to);
                    display.swap(from, to);
                }

                if (to - from + 1 > 2) {
                    int third = (to - from + 1) / 3;
                    algorithm.addToStart(
                            new StoogeSortAction(from, to - third),
                            new StoogeSortAction(from + third, to),
                            new StoogeSortAction(from, to - third)
                    );
                    display.newGroup();
                    display.animate(
                            stoogeSort.sectionList.addSections(
                                    new AnimatedSection(display, from + third - to + 1, true),
                                    new AnimatedSection(display, to - from - third + 1, true),
                                    new AnimatedSection(display, from + third - to + 1, true)
                            )
                    );
                }
            }
        }
    }
}
