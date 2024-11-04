package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.arraydisplay.AnimationGroup;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDetailedDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;
import javafx.animation.Timeline;

import java.util.ArrayList;
import java.util.List;

public abstract class ActionSortingAlgorithm extends SortingAlgorithm {

    protected ArrayList<AlgorithmAction> actions;
    protected ArrayList<AlgorithmAction> toAdd;
    protected ArrayDetailedDisplay display;

    public ActionSortingAlgorithm(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);

        actions = new ArrayList<>();
        toAdd = new ArrayList<>();
    }

    @Override
    protected void runAlgorithm(ArrayDisplay display) {
        AlgorithmAction currentAction;
        do {
            if (actions.isEmpty()) {
                System.out.println("Action list is empty");
                return;
            }
            currentAction = actions.getFirst();
            currentAction.perform(this, display);
            actions.remove(currentAction);
            actions.addAll(0, toAdd);
            toAdd.clear();
        } while (!currentAction.takesStep);
    }

    @Override
    public void iterateDetailed(ArrayDetailedDisplay display) {
        AlgorithmAction currentAction;
        do {
            if (actions.isEmpty()) {
                System.out.println("Action list is empty");
                return;
            }
            currentAction = actions.getFirst();
            currentAction.performDetailed(this, display);
            actions.remove(currentAction);
            actions.addAll(0, toAdd);
            toAdd.clear();
        } while (!currentAction.takesStep);
    }

    @Override
    public boolean isDone() {
        return actions.isEmpty();
    }

    protected void addToStart(AlgorithmAction... actions) {
        toAdd.addAll(List.of(actions));
    }

    protected static abstract class AlgorithmAction {
        protected boolean takesStep = true;
        abstract void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display);

        public void performDetailed(ActionSortingAlgorithm algorithm, ArrayDetailedDisplay display) {
            // TODO: Eventually make this abstract
            System.out.println("TODO: Should this action (" + getClass().getSimpleName() + ") have a detailed version?");
            perform(algorithm, display);
        }
    }

    protected static class Wait extends AlgorithmAction {
        @Override
        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {}

        @Override
        public void performDetailed(ActionSortingAlgorithm algorithm, ArrayDetailedDisplay display) {}
    }

    protected static class Move extends AlgorithmAction {
        private final int from;
        private final int to;

        public Move(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            algorithm.move(from, to);
            display.writeIndex(to);
        }

        @Override
        public void performDetailed(ActionSortingAlgorithm algorithm, ArrayDetailedDisplay display) {
            algorithm.move(from, to);
            display.move(from, to);
        }
    }

    protected static class Swap extends AlgorithmAction {
        private final int firstIndex;
        private final int secondIndex;

        public Swap(int firstIndex, int secondIndex) {
            this.firstIndex = firstIndex;
            this.secondIndex = secondIndex;
        }

        @Override
        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            algorithm.swap(firstIndex, secondIndex);
            display.writeIndex(firstIndex);
            display.writeIndex(secondIndex);
        }

        @Override
        public void performDetailed(ActionSortingAlgorithm algorithm, ArrayDetailedDisplay display) {
            algorithm.swap(firstIndex, secondIndex);
            display.swap(firstIndex, secondIndex);
        }
    }

    protected static class LaterAction extends AlgorithmAction {

        private final Runnable action;

        public LaterAction(Runnable action) {
            this(action, false);
        }

        public LaterAction(Runnable action, boolean takesStep) {
            this.action = action;
            this.takesStep = takesStep;
        }

        @Override
        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            action.run();
        }

        @Override
        public void performDetailed(ActionSortingAlgorithm algorithm, ArrayDetailedDisplay display) {
            action.run();
        }
    }

    protected static class AnimationAction extends AlgorithmAction {
        private final AnimationGroup group;

        public AnimationAction(AnimationGroup group) {
            this.group = group;
            takesStep = false;
        }

        public AnimationAction(Timeline... timelines) {
            this(new AnimationGroup(timelines));
        }

        @Override
        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            System.out.println("An " + getClass().getSimpleName() + " should not be performing normally!");
        }

        @Override
        public void performDetailed(ActionSortingAlgorithm algorithm, ArrayDetailedDisplay display) {
            display.addAnimations(group);
        }
    }

    protected static class CheckIfSorted extends AlgorithmAction {

        @Override
        void perform(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            for (int i = 0; i < algorithm.list.size() - 1; i++) {
                if (algorithm.list.get(i) > algorithm.list.get(i + 1)) {
                    return;
                }
            }
            algorithm.isDone = true;
        }

        @Override
        public void performDetailed(ActionSortingAlgorithm algorithm, ArrayDetailedDisplay display) {
            for (int i = 0; i < algorithm.list.size() - 1; i++) {
                display.addAnimations(new AnimationGroup(
                        display.readAnimation(i),
                        display.readAnimation(i + 1)
                ));
                if (algorithm.list.get(i) > algorithm.list.get(i + 1)) {
                    return;
                }
            }
            algorithm.isDone = true;
        }
    }
}
