package com.example.javafxsortingalgorithms.settings;

import com.example.javafxsortingalgorithms.algorithms.*;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.*;
import com.example.javafxsortingalgorithms.betteralgorithms.BetterBubbleSort;
import com.example.javafxsortingalgorithms.betteralgorithms.BetterQuickSort;
import com.example.javafxsortingalgorithms.betteralgorithms.BetterSelectionSort;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;
import java.util.function.Consumer;

public class Settings {

    public static int defaultNumberElements = 250;
    public static int defaultArrayBorder = 15;
    public static int defaultElementWidth = 4;
    public static int defaultHeight = 600;

    public static int defaultTestSize = 5000;

    private static final Random random = new Random();

    public static AlgorithmSettings createAlgorithmSettings(String algorithmName) {
        return switch (algorithmName) {
            case "Bitonic":     yield new BitonicSettings();
            case "Bogo":        yield new GenericAlgorithmSettings<>("Bogo Sort", BogoSort::new);
            case "Bubble":      yield new GenericAlgorithmSettings<>("Bubble Sort", BubbleSort::new);
            case "Cartesian":   yield new GenericAlgorithmSettings<>("Cartesian Tree Sort", CartesianTreeSort::new);
            case "Cocktail":    yield new GenericAlgorithmSettings<>("Cocktail Shaker Sort", CocktailShakerSort::new);
            case "Comb":        yield new CombSortSettings();
            case "Exchange":    yield new ExchangeSortSettings();
            case "Gnome":       yield new GenericAlgorithmSettings<>("Gnome Sort", GnomeSort::new);
            case "Gravity":     yield new GenericAlgorithmSettings<>("Gravity Sort", GravitySort::new);
            case "Heap":        yield new GenericAlgorithmSettings<>("Heap Sort", HeapSort::new);
            case "Insertion":   yield new InsertionSortSettings();
            case "Merge":       yield new MergeSortSettings();
            case "OddEven":     yield new GenericAlgorithmSettings<>("Odd-Even Sort", OddEvenSort::new);
            case "QuantumBogo": yield new GenericAlgorithmSettings<>("Quantum Bogo Sort", QuantumBogoSort::new);
            case "Quick":       yield new QuickSortSettings();
            case "Radix":       yield new RadixSortSettings();
            case "Selection":   yield new SelectionSortSettings();
            case "Sleep":       yield new GenericAlgorithmSettings<>("Sleep Sort", SleepSort::new);
            case "Shell":       yield new ShellSortSettings();
            case "Stooge":      yield new GenericAlgorithmSettings<>("Stooge Sort", StoogeSort::new);
            case "Strand":      yield new GenericAlgorithmSettings<>("Strand Sort", StrandSort::new);
            case "Selection2":  yield new GenericAlgorithmSettings<>("Selection2", BetterSelectionSort::new);
            case "BubbleSpace":  yield new GenericAlgorithmSettings<>("Bubble Space", BetterBubbleSort::new);
            case "QuickSpace":  yield new GenericAlgorithmSettings<>("Quick Sort Space", BetterQuickSort::new);
            case "Cycle":       yield new GenericAlgorithmSettings<>("Strand Sort", CycleSort::new);
            default:            yield new GenericAlgorithmSettings<>("Unknown Algorithm", GnomeSort::new);
        };
    }

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
