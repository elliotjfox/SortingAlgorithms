package com.example.javafxsortingalgorithms;

import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import com.example.javafxsortingalgorithms.arraydisplay.*;
import com.example.javafxsortingalgorithms.settings.Settings;
import com.example.javafxsortingalgorithms.settings.SettingsPane;
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
    private SortingAlgorithm algorithm;
    private SettingsPane settingsPane;
    private ArrayDisplay display;
    private final Timeline normarlTimeline;
    private final Timeline animatedTimeline;
    private final Menu algorithmMenu;
    private final MenuBar menuBar;
    private final Button settingsButton;
    private Timeline currentTimeline;

    private AlgorithmMode mode;
    private AnimatedMode animatedMode;

    public AlgorithmContainer() {
        normarlTimeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1),
                        event -> iterateNormal(settingsPane.getAlgorithmSettings().getSpeed())
                )
        );
        normarlTimeline.setCycleCount(Animation.INDEFINITE);

        animatedTimeline = new Timeline(
                new KeyFrame(
                        Duration.millis(ArrayAnimatedDisplay.ANIMATION_LENGTH + ArrayAnimatedDisplay.ANIMATION_COOLDOWN),
                        event -> iterateAnimated()
                )
        );
        animatedTimeline.setCycleCount(Animation.INDEFINITE);


        settingsPane = new SettingsPane();
        createArray(settingsPane.getDisplaySettings().getNumberElements());
        // Change this type
        display = new ArrayColourfulDisplay(list, settingsPane);

        algorithmMenu = createAlgorithmSelector();
        menuBar = new MenuBar(algorithmMenu);

        settingsButton = new Button("Settings");
        settingsButton.setOnAction(event -> settingsPane.show());
        selectAlgorithm("Bubble Sort", "Bubble");
//        settingsPane.setAlgorithmSettings(Settings.createSettings(algorithmSelector.getSelectionModel().getSelectedItem()));

        mode = AlgorithmMode.NORMAL;
        animatedMode = AnimatedMode.ITERATE;
        currentTimeline = normarlTimeline;
    }

    public void createAlgorithm() {
        algorithm = settingsPane.getAlgorithmSettings().createAlgorithm(list);
        if (mode == AlgorithmMode.ANIMATED) {
            if (display instanceof ArrayAnimatedDisplay) {
                algorithm.startAnimated((ArrayAnimatedDisplay) display);
            } else {
                System.out.println("Display is not a animated display when it should be!");
            }
        }
    }

    public void createArray(int size) {
        list = Settings.getRandomUniformList(size);
//        list = Settings.getArray(size, 0, 25);
//        list = Settings.getRandom(size);
        if (display != null) display.setList(list);
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
        if (display != null) display.setList(this.list);
        resetAlgorithm();
    }

    public void reversed() {
        reset(Settings.getReverseList(settingsPane.getDisplaySettings().getNumberElements()));
    }

    public void finish() {
        stop();
        display.playFinish();
        resetAlgorithm();
    }

    public void resetAlgorithm() {
        settingsPane.getAlgorithmSettings().resetAlgorithm();
        algorithm = null;
    }

    public void startTest(TestDisplay testDisplay) {
        Settings.getTestList(settingsPane.getTestSettings().getNumberElements(), testList -> {
            list = testList;
            algorithm = settingsPane.getAlgorithmSettings().createInstantAlgorithm(list);
            algorithm.solveInstant(testDisplay);
        });
    }

    public void iterateNormal(int num) {
        for (int i = 0; i < num; i++) {
            if (algorithm.isDone()) break;
            algorithm.iterate(display);
        }
        if (algorithm.isDone()) {
            if (!SortingAlgorithm.isListSorted(list)) {
                System.out.println("!!! --- Algorithm is done, but list is not sorted --- !!!");
            } else {
                finish();
            }
        }
        display.drawArray();
    }

    public void iterateAnimated() {
        if (!(display instanceof ArrayAnimatedDisplay animatedDisplay)) {
            System.out.println("Display is not a animated display!!");
            return;
        }

        // So that iterating doesn't take any time
        if (animatedMode == AnimatedMode.ITERATE) {
            // Only check if we would be iterating
            if (algorithm.isDone()) {
                finish();
                return;
            }
            animatedDisplay.newGroup();
            algorithm.iterateAnimated(animatedDisplay);
            if (animatedDisplay.hasAnimations() || animatedDisplay.needsToMoveElements()) {
                animatedMode = AnimatedMode.ANIMATIONS;
            }
        }

        if (animatedDisplay.hasAnimations()) {
            animatedDisplay.playAnimations();
        } else if (animatedDisplay.needsToMoveElements()) {
            animatedDisplay.playFinalAnimations();
        }

        if (!animatedDisplay.hasAnimations() && !animatedDisplay.needsToMoveElements()) {
            animatedMode = AnimatedMode.ITERATE;
        }
    }

    public void enterNormalMode() {
        // TODO: Replace with selecting the correct one
        stop();
        mode = AlgorithmMode.NORMAL;
        currentTimeline = normarlTimeline;
        display = new ArrayColourfulDisplay(list, settingsPane);
    }

    public void enterAnimatedMode() {
        stop();
        mode = AlgorithmMode.ANIMATED;
        currentTimeline = animatedTimeline;
        display = new ArrayAnimatedDisplay(settingsPane);
        reset();
    }

    public void selectAlgorithm(String displayName, String name) {
        settingsPane.setAlgorithmSettings(Settings.createAlgorithmSettings(name));
        algorithmMenu.setText(displayName);
    }

    public boolean hasCreatedAlgorithm() {
        return algorithm != null;
    }

    public Node getSelector() {
//        return algorithmSelector;
        return menuBar;
    }

    public Node getSettingsButton() {
        return settingsButton;
    }

    public Node getDisplay() {
        return display;
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
                createMenuItem("Bitonic Sort", "Bitonic")
        );

        Menu miscellaneous = new Menu(
                "Miscellaneous", null,
                createMenuItem("Radix Sort", "Radix"),
                createMenuItem("Odd Even Sort", "OddEven"),
                createMenuItem("Exchange Sort", "Exchange"),
                createMenuItem("Strand Sort", "Strand"),
                createMenuItem("Cartesian Tree Sort", "Cartesian"),
                createMenuItem("Shell Sort 2", "Shell2"),
                createMenuItem("Cycle Sort", "Cycle")
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
