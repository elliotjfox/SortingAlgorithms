package com.example.javafxsortingalgorithms.algorithmupdates;

import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplayBase;

public class FinishUpdate implements DisplayUpdate {
    @Override
    public void performChange(ArrayDisplayBase display) {
        System.out.println("Done!");
    }
}
