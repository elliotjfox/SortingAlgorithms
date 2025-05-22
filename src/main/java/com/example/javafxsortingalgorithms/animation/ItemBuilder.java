package com.example.javafxsortingalgorithms.animation;

import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder class for creating an AnimatedItem. Having a display is required, and there is optionally a position and a list of nodes that will be added to the AnimatedItem.
 */
public class ItemBuilder {

    // Required arguments
    private final AnimatedArrayDisplay display;

    // Optional arguments
    private ItemPosition position;
    private final List<Node> nodes;

    /**
     * Creates a Builder that will create an item that uses the provided display.
     * @param display The display object the item this is building will be added to
     */
    public ItemBuilder(AnimatedArrayDisplay display) {
        this.display = display;
        this.nodes = new ArrayList<>();
    }

    /**
     * Set the position of the item.
     * @param position The new position of the item
     * @return This builder
     */
    public ItemBuilder at(ItemPosition position) {
        this.position = position;
        return this;
    }

    /**
     * Set the position of the item, by index and height.
     * @param index The index of the position
     * @param height The height of the position
     * @return This builder
     */
    public ItemBuilder at(int index, double height) {
        this.position = new ItemIndexPosition(index, height);
        return this;
    }

    /**
     * Set the position of the item, by x and y position.
     * @param x The x position
     * @param y The y position
     * @return This builder
     */
    public ItemBuilder at(double x, double y) {
        this.position = new ItemExactPosition(x, y);
        return this;
    }

    /**
     * Adds nodes onto the item.
     * @param nodes The nodes to be added to the item
     * @return This builder
     */
    public ItemBuilder add(Node... nodes) {
        this.nodes.addAll(List.of(nodes));
        return this;
    }

    /**
     * Creates and returns the item this was building.
     * @return The completed item
     */
    public AnimatedItem build() {
        return new AnimatedItem(display, position, nodes);
    }

    public AnimatedSection buildSection(int width) {
        return buildSection(width * display.getElementWidth(), true);
    }

    public AnimatedSection buildSection(int width, boolean hasEdges) {
        return new AnimatedSection(display, position, nodes, width * display.getElementWidth(), hasEdges);
    }

    public AnimatedSection buildSection(double exactWidth, boolean hasEdges) {
        return new AnimatedSection(display, position, nodes, exactWidth, hasEdges);
    }

    public static AnimatedItem defaultArrow(AnimatedArrayDisplay display, int index) {
        return new ItemBuilder(display).add(PolygonWrapper.triangle(display, Color.BLACK, true)).at(index, 0).build();
    }
}
