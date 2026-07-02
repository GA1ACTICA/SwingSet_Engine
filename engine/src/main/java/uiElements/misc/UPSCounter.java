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

package uiElements.misc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import gameEngine.engineModules.ClassFactory;
import gameEngine.engineModules.EngineContext;
import gameEngine.interfaces.MenuInterface.MenuSetPosition;
import gameEngine.interfaces.Updatable;
import gameEngine.interfaces.drawables.UIDrawable;

/**
 * A UPS counter to measure the updates per second which could be seen as the
 * approximate framerate since the framerate cant be directly measured in
 * {@link JPanel#paintComponents(Graphics)}.
 */
public class UPSCounter implements UIDrawable, Updatable, MenuSetPosition {

    private boolean show = false;

    private int zIndex = 0;

    private int ups = 0;
    private int upsCounter = 0;
    private long lastTime = System.nanoTime();
    private double timer = 0;

    private int x = 10;
    private int y = 25;
    private Font font = new Font("Arial", Font.PLAIN, 25);
    private Color color = Color.BLACK;

    private EngineContext context;

    /**
     * Creates and registers an FPS/UPS counter.
     * <p>
     * In Swing/AWT-based applications, frame rate cannot be measured directly with
     * high accuracy. Instead, this class measures the number of {@link Updatable}
     * update calls per second (UPS). If rendering occurs on every update, this
     * value can be used as an approximation of the frame rate (FPS).
     *
     * @param context the engine context containing elements related to rendering,
     *                updating, and input handling
     */
    public UPSCounter(EngineContext context) {
        this.context = context;
        ClassFactory.create(this, context);
    }

    @Override
    public void setZIndex(int zIndex) {
        ClassFactory.updatePriority(this, context, zIndex);
        this.zIndex = zIndex;
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }

    /**
     * Returns if the element is visible.
     * 
     * @return {@code true} if the element is visible,
     *         {@code false} otherwise
     */
    public boolean isVisible() {
        return show;
    }

    @Override
    public void show() {
        show = true;
    }

    @Override
    public void hide() {
        show = false;
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setPosition(Point position) {
        this.x = position.x;
        this.y = position.y;
    }

    @Override
    public void translatePosition(int dx, int dy) {
        x += dx;
        y += dy;
    }

    /**
     * Sets the color for the displayed text and value.
     * 
     * @param color The color for the UPS counter
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the font for the displayed text and value.
     * 
     * @param font The font for the UPS counter
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Returns the X coordinate in screen coordinates (untransformed).
     * 
     * @return The X coordinates
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y coordinate in screen coordinates (untransformed).
     * 
     * @return The Y coordinates
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the current updates per second measured during this interval.
     * 
     * @return the UPS
     */
    public int getUPS() {
        return ups;
    }

    @Override
    public void draw(Graphics g) {
        if (!show)
            return;

        g.setFont(font);
        g.setColor(color);
        g.drawString("FPS/UPS: " + Integer.toString(ups), x, y);
    }

    @Override
    public void update(float deltaTime) {

        upsCounter++;

        long now = System.nanoTime();
        timer += (now - lastTime) / 1_000_000_000.0;
        lastTime = now;

        if (timer >= 1.0) {
            ups = upsCounter;
            upsCounter = 0;
            timer -= 1.0;
        }
    }
}
