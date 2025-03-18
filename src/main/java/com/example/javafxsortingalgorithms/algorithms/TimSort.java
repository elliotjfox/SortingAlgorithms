package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.AnimatedArrayDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;

import java.util.ArrayList;
import java.util.List;

// TODO: Still some bugs
public class TimSort extends ActionSortingAlgorithm {

    private record Sublist(int left, int right) {
        public int getSize() {
            return right - left + 1;
        }

        @Override
        public String toString() {
            return "Sublist{" +
                    "left=" + left +
                    ", right=" + right +
                    ", size=" + getSize() +
                    '}';
        }
    }

    private int minrun;
    private List<Sublist> stack;

    public TimSort(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);

        stack = new ArrayList<>();
        minrun = calculateMinrun();
        System.out.println("Minrun: " + minrun);
        for (int i = 0; i < list.size(); i += minrun) {
            addToStart(new FindRun(i));
        }
//        actions.add(new LaterAction(() -> {
//            while (stack.size() > 1) {
//                addMerge(stack.get(stack.size() - 2), stack.getLast());
//            }
//        }));
        catchUpActions();
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

    }

    @Override
    public String getName() {
        return "Tim Sort";
    }

    private int calculateMinrun() {
        for (int i = 32; i < 64; i++) {
            if (isPowerOfTwo(list.size() / i) && !isPowerOfTwo(list.size() / (i + 1))) {
                return i + 1;
            }
        }
        return 64;
    }

    private boolean isPowerOfTwo(int n) {
        return n == (int) (Math.pow(2, (int) (Math.log(n) / Math.log(2))));
    }

    private void verifyStack() {
        System.out.println("verifying");
        System.out.println(stack);
        if (!needsToMerge()) return;
        System.out.println("Do need to merge");

        if (stack.size() == 2) {
            addMerge(stack.getFirst(), stack.getLast());
            return;
        }

        if (stack.get(stack.size() - 3).getSize() < stack.getLast().getSize()) {
            addMerge(stack.get(stack.size() - 3), stack.get(stack.size() - 2));
        } else {
            addMerge(stack.get(stack.size() - 2), stack.getLast());
        }
    }

    private void addMerge(Sublist sublist1, Sublist sublist2) {
        stack.set(stack.indexOf(sublist1), new Sublist(sublist1.left, sublist2.right));
//        stack.remove(sublist1);
        stack.remove(sublist2);
        addToStart(new Merge(sublist1.left, sublist2.left, sublist2.right));
//        stack.add(index, new Sublist(sublist1.left, sublist2.right));
    }

    private boolean needsToMerge() {
        if (stack.size() <= 1) return false;
        if (stack.size() == 2) {
            return stack.getFirst().getSize() <= stack.getLast().getSize();
        }
        if (stack.get(stack.size() - 2).getSize() <= stack.getLast().getSize()) return true;
        return stack.get(stack.size() - 3).getSize() <= stack.get(stack.size() - 2).getSize() + stack.getLast().getSize();
    }


    private static abstract class TimSortAction extends AlgorithmAction {
        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            if (algorithm instanceof TimSort timSort) {
                execute(timSort, display);
            }
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
            if (algorithm instanceof TimSort timSort) {
                executeAnimated(timSort, display);
            }
        }

        abstract void execute(TimSort timSort, ArrayDisplay display);

        abstract void executeAnimated(TimSort timSort, AnimatedArrayDisplay display);
    }

    private static class FindRun extends TimSortAction {

        private final int from;

        public FindRun(int from) {
            this.from = from;
        }

        @Override
        void execute(TimSort timSort, ArrayDisplay display) {
            boolean ascending = timSort.list.get(from) < timSort.list.get(from + 1);
            int last = timSort.list.get(from + 1);
            int i;
            for (i = from + 2; i < from + timSort.minrun && i < timSort.list.size(); i++) {
                if (ascending && timSort.list.get(i) < last) {
                    break;
                } else if (!ascending && timSort.list.get(i) > last) {
                    break;
                } else {
                    timSort.addToStart(new Wait());
                    last = timSort.list.get(i);
                }
            }
            timSort.addToStart(new InsertionSort(ascending, from, Math.min(from + timSort.minrun, timSort.list.size()), i));
            if (!ascending) {
                timSort.addToStart(new Flip(from, Math.min(from + timSort.minrun, timSort.list.size()) - 1));
            }
            System.out.println("Found " + (ascending ? "ascending" : "descending") + " run of length " + (i - from) + " (" + from + " to " + i + ")");
            timSort.stack.add(new Sublist(from, Math.min(from + timSort.minrun, timSort.list.size()) - 1));
            timSort.verifyStack();
        }

        @Override
        void executeAnimated(TimSort timSort, AnimatedArrayDisplay display) {

        }
    }

    private static class InsertionSort extends TimSortAction {

        private final boolean ascending;
        private final int left;
        private final int right;
        private final int index;

        public InsertionSort(boolean ascending, int left, int right, int index) {
            this.ascending = ascending;
            this.left = left;
            this.right = right;
            this.index = index;
        }

        @Override
        void execute(TimSort timSort, ArrayDisplay display) {
            if (ascending) {
                for (int i = index - 1; i >= left; i--) {
                    if (timSort.list.get(i) < timSort.list.get(index)) {
                        addOthers(timSort, i + 1);
                        return;
                    }
                    timSort.addToStart(new Wait());
                }
                addOthers(timSort, left);
            } else {
                for (int i = index - 1; i >= left; i--) {
                    if (timSort.list.get(i) > timSort.list.get(index)) {
                        addOthers(timSort, i + 1);
                        return;
                    }
                    timSort.addToStart(new Wait());
                }
                addOthers(timSort, left);
            }
        }

        private void addOthers(TimSort timSort, int to) {
            timSort.addToStart(new Move(index, to));
            if (index + 1 < right) {
                timSort.addToStart(new InsertionSort(ascending, left, right, index + 1));
            }
        }

        @Override
        void executeAnimated(TimSort timSort, AnimatedArrayDisplay display) {

        }
    }

    private static class Merge extends TimSortAction {

        private int left;
        private int right;
        private final int end;

        public Merge(int left, int right, int end) {
            this.left = left;
            this.right = right;
            this.end = end;
        }

        @Override
        void execute(TimSort timSort, ArrayDisplay display) {
            int i = left;
            while (right <= end && left < right && i < right) {
                if (timSort.list.get(left) < timSort.list.get(right)) {
                    // Pause to keep the merge smooth
                    timSort.addToStart(new Wait());
                    left++;
                } else {
                    timSort.addToStart(new Move(right, i));
                    right++;
                }
                i++;
            }
            timSort.verifyStack();
        }

        @Override
        void executeAnimated(TimSort timSort, AnimatedArrayDisplay display) {

        }
    }
}
