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
    private boolean needsToMoveElements;
    private final List<AnimationGroup> animationGroups;
    private final AnimationGroup elementAnimationGroup;

    private final BorderPane borderPane;
    private final Pane centerPane;
    private final AnimatedInfo animatedInfo;

    private AnimationGroup currentAnimationGroup;


    public AnimatedArrayDisplay(SettingsPane settingsPane) {
        super(settingsPane);

        items = new ArrayList<>();
        elements = new ArrayList<>();
        needsToMoveElements = false;
        animationGroups = new ArrayList<>();
        elementAnimationGroup = new AnimationGroup();
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

    public void cleanUpItems() {
        centerPane.getChildren().removeAll(items);
        items.clear();
    }

    public void removeItem(AnimatedItem item) {
        centerPane.getChildren().remove(item);
    }

    @Override
    public void initializeElements(int count) {
        System.out.println("asked to create elements " + count);
    }

    @Override
    public void update() {
        System.out.println("Detailed is drawing...");
        for (int i = 0; i < elements.size(); i++) {
            elements.get(i).moveToIndex(i);
        }
        needsToMoveElements = false;
    }

    public void playAnimations() {
        if (animationGroups.isEmpty()) {
            return;
        }
        animationGroups.removeFirst().play();
    }

    public void playFinalAnimations() {
        elementAnimationGroup.play();
        for (int i = 0; i < elements.size(); i++) {
            elements.get(i).moveToIndex(i);
        }
        elementAnimationGroup.clear();
        needsToMoveElements = false;
    }

    public void swap(int firstIndex, int secondIndex) {
        AnimatedElement tmp = elements.get(firstIndex);
        elements.set(firstIndex, elements.get(secondIndex));
        elements.set(secondIndex, tmp);
        needsToMoveElements = true;
    }

    public void move(int index, int targetIndex) {
        if (index < 0 || index >= elements.size() || targetIndex < 0 || targetIndex >= elements.size() || index == targetIndex) return;
        elements.add(targetIndex, elements.remove(index));
        needsToMoveElements = true;
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
        currentAnimationGroup.addTimelines(createReadAnimation(index, list.get(index)));
    }

    public void reading(int... indices) {
        for (int index : indices) {
            if (index < 0 || index >= list.size()) continue;
            currentAnimationGroup.addTimelines(createReadAnimation(index, list.get(index)));
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

    /**
     * Add the provided timelines to the current animation group. Will check the length of each timeline, and print if
     * it's too long, but still add it.
     * @param timelines The timelines to add
     */
    public void animate(Timeline... timelines) {
        for (Timeline timeline : timelines) {
            // Accounting for the read animations being 1 more
            double timelineLength = timeline.getKeyFrames().getLast().getTime().toMillis();
            if (timelineLength - 1 > ANIMATION_LENGTH) {
                System.out.println("Timeline is longer than animation length! (" + timelineLength + " > " + ANIMATION_LENGTH + ")");
            }
        }
        currentAnimationGroup.addTimelines(timelines);
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

    public void onPlay(Runnable runnable) {
        currentAnimationGroup.addOnPlay(runnable);
    }

    public void whenDone(Runnable runnable) {
        currentAnimationGroup.addWhenDone(runnable);
    }

    public void newGroup() {
        currentAnimationGroup = new AnimationGroup();
        animationGroups.add(currentAnimationGroup);
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
        Polygon arrow = createReadArrow();
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

    public boolean hasAnimations() {
        return !animationGroups.isEmpty();
    }

    public boolean needsToMoveElements() {
        return needsToMoveElements || elementAnimationGroup.hasAnimations();
    }

    public AnimationGroup getElementAnimationGroup() {
        return elementAnimationGroup;
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

    public static Polygon createReadArrow() {
        double length = 15;
        return new Polygon(
                0.0, 0.0,
                -length, length / 2,
                -length + length / 4, 0.0,
                -length, -length / 2
        );
    }
}
