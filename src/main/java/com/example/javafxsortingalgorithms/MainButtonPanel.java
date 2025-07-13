package com.example.javafxsortingalgorithms;

import com.example.javafxsortingalgorithms.arraydisplay.DisplayMode;
import com.example.javafxsortingalgorithms.settings.AlgorithmType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.Arrays;
import java.util.List;

public class MainButtonPanel extends FlowPane {
    private final List<Node> normalPane;
    private final List<Node> testPane;
    private final List<Node> animatedPane;
    private final List<Node> comparingPane;

    private final MainDisplay display;

    private final Menu algorithmSelector;

    public MainButtonPanel(MainDisplay display) {
        setHgap(7);

        this.display = display;

        algorithmSelector = createAlgorithmSelector();
        MenuBar algorithmSelectorBar = new MenuBar(algorithmSelector);

        Button playButton = createButton(createPlayIcon(), _ -> display.play());
        Button stopButton = createButton(createPauseIcon(), _ -> display.stop());
        Button stepButton = createButton(createStepIcon(), _ -> display.step());
        Button resetButton = createButton(createResetIcon(), _ -> display.reset());
        Button settingsButton = createButton("Settings", _ -> display.openSettings());
//        Button reversedButton = createButton("Reversed", event -> algorithmDisplay.reversed());
//        Button startTestButton = createButton("Start Test", event -> algorithmDisplay.startTest());

        // Mode buttons
        Button openNormalButton = createButton(createBackIcon(), _ -> display.setMode(DisplayMode.NORMAL));
        Button openTestButton = createButton("Open Test", _ -> display.setMode(DisplayMode.TESTING));
        Button openDetailedButton = createButton("Open Animated", _ -> display.setMode(DisplayMode.ANIMATED));
        Button openCompareButton = createButton("Open Compare", _ -> display.setMode(DisplayMode.COMPARING));

        normalPane = Arrays.asList(algorithmSelectorBar, settingsButton, playButton, stopButton, stepButton, resetButton, /*reversedButton,*/ openTestButton, openDetailedButton, openCompareButton);
        testPane = Arrays.asList(algorithmSelectorBar, settingsButton, /*startTestButton,*/ openNormalButton);
        animatedPane = Arrays.asList(algorithmSelectorBar, settingsButton, playButton, stopButton, stepButton, resetButton, openNormalButton);
        comparingPane = Arrays.asList(algorithmSelectorBar, settingsButton, playButton, stopButton, stepButton, resetButton, openNormalButton);
    }

    public void setMode(DisplayMode mode) {
        getChildren().clear();
        switch (mode) {
            case NORMAL -> getChildren().addAll(normalPane);
            case ANIMATED -> getChildren().addAll(animatedPane);
            case COMPARING -> getChildren().addAll(comparingPane);
            case TESTING -> getChildren().addAll(testPane);
        }
    }

