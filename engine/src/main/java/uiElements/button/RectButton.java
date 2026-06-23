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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

import gameEngine.engineModules.*;
import gameEngine.engineModules.cursor.CursorManager;
import gameEngine.engineModules.cursor.CursorType;
import gameEngine.interfaces.*;
import gameEngine.interfaces.MenuInterface.*;
import gameEngine.interfaces.drawables.UIDrawable;
import utils.GraphicsUtils;
import utils.Utils;
import utils.GraphicsUtils.MaskType;

/**
 * Base implementation of a button UI element.
 * <p>
 * A button represents a boolean state that can be clicked by the user
 * and provides common functionality such as rendering, positioning, and sizing.
 */
public class RectButton implements
        UIDrawable, VisibleMenuInterface, MenuSetPosition, MenuSetSize, Clickable, Hoverable {

    private int zIndex = 0; // default zIndex

    private int x, y, width, height;
    private double angle = 0;
    private boolean show = false;

    protected RectangularShape baseShape;
    protected Shape rotatedShape;

    private Color color = Color.GREEN;
    private Color hoverColor = Color.ORANGE;
    private Color clickColor; // Used to override the normal white overlay when clicked
    private Color disabledColor = Color.LIGHT_GRAY;

    private Image image;
    private Image hoverImage;
    private Image clickImage;
    private Image disabledImage;

    private boolean isEnabled = true;
    private boolean clicked = false;
    private boolean showPress = true;

    private Runnable clickAction;

    // private Runnable hoverAction; // TODO: look into this
    private boolean isHovered = false;
    private boolean showHover = true;

    private Mouse mouse;
    private EngineContext context;

    /**
     * Creates and registers a rectangular button with the specified dimensions.
     * 
     * @param context The engine context containing objects involved in rendering,
     *                updating, and input handling.
     * 
     * @param panel   The panel on which the button is drawn to.
     * 
     * @param mouse   The mouse input handler used for interaction with the
     *                button.
     * 
     * @param x       The x-coordinate of the rectangle's top-left point.
     * 
     * @param y       The y-coordinate of the rectangle's top-left point.
     * 
     * @param width   The width of the rectangle.
     * 
     * @param height  The height of the rectangle.
     */
    public RectButton(EngineContext context, EnginePanel panel, Mouse mouse, int x, int y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this(context, mouse, panel);

    }

    /**
     * Creates and registers a rectangular button with the specified points.
     * 
     * @param context     The engine context containing objects involved in
     *                    rendering, updating, and input handling.
     * 
     * @param panel       The panel on which the button is drawn to.
     * 
     * @param mouse       The mouse input handler used for interaction with the
     *                    button.
     * 
     * @param topLeft     The top-left point of the rectangle.
     * 
     * @param bottomRight The bottom-left point of the rectangle.
     */
    public RectButton(EngineContext context, EnginePanel panel, Mouse mouse, Point topLeft, Point bottomRight) {

        x = (int) topLeft.getX();
        y = (int) topLeft.getY();
        width = (int) bottomRight.getX() - (int) topLeft.getX();
        height = (int) bottomRight.getY() - (int) topLeft.getY();

        this(context, mouse, panel);

    }

    /**
     * Creates and registers a rectangular button with the specified dimensions and
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
     * @param center  The center point of the rectangle.
     * 
     * @param width   The width of the rectangle.
     * 
     * @param height  The height of the rectangle.
     */
    public RectButton(EngineContext context, EnginePanel panel, Mouse mouse, Point center, int width, int height) {

        x = (int) center.getX() - width / 2;
        y = (int) center.getY() - height / 2;
        this.width = width;
        this.height = height;

        this(context, mouse, panel);

    }

    private RectButton(EngineContext context, Mouse mouse, EnginePanel panel) {
        ClassFactory.create(this, context, zIndex);

        this.context = context;
        this.mouse = mouse;

        this.baseShape = new Rectangle2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;
        updateRotatedShape();

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

    @Override
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
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    @Override
    public void setPosition(Point position) {
        this.x = position.x;
        this.y = position.y;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    @Override
    public void translatePosition(int dx, int dy) {
        x += dx;
        y += dy;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    @Override
    public void translateSize(int dWidth, int dHeight) {
        width += dWidth;
        height += dHeight;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    // ————————— Set colors ——————————
    /**
     * Set the color shown when then button is in the normal state.
     * 
     * @param color The color shown when the button is normal
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Set the color shown when then button is in the hovered state.
     * 
     * @param hoverColor The color shown when the button is hovered
     */
    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    /**
     * Set the color shown when then button is in the disabled state.
     * 
     * @param disabledColor The color shown when the button is disabled
     */
    public void setDisabledColor(Color disabledColor) {
        this.disabledColor = disabledColor;
    }

    /**
     * Set the color shown when then button is in the clicked state.
     * 
     * @param clickColor The image color when the button is clicked
     */
    public void setClickColor(Color clickColor) {
        this.clickColor = clickColor;
    }

    // —————————— Set images ——————————
    /**
     * Set the image shown when then button is in the normal state.
     * 
     * @param image The image shown when the button is normal
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Set the image shown when then button is in the hovered state.
     * 
     * @param hoverImage The image shown when the button is hovered
     */
    public void setHoverImage(Image hoverImage) {
        this.hoverImage = hoverImage;
    }

    /**
     * Set the image shown when then button is in the disabled state.
     * 
     * @param disabledImage The image shown when the button is disabled
     */
    public void setDisabledImage(Image disabledImage) {
        this.disabledImage = disabledImage;
    }

    /**
     * Set the image shown when then button is in the clicked state.
     * 
     * @param clickImage The image shown when the button is clicked
     */
    public void setClickImage(Image clickImage) {
        this.clickImage = clickImage;
    }

    // ————————————————————————————————

    /**
     * Sets the center position of the button. This recalculates the
     * top-left coordinates based on the current width and height,
     * updates the base shape, and refreshes the rotated shape.
     *
     * @param center the new center position
     */
    public void setCenter(Point center) {
        x = (int) Math.round(center.getX() - width / 2);
        y = (int) Math.round(center.getY() - height / 2);
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    /**
     * Sets the rotation of the button.
     * Positive angles rotate clockwise, negative angles rotate counterclockwise.
     *
     * <p>
     * <b>Note:</b> Positioning methods return values based on the unrotated
     * shape, not the visually rotated one.
     * </p>
     *
     * @param angle the rotation angle in degrees
     */
    public void setRotation(double angle) {
        this.angle = angle;

        updateRotatedShape();
    }

    /**
     * Enables or disables the visual click effect (color or image change)
     * when the button is pressed.
     *
     * @param clickEffect {@code true} to enable the click effect,
     *                    {@code false} to disable it
     */
    public void setClickEffectEnabled(boolean clickEffect) {
        showPress = clickEffect;
    }

    /**
     * Enables or disables the visual hover effect (color or image change)
     * when the button is hovered.
     *
     * @param hoverEffect {@code true} to enable the hover effect,
     *                    {@code false} to disable it
     */
    public void setHoverEffectEnabled(boolean hoverEffect) {
        showHover = hoverEffect;
    }

    /**
     * Set the ability to interact with the checkbox. This also changes the
     * appearances to the disabled state.
     * 
     * @param isEnabled true to disable the checkbox, false to enable it
     * 
     * @see #setDisabledColor(Color)
     * @see #setDisabledImage(Image)
     */
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
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
     * Returns the width of the checkbox.
     * 
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the checkbox.
     * 
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the center of the checkbox.
     * 
     * @return the center {@link Point}
     */
    public Point getCenter() {
        return new Point(x + width / 2, y + height / 2);
    }

    /**
     * Returns the checkbox angle.
     * 
     * @return the angle
     */
    public double getAngle() {
        return angle;
    }

    @Override
    public boolean isHovered() {
        return isHovered;
    }

    @Override
    public void onClick(Runnable action) {
        this.clickAction = action;

    }

    @Override
    public void draw(Graphics g) {
        if (!show)
            return;

        Graphics2D g2d = (Graphics2D) g;

        // Rotate everything drawn inside
        GraphicsUtils.rotateGraphics(g2d, angle, getCenter(), gRotate -> {

            // Pressed state completely overrides everything
            if (showPress && clicked && clickColor != null) {
                gRotate.setColor(clickColor);
                gRotate.fill(baseShape);
                return;
            }

            Image image;
            Color color;

            if (!isEnabled) {
                image = disabledImage;
                color = disabledColor;
            } else if (isHovered && showHover) {
                image = hoverImage;
                color = hoverColor;
            } else {
                image = this.image;
                color = this.color;
            }

            // Draw base state
            if (image == null) {
                gRotate.setColor(color);
                gRotate.fill(baseShape);
            } else {
                GraphicsUtils.createMask(gRotate, baseShape, MaskType.INSIDE,
                        gMask -> gMask.drawImage(image, x, y, width, height, null));
            }

            // Draw pressed overlay
            if (showPress && clicked) {
                if (clickImage == null) {
                    gRotate.setColor(Utils.mergeRGBColor(hoverColor, Utils.rgba(255, 255, 255, 0.5)));
                    gRotate.fill(baseShape);
                } else {
                    GraphicsUtils.createMask(gRotate, baseShape, MaskType.INSIDE,
                            gMask -> gMask.drawImage(clickImage, x, y, width, height, null));
                }
            }
        });
    }

    /**
     * Updates the rotated version of the base shape based on the current angle.
     * The rotation is performed around the shape's center point.
     *
     * This method should be called whenever the position, size, or rotation
     * changes.
     */
    protected void updateRotatedShape() {

        AffineTransform transform = new AffineTransform();
        Point center = getCenter();

        transform.rotate(Math.toRadians(angle), center.x, center.y);
        rotatedShape = transform.createTransformedShape(baseShape);
    }

    @Override
    public boolean contains(int mouseX, int mouseY) {
        return rotatedShape.contains(mouse.getPoint().x, mouse.getPoint().y);
    }

    @Override
    public void executeOnClick() {
        if (clickAction != null)
            clickAction.run();
    }

    @Override
    public void setHovered(boolean isHovered) {
        this.isHovered = isHovered;

        if (isHovered)
            CursorManager.setCursor(CursorType.POINTER);
        else
            CursorManager.setCursor(CursorType.DEFAULT);

    }

    @Override
    public void onPressed() {
        clicked = true;
    }

    @Override
    public void onReleased() {
        clicked = false;
    }

    /**
     * Returns whether or not the button is currently being pressed.
     * 
     * @return {@code true} if the button is pressed,
     *         {@code false} otherwise
     * 
     * @see #isReleased()
     */
    public boolean isPressed() {
        return clicked;
    }

    /**
     * Returns whether or not the button is currently not being pressed.
     * 
     * @return {@code true} if the button is not pressed,
     *         {@code false} otherwise
     * 
     * @see #isPressed()
     */
    public boolean isReleased() {
        return !clicked;
    }
}
