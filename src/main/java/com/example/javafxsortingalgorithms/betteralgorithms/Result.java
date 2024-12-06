package com.example.javafxsortingalgorithms.betteralgorithms;

public sealed interface Result {

    record LessThan() implements Result {}

    record GreaterThan() implements Result {}

    record Equal() implements Result {}

    record OutsideArray() implements Result {}
}
