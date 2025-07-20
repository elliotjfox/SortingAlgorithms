package com.example.javafxsortingalgorithms.algorithmupdates;

import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplayBase;

public class ReadUpdate implements DisplayUpdate {

    private final int index;
    private final int value;

    public ReadUpdate(int index, int value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public void performChange(ArrayDisplayBase display) {
        display.createReadAnimation(index, value);
    }
}
