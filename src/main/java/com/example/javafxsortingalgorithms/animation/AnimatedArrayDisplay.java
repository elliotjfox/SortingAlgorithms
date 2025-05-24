package com.example.javafxsortingalgorithms.animation;

import com.example.javafxsortingalgorithms.arraydisplay.ArrayDisplay;
import com.example.javafxsortingalgorithms.settings.SettingsPane;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.util.*;
import java.util.function.Function;

public class AnimatedArrayDisplay extends ArrayDisplay {

    public static final double ANIMATION_LENGTH = 400;
    public static final double ANIMATION_COOLDOWN = 75;

    private final List<AnimatedItem> items;
    private final ArrayList<AnimatedElement> elements;

//    private final AnimationGroupManager animationManager;

    // Animation controllers
    private final List<AnimationGroup> animationGroups;
    private AnimationGroup finalAnimationGroup;
    private boolean playingAnimations;
    private boolean elementsMoved;

    private final BorderPane borderPane;
    private final Pane centerPane;
    private final AnimatedInfo animatedInfo;

    public AnimatedArrayDisplay(SettingsPane settingsPane) {
        super(settingsPane);

        items = new ArrayList<>();
        elements = new ArrayList<>();

        animationGroups = new ArrayList<>();
        finalAnimationGroup = new AnimationGroup();
        playingAnimations = false;
        elementsMoved = false;

//        currentTask = new Label();
        animatedInfo = new AnimatedInfo();
        animatedInfo.updateInfo("Current task", "");


        borderPane = new BorderPane();
        centerPane = new Pane();

        Pane top = new Pane();
        top.setPrefHeight(settingsPane.getDisplaySettings().getBorderWidth());

        borderPane.setTop(top);
        borderPane.setCenter(centerPane);

        borderPane.setLeft(animatedInfo);
        animatedInfo.setViewOrder(-1);

        getChildren().add(borderPane);
    }

    @Override
    public void setList(List<Integer> list) {
        this.list = list;
        cleanUpItems();
        resetMax();
        bindHeight();
        if (!elements.isEmpty()) {
            centerPane.getChildren().removeAll(elements);
            elements.clear();
        }
        for (int i = 0; i < list.size(); i++) {
            AnimatedElement element = new AnimatedElement(this, i);
            element.setElementHeight(list.get(i) * getHeightMultiplier(), maxValue * getHeightMultiplier());
            elements.add(element);
            centerPane.getChildren().add(element);
        }
        centerPane.setPrefWidth(list.size() * getElementWidth());
    }

    @Override
    public void playFinish() {
        animatedInfo.finish();
        cleanUpItems();
    }

    public void addItem(AnimatedItem item) {
        centerPane.getChildren().add(item);
        items.add(item);
        if (item.hasPosition()) {
            item.goToPosition();
        }
    }

    public void addItem(AnimatedItem item, double x, double y) {
        centerPane.getChildren().add(item);
        items.add(item);
        item.setPosition(x, y);
    }

    public void addItem(AnimatedItem item, int index, double y) {
        centerPane.getChildren().add(item);
        items.add(item);
        item.setIndex(index, y);
    }

    public void removeItem(AnimatedItem item) {
        centerPane.getChildren().remove(item);
    }

    public void cleanUpItems() {
        centerPane.getChildren().removeAll(items);
        items.clear();
    }

    @Override
    public void initializeElements(int count) {
        System.out.println("asked to create elements " + count);
    }

    @Override
    public void update() {
        // TODO: Is this ever running? It shouldn't be
        System.out.println("Detailed is drawing...");
//        for (int i = 0; i < elements.size(); i++) {
//            elements.get(i).moveToIndex(i);
//        }
    }

    public void resetAnimations() {
        animationGroups.clear();
        newGroup();
        finalAnimationGroup = new AnimationGroup();
        playingAnimations = false;
        elementsMoved = false;
    }

    public void startAnimations() {
        playingAnimations = true;
    }

    public void addElementAnimations() {
        if (elementsMoved) {
            for (int i = 0; i < elements.size(); i++) {
                animateFinal(elements.get(i).moveToIndexTimeline(i, 0));
            }
        }
    }

    public void swap(int firstIndex, int secondIndex) {
        AnimatedElement tmp = elements.get(firstIndex);
        elements.set(firstIndex, elements.get(secondIndex));
        elements.set(secondIndex, tmp);
        elementsMoved = true;
    }

    public void move(int index, int targetIndex) {
        if (index < 0 || index >= elements.size() || targetIndex < 0 || targetIndex >= elements.size() || index == targetIndex) return;
        elements.add(targetIndex, elements.remove(index));
        elementsMoved = true;
    }

    public void newGroup() {
        animationGroups.add(new AnimationGroup());
    }

    private void addGroups(int upTo) {
        while (upTo >= animationGroups.size()) {
            newGroup();
        }
    }

    /**
     * Add the provided timelines to the current animation group.
     * @param timelines The timelines to add
     */
    public void animate(Timeline... timelines) {
        animate(animationGroups.size() - 1, timelines);
    }

    public void animate(int animationGroup, Timeline... timelines) {
        if (playingAnimations) {
            throw new IllegalStateException("Trying to add animations while playing animations!");
        }

        addGroups(animationGroup);

        animationGroups.get(animationGroup).addTimelines(timelines);
    }

    public void onPlay(Runnable... onPlayActions) {
        onPlay(animationGroups.size() - 1, onPlayActions);
    }

