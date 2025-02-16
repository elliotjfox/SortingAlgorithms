package com.example.javafxsortingalgorithms;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.Arrays;
import java.util.List;

/**
 * Represents most of the buttons (not the algorithm selector or open settings button) in the button list.
 */
public class ButtonPane extends FlowPane {

    private final List<Node> normalPane;
    private final List<Node> testPane;
    private final List<Node> detailedPane;
    private final List<Node> comparingPane;

    public ButtonPane(AlgorithmDisplay algorithmDisplay) {
        setHgap(7);

        Button playButton = createButton(createPlayIcon(), event -> algorithmDisplay.play());
        Button stopButton = createButton(createPauseIcon(), event -> algorithmDisplay.stop());
        Button stepButton = createButton(createStepIcon(), event -> algorithmDisplay.step());
        Button resetButton = createButton(createResetIcon(), event -> algorithmDisplay.reset());
        Button reversedButton = createButton("Reversed", event -> algorithmDisplay.reversed());
        Button startTestButton = createButton("Start Test", event -> algorithmDisplay.startTest());
        Button openNormalButton = createButton(createBackIcon(), event -> algorithmDisplay.setMode(AlgorithmDisplay.Mode.NORMAL));
        Button openTestButton = createButton("Open Test", event -> algorithmDisplay.setMode(AlgorithmDisplay.Mode.TESTING));
        Button openDetailedButton = createButton("Open Detailed", event -> algorithmDisplay.setMode(AlgorithmDisplay.Mode.ANIMATED));
        Button openCompareButton = createButton("Open Compare", event -> algorithmDisplay.setMode(AlgorithmDisplay.Mode.COMPARING));

        normalPane = Arrays.asList(playButton, stopButton, stepButton, resetButton, reversedButton, openTestButton, openDetailedButton, openCompareButton);
        testPane = Arrays.asList(startTestButton, openNormalButton);
        detailedPane = Arrays.asList(playButton, stopButton, stepButton, resetButton, openNormalButton);
        comparingPane = Arrays.asList(playButton, stopButton, stepButton, resetButton, openNormalButton);

        getChildren().addAll(normalPane);
    }

    public void setMode(AlgorithmDisplay.Mode mode) {
        System.out.println("Clear");
        getChildren().clear();
        switch (mode) {
            case NORMAL -> getChildren().addAll(normalPane);
            case TESTING -> getChildren().addAll(testPane);
            case ANIMATED -> getChildren().addAll(detailedPane);
            case COMPARING -> getChildren().addAll(comparingPane);
        }
    }

    /**
     * Adds the algorithm selector and its corresponding settings button to the start of the button pane.
     * @param container The container to add components from
     */
    public void addSelector(AlgorithmContainer container) {
        getChildren().addFirst(container.getSettingsButton());
        getChildren().addFirst(container.getSelector());
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

    // TODO: Finish this or download one
//    private static Node createSettingsIcon() {
//        Group group = new Group();
//        Shape shape = Shape.subtract(new Circle(10, 10, 7), new Circle(10, 10, 2));
//        group.getChildren().add(shape);
//
//        group.getChildren().add(new Rectangle(10 - 2, 0, 4, 4));
//        group.getChildren().add(new Rectangle(10 - 2, 20 - 4, 4, 4));
//
//        for (int i = 30; i < 360; i += 60) {
//            if (i == 90 || i == 270) continue;
//            Rectangle rectangle = new Rectangle(4, 4);
//            double x = 10 + 7 * Math.cos(Math.toRadians(i)) + (Math.cos(Math.toRadians(i)) > 0 ? 0 : -0);
//            double y = 10 - 7 * Math.sin(Math.toRadians(i)) + (Math.sin(Math.toRadians(i)) > 0 ? -0 : 0);
//            rectangle.relocate(x, y);
//            rectangle.setRotate(90 - i);
//            group.getChildren().add(rectangle);
//        }
//        return group;
//    }
}
