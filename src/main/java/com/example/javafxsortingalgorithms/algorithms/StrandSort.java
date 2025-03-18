package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;

import java.util.List;

public class StrandSort extends ActionSortingAlgorithm {
    private int buffer;

    public StrandSort(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);

        buffer = 0;
        setInitialActions(new FindStrand(0, false));
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

    }

    @Override
    public String getName() {
        return null;
    }

    private static final class FindStrand extends AlgorithmAction {

        private final int start;
        private final boolean merge;

        public FindStrand(int start, boolean merge) {
            this.start = start;
            this.merge = merge;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            int k = 0;
            int i = start;
            int last = i;

            algorithm.addToStart(new Move(i, k));
            i++;
            while (i < algorithm.getList().size()) {
                if (algorithm.getList().get(i) >= algorithm.getList().get(last)) {
                    algorithm.addToStart(new Move(i, k + 1));
                    k++;
                    last = i;
                } else {
                    int finalI = i;
                    algorithm.addToStart(new LaterAction(() -> display.readIndex(finalI), true));
//                    algorithm.addToStart(new Pause());
                }
                i++;
            }

            if (merge) {
                algorithm.addToStart(new MergeStrand(0,  k + 1, start + k));
            }

            if (start + k + 1 < algorithm.getList().size()) {
                algorithm.addToStart(new FindStrand(start + k + 1, true));
            }
        }
    }

    private static final class MergeStrand extends AlgorithmAction {
        private int left;
        private int right;
        private final int end;

        public MergeStrand(int left, int right, int end) {
            this.left = left;
            this.right = right;
            this.end = end;
        }


        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            int i = left;
            while (left < right && right <= end) {
//                System.out.print(STR."\{left}/\{right}/\{end}");
                if (algorithm.getList().get(left) > algorithm.getList().get(right)) {
                    algorithm.addToStart(new Move(right, i));
                    right++;
                } else {
                    int finalI = i;
                    algorithm.addToStart(new LaterAction(() -> display.writeIndex(finalI), true));
                    left++;
                }
                i++;
//                System.out.println(STR." --> \{left}/\{right}/\{end}");
            }
        }
    }
}
