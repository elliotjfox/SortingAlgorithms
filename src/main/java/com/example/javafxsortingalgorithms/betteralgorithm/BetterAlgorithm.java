package com.example.javafxsortingalgorithms.betteralgorithm;

import com.example.javafxsortingalgorithms.TestEntry;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDetailedDisplay;
import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;

import java.util.List;

public abstract class BetterAlgorithm {

    public enum Mode {
        NORMAL,
        ANIMATED,
        INSTANT
    }

    protected List<Integer> list;

    protected Mode mode;
    protected Wrapper wrapper;

    public BetterAlgorithm() {

    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public void setWrapper(Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    public abstract void initializeNormal();

    public abstract void initializeAnimated();

    public abstract void initializeInstant();

    public abstract void stepNormal();

    public abstract void stepAnimated();

    public abstract void doInstant();

    protected void swap(int i1, int i2) {
        list.set(i1, list.set(i2, list.get(i1)));
        wrapper.swap(i1, i2);
    }

    protected boolean compare(int i, int to) {

        return list.get(i) > list.get(to);
    }

    public static class Wrapper {
        ArrayDetailedDisplay detailedDisplay;
        ArrayDisplay normalDisplay;
        TestEntry testEntry;

        public Wrapper(ArrayDetailedDisplay display) {
            this.detailedDisplay = display;
        }

        public Wrapper(ArrayDisplay display) {
            this.normalDisplay = display;
        }

        public Wrapper(TestEntry testEntry) {
            this.testEntry = testEntry;
        }

        public Wrapper() {}

        public void swap(int i1, int i2) {
            if (testEntry != null) {
                testEntry.addWrite(2);
            } else if (detailedDisplay != null) {
                detailedDisplay.swap(i1, i2);
            } else if (normalDisplay != null) {
                normalDisplay.writeIndex(i1);
                normalDisplay.writeIndex(i2);
            }
        }
    }
}
