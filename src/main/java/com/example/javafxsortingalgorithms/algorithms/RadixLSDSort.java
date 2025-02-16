package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.*;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;

import java.util.ArrayList;
import java.util.List;

/**
 * <h3>Radix Sort (LSD)</h3>
 * First goes through the array, copying everything to an auxiliary array. As it does so,
 * we count how many elements end in each digit. We could use any base to do this. After
 * we have done that, we know calculate where each section of numbers goes. Then we go through
 * the auxiliary array and put it in the correct section, incrementing the indices of the section
 * we add it too. Then we do it again, but instead sort by the next digit. Since this is a stable
 * sorting algorithm, after the first sort, all the ones that end in zero are at the start, then one, then two, etc.
 * After the second, all the ones that end in 00 are at the start, then 01, 02, etc.
 * <br> <br>
 * <h4>Time Complexity</h4>
 * The time complexity of Radix Sort depends on the biggest digit, r. <br>
 * So it would be nlogr, or for the different scaling of r:
 * <table>
 *     <tr>
 *         <td>R</td> <td>TC</td>
 *     </tr>
 *     <tr>
 *         <td>O(1)</td> <td>O(n)</td>
 *     </tr>
 *     <tr>
 *         <td>O(n)</td> <td>O(nlogn)</td>
 *     </tr>
 *      <tr>
 *         <td>O(n<sup>k</sup>)</td> <td>O(nlogn)</td>
 *     </tr>
 *      <tr>
 *         <td>O(k<sup>n</sup>)</td> <td>O(n<sup>2</sup>)</td>
 *      </tr>
 * </table>
 * <h4>Space Complexity</h4>
 * n
 */
// TODO: Compact some code into methods
public class RadixLSDSort extends SortingAlgorithm {

    private final String ALGORITHM_DESCRIPTION = """
            Radix sort works by sorting the numbers first by the last digit, then the second last, and so on. This works because radix sort keeps the order the numbers were in. After the first pass through the array, all the numbers that end in 0 will be at the start, followed by the numbers that end in 1, and so on. After the second pass, it will move all the number with 0 in the tens spot to the front, preserving their order. This means that the first set of digits will end in 00, then 01, then 02, etc. We repeat this process until the array is sorted. The example used base 10, but any base can be used.\s
            The time complexity of radix sort depends on how the biggest number, r, scales with the size of the array. In this program, r scales with n, O(n), meaning the overall time complexity is O(nlogn). If r is constant, the time complexity drops to O(n).
            The space complexity depends on if you choose in-place or not. In-place would write the number directly back to the array as we figure out where they go, while not in-place copies them to an auxiliary array, the same size as the original array. In-place: O(1), not in-place: O(n)
            """;

    private enum SortState {
        COPYING_TO_AUX,
        COPYING_TO_MAIN,
        IN_PLACE
    }

    private final int base;
    private SortState state;
    private int digit;
    private ArrayList<Integer> auxiliaryArray;
    private int counter;
    private ArrayList<Integer> digitCount;
    private final ArrayList<Integer> indices;
//    private ArrayManager auxArrayManager;

    public RadixLSDSort(List<Integer> arrayList, boolean isInstant, int base, boolean inPlace) {
        super(arrayList, isInstant);

        this.base = base;
        digit = 0;
        counter = 0;

        indices = new ArrayList<>();

        if (inPlace) {
            state = SortState.IN_PLACE;
            for (int i = 0; i < base; i++) indices.add(0);
        } else {
            state = SortState.COPYING_TO_AUX;
            auxiliaryArray = new ArrayList<>();
            digitCount = new ArrayList<>();
            for (int i = 0; i < base; i++) {
                digitCount.add(0);
                indices.add(0);
            }
        }
    }

    @Override
    protected void runAlgorithm(ArrayDisplay display) {

        switch (state) {
            case COPYING_TO_AUX -> {
                // Add to auxiliaryArray
                auxiliaryArray.add(list.get(counter));

                display.readIndex(counter);

                // Get the current digit of the number we are currently copying
                int currentDigit = getDigit(list.get(counter), digit);

                // Increase the count of that digit
                increment(digitCount, currentDigit);
                counter++;

                // If we are at the end
                if (counter >= list.size()) {
                    // Count where each section of numbers should start
                    indices.set(0, 0);
                    for (int i = 1; i < digitCount.size(); i++) {
                        indices.set(i, indices.get(i - 1) + digitCount.get(i - 1));
                    }
                    state = SortState.COPYING_TO_MAIN;
                    counter = 0;
                }
            }
            case COPYING_TO_MAIN -> {
                // Get the right digit of the number we are currently copying back to the main array
                int currentDigit = getDigit(auxiliaryArray.get(counter), digit);

                // Copy the current number to the correct place, using the indices array, then increment the place
                list.set(indices.get(currentDigit), auxiliaryArray.get(counter));
                display.writeIndex(indices.get(currentDigit));

                increment(indices, currentDigit);
                counter++;

                if (counter >= list.size()) {
                    // Reset things to sort by the next digit
                    counter = 0;
                    digit++;
                    auxiliaryArray.clear();
                    for (int i = 0; i < base; i++) {
                        digitCount.set(i, 0);
                        indices.set(i, 0);
                    }
                    state = SortState.COPYING_TO_AUX;
                    if (isListSorted(list)) {
                        isDone = true;
                    }
                }
            }
            case IN_PLACE -> {
                if (counter >= list.size()) {
                    counter = 0;
                    digit++;
                    for (int i = 0; i < base; i++) {
                        indices.set(i, 0);
                    }
                    if (isListSorted(list)) {
                        isDone = true;
                    }
                }

                int currentDigit = getDigit(list.get(counter), digit);
                move(counter, indices.get(currentDigit));

                display.writeIndex(indices.get(currentDigit));
                display.readIndex(counter);

                incrementFollowing(indices, currentDigit);
                counter++;
            }
        }
    }

