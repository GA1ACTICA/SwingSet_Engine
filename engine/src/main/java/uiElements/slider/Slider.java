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

package uiElements.slider;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.Objects;

import gameEngine.engineModules.ClassFactory;
import utils.MathUtils;
import gameEngine.engineModules.EngineContext;
import gameEngine.engineModules.Mouse;
import gameEngine.interfaces.MenuInterface;
import gameEngine.interfaces.Updatable;
import gameEngine.interfaces.drawables.UIDrawable;
import gameEngine.records.FixResult;
import uiElements.button.RectButton;

public class Slider implements UIDrawable, Updatable, MenuInterface {

    private boolean show = false;

    private int zIndex = 0;

    // Slider looks
    private Point pointOne, pointTwo;
    private Color trackColor = new Color(60, 60, 60, 153); // ~60% transparency
    private int trackWidth = 5;

    // Slider values
    private double sliderPercentage = 0.50;
    private int sliderMin = 0;
    private int sliderMax = 100;

    // handle looks
    private int handleWidth = trackWidth + 8;
    private int handleHeight = 25;
    private double handleAngle;

    private RectButton handle;
    private final Mouse mouse;

    private EngineContext context;

    /**
     * Creates and registers a slider between the specified two {@link Point
     * Points}.
     * 
     * @param context  The engine context containing objects involved in rendering,
     *                 updating, and input handling.
     * 
     * @param mouse    The mouse input handler used for interaction with the
     *                 handle.
     * 
     * @param pointOne The first point for the track.
     * 
     * @param pointTwo The second point of the track.
     */
    public Slider(EngineContext context, Mouse mouse, Point pointOne, Point pointTwo) {
        ClassFactory.create(this, context);
        this.mouse = mouse;
        this.pointOne = pointOne;
        this.pointTwo = pointTwo;
        this.context = context;

        Point middle = new Point(((pointOne.x + pointTwo.x) / 2), ((pointOne.y + pointTwo.y) / 2));

        handle = new RectButton(context, mouse, middle,
                handleWidth, handleHeight);

        handleAngle = Math.toDegrees(Math.atan2(
                pointTwo.y - pointOne.y,
                pointTwo.x - pointOne.x)) - 90;

        handle.setRotation(handleAngle);

    }

    @Override
    public void setZIndex(int zIndex) {
        ClassFactory.updatePriority(this, context, zIndex);
        handle.setZIndex(zIndex);
        this.zIndex = zIndex;
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }

    public boolean isVisible() {
        return show;
    }

    @Override
    public void show() {
        show = true;
        handle.show();
    }

    @Override
    public void hide() {
        show = false;
        handle.hide();
    }

    /**
     * Returns the length between the two {@link Point Points} that define the
     * beginning
     * and the end of the track.
     * 
     * @return the length between the points
     */
    public double getLength() {
        return MathUtils.pythagoras(pointOne, pointTwo);
    }

    /**
     * Calculates and returns the slider value based on its current percentage
     * multiplied the slider's max-value.
     * 
     * @return the current value of the slider
     */
    public double getSliderValue() {
        return sliderPercentage * sliderMax;
    }

    /**
     * Returns the lowest value the slider can represent.
     * 
     * @return The minimum value
     */
    public int getSliderMin() {
        return sliderMin;
    }

    /**
     * Returns the highest value the slider can represent.
     * 
     * @return The maximum value
     */
    public int getSliderMax() {
        return sliderMax;
    }

    /**
     * Returns the slider handle's position as a percentage of the track length.
     * The value is in the range {@code 0.0} to {@code 1.0}.
     *
     * @return the slider handle's position as a percentage of the track length
     */
    public double getSliderPercentage() {
        return sliderPercentage;
    }

    /**
     * Returns the color used for painting the track the handle slides along.
     * 
     * @return The color used for the track
     */
    public Color getTrackColor() {
        return trackColor;
    }

    /**
     * Returns the {@link RectButton} representing the slider handle.
     * <p>
     * Allows customization of the handle's appearance.
     * Modifying the returned instance affects this slider directly.
     *
     * @return the internal RectButton used as the handle
     */
    public RectButton getHandle() {
        return handle;
    }

    /**
     * Returns the first {@link Point} initialized in the constructor,
     * {@link #setSliderPoints(Point, Point)} or
     * {@link #setSliderPoints(Point, Point, boolean)}
     * 
     * @return the first point
     */
    public Point getPointOne() {
        return pointOne;
    }

    /**
     * Returns the second {@link Point} initialized in the constructor,
     * {@link #setSliderPoints(Point, Point)} or
     * {@link #setSliderPoints(Point, Point, boolean)}
     * 
     * @return the second point
     */
    public Point getPointTwo() {
        return pointTwo;
    }

    /**
     * Sets the color of the track the handle slides along
     * 
     * @param trackColor the new color of the track
     */
    public void setColor(Color trackColor) {
        this.trackColor = trackColor;
    }