    private Menu createAlgorithmSelector() {
        Menu basic = new Menu(
                "Basic", null,
                createMenuItem(AlgorithmType.SELECTION),
                createMenuItem(AlgorithmType.INSERTION),
                createMenuItem(AlgorithmType.BUBBLE),
                createMenuItem(AlgorithmType.GNOME)
        );

        Menu improved = new Menu(
                "Improved", null,
                createMenuItem(AlgorithmType.COCKTAIL),
                createMenuItem(AlgorithmType.COMB),
                createMenuItem(AlgorithmType.HEAP),
                createMenuItem(AlgorithmType.SHELL)
        );

        Menu recursive = new Menu(
                "Recursive", null,
                createMenuItem(AlgorithmType.MERGE),
                createMenuItem(AlgorithmType.QUICK)
        );

        Menu sortingNetworks = new Menu(
                "Sorting Networks", null,
                createMenuItem(AlgorithmType.BITONIC),
                createMenuItem(AlgorithmType.ODD_EVEN_MERGE)
        );

        Menu miscellaneous = new Menu(
                "Miscellaneous", null,
                createMenuItem(AlgorithmType.TIM),
                createMenuItem(AlgorithmType.RADIX),
                createMenuItem(AlgorithmType.ODD_EVEN),
                createMenuItem(AlgorithmType.EXCHANGE),
                createMenuItem(AlgorithmType.STRAND),
                createMenuItem(AlgorithmType.CARTESIAN),
                createMenuItem(AlgorithmType.CYCLE),
                createMenuItem(AlgorithmType.PANCAKE)
        );

        Menu jokeAlgorithms = new Menu(
                "Joke Algorithms", null,
                createMenuItem(AlgorithmType.SLEEP),
                createMenuItem(AlgorithmType.STOOGE),
                createMenuItem(AlgorithmType.BOGO),
                createMenuItem(AlgorithmType.QUANTUM_BOGO),
                createMenuItem(AlgorithmType.GRAVITY)
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

    private MenuItem createMenuItem(AlgorithmType algorithm) {
        MenuItem item = new MenuItem();
        item.setText(algorithm.getDisplayName());
        item.setOnAction(_ -> display.algorithmSelected(algorithm));
        return item;
    }

    public void setSelectedAlgorithm(AlgorithmType algorithm) {
        algorithmSelector.setText(algorithm.getDisplayName());
    }

    /**
     * Creates and returns a button with the provided text, that performs the provided action when pressed.
     * @param text The text on the button
     * @param onAction What the button does when pressed
     * @return The button
     */
    private static Button createButton(String text, EventHandler<ActionEvent> onAction) {
        Button button = new Button(text);
        button.setOnAction(onAction);
        return button;
    }

    /**
     * Creates and returns a button with the provided icon, no text, that performs the provided action when pressed.
     * @param icon The icon of the button
     * @param onAction What the button does when pressed
     * @return The button
     */
    private static Button createButton(Node icon, EventHandler<ActionEvent> onAction) {
        Button button = new Button("", icon);
        button.setOnAction(onAction);
        return button;
    }

    /**
     * Creates and returns a polygon containing a play icon.
     * @return The play icon
     */
    private static Node createPlayIcon() {
        double length = 10;
        return new Polygon(
                0.0, 0.0,
                length * Math.sin(Math.toRadians(60)), length / 2,
                0.0, length
        );
    }

    /**
     * Creates and returns a group containing rectangles that make up a pause icon.
     * @return The pause icon
     */
    private static Node createPauseIcon() {
        return new Group(new Rectangle(3.3, 10), new Rectangle(6.6, 0, 3.3, 10));
    }

    /**
     * Creates and returns a group containing a triangle and rectangle that make up a step icon.
     * @return The step icon
     */
    private static Node createStepIcon() {
        Rectangle rectangle = new Rectangle(3.3, 10);
        rectangle.setX(10);
        return new Group(createPlayIcon(), rectangle);
    }

    /**
     * Creates and returns a group containing arc and a triangle that make up a reset icon.
     * @return The reset icon
     */
    private static Node createResetIcon() {
        Arc outerArc = new Arc(5, 5, 6, 6, 0, -270);
        outerArc.setType(ArcType.ROUND);
        Arc innerArc = new Arc(5, 5, 4, 4, 0, -270);
        innerArc.setType(ArcType.ROUND);

        Shape reset = Shape.subtract(outerArc, innerArc);
        reset.setFill(Color.BLACK);
        reset.setLayoutX(3);
        reset.setLayoutY(3);

        double length = 7;
        Polygon triangle = new Polygon(
                0, 0,
                length * Math.sin(Math.toRadians(60)), length / 2,
                0, length
        );
        triangle.relocate(7, 0);

        return new Group(reset, triangle);
    }

    /**
     * Creates and returns a group containing a triangle and rectangle that make up a back icon.
     * @return The back icon
     */
    private static Node createBackIcon() {
        Group group = new Group();

        double length = 9;
        double sinLength = length * Math.sin(Math.toRadians(60));
        group.getChildren().add(new Polygon(
                0, 5,
                sinLength, 5 - length / 2,
                sinLength, 5 + length / 2
        ));
        group.getChildren().add(new Rectangle(sinLength, 3, 5, 4));

        return group;
    }
}
