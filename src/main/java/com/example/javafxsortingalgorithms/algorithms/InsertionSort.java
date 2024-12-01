package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.*;

import java.util.List;
import java.util.function.Function;

public class InsertionSort extends ActionSortingAlgorithm {

    public enum SearchType {
        LEFT_LINEAR("Left-most index", "Left-most value"),
        RIGHT_LINEAR("Right-most index", "Right-most value"),
        BINARY("Index", "Value");

        private final String index;
        private final String value;

        SearchType(String index, String value) {
            this.index = index;
            this.value = value;
        }
    }

    private final SearchType searchType;

    private AnimatedSection searchSection;
    private AnimatedArrow arrow;

    private final double sectionHeight = -10;
    private final double arrowHeight = 0;

    public InsertionSort(List<Integer> arrayList, boolean isInstant, SearchType searchType) {
        super(arrayList, isInstant);

        this.searchType = searchType;

        actions.add(new Root());
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {
        switch (searchType) {
            case RIGHT_LINEAR -> instantRight(entry);
            case LEFT_LINEAR -> instantLeft(entry);
            case BINARY -> System.out.println("Binary instant not implemented yet");
        }
    }

    private void instantRight(TestEntry entry) {
        // Technically not necessary to start at 0, we could start at 1
        for (int i = 0; i < list.size(); i++) {
            int j = i - 1;
            // I think the > instead of >= makes this stable?
            while (j >= 0 && list.get(j) > list.get(i)) {
                entry.addRead(2);
                j--;
            }
            move(i, j + 1);
            entry.addWrite();
            entry.updateProgress((double) i / list.size());
        }
    }

    private void instantLeft(TestEntry entry) {
        // Technically not necessary to start at 0, we could start at 1
        for (int i = 0; i < list.size(); i++) {
            int j = 0;
            // I think the <= over the < makes this stable?
            while (j < i && list.get(j) <= list.get(i)) {
                entry.addRead(2);
                j++;
            }
            move(i, j);
            entry.addWrite();
            entry.updateProgress((double) i / list.size());
        }
    }

    @Override
    public void startAnimated(ArrayAnimatedDisplay display) {
        searchSection = new AnimatedSection(display, 1.0, true);
        display.addItem(searchSection, 0, sectionHeight);
        arrow = new AnimatedArrow(display, true);
        display.addItem(arrow, 0, arrowHeight);

        display.setCurrentTask("Searching for correct position");
        display.updateInfo("Sorted", 1);
        display.updateInfo(searchType.index, 0);
        display.updateInfo(searchType.value, 0);
        display.updateInfo("Current index", 0);
        display.updateInfo("Current value", 0);
    }

    @Override
    public String getName() {
        return switch (searchType) {
            case LEFT_LINEAR -> "Insertion Sort\nLeft Search";
            case RIGHT_LINEAR -> "Insertion Sort\nRight Search";
            case BINARY -> "Insertion Sort\nBinary Search";
        };
    }

    private static class Root extends AlgorithmAction {
        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            if (algorithm instanceof InsertionSort insertionSort) {
                switch (insertionSort.searchType) {
                    case RIGHT_LINEAR -> {
                        for (int i = 1; i < algorithm.list.size(); i++) algorithm.addToStart(new RightSearch(i - 1, i));
                    }
                    case LEFT_LINEAR -> {
                        for (int i = 1; i < algorithm.list.size(); i++) algorithm.addToStart(new LeftSearch(0, i - 1, i));
                    }
                    case BINARY -> {
                        for (int i = 1; i < algorithm.list.size(); i++) algorithm.addToStart(new BinarySearch(0, i - 1, i));
                    }
                }
            }
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, ArrayAnimatedDisplay display) {
            if (!(algorithm instanceof InsertionSort insertionSort)) return;
            Function<Integer, InsertionSearchAction> addSearch = switch (insertionSort.searchType) {
                case RIGHT_LINEAR -> (i) -> new RightSearch(i - 1, i);
                case LEFT_LINEAR -> (i) -> new LeftSearch(0, i - 1, i);
                case BINARY -> (i) -> new BinarySearch(0, i - 1, i);
            };

            for (int i = 1; i < algorithm.list.size(); i++) {
                int finalI = i + 1;
                algorithm.addToStart(
                        addSearch.apply(i),
                        new LaterAction(() -> display.updateInfo("Sorted", finalI))
                );
            }
        }
    }

