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

package uiElements.button;

import java.awt.Point;
import java.awt.geom.Ellipse2D;

import gameEngine.engineModules.EngineContext;
import gameEngine.engineModules.EnginePanel;
import gameEngine.engineModules.Mouse;

public class OvalButton extends RectButton {

    /**
     * Creates and registers a oval button with the specified dimensions.
     * 
     * @param context The engine context containing objects involved in rendering,
     *                updating, and input handling.
     * 
     * @param panel   The panel on which the button is drawn to.
     * 
     * @param mouse   The mouse input handler used for interaction with the
     *                button.
     * 
     * @param x       The x-coordinate of the oval's top-left point.
     * 
     * @param y       The y-coordinate of the oval's top-left point.
     * 
     * @param width   The width of the oval.
     * 
     * @param height  The height of the oval.
     */
    public OvalButton(EngineContext context, EnginePanel panel, Mouse mouse, int x, int y, int width, int height) {

        super(context, panel, mouse, x, y, width, height);

        this.baseShape = new Ellipse2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;

        updateRotatedShape();

    }

    /**
     * Creates and registers a oval button with the specified points.
     * 
     * @param context     The engine context containing objects involved in
     *                    rendering, updating, and input handling.
     * 
     * @param panel       The panel on which the button is drawn to.
     * 
     * @param mouse       The mouse input handler used for interaction with the
     *                    button.
     * 
     * @param topLeft     The top-left point of the oval.
     * 
     * @param bottomRight The bottom-left point of the oval.
     */
    public OvalButton(EngineContext context, EnginePanel panel, Mouse mouse, Point topLeft, Point bottomRight) {

        int x = (int) topLeft.getX();
        int y = (int) topLeft.getY();
        int width = (int) bottomRight.getX() - (int) topLeft.getX();
        int height = (int) bottomRight.getY() - (int) topLeft.getY();

        super(context, panel, mouse, x, y, width, height);

        this.baseShape = new Ellipse2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;

        updateRotatedShape();

    }

    /**
     * Creates and registers a oval button with the specified dimensions and center
     * point.
     *
     * @param context The engine context containing objects involved in rendering,
     *                updating, and input handling.
     * 
     * @param panel   The panel on which the button is drawn to.
     * 
     * @param mouse   The mouse input handler used for interaction with the
     *                button.
     * 
     * @param center  The center point of the oval.
     * 
     * @param width   The width of the oval.
     * 
     * @param height  The height of the oval.
     */
    public OvalButton(EngineContext context, EnginePanel panel, Mouse mouse, Point center, int width, int height) {

        int x = (int) center.getX() - width / 2;
        int y = (int) center.getY() - height / 2;

        super(context, panel, mouse, x, y, width, height);

        this.baseShape = new Ellipse2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;

        updateRotatedShape();

    }

    /**
     * Creates and registers a circular button with the specified dimensions and
     * center point.
     *
     * @param context The engine context containing objects involved in rendering,
     *                updating, and input handling.
     * 
     * @param panel   The panel on which the button is drawn to.
     * 
     * @param mouse   The mouse input handler used for interaction with the
     *                button.
     * 
     * @param center  The circle's center point.
     * 
     * @param radius  The circle's radius.
     */
    public OvalButton(EngineContext context, EnginePanel panel, Mouse mouse, Point center, int radius) {

        int x = (int) center.getX() - radius;
        int y = (int) center.getY() - radius;

        int length = radius * 2;

        super(context, panel, mouse, x, y, length, length);

        this.baseShape = new Ellipse2D.Float(x, y, length, length);
        this.rotatedShape = baseShape;

        updateRotatedShape();
    }

}
