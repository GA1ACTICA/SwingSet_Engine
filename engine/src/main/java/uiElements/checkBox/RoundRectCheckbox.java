/**
 * Project: SwingSet_Engine
 *
 * Author: Galactica
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 *
 * Copyright © 2026 Galactica
 */

package uiElements.checkBox;

import java.awt.Point;
import java.awt.geom.RoundRectangle2D;

import gameEngine.engineModules.EngineContext;
import gameEngine.engineModules.Mouse;

/**
 * A checkbox rendered using a rounded rectangle shape.
 * <p>
 * Behaves identically to {@link RectCheckbox} but uses a rounded rectangle
 * appearance.
 */
public class RoundRectCheckbox extends RectCheckbox {

    /**
     * Creates and registers a rounded rectangular checkbox with the specified
     * dimensions and rounded corners.
     * 
     * @param context   The engine context containing objects involved in rendering,
     *                  updating, and input handling.
     * 
     * @param mouse     The mouse input handler used for interaction with the
     *                  checkbox.
     * 
     * @param x         The x-coordinate of the rectangle's top-left point.
     * 
     * @param y         The y-coordinate of the rectangle's top-left point.
     * 
     * @param width     The width of the rectangle.
     * 
     * @param height    The height of the rectangle.
     *
     * @param arcWidth  The horizontal diameter of the corner arcs. Typically should
     *                  not exceed the rectangle's width.
     * 
     * @param arcHeight The vertical diameter of the corner arcs. Typically should
     *                  not exceed the rectangle's height.
     */
    public RoundRectCheckbox(EngineContext context, Mouse mouse, int x, int y, int width, int height,
            int arcWidth,
            int arcHeight) {
        super(context, mouse, x, y, width, height);

        this.baseShape = new RoundRectangle2D.Float(x, y, width, height, arcWidth, arcHeight);
        this.rotatedShape = baseShape;

        updateRotatedShape();
    }

    /**
     * Creates and registers a rectangular checkbox with the specified {@link Point
     * Points} and rounded corners.
     * 
     * @param context     The engine context containing objects involved
     *                    in rendering, updating, and input handling.
     * 
     * @param mouse       The mouse input handler used for interaction with the
     *                    checkbox.
     * 
     * @param topLeft     The top-left point of the rectangle.
     * 
     * @param bottomRight The bottom-left point of the rectangle.
     * 
     * @param arcWidth    The horizontal diameter of the corner arcs. Typically
     *                    should not exceed the rectangle's width.
     * 
     * @param arcHeight   The vertical diameter of the corner arcs. Typically should
     *                    not exceed the rectangle's height.
     */
    public RoundRectCheckbox(EngineContext context, Mouse mouse, Point topLeft, Point bottomRight,
            int arcWidth,
            int arcHeight) {

        int x = (int) topLeft.getX();
        int y = (int) topLeft.getY();
        int width = (int) bottomRight.getX();
        int height = (int) bottomRight.getY();

        super(context, mouse, x, y, width, height);
        this.baseShape = new RoundRectangle2D.Float(x, y, width, height, arcWidth, arcHeight);
        this.rotatedShape = baseShape;

        updateRotatedShape();
    }

    /**
     * Creates and registers a rectangular checkbox with the specified dimensions,
     * center {@link Point} and rounded corners.
     *
     * @param context   The engine context containing objects involved in rendering,
     *                  updating, and input handling.
     * 
     * @param mouse     The mouse input handler used for interaction with the
     *                  checkbox.
     * 
     * @param center    The center point of the rectangle.
     * 
     * @param width     The width of the rectangle.
     * 
     * @param height    The height of the rectangle.
     * 
     * @param arcWidth  The horizontal diameter of the corner arcs. Typically
     *                  should not exceed the rectangle's width.
     * 
     * @param arcHeight The vertical diameter of the corner arcs. Typically should
     *                  not exceed the rectangle's height.
     */
    public RoundRectCheckbox(EngineContext context, Mouse mouse, Point center, int width, int height,
            int arcWidth,
            int arcHeight) {

        int x = (int) center.getX() - width / 2;
        int y = (int) center.getY() - height / 2;

        super(context, mouse, x, y, width, height);

        this.baseShape = new RoundRectangle2D.Float(x, y, width, height, arcWidth, arcHeight);
        this.rotatedShape = baseShape;

        updateRotatedShape();
    }

    /**
     * Creates and registers a circular checkbox with the specified dimensions and
     * center {@link Point}.
     * 
     * @param context The engine context containing objects involved in rendering,
     *                updating, and input handling.
     * 
     * 
     * @param mouse   The mouse input handler used for interaction with the
     *                checkbox.
     * 
     * @param center  The center point from where the circle is created.
     * 
     * @param radius  The circles radius.
     */
    public RoundRectCheckbox(EngineContext context, Mouse mouse, Point center, int radius) {
        int x = center.x - radius;
        int y = center.y - radius;

        int length = radius * 2;

        super(context, mouse, x, y, length, length);

        this.baseShape = new RoundRectangle2D.Float(x, y, length, length, length, length);
        this.rotatedShape = baseShape;

        updateRotatedShape();
    }

}
