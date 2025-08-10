package com.example.javafxsortingalgorithms.animation;

import com.example.javafxsortingalgorithms.AlgorithmController;
import com.example.javafxsortingalgorithms.algorithmupdates.AnimationUpdate;
import com.example.javafxsortingalgorithms.algorithmupdates.GenerateAnimationUpdate;
import com.example.javafxsortingalgorithms.arraydisplay.DisplaySettings;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AnimatedDeck extends AnimatedItem {

    private static final double CARD_DIMENSION_RATIO = 1.618;
    private static final double CARD_PADDING_FRACTION = 0.25;

    private final List<AnimatedCard> initialCards;
    private final List<AnimatedCard> cards;
    private final List<List<AnimatedCard>> dealtCards;

    private final int upperViewOrder;

    public AnimatedDeck(List<Integer> list) {
        initialCards = new ArrayList<>();
        for (Integer i : list) {
            initialCards.add(new AnimatedCard(i));
        }
        cards = new ArrayList<>(initialCards);
        dealtCards = new ArrayList<>();

        upperViewOrder = -list.size();
    }

    @Override
    public void generateVisuals(DisplaySettings settings) {
        getChildren().clear();

        for (int i = 0; i < initialCards.size(); i++) {
            AnimatedCard card = initialCards.get(initialCards.size() - i - 1);
            getChildren().add(card);
            card.setViewOrder(0);
            card.generateVisuals(settings);
            card.setLayoutX(0);
            card.setLayoutY(i * CARD_PADDING_FRACTION * settings.elementWidth());
        }
    }

    public GenerateAnimationUpdate dealTo(int pile) {
        while (dealtCards.size() < pile + 1) {
            dealtCards.add(new ArrayList<>());
        }

        AnimatedCard card = cards.removeFirst();
        int depth = dealtCards.get(pile).size();
        dealtCards.get(pile).add(card);

        return moveCardToPosition(card, pile + 1, depth, upperViewOrder, -depth);
    }

    public GenerateAnimationUpdate takeFromPile(int pile) {
        List<AnimatedCard> cardPile = getPile(pile);
        if (cardPile == null) return null;

        AnimatedCard card = cardPile.removeLast();
        if (cardPile.isEmpty()) {
            dealtCards.set(getCorrectedPileIndex(pile), null);
        }

        int depth = cards.size();
        cards.add(card);

        return moveCardToPosition(card, 0, depth, upperViewOrder, -depth);
    }

    public GenerateAnimationUpdate readPile(int pile) {
        int readPileId = getCorrectedPileIndex(pile);
        return readCardPosition(readPileId + 1, dealtCards.get(readPileId).size() - 1);
    }

    public GenerateAnimationUpdate readDeck() {
        return readCardPosition(0, cards.size() - 1);
    }

    private GenerateAnimationUpdate moveCardToPosition(AnimatedCard card, int pile, int depth, int viewOrder, int finalViewOrder) {
        return new GenerateAnimationUpdate(
                settings -> {
                    card.setViewOrder(viewOrder);
                    return new Timeline(new KeyFrame(
                            Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                            _ -> card.setViewOrder(finalViewOrder),
                            new KeyValue(card.layoutXProperty(), getPileX(pile, settings)),
                            new KeyValue(card.layoutYProperty(), getDepthY(depth, settings))
                    ));
                },
                _ -> {}
        );
    }

    private GenerateAnimationUpdate readCardPosition(int pile, int depth) {
        AnimatedReadArrow readArrow = new AnimatedReadArrow();

        return new GenerateAnimationUpdate(
                settings -> {
                    getChildren().add(readArrow);
                    readArrow.generateVisuals(settings);
                    readArrow.setLayoutX(getPileX(pile, settings));
                    // Start arrow at the bottom left
                    readArrow.setLayoutY(getDepthY(depth, settings) + settings.elementWidth() * CARD_DIMENSION_RATIO);
                    readArrow.setViewOrder(upperViewOrder);
                    return new Timeline(new KeyFrame(
                            Duration.millis(AlgorithmController.ANIMATION_LENGTH),
                            _ -> getChildren().remove(readArrow),
                            new KeyValue(readArrow.layoutYProperty(), getDepthY(depth, settings))
                    ));
                },
                _ -> {}
        );
    }

    private List<AnimatedCard> getPile(int pile) {
        int index = getCorrectedPileIndex(pile);
        if (index == -1) return null;
        return dealtCards.get(index);
    }

    private int getCorrectedPileIndex(int pile) {
        int count = 0;
        for (int i = 0; i < dealtCards.size(); i++) {
            if (dealtCards.get(i) == null) continue;
            if (count == pile) return i;
            count++;
        }
        return -1;
    }

    private double getPileX(int pile, DisplaySettings settings) {
        return pile * (1 + CARD_PADDING_FRACTION) * settings.elementWidth();
    }

    private double getDepthY(int depth, DisplaySettings settings) {
        return depth * CARD_PADDING_FRACTION * settings.elementWidth();
    }

    private double getTotalHeight(DisplaySettings settings) {
        return getDepthY(-upperViewOrder, settings) + settings.elementWidth() * CARD_DIMENSION_RATIO;
    }

    private static class AnimatedCard extends AnimatedItem {
        private final int value;
        private final StackPane stackPane;
        private final Rectangle rectangle;
        private final Label label;

        public AnimatedCard(int value) {
            this.value = value;

            stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);

            rectangle = new Rectangle();
            rectangle.setStroke(Color.BLACK);
            stackPane.getChildren().add(rectangle);

            label = new Label(Integer.toString(value));
            label.setTextAlignment(TextAlignment.CENTER);
            stackPane.getChildren().add(label);

            getChildren().add(stackPane);
        }

        @Override
        public void generateVisuals(DisplaySettings settings) {
            getChildren().clear();

            getChildren().add(stackPane);
            rectangle.setWidth(settings.elementWidth());
            rectangle.setHeight(settings.elementWidth() * CARD_DIMENSION_RATIO);
            rectangle.setFill(Color.hsb(360.0 * value / settings.maxValue(), 1.0 ,1.0));
        }
    }
}
