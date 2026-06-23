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

package uiElements.menuContainer;

import java.awt.*;

import gameEngine.engineModules.ClassFactory;
import gameEngine.engineModules.EngineContext;
import gameEngine.interfaces.MenuInterface.VisibleMenuInterface;
import gameEngine.interfaces.Painter;
import gameEngine.interfaces.drawables.UIDrawable;

public class GraphicalUIContainer extends UIContainer
        implements UIDrawable, VisibleMenuInterface {

    private boolean show;

    private int zIndex = -1;

    private int x = 100;
    private int y = 100;
    private int width = 200;
    private int height = 200;
    private Painter customDrawAction = (g) -> {
        g.setColor(new Color(10, 10, 10, 125));
        g.fillRect(x, y, width, height);

        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.PLAIN, 25));
        g.drawString("This is a menu", x + (int) (width / 2), y + (int) (height / 2));
    };

    @Override
    public boolean isVisible() {
        return show;
    }

    private EngineContext context;

    /**
     * Creates a standalone menu with no initial UI elements and registers it
     * with the engine using the provided context.
     * <p>
     * The menu has a default z-index of -1, placing it behind standard UI
     * elements (which typically use a z-index of 0). This causes it to
     * behave as a background layer.
     *
     * <p>
     * As a subclass of {@link UIContainer}, GameMenu provides a graphical
     * context for rendering.
     *
     * @param context the engine context containing objects used for rendering,
     *                updating, and input handling
     */
    public GraphicalUIContainer(EngineContext context) {
        ClassFactory.create(this, context, zIndex);
    };

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
    public void show() {
        show = true;
        super.show();
    }

    @Override
    public void hide() {
        show = false;
        super.hide();
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        super.setSize(width, height);
    }

    @Override
    public void translateSize(int dWidth, int dHeight) {
        width += dWidth;
        height += dHeight;
        super.translateSize(dWidth, dHeight);
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        super.setPosition(x, y);
    }

    @Override
    public void setPosition(Point position) {
        x = position.x;
        y = position.y;
        super.setPosition(position);
    }

    @Override
    public void translatePosition(int dx, int dy) {
        x += dx;
        y += dy;
        super.translatePosition(dx, dy);
    }

    @Override
    public void draw(Graphics g) {
        if (!show)
            return;

        Graphics2D g2d = (Graphics2D) g;

        customDrawAction.paint(g2d);
    }

    /**
     * Sets a custom drawing action used to render the menu background.
     * <p>
     * Unlike standard UI elements, menus may require more flexible or
     * complex rendering. This method allows a custom {@link Painter} to be
     * provided for drawing the menu's background.
     * <p>
     * This is used internally for the background:
     * 
     * <pre>{@code
     * private Painter customDrawAction = (g) -> {
     *     g.setColor(new Color(10, 10, 10, 125));
     *     g.fillRect(x, y, width, height);
     * 
     *     g.setColor(Color.BLACK);
     *     g.setFont(new Font("SansSerif", Font.PLAIN, 25));
     *     AdvancedGraphics.centerAlignedString(g, x + width / 2, (int) (y + height * 0.2),
     *             "This is a menu");
     * };
     * }</pre>
     *
     * @param customDrawAction the drawing logic used to render the background
     */
    public void setCustomDrawAction(Painter customDrawAction) {
        this.customDrawAction = customDrawAction;
    }
}