    private static abstract class InsertionSearchAction extends AlgorithmAction {

        protected final int from;
        protected final int to;
        protected final int index;

        public InsertionSearchAction(int from, int to, int index) {
            this.from = from;
            this.to = to;
            this.index = index;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            if (algorithm instanceof InsertionSort) {
                search((InsertionSort) algorithm, display);
            }
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, ArrayAnimatedDisplay display) {
            if (algorithm instanceof InsertionSort) {
                searchDetailed((InsertionSort) algorithm, display);
            }
        }

        protected abstract void search(InsertionSort algorithm, ArrayDisplay display);

        protected abstract void searchDetailed(InsertionSort algorithm, ArrayAnimatedDisplay display);
    }

    private static class LeftSearch extends InsertionSearchAction {

        public LeftSearch(int from, int to, int index) {
            super(from, to, index);
        }

        @Override
        protected void search(InsertionSort algorithm, ArrayDisplay display) {
            if (from > to) {
                algorithm.addToStart(new Move(index, from));
                return;
            }
            if (algorithm.list.get(index) < algorithm.list.get(from)) {
                algorithm.addToStart(new Move(index, from));
            } else {
                algorithm.addToStart(new LeftSearch(from + 1, to, index));
            }
            display.readIndex(from);
        }

        @Override
        protected void searchDetailed(InsertionSort algorithm, ArrayAnimatedDisplay display) {
            if (from > to) {
                algorithm.searchSection.moveToIndex(from, algorithm.sectionHeight);
                display.animate(algorithm.searchSection.resizeTimeline((to - from + 1) * 25));

                algorithm.addToStart(new Move(index, from));
                return;
            }

            algorithm.arrow.moveToIndex(index, algorithm.arrowHeight);
            algorithm.searchSection.moveToIndex(from, algorithm.sectionHeight);
            display.animate(algorithm.searchSection.resizeTimeline((to - from + 1) * 25));
            display.newGroup();
            display.comparing(index, from);

            if (algorithm.list.get(index) < algorithm.list.get(from)) {
                algorithm.addToStart(new Move(index, from));
            } else {
                algorithm.addToStart(new LeftSearch(from + 1, to, index));
            }
        }
    }

    private static class RightSearch extends InsertionSearchAction {

        public RightSearch(int to, int index) {
            super(0, to, index);
        }

        @Override
        protected void search(InsertionSort algorithm, ArrayDisplay display) {
            if (to < 0) {
                algorithm.addToStart(new Move(index, 0));
                return;
            }
            if (algorithm.list.get(index) > algorithm.list.get(to)) {
                algorithm.addToStart(new Move(index, to + 1));
            } else {
                algorithm.addToStart(new RightSearch(to - 1, index));
            }
            display.readIndex(to);
        }

        @Override
        public void searchDetailed(InsertionSort algorithm, ArrayAnimatedDisplay display) {
            if (to < 0) {
                algorithm.searchSection.moveToIndex(from, algorithm.sectionHeight);
                display.animate(algorithm.searchSection.resizeTimeline((to - from + 1) * 25));
                display.updateInfoWhenDone(SearchType.RIGHT_LINEAR.index, AnimatedInfo.OUT_OF_BOUNDS_INDEX);
                display.updateInfoWhenDone(SearchType.RIGHT_LINEAR.value, AnimatedInfo.OUT_OF_BOUND_VALUE);

                algorithm.addToStart(
                        new LaterAction(() -> display.setCurrentTask("Moving to correct position")),
                        new Move(index, 0),
                        new LaterAction(() -> display.setCurrentTask("Searching for correct position"))
                );
                return;
            }

            algorithm.arrow.moveToIndex(index, algorithm.arrowHeight);
            algorithm.searchSection.moveToIndex(from, algorithm.sectionHeight);
            display.animate(algorithm.searchSection.resizeTimeline((to - from + 1) * 25));
            display.updateInfoWhenDone(SearchType.RIGHT_LINEAR.index, to);
            display.updateInfoWhenDone(SearchType.RIGHT_LINEAR.value, algorithm.getList().get(to));
            display.updateInfoWhenDone("Current index", index);
            display.updateInfoWhenDone("Current value", algorithm.getList().get(index));
            display.newGroup();
            display.comparing(index, to);

            if (algorithm.list.get(index) > algorithm.list.get(to)) {
                algorithm.addToStart(
                        new LaterAction(() -> display.setCurrentTask("Moving to correct position")),
                        new Move(index, to + 1),
                        new LaterAction(() -> display.setCurrentTask("Searching for correct position"))
                );
            } else {
                algorithm.addToStart(new RightSearch(to - 1, index));
            }
            display.readIndex(to);
        }
    }

