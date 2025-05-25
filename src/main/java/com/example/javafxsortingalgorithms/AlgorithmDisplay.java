package com.example.javafxsortingalgorithms;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

/**
 * This is the object that contains every thing displayed on screen: the buttons and the elements being sorted by the algorithm.
 */
public class AlgorithmDisplay extends VBox {

    public enum Mode {
        NORMAL,
        TESTING,
        ANIMATED,
        // TODO: Implement
        COMPARING
    }

    private final ButtonPane buttonPane;

    private final FlowPane arrayPane;
    private TestDisplay testDisplay;

    private final AlgorithmContainer firstAlgorithm;
    private AlgorithmContainer secondAlgorithm;

    private Mode currentMode;

    public AlgorithmDisplay() {
        firstAlgorithm = new AlgorithmContainer();

        buttonPane = new ButtonPane(this);
        getChildren().add(buttonPane);

        arrayPane = new FlowPane(Orientation.HORIZONTAL);
        arrayPane.setAlignment(Pos.CENTER);
        getChildren().add(arrayPane);

        setMode(Mode.NORMAL);
    }

    public void play() {
        System.out.println("Play");
        firstAlgorithm.play();
        if (currentMode == Mode.COMPARING) secondAlgorithm.play();
    }

    public void stop() {
        System.out.println("Stop");
        firstAlgorithm.stop();
        if (currentMode == Mode.COMPARING) secondAlgorithm.stop();
    }

    public void step() {
        System.out.println("Step");
        firstAlgorithm.step();
        if (currentMode == Mode.COMPARING) secondAlgorithm.step();
    }

    public void reset() {
        System.out.println("Reset");
        firstAlgorithm.reset();
        if (currentMode == Mode.COMPARING) secondAlgorithm.reset(firstAlgorithm.getList());
    }

    public void reversed() {
        firstAlgorithm.reversed();
    }

    public void startTest() {
        firstAlgorithm.startTest(testDisplay);
    }

    public void setMode(Mode mode) {
        if (currentMode == mode) return;
        currentMode = mode;
        arrayPane.getChildren().clear();
        testDisplay = null;
        buttonPane.setMode(currentMode);
        switch (currentMode) {
            case NORMAL -> {
                System.out.println("Now normal mode");
                arrayPane.getChildren().add(firstAlgorithm.getDisplay());
                buttonPane.addSelector(firstAlgorithm);
            }
            case TESTING -> {
                System.out.println("Now testing mode");
                testDisplay = new TestDisplay();

                arrayPane.getChildren().add(testDisplay);
                buttonPane.addSelector(firstAlgorithm);
            }
            case ANIMATED -> {
                System.out.println("Now animated mode");
                firstAlgorithm.enterAnimatedMode();
                arrayPane.getChildren().addAll(firstAlgorithm.getDisplay());
                buttonPane.addSelector(firstAlgorithm);
            }
            // TODO: Remove the second algorithm once we exit this mode
            case COMPARING -> {
                System.out.println("Now comparing mode");
                secondAlgorithm = new AlgorithmContainer();
                secondAlgorithm.reset(firstAlgorithm.getList());
                // Reverse order because it adds them to the start
                buttonPane.addSelector(secondAlgorithm);
                buttonPane.addSelector(firstAlgorithm);
                arrayPane.getChildren().addAll(firstAlgorithm.getDisplay(), secondAlgorithm.getDisplay());
            }
        }
    }
}
