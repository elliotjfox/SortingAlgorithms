package com.example.javafxsortingalgorithms.algorithms;

import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettings;
import com.example.javafxsortingalgorithms.algorithms.algorithmsettings.AlgorithmSettingsCheckBox;
import com.example.javafxsortingalgorithms.algorithmupdates.CreateItemUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.ListUpdate;
import com.example.javafxsortingalgorithms.animation.AnimatedBinaryTree;
import com.example.javafxsortingalgorithms.animation.AnimatedDeck;
import com.example.javafxsortingalgorithms.animation.position.*;
import com.example.javafxsortingalgorithms.arraydisplay.DisplayMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PatienceSort extends SortingAlgorithm {

    private static final boolean DEFAULT_USE_BINARY_SEARCH_SETTING = true;
    private static final boolean DEFAULT_USE_HEAP_SETTING = true;

    private final boolean useBinarySearch;
//    private final boolean useHeap;

    private List<Integer> piles;
    private AnimatedDeck deck;
    private List<Integer> heap;
    private AnimatedBinaryTree tree;

    public PatienceSort(List<Integer> list, boolean useBinarySearch/*, boolean useHeap*/) {
        super(list);

        this.useBinarySearch = useBinarySearch;
//        this.useHeap = useHeap;
    }

    @Override
    protected void runAlgorithm() {
        piles = new ArrayList<>();
        piles.add(0);

        if (mode == DisplayMode.ANIMATED) {
            deck = new AnimatedDeck(list);
            currentChanges.add(new CreateItemUpdate(deck));
            animation.setItemPosition(deck,
                    new AnimationPosition(
                            new ElementScaledIndex(list.size() + 0.5),
                            new ExactHeight(0)
                    )
            );
        }

        // The first number is already in a pile
        if (deck != null) currentChanges.add(deck.dealTo(0));
        animation.addFrame();
        for (int i = 1; i < list.size(); i++) {
            if (useBinarySearch) {
                binarySearch(i);
            } else {
                linearSearch(i);
            }
        }

//        if (useHeap) {
//            heap = new ArrayList<>();
//            for (int i = 0; i < piles.size(); i++) {
//                heap.add(i);
//            }
//            for (int i = heap.size() / 2; i >= 0; i--) {
//                maxHeapify(i);
//            }
//        }
        for (int i = 0; i < list.size(); i++) {
            linearMin(i);
//            System.out.println("Heap:  " + heap);
//            System.out.println("Piles: " + heap.stream().map(piles::get).toList());
//            System.out.println("List:  " + heap.stream().map(piles::get).map(list::get).toList());
//            if (useHeap) {
//                heapMin(i);
//            } else {
//                linearMin(i);
//            }
        }
    }

    private void linearSearch(int i) {
        // Linear search for the first one larger than the current number (i)
        for (int j = 0; j < piles.size(); j++) {
            if (deck != null) {
                currentChanges.add(deck.readPile(j));
                currentChanges.add(deck.readDeck());
            }
            animation.readIndex(i);
            animation.readIndex(piles.get(j));
            trial.addRead(2);
            trial.addComparison();
            if (list.get(i) < list.get(piles.get(j))) {
                // We've found a pile, insert it and return
                animation.addFrame();
                move(i, piles.get(j) + 1);
                if (deck != null) currentChanges.add(deck.dealTo(j));
                incrementFollowing(piles, j);
                addFrame();
                return;
            }
            addFrame();
        }

        // Number didn't fit into any pile, so start a new pile
        piles.add(i);
        if (deck != null) currentChanges.add(deck.dealTo(piles.size() - 1));
        addFrame();
    }

    private void binarySearch(int i) {
        int pile = binarySearch(i, 0, piles.size() - 1);

        trial.addRead(2);
        trial.addComparison();
        if (list.get(piles.get(pile)) >= list.get(i)) {
            // We can add to the pile
            move(i, piles.get(pile) + 1);
            if (deck != null) currentChanges.add(deck.dealTo(pile));
            incrementFollowing(piles, pile);
        } else {
            // Otherwise, start a new pile
            piles.add(i);
            if (deck != null) currentChanges.add(deck.dealTo(piles.size() - 1));
        }
        addFrame();
    }

    // [left, right]
    private int binarySearch(int i, int left, int right) {
        if (left >= right) {
            if (deck != null) {
                currentChanges.add(deck.readDeck());
                currentChanges.add(deck.readPile(left));
            }
            addFrame();
            return right;
        }

        int mid = (left + right) / 2;
        if (deck != null) {
            currentChanges.add(deck.readDeck());
            currentChanges.add(deck.readPile(mid));
        }
        addFrame();
        trial.addRead(2);
        trial.addComparison();
        if (list.get(piles.get(mid)) >= list.get(i)) {
            return binarySearch(i, left, mid);
        } else {
            return binarySearch(i, mid + 1, right);
        }
    }

    private void linearMin(int target) {
        int smallest = 0;
        for (int j = 1; j < piles.size(); j++) {
            if (deck != null) {
                currentChanges.add(deck.readPile(j));
                currentChanges.add(deck.readPile(smallest));
                animation.readIndex(piles.get(j));
                animation.readIndex(piles.get(smallest));
            }
            trial.addRead(2);
            trial.addComparison();
            if (list.get(piles.get(j)) < list.get(piles.get(smallest))) {
                smallest = j;
            }
            addFrame();
        }
        move(piles.get(smallest), target);
        if (deck != null) currentChanges.add(deck.takeFromPile(smallest));
        addFrame();
        incrementPrevious(piles, smallest);
        removeDuplicates(piles, target);
    }

    private void heapMin(int target) {
        int smallestPile = heap.getFirst();
        move(piles.get(smallestPile), target);
        if (deck != null) currentChanges.add(deck.takeFromPile(smallestPile));
        addFrame();
        incrementPrevious(piles, smallestPile);
        removeDuplicates(piles, target);
        maxHeapify(0);
    }

    private void maxHeapify(int from) {
        if (from * 2 + 2 < heap.size()) {
//            if (tree != null) {
//                currentChanges.add(tree.readIndex(from));
//                animation.addFrame();
//                currentChanges.add(tree.readIndex(from));
//                currentChanges.add(tree.readIndex(from));
//            }

            if (
                    list.get(piles.get(heap.get(from))) >= list.get(piles.get(heap.get(from * 2 + 1))) ||
                    list.get(piles.get(heap.get(from))) >= list.get(piles.get(heap.get(from * 2 + 2)))
            ) {
                int smallestChild = list.get(piles.get(heap.get(from * 2 + 1))) < list.get(piles.get(heap.get(from * 2 + 2))) ? from * 2 + 1 : from * 2 + 2;
                ListUpdate.swap(heap, from, smallestChild);
                addFrame();
                maxHeapify(smallestChild);
            }
        } else if (from * 2 + 1 < heap.size()) {
            if (list.get(piles.get(heap.get(from))) > list.get(piles.get(heap.get(from * 2 + 1)))) {
                ListUpdate.swap(heap, from, from * 2 + 1);
                addFrame();
            }
        }
    }

    private void removeDuplicates(List<Integer> list, int limit) {
        for (int i = list.size() - 2; i >= 0; i--) {
            if (Objects.equals(list.get(i), list.get(i + 1)) || list.get(i) <= limit) {
                list.remove(i);
            }
        }
    }

    private void incrementFollowing(List<Integer> list, int index) {
        for (int i = index; i < list.size(); i++) {
            increment(list, i);
        }
    }

    private void incrementPrevious(List<Integer> list, int index) {
        for (int i = 0; i < index; i++) {
            increment(list, i);
        }
    }

    private void increment(List<Integer> list, int index) {
        list.set(index, list.get(index) + 1);
    }

    @Override
    public String getName() {
        return "Patience Sort";
    }

    public static AlgorithmSettings<PatienceSort> getSettings() {
        AlgorithmSettingsCheckBox useBinarySearchSetting = new AlgorithmSettingsCheckBox("Use Binary Search", DEFAULT_USE_BINARY_SEARCH_SETTING);
//        AlgorithmSettingsCheckBox useHeapSetting = new AlgorithmSettingsCheckBox("Use Heap", DEFAULT_USE_HEAP_SETTING);

        return new AlgorithmSettings<>(
                "Patience Sort",
                list -> new PatienceSort(list, useBinarySearchSetting.getValue()/*, useHeapSetting.getValue()*/),
                useBinarySearchSetting//, useHeapSetting
        );
    }
}
