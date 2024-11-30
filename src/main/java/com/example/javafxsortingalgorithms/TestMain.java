package com.example.javafxsortingalgorithms;

import com.example.javafxsortingalgorithms.betteralgorithms.Bounds;

public class TestMain {
    public static void main(String... args) {
        Bounds a = new Bounds(0, 3, true, true);
        Bounds b = new Bounds(5, 10, true, true);

        System.out.println(Bounds.intersect(a, b));
    }
}
