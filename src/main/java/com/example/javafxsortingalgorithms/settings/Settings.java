package com.example.javafxsortingalgorithms.settings;

import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class Settings {

    public static int defaultNumberElements = 250;
    public static int defaultArrayBorder = 15;
    public static int defaultElementWidth = 4;
    public static int defaultHeight = 600;

    public static int defaultTestSize = 5000;

    private static final Random random = new Random();

    public static List<Integer> getReverseList(int size) {
        List<Integer> list  = new ArrayList<>();

        for (int i = size - 1; i >= 0; i--) {
            list.add(i);
        }

        return list;
    }

    public static List<Integer> getRandomUniformList(int size) {
        List<Integer> list = new ArrayList<>();

        // Very arbitrary value lol
        if (size >= 5000) {
            System.out.println("Warning: suspiciously large number for normal list size, returning size 5000");
            return getRandomUniformList(5000);
        }

        for (int i = 0; i < size; i++) {
            list.add(i);
        }

        Collections.shuffle(list, random);

        return list;
    }

    public static List<Integer> getRandomList(int size) {
        List<Integer> array = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            array.add(random.nextInt(size));
        }

        Collections.shuffle(array, random);

        return array;
    }

    public static List<Integer> getRangeList(int size, int min, int max) {
        List<Integer> array = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            int tmp = random.nextInt(max - min) + min;
//            System.out.println(tmp);
            array.add(tmp);
        }
        Collections.shuffle(array, random);
        return array;
    }

    // TODO: Fix this
    public static List<Integer> getApproxList(int size, Function<Integer, Integer> function, int initialRange) {
        List<Integer> list = new ArrayList<>();
        List<Integer> candidates = new ArrayList<>();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) arr[i] = i;

        for (int i = 0; i < size; i++) {
            int target = function.apply(i);
            for (int j = target - initialRange; j <= target + initialRange; j++) {
                addCandidate(arr, candidates, j);
            }

            int range = initialRange + 1;
            while (candidates.isEmpty()) {
                addCandidate(arr, candidates, target - range);
                addCandidate(arr, candidates, target + range);
                range++;
            }

            System.out.println("Selecting for i=" + i + ", out of " + candidates.size() + " candidates (" + candidates + ")");
            int selected = candidates.get(random.nextInt(candidates.size()));
            list.add(selected);
            arr[selected] = -1;
            candidates.clear();
        }

        return list;
    }

//    private static void addCandidates(int[] arr, List<Integer> candidates, int from, int distance) {
//        if (from - distance )
//    }

    private static void addCandidate(int[] arr, List<Integer> candidates, int x) {
        if (x >= 0 && x < arr.length && arr[x] != -1) {
            candidates.add(x);
        }
    }

    public static void getTestList(int size, Consumer<List<Integer>> whenDone) {
        List<Integer> array = new ArrayList<>();

        if (size >= 500000) {
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            ProgressBar progressBar = new ProgressBar();
            VBox dialogBox = new VBox(20, new Label("Creating array..."), progressBar);
            Scene scene = new Scene(dialogBox, 300, 200);
            dialog.setScene(scene);
            dialog.show();

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    updateProgress(0, size);
                    for (int i = 0; i < size; i++) {
                        array.add(i);
                    }

                    for (int i = size; i > 1; i--) {
                        array.set(i - 1, array.set(random.nextInt(i), array.get(i - 1)));
                        updateProgress(size - i, size);
                    }

                    return null;
                }
            };

            progressBar.progressProperty().bind(task.progressProperty());

            task.setOnSucceeded(event -> {
                dialog.close();
                whenDone.accept(array);
            });

            new Thread(task).start();
        } else {
            for (int i = 0; i < size; i++) {
                array.add(i);
            }

            Collections.shuffle(array, random);

            whenDone.accept(array);
        }
    }
}