    /**
     * Replaces the internally used {@link RectButton} representing the slider
     * handle.
     * <p>
     * The previous handle is removed and the new one is assigned. Its position will
     * be updated automatically, so it does not need to match the previous handle's
     * position.
     *
     * @param handle           the new handle
     * 
     * @param preserveRotation decides if the handel retains it's rotation
     * 
     * @throws NullPointerException if {@code handle} is {@code null}
     */
    public void setHandle(RectButton handle, boolean preserveRotation) {
        Objects.requireNonNull(handle, "Handle must not be null");

        ClassFactory.remove(this.handle, context);
        this.handle = handle;

        // Ensure correct position regardless of constructor values
        updateSlider();

        if (!preserveRotation)
            handle.setRotation(handleAngle);

    }

    /**
     * Replaces the internally used {@link RectButton} representing the slider
     * handle.
     * <p>
     * The previous handle is removed and the new one is assigned. Its position will
     * be updated automatically, so it does not need to match the previous handle's
     * position.
     *
     * <p>
     * <b>Note:</b> The handle's rotation will be aligned with the slider.
     * Any custom rotation will be overridden. To preserve rotation, use
     * {@link #setHandle(RectButton, boolean)}.
     *
     * @param handle the new handle
     * 
     * @throws NullPointerException if {@code handle} is {@code null}
     */
    public void setHandle(RectButton handle) {
        setHandle(handle, false); // default: align with slider
    }

    /**
     * Both sets the slider current value and updates the handle's position on the
     * slider based on the slider's max-value.
     * 
     * @param value the new value of the slider
     */
    public void setSliderValue(int value) {
        setPercentage((double) value / sliderMax);
    }

    /**
     * Sets the new max-value for the slider.
     * <p>
     * The default value of sliderMax in {@code 100}
     * 
     * @param sliderMax the new max-value
     */
    public void setSliderMax(int sliderMax) {
        this.sliderMax = sliderMax;
    }

    /**
     * Sets the new min-value for the slider.
     * <p>
     * The default value of sliderMin in {@code 0}
     * 
     * @param sliderMin the new min-value
     */
    public void setSliderMin(int sliderMin) {
        this.sliderMin = sliderMin;
    }

    /**
     * Sets the track width which the handle slider along.
     * 
     * @param trackWidth the width of the track
     */
    public void settTackWidth(int trackWidth) {
        this.trackWidth = trackWidth;
    }

    /**
     * Sets a new position for both {@code pointOne} and {@code pointTwo} and
     * updates the slider.
     * <p>
     * The handle's rotation will be updated automatically to align with the slider,
     * unless {@code preserveRotation} is set to {@code true}.
     *
     * @param pointOne         the first point
     * 
     * @param pointTwo         the second point
     * 
     * @param preserveRotation if {@code true}, the handle retains its current
     *                         rotation
     * 
     * @throws NullPointerException if {@code pointOne} or {@code pointTwo} is
     *                              {@code null}
     */
    public void setSliderPoints(Point pointOne, Point pointTwo, boolean preserveRotation) {
        Objects.requireNonNull(pointOne, "pointOne must not be null");
        Objects.requireNonNull(pointTwo, "pointTwo must not be null");

        if (!preserveRotation) {
            handleAngle = Math.toDegrees(Math.atan2(
                    pointTwo.y - pointOne.y,
                    pointTwo.x - pointOne.x)) - 90;

            handle.setRotation(handleAngle);
        }

        this.pointOne = pointOne;
        this.pointTwo = pointTwo;

        updateSlider();
    }

    /**
     * Sets a new position for both {@code pointOne} and {@code pointTwo} and
     * updates the slider.
     * <p>
     * <b>Note:</b> The handle's rotation will be aligned with the slider. Any
     * custom rotation will be overridden. To preserve rotation, use
     * {@link #setSliderPoints(Point, Point, boolean)}.
     *
     * @param pointOne the first point
     * 
     * @param pointTwo the second point
     * 
     * @throws NullPointerException if {@code pointOne} or {@code pointTwo} is
     *                              {@code null}
     */
    public void setSliderPoints(Point pointOne, Point pointTwo) {
        setSliderPoints(pointOne, pointTwo, false);
    }

    /**
     * Both set the slider current percentage and updates the handle's position on
     * the slider.
     * 
     * @param percentage The new percentage of the slider
     */
    public void setPercentage(double percentage) {
        this.sliderPercentage = percentage;

        updateSlider();
    }

    /**
     * Updates the handel's position based on the slider's current percentage.
     */
    public void updateSlider() {
        double deltaX = pointTwo.x - pointOne.x;
        double deltaY = pointTwo.y - pointOne.y;

        int px = (int) Math.round(pointOne.x + sliderPercentage * deltaX);
        int py = (int) Math.round(pointOne.y + sliderPercentage * deltaY);

        handle.setCenter(new Point(px, py));
    }

    @Override
    public void draw(Graphics g) {
        if (!show)
            return;

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(trackColor);

        // Draws a line with thickness "trackWidth"
        g2d.setStroke(new BasicStroke(trackWidth));
        g2d.draw(new Line2D.Float(pointOne, pointTwo));

    }

    @Override
    public void update(float deltaTime) {
        if (!show)
            return;

        // Only run when holding / dragging
        if (handle.isPressed()) {

            FixResult sliderResult = MathUtils.fixToLine(mouse.getPoint(), pointOne, pointTwo);

            sliderPercentage = sliderResult.progress();
            handle.setCenter(sliderResult.point());

        }
    }
}
