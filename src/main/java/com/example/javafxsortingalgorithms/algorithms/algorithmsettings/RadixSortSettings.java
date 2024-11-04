package com.example.javafxsortingalgorithms.algorithms.algorithmsettings;

import com.example.javafxsortingalgorithms.algorithms.RadixLSDSort;
import com.example.javafxsortingalgorithms.algorithms.SortingAlgorithm;
import com.example.javafxsortingalgorithms.settings.IntegerInputBox;
import javafx.scene.control.CheckBox;

import java.util.List;

public class RadixSortSettings extends AlgorithmSettings {

    private RadixLSDSort radixSort;

    private int base;
    private boolean inPlace = false;

    private final IntegerInputBox baseInputBox;
    private final CheckBox inPlaceCheckBox;

    private final String ALGORITHM_DESCRIPTION = """
            Radix sort works by sorting the numbers first by the last digit, then the second last, and so on. This works because radix sort keeps the order the numbers were in. After the first pass through the array, all the numbers that end in 0 will be at the start, followed by the numbers that end in 1, and so on. After the second pass, it will move all the number with 0 in the tens spot to the front, preserving their order. This means that the first set of digits will end in 00, then 01, then 02, etc. We repeat this process until the array is sorted. The example used base 10, but any base can be used.\s
            The time complexity of radix sort depends on how the biggest number, r, scales with the size of the array. In this program, r scales with n, O(n), meaning the overall time complexity is O(nlogn). If r is constant, the time complexity drops to O(n).
            The space complexity depends on if you choose in-place or not. In-place would write the number directly back to the array as we figure out where they go, while not in-place copies them to an auxiliary array, the same size as the original array. In-place: O(1), not in-place: O(n)
            """;

    public RadixSortSettings() {
        super("Radix Sort");

        baseInputBox = new IntegerInputBox(() -> base, (i) -> base = i);
        addSetting("Base", baseInputBox);

        inPlaceCheckBox = new CheckBox();
        inPlaceCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> inPlace = newValue);
        addSetting("In place?", inPlaceCheckBox);
        addSetting(buildResetButton());

    }

    @Override
    public void resetSettings() {
        super.resetSettings();

        base = 10;
        inPlace = true;

        if (baseInputBox != null) baseInputBox.updateValue();
        if (inPlaceCheckBox != null) inPlaceCheckBox.setSelected(inPlace);
    }

    @Override
    public SortingAlgorithm createAlgorithm(List<Integer> array) {
        return radixSort = new RadixLSDSort(array, false, base, inPlace);
    }

    @Override
    public SortingAlgorithm createInstantAlgorithm(List<Integer> array) {
        return radixSort = new RadixLSDSort(array, true, base, inPlace);
    }

    @Override
    public SortingAlgorithm getAlgorithm() {
        return radixSort;
    }

    @Override
    public void resetAlgorithm() {
        radixSort = null;
    }
}
