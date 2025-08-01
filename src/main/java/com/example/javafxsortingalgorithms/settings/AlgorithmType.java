package com.example.javafxsortingalgorithms.settings;

import com.example.javafxsortingalgorithms.algorithms.*;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;

public enum AlgorithmType {
    BITONIC("Bitonic Sort", BitonicSort.getSettings()),
    BOGO("Bogo Sort", new AlgorithmSettings<>("Bogo Sort", BogoSort::new)),
    BUBBLE("Bubble Sort", new AlgorithmSettings<>("Bubble Sort", BubbleSort::new)),
    CARTESIAN("Cartesian Tree Sort", new AlgorithmSettings<>("Cartesian Tree Sort", CartesianTreeSort::new)),
    COCKTAIL("Cocktail Shaker Sort", new AlgorithmSettings<>("Cocktail Shaker Sort", CocktailShakerSort::new)),
    COMB("Comb Sort", CombSort.getSettings()),
    CYCLE("Cycle Sort", new AlgorithmSettings<>("Strand Sort", CycleSort::new)),
    EXCHANGE("Exchange Sort", ExchangeSort.getSettings()),
    GNOME("Gnome Sort", new AlgorithmSettings<>("Gnome Sort", GnomeSort::new)),
    GRAVITY("Gravity Sort", new AlgorithmSettings<>("Gravity Sort", GravitySort::new)),
    HEAP("Heap Sort", new AlgorithmSettings<>("Heap Sort", HeapSort::new)),
    INSERTION("Insertion Sort", InsertionSort.getSettings()),
    MERGE("Merge Sort", MergeSort.getSettings()),
    ODD_EVEN("Odd Even Sort", new AlgorithmSettings<>("Odd-Even Sort", OddEvenSort::new)),
    ODD_EVEN_MERGE("Odd Even Merge Sort", new AlgorithmSettings<>("Odd-Even Merge Sort", OddEvenMergeSort::new)),
    PANCAKE("Pancake Sort", PancakeSort.getSettings()),
    QUANTUM_BOGO("Quantum Bogo Sort", new AlgorithmSettings<>("Quantum Bogo Sort", QuantumBogoSort::new)),
    QUICK("Quick Sort", QuickSort.getSettings()),
    RADIX("Radix Sort", RadixLSDSort.getSettings()),
    SELECTION("Selection Sort", SelectionSort.getSettings()),
    SHELL("Shell Sort", ShellSort.getSettings()),
    SLEEP("Sleep Sort", new AlgorithmSettings<>("Sleep Sort", SleepSort::new)),
    STOOGE("Stooge Sort", new AlgorithmSettings<>("Stooge Sort", StoogeSort::new)),
    STRAND("Strand Sort", new AlgorithmSettings<>("Strand Sort", StrandSort::new)),
    TIM("Tim Sort", TimSort.getSettings());

    private final String displayName;
    private final AlgorithmSettings<?> settings;

    AlgorithmType(String displayName, AlgorithmSettings<?> settings) {
        this.displayName = displayName;
        this.settings = settings;
    }

    public String getDisplayName() {
        return displayName;
    }

    public AlgorithmSettings<?> getSettings() {
        return settings;
    }
}

