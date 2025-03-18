package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.arraydisplay.AnimatedArrayDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;
import javafx.animation.Timeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public abstract class ActionSortingAlgorithm extends SortingAlgorithm {

    protected final Stack<AlgorithmAction> actions;
    protected final Stack<AlgorithmAction> toAdd;
    protected AnimatedArrayDisplay display;

    protected ActionSortingAlgorithm(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);

        actions = new Stack<>();
        toAdd = new Stack<>();
    }

    @Override
    protected void runAlgorithm(ArrayDisplay display) {
        AlgorithmAction currentAction;
        do {
            if (actions.isEmpty()) {
                System.out.println("No more actions left");
                return;
            }
            currentAction = actions.pop();
            currentAction.execute(this, display);
            catchUpActions();
        } while (!currentAction.takesStep);
    }

    @Override
    public void iterateAnimated(AnimatedArrayDisplay display) {
//        AlgorithmAction currentAction;
//        do {
//            if (actions.isEmpty()) {
//                System.out.println("Action list is empty");
//                return;
//            }
//            currentAction = actions.getFirst();
//            currentAction.executeAnimated(this, display);
//            actions.remove(currentAction);
//            actions.addAll(0, toAdd);
//            toAdd.clear();
//        } while (!currentAction.takesStep);
    }

    @Override
    public boolean isDone() {
        return actions.isEmpty();
    }

    protected void setInitialActions(AlgorithmAction... actions) {
        addToStart(actions);
        catchUpActions();
    }

    protected void addToStart(AlgorithmAction... actions) {
        toAdd.addAll(Arrays.asList(actions));
    }

    protected void catchUpActions() {
        while (!toAdd.isEmpty()) {
            actions.add(toAdd.pop());
        }
    }

    /**
     * A class representing an action that an algorithm would take. There are two methods to override,{@link #execute(ActionSortingAlgorithm, ArrayDisplay) execute(algorithm, display)}
     * and {@link #executeAnimated(ActionSortingAlgorithm, AnimatedArrayDisplay) executeAnimated(algorithm, animatedDisplay)}.
     */
    protected static abstract class AlgorithmAction {
        protected boolean takesStep = true;

        /**
         * This is called when this action is the next one for an action sorting algorithm. The action will execute on the
         * provided algorithm and display. If {@code takesStep} is true, the algorithm will stop, until it is told to do
         * its next step. If {@code takesStep} is false, the algorithm will proceed to the next action, until it reaches
         * one that does take step.
         * @param algorithm The algorithm this action belongs to
         * @param display The display the algorithm belongs to
         */
        abstract void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display);

        /**
         * This is called when this action is the next one for an action sorting algorithm in animated mode. This action
         * will execute on the provided algorithm, including queueing up animations to the provided animated display.
         * <p>
         * If not overridden, will print a warning, then call execute.
         * @param algorithm The algorithm this action belongs to
         * @param display The animated display the algorithm belongs to
         */
        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
            // TODO: Eventually make this abstract
            System.out.println("TODO: Should this action (" + getClass().getSimpleName() + ") have a detailed version?");
            execute(algorithm, display);
        }
    }

    /**
     * An action that doesn't do anything, but does take a step. Can be used to even out animations, or add fake time
     * to an algorithm in normal mode, or to catch up the animations for an animated display.
     */
    protected static class Wait extends AlgorithmAction {
        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {}

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {}
    }

    /**
     * An action that moves the element at a specified index to another specified index. When executed, automatically calls
     * {@link ArrayDisplay#writeIndex(int) writeIndex()}, or {@link AnimatedArrayDisplay#move(int, int) move()}, depending on
     * which mode.
     */
    protected static class Move extends AlgorithmAction {
        private final int from;
        private final int to;

        /**
         * Creates a new Move action that, when executed, will move the element at index {@code from} to index {@code to}
         * @param from What the index of the element we want to move is
         * @param to What the final index of the element is
         */
        public Move(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            algorithm.move(from, to);
            display.writeIndex(to);
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
            algorithm.move(from, to);
            display.move(from, to);
        }
    }

    /**
     * An action that swaps two elements. When executed, automatically calls {@link ArrayDisplay#writeIndex(int) writeIndex()}
     * for the two indices, or {@link AnimatedArrayDisplay#swap(int, int) swap()} depending on which mode we are in.
     */
    protected static class Swap extends AlgorithmAction {
        private final int firstIndex;
        private final int secondIndex;

        /**
         * Creates a Swap action that, when executed, will swap the elements with the indices {@code firstIndex} and {@code secondIndex}.
         * @param firstIndex The index of one of the elements to swap
         * @param secondIndex The index of the other element to swap
         */
        public Swap(int firstIndex, int secondIndex) {
            this.firstIndex = firstIndex;
            this.secondIndex = secondIndex;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            algorithm.swap(firstIndex, secondIndex);
            display.writeIndex(firstIndex);
            display.writeIndex(secondIndex);
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
            algorithm.swap(firstIndex, secondIndex);
            display.swap(firstIndex, secondIndex);
        }
    }

    protected static class Set extends AlgorithmAction {

        private final int index;
        private final int value;

        public Set(int index, int value) {
            this.index = index;
            this.value = value;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            algorithm.getList().set(index, value);
        }
    }

    /**
     * An action that runs a {@link Runnable} when executed.
     */
    protected static class LaterAction extends AlgorithmAction {

        private final Runnable action;

        /**
         * Creates a LaterAction that will run the provided runnable when executed. Default does not take a step.
         * @param action The runnable to run
         */
        public LaterAction(Runnable action) {
            this(action, false);
        }

        /**
         * Creates a LaterAction that will run the provided runnable when executed.
         * @param action The runnable to run
         * @param takesStep Whether this action takes a step
         */
        public LaterAction(Runnable action, boolean takesStep) {
            this.action = action;
            this.takesStep = takesStep;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            action.run();
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
            action.run();
        }
    }

    protected static class AnimationAction extends AlgorithmAction {
        private final Timeline[] timelines;

        public AnimationAction(Timeline... timelines) {
            this.timelines = timelines;
            takesStep = false;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            System.out.println("An AnimationAction should not be performing normally!");
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
            display.animate(timelines);
        }
    }

    protected static class Flip extends AlgorithmAction {

        private final int from;
        private final int to;

        // Inclusive [from, to]
        public Flip(int from, int to) {
            this(from, to, true);
        }

        // Inclusive [from, to]
        public Flip(int from, int to, boolean takesStep) {
            this.from = from;
            this.to = to;
            this.takesStep = takesStep;
        }

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            if (takesStep) {
                for (int i = 0; i <= (to - from) / 2; i++) {
                    algorithm.addToStart(new Swap(from + i, to - i));
                }
            } else {
                for (int i = 0; i <= (to - from) / 2; i++) {
                    algorithm.swap(from + i, to - i);
                }
            }
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
            if (takesStep) {
                for (int i = 0; i <= (to - from) / 2; i++) {
                    algorithm.addToStart(new Swap(from + i, to - i));
                }
            } else {
                for (int i = 0; i <= (to - from) / 2; i++) {
                    algorithm.swap(from + i, to - i);
                    display.swap(from + i, to - i);
                }
            }
        }
    }

    protected static class CheckIfSorted extends AlgorithmAction {

        @Override
        void execute(ActionSortingAlgorithm algorithm, ArrayDisplay display) {
            for (int i = 0; i < algorithm.list.size() - 1; i++) {
                if (algorithm.list.get(i) > algorithm.list.get(i + 1)) {
                    return;
                }
            }
            algorithm.isDone = true;
        }

        @Override
        public void executeAnimated(ActionSortingAlgorithm algorithm, AnimatedArrayDisplay display) {
            for (int i = 0; i < algorithm.list.size() - 1; i++) {
                display.comparing(i, i + 1);
                if (algorithm.list.get(i) > algorithm.list.get(i + 1)) {
                    return;
                }
            }
            algorithm.isDone = true;
        }
    }
}
