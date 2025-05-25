package com.example.javafxsortingalgorithms;

import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import com.example.javafxsortingalgorithms.animation.AnimatedArrayDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.*;
import com.example.javafxsortingalgorithms.settings.Settings;
import com.example.javafxsortingalgorithms.settings.SettingsPane;
import com.example.javafxsortingalgorithms.simple.SimpleAlgorithm;
import com.example.javafxsortingalgorithms.simple.SimpleBubbleSort;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmContainer {

    // TODO: Finish getting animated working
    public enum AlgorithmMode {
        NORMAL,
        ANIMATED,
        TESTING,
        COMPARING // TODO: Might not be needed
    }

    // TODO: Could just use a boolean
    private enum AnimatedMode {
        ITERATE,
        ANIMATIONS
    }

    private List<Integer> list;
    private SettingsPane settingsPane;

    private SimpleAlgorithm simpleAlgorithm;
    private SimpleDisplay simpleDisplay;

    private final Menu algorithmMenu;
    private final MenuBar menuBar;
    private final Button settingsButton;

    private final Timeline normalTimeline;
    private final Timeline animatedTimeline;
    private Timeline currentTimeline;

    private AlgorithmMode mode;
    private AnimatedMode animatedMode;

    public AlgorithmContainer() {
        normalTimeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1),
                        event -> iterateNormal(settingsPane.getAlgorithmSettings().getSpeed())
                )
        );
        normalTimeline.setCycleCount(Animation.INDEFINITE);

        animatedTimeline = new Timeline(
                new KeyFrame(
                        Duration.millis(AnimatedArrayDisplay.ANIMATION_LENGTH + AnimatedArrayDisplay.ANIMATION_COOLDOWN),
                        event -> iterateAnimated()
                )
        );
        animatedTimeline.setCycleCount(Animation.INDEFINITE);


        settingsPane = new SettingsPane();
        createArray(settingsPane.getDisplaySettings().getNumberElements());

        simpleDisplay = new SimpleDisplay(settingsPane, list);

        algorithmMenu = createAlgorithmSelector();
        menuBar = new MenuBar(algorithmMenu);

        settingsButton = new Button("Settings");
        settingsButton.setOnAction(event -> settingsPane.show());
        selectAlgorithm("Bubble Sort", "Bubble");
