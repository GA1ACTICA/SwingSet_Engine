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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
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
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.io.IOException;

import gameEngine.interfaces.*;
import gameEngine.engineModules.*;
import gameEngine.engineModules.cursor.*;
import gameEngine.interfaces.MenuInterface.*;
import gameEngine.interfaces.drawables.UIDrawable;
import utils.ErrorManagement;
import utils.GraphicsUtils;
import utils.GraphicsUtils.MaskType;

public class TextField
        implements MouseNotifier, KeyNotifier, Clickable, Hoverable, UIDrawable, Updatable, MenuInterface, MenuSetSize,
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
    private Color color = GraphicsUtils.rgb(187, 187, 187);
    private Color hoverColor = GraphicsUtils.rgb(209, 209, 209);
    private Color pressedColor = GraphicsUtils.rgb(230, 230, 230);

    // Images
    private Image image;
    private Image hoverImage;
    private Image pressedImage;

    private Font fieldFont = new Font(Font.SANS_SERIF, Font.PLAIN, 0);
    private StringBuffer text = new StringBuffer("");
    private FontMetrics fontMetrics;

    private Runnable clickAction;

    private EngineContext context;
    private Keys keys;

    // Behavioral variables
    private boolean isHovered;
    private boolean isFocused;
    private boolean isEnabled;
    private boolean isPressed;

    private boolean showPress = true;
    private boolean showHover = true;

    private int caretOffset;
    private int highlightStartX = 0;
    private int highlightWidth = 0;
    private Integer highlightStartIndex = null;
    private Integer highlightEndIndex = null;

    /**
     * 
     * Important: TextField class is currently under development and is not to be
     * used!
     * 
     * @param mouse
     * 
     * @param context
     * 
     * @param x
     * 
     * @param y
     * 
     * @param width
     * 
     * @param height
     */
    public TextField(EngineContext context, Mouse mouse, Keys keys, int x, int y, int width,
            int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.keys = keys;

        this(context);
    }

    /**
     * 
     * Important: TextField class is currently under development and is not to be
     * used!
     * 
     * @param mouse
     * 
     * @param context
     * 
     * @param topLeft
     * 
     * @param bottomRight
     */
    public TextField(EngineContext context, Mouse mouse, Keys keys, Point topLeft,
            Point bottomRight) {

        x = (int) topLeft.getX();
        y = (int) topLeft.getY();
        width = (int) bottomRight.getX() - (int) topLeft.getX();
        height = (int) bottomRight.getY() - (int) topLeft.getY();
        this.keys = keys;

        this(context);
    }

    /**
     * 
     * Important: TextField class is currently under development and is not to be
     * used!
     * 
     * @param mouse
     * 
     * @param context
     * 
     * @param middle
     * 
     * @param width
     * 
     * @param height
     */
    public TextField(EngineContext context, Mouse mouse, Keys keys, Point middle, int width,
            int height) {

        x = (int) middle.getX() - width / 2;
        y = (int) middle.getY() - height / 2;
        this.width = width;
        this.height = height;
        this.keys = keys;

        this(context);

    }

    private TextField(EngineContext context) {
        ClassFactory.create(this, context);

        this.baseShape = new Rectangle2D.Float(x, y, width, height);
        this.rotatedShape = baseShape;
        updateRotatedShape();

        fieldFont = GraphicsUtils.matchFontToHeight(fieldFont, "ÅÄÖgjpqÉÁÂÂÂÂ",
                height - 2);
    }

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

    public void setColor(Color color) {
        this.color = color;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setHoverImage(Image hoverImage) {
        this.hoverImage = hoverImage;
    }

    public void setMiddle(Point middle) {
        x = (int) middle.getX() - width / 2;
        y = (int) middle.getY() - height / 2;
        baseShape.setFrame(x, y, width, height);

        updateRotatedShape();
    }

    public void setRotation(double angle) {
        this.angle = angle;

        updateRotatedShape();
    }

    public void setFont(Font font) {
        fieldFont = GraphicsUtils.matchFontToHeight(fieldFont, "ÅÄÖgjpqÉÁÂÂÂÂ",
                height - 2);
    }

    /**
     * Enables or disables the visual click effect (color or image change)
     * when the button is pressed.
     *
     * @param isEnabled true to enable the click effect, false to disable it
     */
    public void setClickEffectEnabled(boolean isEnabled) {
        showPress = isEnabled;
    }

    /**
     * Enables or disables the visual hover effect (color or image change)
     * when the button is hovered.
     *
     * @param hoverEffect true to enable the hover effect, false to disable it
     */
    public void setHoverEffectEnabled(boolean hoverEffect) {
        showHover = hoverEffect;
    }

    // Get methods
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Point getMiddlePoint() {
        return new Point(x + width / 2, y + height / 2);
    }

    public double getAngle() {
        return angle;
    }

    // Get color
    public Color getColor() {
        return color;
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    public Color getPressedColor() {
        return pressedColor;
    }

    // Get image
    public Image getImage() {
        return image;
    }

    public Image getHoverImage() {
        return hoverImage;
    }

    public Image getPressedImage() {
        return pressedImage;
    }

    public StringBuffer getText() {
        return text;
    }

    @Override
    public void draw(Graphics g) {
        if (!show)
            return;

        fontMetrics = g.getFontMetrics(fieldFont);

        Graphics2D g2d = (Graphics2D) g;

        GraphicsUtils.rotateGraphics(g2d, angle, getMiddlePoint(), (gRotate) -> {
            Image image;
            Color color;

            if (isPressed && showPress) {
                color = pressedColor;
                image = pressedImage;

            } else if (isHovered && showHover) {
                color = hoverColor;
                image = hoverImage;

            } else {
                color = this.color;
                image = this.image;
            }

            if (image == null) {
                gRotate.setColor(color);
                gRotate.fillRect(x, y, width, height);
            } else
                gRotate.drawImage(image, x, y, width, height, null);

            GraphicsUtils.createMask(gRotate, baseShape, MaskType.INSIDE,
                    (gMask) -> {

                        Composite old = gMask.getComposite();
                        gMask.setComposite(AlphaComposite.SrcOver);

                        if (isFocused)
                            gMask.setColor(GraphicsUtils.rgba(0, 174, 255, 0.16));
                        else
                            gMask.setColor(GraphicsUtils.rgba(0, 0, 0, 0.16));

                        gMask.fillRect(highlightStartX, y, highlightWidth, height);

                        if (cursorVisible && isFocused) {
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

                        gMask.setComposite(old);
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
        Point middle = getMiddlePoint();

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

        if (isHovered)
            CursorManager.setCursor(CursorType.TEXT);
        else
            CursorManager.setCursor(CursorType.DEFAULT);

    }

    @Override
    public boolean contains(int mouseX, int mouseY) {
        return rotatedShape.contains(new Point(mouseX, mouseY));
    }

    @Override
    public void onClick(Runnable action) {
        clickAction = action;
    }

    @Override
    public void executeOnClick() {
        if (clickAction != null)
            clickAction.run();
    }

    @Override
    public void onPressed() {
        isFocused = true;
        isPressed = true;
    }

    @Override
    public void onReleased() {
        isPressed = false;
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

        System.out.println("Start: " + highlightStartIndex + " End: " + highlightEndIndex);
        System.out.println(highlightStartIndex != highlightEndIndex);

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
        if (!contains(e.getX(), e.getY()) || !isFocused || !show) {
            isFocused = false;
            return;
        } else {
            cursorTimer = 0;
            cursorVisible = true;
            setCaretAt(e.getX(), e.getY());

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

        if (dragging) {
            cursorTimer = 0;
            cursorVisible = true;

            setCaretAt(x, y);

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

        System.out.println("Start: " + start + " End: " + end);
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
