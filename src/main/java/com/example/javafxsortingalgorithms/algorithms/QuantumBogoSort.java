package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.Main;
import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class QuantumBogoSort extends ActionSortingAlgorithm {

    public QuantumBogoSort(List<Integer> arrayList, boolean isInstant) {
        super(arrayList, isInstant);

        setInitialActions(new Randomize());
    }

    @Override
    protected void runAlgorithm(ArrayDisplay display) {
        AlgorithmAction currentAction;
        do {
            if (actions.isEmpty()) {
                isDone = true;
                if (!isListSorted(list)) {
                    startUniverseDestruction();
                    return;
                }
            }
            currentAction = actions.pop();
            currentAction.execute(this, display);
            catchUpActions();
        } while (!currentAction.takesStep);
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {

    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public String getName() {
        return null;
    }

    private static void startUniverseDestruction() {
        VBox vBox = new VBox(5);
        Label label = new Label("Quantum Bogo Sort failed to sort list!\nDestroying universe in");
        label.setTextFill(Color.RED);
        label.setTextAlignment(TextAlignment.CENTER);
        Label countdown = new Label("10");
        countdown.setTextFill(Color.RED);
        countdown.setFont(new Font(20));
        countdown.setTextAlignment(TextAlignment.CENTER);
        vBox.getChildren().addAll(new StackPane(label), new StackPane(countdown));

        Timeline timeline = new Timeline();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(i),
                    event -> {
                        countdown.setText(Integer.toString(10 - finalI));
                    }
            ));
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(vBox, 400, 400));

        timeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(10),
                event -> {
                    Main.close();
                    stage.close();
                }
        ));

        stage.show();

        timeline.play();
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