    public void onPlay(int animationGroup, Runnable... onPlayActions) {
        if (playingAnimations) {
            throw new IllegalStateException("Trying to add on play actions while playing animations!");
        }

        addGroups(animationGroup);

        animationGroups.get(animationGroup).addOnPlay(onPlayActions);
    }

    public void whenDone(Runnable... whenDoneActions) {
        whenDone(animationGroups.size() - 1, whenDoneActions);
    }

    public void whenDone(int animationGroup, Runnable... whenDoneActions) {
        if (playingAnimations) {
            throw new IllegalStateException("Trying to add when done actions while playing animations!");
        }

        addGroups(animationGroup);

        animationGroups.get(animationGroup).addWhenDone(whenDoneActions);
    }

    public void animateFinal(Timeline... timelines) {
        if (playingAnimations) {
            throw new IllegalStateException("Trying to add animations while playing animations!");
        }

        finalAnimationGroup.addTimelines(timelines);
    }

    public void onPlayFinal(Runnable... onPlayActions) {
        if (playingAnimations) {
            throw new IllegalStateException("Trying to add on play actions while playing animations!");
        }

        finalAnimationGroup.addOnPlay(onPlayActions);
    }

    public void whenDoneFinal(Runnable... whenDoneActions) {
        if (playingAnimations) {
            throw new IllegalStateException("Trying to add when done actions while playing animations!");
        }

        finalAnimationGroup.addWhenDone(whenDoneActions);
    }

    public AnimationGroup getNextAnimationGroup() {
        AnimationGroup current;

        do {
            if (animationGroups.isEmpty()) {
                if (finalAnimationGroup == null) {
                    // List is empty, and final animation group is null
                    return null;
                } else {
                    // Set current to the final element group, the while block will check for if it has animations
                    current = finalAnimationGroup;
                    finalAnimationGroup = null;
                }
            } else {
                current = animationGroups.removeFirst();
            }
        } while (current.isEmpty());

        return current;
    }

    public boolean hasAnimationsLeft() {
        for (AnimationGroup animationGroup : animationGroups) {
            if (!animationGroup.isEmpty()) {
                return true;
            }
        }

        if (finalAnimationGroup == null) return false;
        else return !finalAnimationGroup.isEmpty();
    }


    public Timeline highlightAnimation(Function<Integer, Boolean> condition) {
        List<KeyValue> keyValues = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            if (condition.apply(i)) {
                keyValues.add(new KeyValue(elements.get(i).colourProperty(), AnimatedElement.calculateColour(list.get(i), maxValue)));
            } else {
                keyValues.add(new KeyValue(elements.get(i).colourProperty(), Color.LIGHTGRAY));
            }
        }

        return new Timeline(
                new KeyFrame(
                        Duration.millis(ANIMATION_LENGTH),
                        "",
                        event -> {},
                        keyValues
                )
        );
    }

    public Timeline recolourTimeline() {
        return highlightAnimation(i -> true);
    }

    /**
     * Adds a read animation at the provided index to the current animation group.
     * @param index The index that is read
     */
    public void reading(int index) {
        animate(createReadAnimation(index, list.get(index)));
    }

    public void reading(int... indices) {
        for (int index : indices) {
            if (index < 0 || index >= list.size()) continue;
            animate(createReadAnimation(index, list.get(index)));
        }
    }

    /**
     * Add a read animation at the two provided indices to the current animation group.
     * @param firstIndex The first index
     * @param secondIndex The second index
     */
    public void comparing(int firstIndex, int secondIndex) {
        reading(firstIndex);
        reading(secondIndex);
    }

    public void setCurrentTask(String task) {
        animatedInfo.updateInfo("Current task", task);
    }

    public void updateInfoWhenDone(String key, Object value) {
        whenDone(() -> animatedInfo.updateInfo(key, value));
    }

    public void updateInfoOnPlay(String key, Object value) {
        onPlay(() -> animatedInfo.updateInfo(key, value));
    }

    public void updateInfo(String key, Object value) {
        animatedInfo.updateInfo(key, value);
    }

    /**
     * Creates and returns a read animation. The animation consists of a small arrow appearing and going up the top of the element
     * at the provided index and height. The timeline will be {@link #ANIMATION_LENGTH} + 1 milliseconds long. When the timeline starts,
     * it adds the arrow onto the center pane, then it rises at a constant speed. Then at the end, it removes the arrow from the
     * center pane.
     * @param index The index to do the read animation at
     * @param height The height of the element at the index when the animation should play
     * @return The timeline animation
     */
    public Timeline createReadAnimation(int index, double height) {
        Polygon arrow = PolygonWrapper.readArrow();
        arrow.setLayoutX(getX(settingsPane, index));
        arrow.setLayoutY(maxValue * getHeightMultiplier());
        return new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        event -> centerPane.getChildren().add(arrow)
                ),
                new KeyFrame(
                        // TODO: Fine tune this, because the tallest one will instantly disappear when it reaches the top
                        Duration.millis(height / maxValue * ANIMATION_LENGTH),
                        new KeyValue(arrow.layoutYProperty(), getHeightMultiplier() * (maxValue - height))
                ),
                new KeyFrame(
                        Duration.millis(ANIMATION_LENGTH + 1),
                        event -> centerPane.getChildren().remove(arrow)
                )
        );
    }

    public AnimatedInfo getDetailedInfo() {
        return animatedInfo;
    }

    public SettingsPane getSettings() {
        return settingsPane;
    }

    public static double getX(SettingsPane settingsPane, int index) {
        return settingsPane.getDisplaySettings().getElementWidth() * index;
    }
}