    private static class BinarySearch extends InsertionSearchAction {

        public BinarySearch(int from, int to, int index) {
            super(from, to, index);
        }

        @Override
        protected void search(InsertionSort algorithm, ArrayDisplay display) {
            if (from >= to) {
                if (algorithm.list.get(index) > algorithm.list.get(from)) {
                    algorithm.addToStart(new Move(index, from + 1));
                } else {
                    algorithm.addToStart(new Move(index, from));
                }
            } else {
                int mid = (from + to) / 2;
                if (algorithm.list.get(index) > algorithm.list.get(mid)) {
                    algorithm.addToStart(new BinarySearch(mid + 1, to, index));
                } else if (algorithm.list.get(index) < algorithm.list.get(mid)) {
                    algorithm.addToStart(new BinarySearch(from, mid - 1, index));
                } else {
                    // AKA: array.get(index) == array.get(mid)
                    algorithm.addToStart(new Move(index, mid));
                }
                display.readIndex(mid);
            }
        }

        @Override
        protected void searchDetailed(InsertionSort algorithm, ArrayAnimatedDisplay display) {
            if (from >= to) {
                algorithm.searchSection.moveToIndex(from, algorithm.sectionHeight);
                display.animate(algorithm.searchSection.resizeTimeline(to - from + 1));
                display.newGroup();
                display.comparing(index, from);
//                display.addAnimations(
//                        new AnimationGroup(
//                                display.moveItemToElementAnimation(algorithm.searchSection, from, algorithm.sectionHeight),
//                                algorithm.searchSection.resizeTimeline((to - from + 1) * 25)
//                        ),
//                        new AnimationGroup(
//                                display.readAnimation(index),
//                                display.readAnimation(from)
//                        )
//                );
                if (algorithm.list.get(index) > algorithm.list.get(from)) {
                    algorithm.addToStart(new Move(index, from + 1));
                } else {
                    algorithm.addToStart(new Move(index, from));
                }
            } else {
                int mid = (from + to) / 2;

                algorithm.arrow.moveToIndex(index, algorithm.arrowHeight);
                algorithm.searchSection.moveToIndex(index, algorithm.sectionHeight);
                display.animate(algorithm.searchSection.resizeTimeline(to - from + 1));
                display.newGroup();
                display.comparing(index, mid);
//                display.addAnimations(
//                        new AnimationGroup(
//                                display.moveItemToElementAnimation(algorithm.arrow, index, algorithm.arrowHeight),
//                                display.moveItemToElementAnimation(algorithm.searchSection, from, algorithm.sectionHeight),
//                                algorithm.searchSection.resizeTimeline((to - from + 1) * 25)
//                        ),
//                        new AnimationGroup(
//                                display.readAnimation(index),
//                                display.readAnimation(mid)
//                        )
//                );

                if (algorithm.list.get(index) > algorithm.list.get(mid)) {
                    algorithm.addToStart(new BinarySearch(mid + 1, to, index));
                } else if (algorithm.list.get(index) < algorithm.list.get(mid)) {
                    algorithm.addToStart(new BinarySearch(from, mid - 1, index));
                } else {
                    // AKA: array.get(index) == array.get(mid)
                    algorithm.addToStart(new Move(index, mid));
                }
                display.readIndex(mid);
            }
        }
    }




    // Right linear search
//    void insertionSort() {
//        for (int i = 1; i < list.size(); i++) {
//            int j = i - 1;
//            while (j >= 0 && list.get(j) > list.get(i)) {
//                j--;
//            }
//            move(i, j + 1);
//        }
//    }









}
