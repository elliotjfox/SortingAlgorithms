package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;

import java.util.List;
import java.util.Random;

public class BogoSort extends ActionSortingAlgorithm {

    public BogoSort(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);

        setInitialActions(new Randomize());
    }

    // TODO: Make a way to run this on a thread, so you aren't just waiting forever
    //  And generically, if the algorithm is taking too long, start a thread for it
    @Override
    protected void runAlgorithm() {
        Random r = new Random();
        while (!isListSorted(list)) {
            for (int i = list.size(); i > 1; i--) {
                swap(i - 1, r.nextInt(i));
                addFrame();
            }
        }
    }

    @Override
    protected void runAlgorithm(ArrayDisplay display) {
        AlgorithmAction currentAction;
        do {
            if (actions.isEmpty()) {
                actions.add(new Randomize());
            }
            currentAction = actions.pop();
            currentAction.execute(this, display);
            catchUpActions();
        } while (!currentAction.takesStep);
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {
//        Random random = new Random();
//        while (!isListSorted(list)) {
//            for (int i = list.size(); i > 1; i--) {
//                entry.addWrite(2);
//                swap(i - 1, random.nextInt(i));
//            }
//        }
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public String getName() {
        return null;
    }

    private static class Randomize extends AlgorithmAction {

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            for (int i = 0; i < algorithm.list.size(); i++) {
                algorithm.addToStart(new Swap(i, (int) (Math.random() * (algorithm.list.size() - i)) + i));
            }
            algorithm.addToStart(new CheckIfSorted());
        }
    }
}
