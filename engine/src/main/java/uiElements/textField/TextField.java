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

package uiElements.textField;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.io.IOException;

import javax.swing.JPanel;

import gameEngine.engineModules.ClassFactory;
import gameEngine.engineModules.EngineContext;
import gameEngine.engineModules.EnginePanel;
import gameEngine.engineModules.Keys;
import gameEngine.engineModules.cursor.CursorManager;
import gameEngine.engineModules.cursor.CursorType;
import gameEngine.interfaces.*;
import gameEngine.interfaces.MenuInterface.MenuSetPosition;
import gameEngine.interfaces.MenuInterface.MenuSetSize;
import gameEngine.interfaces.drawables.UIDrawable;
import utils.ErrorManagement;
import utils.GraphicsUtils;
import utils.GraphicsUtils.MaskType;

/**
 * Provides a basic implementation of a single-line only text field for
 * displaying and editing text in a graphical user interface.
 */
public class TextField
        implements MouseNotifier, KeyNotifier, Clickable, UIDrawable, Updatable, MenuSetSize,
        MenuSetPosition {

    private int zIndex = 0;

    private boolean show;

    private int x;
    private int y;
    private int width;
    private int height;

    private double angle;

    private RectangularShape baseShape;
    private Shape rotatedShape;
    // Colors
    private Color focusedColor = GraphicsUtils.rgb(187, 187, 187);
    private Color unFocusedColor = GraphicsUtils.rgb(172, 172, 172);
    private Color disabledColor = GraphicsUtils.rgb(116, 116, 116);
    private Color highlightColor = GraphicsUtils.rgba(0, 123, 218, 0.25);

    // Images
    private Image focusedImage;
    private Image unFocusedImage;
    private Image disabledImage;

    private Font fieldFont = new Font(Font.SANS_SERIF, Font.PLAIN, 0);
    private StringBuffer text = new StringBuffer("");
    private FontMetrics fontMetrics;

    private Runnable clickAction;

    private EngineContext context;
    private EnginePanel panel;
    private Keys keys;

    // Behavioral variables
    private boolean isHovered;
    private boolean isFocused;
    private boolean isEnabled = true; // TODO: implement disabled state

    private int caretOffset;
    private int highlightStartX = 0;
    private int highlightWidth = 0;
    private Integer highlightStartIndex = null;
    private Integer highlightEndIndex = null;

    private UIElementLayout layout = UIElementLayout.NONE;

    /**
     * 
     * Creates and registers a textfield with the specified dimensions.
     * 
     * @param context the engine context containing objects involved in rendering,
     *                updating, and input handling.
     * 
     * @param panel   the {@link JPanel} which contains the main render loop.
     * 
     * @param keys    the keyboard input handler used for interaction with the
     *                textfield.
     * 
     * @param x       the x-coordinate of the textfield's topLeft point.
     * 
     * @param y       the y-coordinate of the textfield's topLeft point.
     * 
     * @param width   the width of the textfield.
     * 
     * @param height  the height of the textfield.
     */
    public TextField(EngineContext context, EnginePanel panel, Keys keys, int x, int y, int width,
            int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this(context, panel, keys);
    }

    /**
     * Creates and registers a textfield with the specified {@link Point Points}.
     * 
     * @param context     the engine context containing objects involved in
     *                    rendering, updating, and input handling.
     * 
     * @param panel       the {@link JPanel} which contains the main render loop.
     * 
     * @param keys        the keyboard input handler used for interaction with the
     *                    textfield.
     * 
     * @param topLeft     the top left point of the rectangle.
     * 
     * @param bottomRight the bottom left point of the rectangle.
     */
    public TextField(EngineContext context, EnginePanel panel, Keys keys, Point topLeft,
            Point bottomRight) {

        x = (int) topLeft.getX();
        y = (int) topLeft.getY();
        width = (int) bottomRight.getX() - (int) topLeft.getX();
        height = (int) bottomRight.getY() - (int) topLeft.getY();

        this(context, panel, keys);
    }

    /**
     * Creates and registers a textfield with the specified dimensions
     * and center {@link Point}.
     * 
     * @param context the engine context containing objects involved in
     *                rendering, updating, and input handling.
     * 
     * @param panel   the {@link JPanel} which contains the main render loop.
     * 
     * @param keys    the keyboard input handler used for interaction with the
     *                textfield.
     * 
     * @param middle  the middle point of the rectangle.
     * 
     * @param width   the width of the textfield.
     * 
     * @param height  the height of the textfield.
     */
    public TextField(EngineContext context, EnginePanel panel, Keys keys, Point middle, int width,
            int height) {

        x = (int) middle.getX() - width / 2;
        y = (int) middle.getY() - height / 2;
        this.width = width;
        this.height = height;

        this(context, panel, keys);

    }

    private TextField(EngineContext context, EnginePanel panel, Keys keys) {
        ClassFactory.create(this, context);

        this.baseShape = new Rectangle2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;
        updateRotatedShape();

        fieldFont = GraphicsUtils.matchFontToHeight(fieldFont, "ÅÄÖgjpqÉÁÂÂÂÂ",
                height - 2);

        this.keys = keys;
        this.panel = panel;
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
    public void setZIndex(int zIndex) {
        ClassFactory.updatePriority(this, context, zIndex);
        this.zIndex = zIndex;
    }

    @Override
    public int getZIndex() {
        return zIndex;
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
     * Set the color shown when then textfield is in the focused state.
     * 
     * @param focusedColor the color shown when the textfield's state is focused
     */
    public void setFocusedColor(Color focusedColor) {
        this.focusedColor = focusedColor;
    }

    /**
     * Set the color shown when then textfield is in the unfocused state.
     * 
     * @param unFocusedColor the color shown when the textfield's state is unfocused
     */
    public void setUnFocusedColor(Color unFocusedColor) {
        this.unFocusedColor = unFocusedColor;
    }

    /**
     * Set the color shown when then textfield is in the disabled state.
     * 
     * @param disabledColor the color shown when the textfield is disabled
     */
    public void setDisabledColor(Color disabledColor) {
        this.disabledColor = disabledColor;
    }

    // —————————— Set images ——————————
    /**
     * Set the image shown when then textfield is in the focused state.
     * 
     * @param focusedImage the image shown when the textfield's state is focused
     */
    public void setFocusedImage(Image focusedImage) {
        this.focusedImage = focusedImage;
    }

    /**
     * Set the image shown when then textfield is in the unfocused state.
     * 
     * @param unFocusedImage the image shown when the textfield's state is unfocused
     */
    public void setUnFocusedImage(Image unFocusedImage) {
        this.unFocusedImage = unFocusedImage;
    }

    /**
     * Set the image shown when then textfield is in the disabled state.
     * 
     * @param disabledImage the image shown when the textfield is disabled
     */
    public void setDisabledImage(Image disabledImage) {
        this.disabledImage = disabledImage;
    }

    // ————————————————————————————————

    /**
     * Sets the center position of the textfield. This recalculates the
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
     * Sets the rotation of the textfield.
     * Positive angles rotate clockwise, negative angles rotate counterclockwise.
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
     * Set the ability to interact with the textfield. This also changes the
     * appearances to the disabled state.
     * 
     * @param isEnabled {@code false} to disable the textfield, {@code true} to
     *                  enable it
     * 
     * @see #setDisabledColor(Color)
     * @see #setDisabledImage(Image)
     */
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;

        if (!isEnabled)
            isFocused = false;
    }

    /**
     * Sets the font for the textfield.
     * <p>
     * If {@code preserveSize} is {@code false}, the font will be resized to
     * approximately match the height of the text field using
     * {@link GraphicsUtils#matchFontToHeight(Font, String, int)}
     * else the font will retain its original size.
     * <p>
     * <b>Note:</b> {@link GraphicsUtils#matchFontToHeight(Font, String, int)} uses
     * the dummyText: "ÅÄÖgjpqÉÁÂÂÂÂ" to get the {@link FontMetrics}.
     * 
     * @param font         the new font
     * 
     * @param preserveSize {@code true} if the size should be retained,
     *                     {@code false} otherwise
     */
    public void setFont(Font font, boolean preserveSize) {
        if (!preserveSize)
            fieldFont = GraphicsUtils.matchFontToHeight(font, "ÅÄÖgjpqÉÁÂÂÂÂ",
                    height - 2);
        else
            fieldFont = font;
    }

    @Override
    public void setLayout(UIElementLayout layout) {
        this.layout = layout;
    }

    /**
     * Returns the X coordinate in screen coordinates (untransformed).
     * 
     * @return the X coordinates
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y coordinate in screen coordinates (untransformed).
     * 
     * @return the Y coordinates
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
    public UIElementLayout getLayout() {
        return layout;
    }

    /**
     * Returns the text currently displayed in the textfield.
     * 
     * @return the text in the textfield
     */
    public StringBuffer getText() {
        return text;
    }

    @Override
    public void draw(Graphics g) {
        if (!show)
            return;

        fontMetrics = g.getFontMetrics(fieldFont);

        Graphics2D g2d = (Graphics2D) g;

        GraphicsUtils.rotateGraphics(g2d, angle, getCenter(), (gRotate) -> {

            GraphicsUtils.createMask(gRotate, baseShape, MaskType.INSIDE,
                    (gMask) -> {
                        Image image = null;
                        Color color = null;

                        if (!isEnabled) {
                            color = disabledColor;
                            image = disabledImage;
                        } else if (isFocused) {
                            color = focusedColor;
                            image = focusedImage;
                        } else if (!isFocused) {
                            color = unFocusedColor;
                            image = unFocusedImage;
                        }

                        if (image != null)
                            gMask.drawImage(image, x, y, width, height, null);
                        else {
                            gMask.setColor(color);
                            gMask.fillRect(x, y, width, height);
                        }

                        gMask.setColor(highlightColor);
                        gMask.fillRect(highlightStartX, y, highlightWidth, height);

                        if (cursorVisible && isFocused && isEnabled) {
                            gMask.setColor(Color.BLACK);
                            gMask.fillRect(
                                    x + 10 + fontMetrics.stringWidth(text.substring(0, text.length() - caretOffset)),
                                    y + 2, 2,
                                    height - 4);
                        }

                        gMask.setFont(fontMetrics.getFont());
                        gMask.setColor(Color.BLACK);
                        gMask.drawString(text.toString(), x + 10,
                                y + fontMetrics.getHeight() - fontMetrics.getDescent());

                    });
        });
    }

    private float cursorTimer = 0f;
    private boolean cursorVisible = true;

    @Override
    public void update(float deltaTime) {
        if (!show)
            return;

        cursorTimer += deltaTime;

        if (cursorTimer >= 0.5f) {
            cursorTimer -= 0.5f;
            cursorVisible = !cursorVisible;
        }

    }

    // Call updateRotatedShape every time the position, size or rotation changes
    private void updateRotatedShape() {

        AffineTransform transform = new AffineTransform();
        Point middle = getCenter();

        transform.rotate(Math.toRadians(angle), middle.x, middle.y);
        rotatedShape = transform.createTransformedShape(baseShape);
    }

    @Override
    public boolean isHovered() {
        return isHovered;
    }

    @Override
    public void setHovered(boolean isHovered) {
        this.isHovered = isHovered;

        if (!isEnabled) {
            if (isHovered)
                CursorManager.setCursor(CursorType.NOT_ALLOWED);
            else
                CursorManager.setCursor(CursorType.DEFAULT);
            return;
        }

        if (isHovered)
            CursorManager.setCursor(CursorType.TEXT);
        else
            CursorManager.setCursor(CursorType.DEFAULT);

    }

    @Override
    public boolean contains(int mouseX, int mouseY) {
        return rotatedShape.contains(mouseX, mouseY);
    }

    @Override
    public void onClick(Runnable action) {
        clickAction = action;
    }

    @Override
    public void executeOnClick() {
        if (!isEnabled)
            return;

        if (clickAction != null)
            clickAction.run();
    }

    @Override
    public void onPressed() {
        if (!isEnabled)
            return;

        isFocused = true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void notifyPress(Clickable press) {
        if (press != this) {
            isFocused = false;
        }
    }

    @Override
    public void keyTypedNotification(KeyEvent e) {
        if (!isFocused || !show)
            return;

        if (highlightStartIndex == null)
            insertCharacter();
        else {
            removeMarkedCharacters();
            clearHighlight();
            insertCharacter();
        }
    }

    @Override
    public void keyPressedNotification(KeyEvent e) {
        if (!isFocused || !show)
            return;

        // <Control> dependent combinations
        if (keys.getKeysPressed().contains(KeyEvent.VK_CONTROL)) {

            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            if (highlightStartIndex != highlightEndIndex) {

                // Copy
                if (keys.getKeysPressed().contains(KeyEvent.VK_C)) {
                    try {
                        clipboard.setContents(new StringSelection(getMarkedCharacters()), null);
                    } catch (IllegalStateException exception) {
                        ErrorManagement.reportError(exception,
                                "Clipboard may be occupied by another program and could not be accessed.");
                    }

                    // Paste
                } else if (keys.getKeysPressed().contains(KeyEvent.VK_X)) {
                    try {
                        clipboard.setContents(new StringSelection(getMarkedCharacters()), null);
                    } catch (IllegalStateException exception) {
                        ErrorManagement.reportError(exception,
                                "Clipboard may be occupied by another program and could not be accessed.");
                    }

                    removeMarkedCharacters();
                }
                // Cut
            } else if (keys.getKeysPressed().contains(KeyEvent.VK_V)
                    && clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                try {
                    paste((String) clipboard.getData(DataFlavor.stringFlavor));
                } catch (UnsupportedFlavorException | IOException exception) {
                    exception.printStackTrace();
                }
                return;
            }
        }

        cursorTimer = 0;
        cursorVisible = true;

        if (keys.getKeysPressed().contains(KeyEvent.VK_BACK_SPACE)) {
            if (text.length() == 0)
                return;

            if (highlightStartIndex != null) {
                removeMarkedCharacters();
                return;
            }

            if (caretOffset != text.length()) {
                text.deleteCharAt(text.length() - 1 - caretOffset);
                return;
            }
        }

        if (keys.getKeysPressed().contains(KeyEvent.VK_DELETE)) {
            if (text.length() == 0)
                return;

            if (highlightStartIndex != null) {
                removeMarkedCharacters();
                return;
            }

            if (caretOffset != 0) {
                text.deleteCharAt(text.length() - caretOffset);
                caretOffset--;
                return;
            }
        }

        // Caret manipulation
        if (keys.getKeysPressed().contains(KeyEvent.VK_HOME))
            caretOffset = text.length();

        if (keys.getKeysPressed().contains(KeyEvent.VK_END))
            caretOffset = 0;

        if (keys.getKeysPressed().contains(KeyEvent.VK_LEFT) && caretOffset < text.length()) {
            caretOffset++;
        }

        if (keys.getKeysPressed().contains(KeyEvent.VK_RIGHT) && caretOffset > 0) {
            caretOffset--;
        }

        // Highlighting
        if (keys.getKeysPressed().contains(KeyEvent.VK_SHIFT)) {
            if (highlightStartIndex == null)
                highlightStartIndex = text.length() - caretOffset;

            highlightEndIndex = text.length() - caretOffset;
            setMarkedCharacters(highlightStartIndex, highlightEndIndex);

        } else if (keys.getKeysPressed().contains(KeyEvent.VK_LEFT) || keys.getKeysPressed().contains(KeyEvent.VK_RIGHT)
                || keys.getKeysPressed().contains(KeyEvent.VK_END) || keys.getKeysPressed().contains(KeyEvent.VK_HOME))

            clearHighlight();

    }

    @Override
    public void mousePressNotification(MouseEvent e) {
        if (!show)
            return;

        Point2D point = panel.getTranslatedPoint(new Point(e.getX(), e.getY()), layout);

        if (!contains((int) point.getX(), (int) point.getY())) {
            isFocused = false;
            return;
        } else {
            cursorTimer = 0;
            cursorVisible = true;
            setCaretAt((int) point.getX(), (int) point.getY());

            if (keys.getKeysPressed().contains(KeyEvent.VK_SHIFT)) {
                if (highlightStartIndex == null)
                    highlightStartIndex = text.length() - caretOffset;
                else
                    highlightEndIndex = text.length() - caretOffset;

                setMarkedCharacters(highlightStartIndex, highlightEndIndex);
            } else
                clearHighlight();
        }
    }

    @Override
    public void mouseMovementNotification(int x, int y, boolean dragging) {
        if (!isFocused || !show)
            return;

        Point2D point = panel.getTranslatedPoint(new Point(x, y), layout);

        if (dragging) {
            cursorTimer = 0;
            cursorVisible = true;

            setCaretAt((int) point.getX(), (int) point.getY());

            if (highlightStartIndex == null)
                highlightEndIndex = highlightStartIndex = text.length() - caretOffset;
            else
                highlightEndIndex = text.length() - caretOffset;

            setMarkedCharacters(highlightStartIndex, highlightEndIndex);
        }
    }

    private void setMarkedCharacters(int start, int end) {

        if (start > end) {
            int tmp = start;
            start = end;
            end = tmp;
        }

        highlightStartX = x + 10
                + fontMetrics.stringWidth(text.substring(0, start));

        highlightWidth = fontMetrics.stringWidth(
                text.substring(start, end));
    }

    private String getMarkedCharacters() {
        int start;
        int end;

        if (highlightStartIndex == null)
            return null;

        if (highlightStartIndex > highlightEndIndex) {
            int tmp = highlightStartIndex;
            start = highlightEndIndex;
            end = tmp;
        } else {
            start = highlightStartIndex;
            end = highlightEndIndex;
        }

        return text.substring(start, end);

    }

    private void clearHighlight() {
        highlightStartX = 0;
        highlightWidth = 0;
        highlightStartIndex = null;
        highlightEndIndex = null;
    }

    private void setCaretAt(int x, int y) {
        int caretPosition = 0;
        int closestDistance = Integer.MAX_VALUE;

        int caretX = this.x + 10;

        for (int i = 0; i <= text.length(); i++) {

            int distance = Math.abs(x - caretX);

            if (distance < closestDistance) {
                closestDistance = distance;
                caretPosition = i;
            }

            if (i < text.length()) {
                caretX += fontMetrics.charWidth(text.charAt(i));
            }
        }

        caretOffset = text.length() - caretPosition;
    }

    private void paste(String content) {

        if (highlightStartIndex != null) {
            removeMarkedCharacters();
        }

        text = new StringBuffer(text.substring(0, text.length() - caretOffset) + content
                + text.substring(text.length() - caretOffset, text.length()));

    }

    private void removeMarkedCharacters() {
        int start;
        int end;

        if (highlightStartIndex > highlightEndIndex) {
            int tmp = highlightStartIndex;
            start = highlightEndIndex;
            end = tmp;
        } else {
            start = highlightStartIndex;
            end = highlightEndIndex;
        }

        text = new StringBuffer(
                text.substring(0, start) + text.substring(end, text.length()));

        caretOffset = text.length() - start;
        clearHighlight();
    }

    private void insertCharacter() {
        Character input = keys.pollTypedCharacter();

        if (input != null) {
            text.insert(text.length() - caretOffset, input);
        }
    }
}
