package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.AnimationGroup;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDetailedDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.DetailedSortingNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BitonicSort extends ActionSortingAlgorithm {

    private final boolean fastMode;

    private DetailedSortingNetwork sortingNetwork;

    public BitonicSort(List<Integer> arrayList, boolean isInstant, boolean fastMode) {
        super(arrayList, isInstant);

        this.fastMode = fastMode;


        if (isInstant) return;

        actions.add(createAllSwap());
        for (int width = 4; width <= roundUpToPowerOf2(arrayList.size()); width *= 2) {
            if (width > 1) {
                for (int i = width; i > 1; i -= 2) {
                    BitonicComparison comparison = new BitonicComparison();
                    for (int j = (width - i) / 2; j < arrayList.size(); j += width) {
                        comparison.addComparisons(j, j + i - 1);
                    }
                    actions.add(comparison);
                }
            }

            for (int i = width / 2; i >= 4; i /= 2) {
                int distance = i / 2;
                for (int j = 0; j < distance; j++) {
                    BitonicComparison comparison = new BitonicComparison();
                    for (int k = j; k < list.size(); k += i) {
                        comparison.addComparisons(k, k + distance);
                    }
                    actions.add(comparison);
                }
            }

//            if (width > 1) {
//                BitonicComparison comparison = new BitonicComparison();
//                for (int i = width; i > 1; i -= 2) {
//                    for (int j = (width - i) / 2; j < arrayList.size(); j += width) {
//                        comparison.addComparisons(j, j + i - 1);
//                    }
//                }
//                actions.add(comparison);
//            }
//
//            for (int i = width / 2; i >= 4; i /= 2) {
//                int distance = i / 2;
//                BitonicComparison comparison = new BitonicComparison();
//                for (int j = 0; j < distance; j++) {
//                    for (int k = j; k < list.size(); k += i) {
//                        comparison.addComparisons(k, k + distance);
//                    }
//                }
//                actions.add(comparison);
//            }

            actions.add(createAllSwap());
        }
    }

    private BitonicComparison createAllSwap() {
        BitonicComparison comparison = new BitonicComparison();
        for (int i = 0; i < list.size() - 1; i += 2) {
            comparison.addComparisons(i, i + 1);
        }
        return comparison;
    }
//
//    private InstantThread createAllSwapThread() {
//        InstantThread thread = new InstantThread(list);
//        for (int i = 0; i < list.size() - 1; i += 2) {
//            thread.addComparisons(i, i + 1);
//        }
//        return thread;
//    }

    private void compareAndSwap(int first, int second, TestEntry testEntry) {
        if (first < 0 || second < 0 || first >= list.size() || second >= list.size()) return;
        testEntry.addRead(2);
        if (list.get(first) > list.get(second)) {
            swap(first, second);
            testEntry.addWrite(2);
        }
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

        for (int i = 0; i < list.size() - 1; i += 2) {
            compareAndSwap(i, i + 1, entry);
        }

        for (int width = 4; width <= roundUpToPowerOf2(list.size()); width *= 2) {
            entry.updateProgress(log2(width / 2.0) / log2(roundUpToPowerOf2(list.size())));
            if (width > 1) {
                for (int i = width; i > 1; i -= 2) {
                    for (int j = (width - i) / 2; j < list.size(); j += width) {
                        compareAndSwap(j, j + i - 1, entry);
                    }
                }
            }

            for (int i = width / 2; i >= 4; i /= 2) {
                int distance = i / 2;
                for (int j = 0; j < distance; j++) {
                    for (int k = j; k < list.size(); k += i) {
                        compareAndSwap(k, k + distance, entry);
                    }
                }
            }

            for (int i = 0; i < list.size() - 1; i += 2) {
                compareAndSwap(i, i + 1, entry);
            }
        }

//        List<InstantThread> threads = new ArrayList<>();
//
//        threads.add(createAllSwapThread());
//        for (int width = 4; width <= roundUpToPowerOf2(list.size()); width *= 2) {
//            if (width > 1) {
//                for (int i = width; i > 1; i -= 2) {
//                    InstantThread thread = new InstantThread(list);
//                    for (int j = (width - i) / 2; j < list.size(); j += width) {
//                        thread.addComparisons(j, j + i - 1);
//                    }
//                    threads.add(thread);
//                }
//            }
//
//            for (int i = width / 2; i >= 4; i /= 2) {
//                int distance = i / 2;
//                for (int j = 0; j < distance; j++) {
//                    InstantThread thread = new InstantThread(list);
//                    for (int k = j; k < list.size(); k += i) {
//                        thread.addComparisons(k, k + distance);
//                    }
//                    threads.add(thread);
//                }
//            }
//
//            threads.add(createAllSwapThread());
//        }
    }

    @Override
    public void startDetailed(ArrayDetailedDisplay display) {
        sortingNetwork = new DetailedSortingNetwork(list);
        display.addItem(sortingNetwork, 0, 0);

        // Buffer for the first one
        sortingNetwork.addComparisons(new ArrayList<>());
        for (AlgorithmAction action : actions) {
            if (action instanceof BitonicComparison comparison) {
                sortingNetwork.addComparisons(comparison.comparisons);
            }
        }

        display.addAnimations(new AnimationGroup(sortingNetwork.moveUp()));
    }

    @Override
    public String getName() {
        return null;
    }

//    protected static class BitonicSection extends AlgorithmAction {
//        private final int width;
//
//        public BitonicSection(int width) {
//            this.width = width;
//        }
//
//        @Override
//        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
//            if (width > 1) {
//                algorithm.addToStart(new BitonicOppositeSection(width));
//            }
//
//            for (int i = width / 2; i >= 4; i /= 2) {
//                algorithm.addToStart(new BitonicDiagonalSection(i));
//            }
//
//            algorithm.addToStart(new BitonicAllSwap());
//        }
//    }

//    protected static class BitonicOppositeSection extends AlgorithmAction {
//        private final int maxWidth;
//
//        public BitonicOppositeSection(int width) {
//            this.maxWidth = roundUpToPowerOf2(width);
//            takesStep = false;
//        }
//
//        @Override
//        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
//            for (int i = maxWidth; i > 1; i -= 2) {
//                BitonicComparison comparison = new BitonicComparison();
//                for (int j = (maxWidth - i) / 2; j < algorithm.list.size(); j += maxWidth) {
//                    comparison.addComparisons(j, j + i - 1);
//                }
//                algorithm.addToStart(comparison);
//            }
//        }
//    }

//    protected static class BitonicDiagonalSection extends AlgorithmAction {
//        private final int boxWidth;
//
//        public BitonicDiagonalSection(int boxWidth) {
//            this.boxWidth = boxWidth;
//        }
//
//        @Override
//        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
//            int distance = boxWidth / 2;
//            for (int i = 0; i < distance; i++) {
//                BitonicComparison comparison = new BitonicComparison();
//                for (int j = i; j < algorithm.list.size(); j += boxWidth) {
//                    comparison.addComparisons(j, j + distance);
//                }
//                algorithm.addToStart(comparison);
//            }
//        }
//    }

    protected static class BitonicComparison extends AlgorithmAction {

        protected final List<Integer> comparisons;

        public BitonicComparison(Integer... comparisons) {
            this.comparisons = new ArrayList<>();
            this.comparisons.addAll(Arrays.asList(comparisons));
        }

        public void addComparisons(Integer... comparisons) {
            this.comparisons.addAll(Arrays.asList(comparisons));
        }

        @Override
        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            for (int i = 0; i < comparisons.size() - 1; i += 2) {
                if (comparisons.get(i) < 0 || comparisons.get(i + 1) >= algorithm.list.size()) continue;
                if (algorithm.list.get(comparisons.get(i)) > algorithm.list.get(comparisons.get(i + 1))) {
                    if (algorithm instanceof BitonicSort && ((BitonicSort) algorithm).fastMode) {
                        algorithm.swap(comparisons.get(i), comparisons.get(i + 1));
                    } else {
                        algorithm.addToStart(new Swap(comparisons.get(i), comparisons.get(i + 1)));
                    }
                }
            }
        }

        @Override
        public void performDetailed(ActionSortingAlgorithm algorithm, ArrayDetailedDisplay display) {
            if (algorithm instanceof BitonicSort bitonicSort) {
                AnimationGroup group = new AnimationGroup();
                for (int i = 0; i < comparisons.size() - 1; i += 2) {
                    if (comparisons.get(i) < 0 || comparisons.get(i + 1) >= algorithm.list.size()) continue;
                    group.addTimelines(
                            display.readAnimation(comparisons.get(i)),
                            display.readAnimation(comparisons.get(i + 1))
                    );
                }

                display.addAnimations(
                        new AnimationGroup(
                                bitonicSort.sortingNetwork.moveUp(),
                                bitonicSort.sortingNetwork.removeFirst()
                        ),
                        group
                );
                for (int i = 0; i < comparisons.size() - 1; i += 2) {
                    if (comparisons.get(i) < 0 || comparisons.get(i + 1) >= algorithm.list.size()) continue;
                    if (algorithm.list.get(comparisons.get(i)) > algorithm.list.get(comparisons.get(i + 1))) {
                        if (((BitonicSort) algorithm).fastMode) {
                            algorithm.swap(comparisons.get(i), comparisons.get(i + 1));
                            display.swap(comparisons.get(i), comparisons.get(i + 1));
                        } else {
                            algorithm.addToStart(new Swap(comparisons.get(i), comparisons.get(i + 1)));
                        }
                    }
                }
//                display.addAnimations(new AnimationGroup(
//                        bitonicSort.sortingNetwork.removeFirst()
//                ));
//                display.getElementAnimationGroup().addTimelines(bitonicSort.sortingNetwork.removeFirst());
            }
        }
    }

    protected static class BitonicAllSwap extends BitonicComparison {

        @Override
        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            for (int i = 0; i < algorithm.list.size() - 1; i += 2) {
                addComparisons(i, i + 1);
            }
            super.perform(algorithm, display);
        }

        @Override
        public void performDetailed(ActionSortingAlgorithm algorithm, ArrayDetailedDisplay display) {
            for (int i = 0; i < algorithm.list.size() - 1; i += 2) {
                addComparisons(i, i + 1);
            }
            super.performDetailed(algorithm, display);
        }
    }

//    protected static class InstantThread extends BitonicComparison implements Runnable {
//
//        private List<Integer> list;
//        private List<InstantThread> threads;
//
//        public InstantThread(List<Integer> list, List<InstantThread> threads, Integer... comparisons) {
//            this.list = list;
//            this.threads = threads;
//            addComparisons(comparisons);
//        }
//
//        @Override
//        public void run() {
//            for (int i = 0; i < comparisons.size() - 1; i += 2) {
//                int firstIndex = comparisons.get(i);
//                int secondIndex = comparisons.get(i + 1);
//                if (firstIndex < 0 || secondIndex < 0 || firstIndex >= list.size() || secondIndex >= list.size()) continue;
//                if (list.get(firstIndex) > list.get(comparisons.get(i + 1))) {
//                    int tmp = list.get(firstIndex);
//                    list.set(firstIndex, list.get(secondIndex));
//                    list.set(secondIndex, tmp);
//                }
//            }
//            threads.remove(this);
//        }
//    }

    public static int roundUpToPowerOf2(int i) {
        return (int) Math.pow(2, Math.ceil(log2(i)));
    }

    public static double log2(double a) {
        return Math.log(a) / Math.log(2);
    }
}