//        settingsPane.setAlgorithmSettings(Settings.createSettings(algorithmSelector.getSelectionModel().getSelectedItem()));

        mode = AlgorithmMode.NORMAL;
        animatedMode = AnimatedMode.ITERATE;
        currentTimeline = normalTimeline;
    }

    public void createAlgorithm() {
//        algorithm = settingsPane.getAlgorithmSettings().createAlgorithm(list);
        // TODO: Select correct algorithm here
        simpleAlgorithm = new SimpleBubbleSort(list);
        simpleAlgorithm.link(simpleDisplay);

        if (mode == AlgorithmMode.ANIMATED) {
            if (simpleDisplay instanceof AnimatedArrayDisplay) {
                simpleAlgorithm.startAnimations((AnimatedArrayDisplay) simpleDisplay);
            } else {
                System.out.println("Display is not a animated display when it should be!");
            }
        }
    }

    public void createArray(int size) {
        list = Settings.getRandomUniformList(size);
//        list = Settings.getArray(size, 0, 25);
//        list = Settings.getRandom(size);
//        list = Settings.getApproxList(size, x -> x, size / 10);
//        list = Settings.getApproxList(size, x -> -Math.abs(size / 2 - x) + size, size / 10);
//        if (display != null) display.setList(list);
        if (simpleDisplay != null) simpleDisplay.setList(list);
    }

    public void play() {
        if (!hasCreatedAlgorithm()) {
            createAlgorithm();
        }
        currentTimeline.playFromStart();
    }

    public void stop() {
        currentTimeline.stop();
    }

    public void step() {
        stop();
        if (!hasCreatedAlgorithm()) createAlgorithm();
        switch (mode) {
            case NORMAL -> iterateNormal(1);
            case ANIMATED -> iterateAnimated();
            default -> System.out.println("Tried to step, but can't");
        }
    }

    public void reset() {
        stop();
        createArray(settingsPane.getDisplaySettings().getNumberElements());
        resetAlgorithm();
    }

    public void reset(List<Integer> list) {
        stop();
        this.list = new ArrayList<>(list);
//        if (display != null) display.setList(this.list);
        if (simpleDisplay != null) simpleDisplay.setList(this.list);
        resetAlgorithm();
    }

    public void reversed() {
        reset(Settings.getReverseList(settingsPane.getDisplaySettings().getNumberElements()));
    }

    public void finish() {
        stop();
//        display.playFinish();
        resetAlgorithm();
    }

    public void resetAlgorithm() {
        settingsPane.getAlgorithmSettings().resetAlgorithm();
        simpleAlgorithm = null;
    }

    public void startTest(TestDisplay testDisplay) {
        Settings.getTestList(settingsPane.getTestSettings().getNumberElements(), testList -> {
            list = testList;

            // TODO Fix
//            algorithm = settingsPane.getAlgorithmSettings().createInstantAlgorithm(list);
//            algorithm.solveInstant(testDisplay);
        });
    }

    public void iterateNormal(int num) {
        if (simpleDisplay.hasFramesLeft()) {
            simpleDisplay.showNextFrame();
        } else if (simpleAlgorithm.isDone()) {
            if (!SortingAlgorithm.isListSorted(list)) {
                System.out.println("!!! --- Algorithm is done, but list is not sorted --- !!!");
            } else {
                finish();
            }
        } else {
            for (int i = 0; i < num; i++) {
                if (simpleAlgorithm.isDone()) break;
                simpleAlgorithm.iterate();
            }
        }
    }

    public void iterateAnimated() {
        if (!(simpleDisplay instanceof AnimatedArrayDisplay animatedDisplay)) {
            System.out.println("Display is not a animated display!!");
            return;
        }

        // If we need to iterate, do the next step
        if (animatedMode == AnimatedMode.ITERATE) {
            // But only if there is still actions left to do
            if (simpleAlgorithm.isDone()) {
                finish();
                return;
            }

            animatedDisplay.resetAnimations();

            // Do the iteration, this will add all the animations in groups
            simpleAlgorithm.iterateAnimated();

            // TODO: Need to be able to force the element animation group to go whenever "doneStep" is called in SimpleAlgorithm

            // If there are any animations to do, make sure we do them before iterating next
            if (animatedDisplay.hasAnimationsLeft()) {
                animatedMode = AnimatedMode.ANIMATIONS;
                animatedDisplay.startAnimations();
            }
        }

        // If we have animations, do the next group
        if (animatedDisplay.hasAnimationsLeft()) {
            animatedDisplay.getNextAnimationGroup().play();
        }

        // If no more animations left, switch back to iterating
        if (!animatedDisplay.hasAnimationsLeft()) {
            animatedMode = AnimatedMode.ITERATE;
        }
    }

    public void enterNormalMode() {
        // TODO: Replace with selecting the correct one
        stop();
        mode = AlgorithmMode.NORMAL;
        currentTimeline = normalTimeline;
//        display = new ArrayColourfulDisplay(list, settingsPane);

        simpleDisplay = new SimpleDisplay(settingsPane);
        simpleDisplay.setList(list);
    }

    public void enterAnimatedMode() {
        stop();
        mode = AlgorithmMode.ANIMATED;
        currentTimeline = animatedTimeline;
        simpleDisplay = new AnimatedArrayDisplay(settingsPane);
        reset();
    }

    public void selectAlgorithm(String displayName, String name) {
        settingsPane.setAlgorithmSettings(Settings.createAlgorithmSettings(name));
        algorithmMenu.setText(displayName);
    }

    public boolean hasCreatedAlgorithm() {
        return simpleAlgorithm != null;
    }

    public Node getSelector() {
//        return algorithmSelector;
        return menuBar;
    }

    public Node getSettingsButton() {
        return settingsButton;
    }

    public Node getDisplay() {
        return simpleDisplay;
//        return display;
    }

    public List<Integer> getList() {
        return list;
    }

    private Menu createAlgorithmSelector() {
        Menu basic = new Menu(
                "Basic", null,
                createMenuItem("Selection Sort", "Selection"),
                createMenuItem("Insertion Sort", "Insertion"),
                createMenuItem("Bubble Sort", "Bubble"),
                createMenuItem("Gnome Sort", "Gnome")
        );

        Menu improved = new Menu(
                "Improved", null,
                createMenuItem("Cocktail Shaker Sort", "Cocktail"),
                createMenuItem("Comb Sort", "Comb"),
                createMenuItem("Heap Sort", "Heap"),
                createMenuItem("Shell Sort", "Shell")
        );

        Menu recursive = new Menu(
                "Recursive", null,
                createMenuItem("Merge Sort", "Merge"),
                createMenuItem("Quick Sort", "Quick")
        );

        Menu sortingNetworks = new Menu(
                "Sorting Networks", null,
                createMenuItem("Bitonic Sort", "Bitonic"),
                createMenuItem("Odd Even Merge Sort", "OddEvenMerge")
        );

        Menu miscellaneous = new Menu(
                "Miscellaneous", null,
                createMenuItem("Tim Sort", "Tim"),
                createMenuItem("Radix Sort", "Radix"),
                createMenuItem("Odd Even Sort", "OddEven"),
                createMenuItem("Exchange Sort", "Exchange"),
                createMenuItem("Strand Sort", "Strand"),
                createMenuItem("Cartesian Tree Sort", "Cartesian"),
                createMenuItem("Cycle Sort", "Cycle"),
                createMenuItem("Pancake Sort", "Pancake")
                );

        Menu jokeAlgorithms = new Menu(
                "Joke Algorithms", null,
                createMenuItem("Sleep Sort", "Sleep"),
                createMenuItem("Stooge Sort", "Stooge"),
                createMenuItem("Bogo Sort", "Bogo"),
                createMenuItem("Quantum Bogo Sort", "QuantumBogo"),
                createMenuItem("Gravity Sort", "Gravity")
        );

        return new Menu(
                "Algorithms", null,
                basic,
                improved,
                recursive,
                sortingNetworks,
                miscellaneous,
                jokeAlgorithms
        );
    }

    private MenuItem createMenuItem(String s, String other) {
        MenuItem item = new MenuItem(s);
        item.setOnAction(event -> selectAlgorithm(s, other));
        return item;
    }
}