    @Override
    protected void instantAlgorithm(TestEntry entry) {
//        radixSort();
        if (state == SortState.IN_PLACE) {
            int instantDigit = 0;
            // Setting up progress keeping ability
            int maxDigit = list.getFirst();
            for (Integer i : list) if (i > maxDigit) maxDigit = i;
            int numPasses = (int) (Math.log10(maxDigit) / Math.log10(base) + 0.5) + 1;
            System.out.println("num passes for " + maxDigit + " base " + base + " is: " + numPasses);

            ArrayList<Integer> instantIndices = new ArrayList<>();
            for (int i = 0; i < base; i++) instantIndices.add(0);
            while (Math.pow(base, instantDigit) < maxDigit) {
//            System.out.println("Going through " + instantDigit);
                for (int i = 0; i < list.size(); i++) {
                    entry.addRead();
                    int currentDigit = getDigit(list.get(i), instantDigit);
                    entry.addWrite();
                    move(i, instantIndices.get(currentDigit));
                    incrementFollowing(instantIndices, currentDigit);
                    // TODO: This doesn't work properly
//                    entry.updateProgress((double) instantDigit / numPasses + (double) i / list.size() / numPasses);
                }
                instantDigit++;
                for (int i = 0; i < base; i++) instantIndices.set(i, 0);
            }
        } else {
//            int instantDigit = 0;
//            ArrayList<Integer> tmp;
//            ArrayList<Integer> instantIndices = new ArrayList<>();
//            ArrayList<Integer> instantDigitCount = new ArrayList<>();
//            for (int i = 0; i < base; i++) {
//                instantIndices.add(0);
//                instantDigitCount.add(0);
//            }
//            while (!isSorted()) {
//                tmp = new ArrayList<>(arrayList);
//                for (int i = 0; i < tmp.size(); i++) {
//                    int digit = getDigit(arrayList.get(i), instantDigit);
//                    increment(instantDigitCount, digit);
//                }
//                for (int i = 1; i < instantIndices.size(); i++) {
//                    instantIndices.set(i, instantIndices.get(i - 1) + instantDigitCount.get(i - 1));
//                }
//                for (int i = 0; i < tmp.size(); i++) {
//                    int currentDigit = getDigit(auxiliaryArray.get(i), digit);
//
//                    arrayList.set(indices.get(currentDigit), auxiliaryArray.get(i));
//                    colourBar(indices.get(getDigit(auxiliaryArray.get(i), digit)), ColourAction.WRITE);
//
//                    increment(indices, currentDigit);
//                }
//                digit++;
//                auxiliaryArray.clear();
//                for (int i = 0; i < base; i++) {
//                    digitCount.set(i, 0);
//                    indices.set(i, 0);
//                }
//            }
        }
    }


    private void incrementFollowing(ArrayList<Integer> arrayList, int startingIndex) {
        for (int i = startingIndex; i < arrayList.size(); i++) {
            increment(arrayList, i);
        }
    }

    private void increment(ArrayList<Integer> array, int index) {
        array.set(index, array.get(index) + 1);
    }

    private int getDigit(int num, int digit) {
        return (int) (num / Math.pow(base, digit) % base);
    }

    @Override
    public String getName() {
        if (state == SortState.IN_PLACE) {
            return "In-Place Radix Sort\nBase: " + base;
        }
        return "Radix Sort\nBase: " + base;
    }

    public static AlgorithmSettings<RadixLSDSort> getSettings() {
        AlgorithnSettingsCheckBox inPlaceSetting = new AlgorithnSettingsCheckBox("In Place", true);
        AlgorithmSettingsInputBox<Integer> baseSetting = new AlgorithmSettingsInputBox<>(
                "Base", 10,
                Integer::parseInt, i -> i > 1
        );

        return new AlgorithmSettings<>(
                "Radix Sort",
                (l, b) -> new RadixLSDSort(l, b, baseSetting.getValue(), inPlaceSetting.getValue()),
                baseSetting,
                inPlaceSetting
        );
    }

//    private void radixSort() {
//        int currentDigit = 0;
//
//        int maxDigit = list.getFirst();
//        for (Integer i : list) if (i > maxDigit) maxDigit = i;
//
//        int[] indices = new int[base];
//
//        while (Math.pow(base, currentDigit) < maxDigit) {
//            for (int i = 0; i < list.size(); i++) {
//                int digit = getDigit(list.get(i), currentDigit);
//                move(i, indices[digit]);
//
//                // Increment indices from digit onward
//                for (int j = digit; j < base; j++) indices[j]++;
//            }
//            currentDigit++;
//            // Reset indices
//            indices = new int[base];
//        }
//    }

}

