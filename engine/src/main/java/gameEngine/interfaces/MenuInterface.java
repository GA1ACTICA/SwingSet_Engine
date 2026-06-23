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

package gameEngine.interfaces;

import java.awt.Point;

import uiElements.button.RectButton;
import uiElements.misc.UPSCounter;

/**
 * Base interface for all menu elements.
 * <p>
 * All menu elements support visibility control via {@link #show()} and
 * {@link #hide()}.
 *
 * <p>
 * Additional behavior is defined through optional capability interfaces.
 * A element may implement one or more of these to expose extra functionality.
 *
 * <p>
 * Examples of optional capabilities:
 * <ul>
 * <li>{@link MenuSetColor} — allows changing color</li>
 * <li>{@link MenuSetSize} — allows resizing</li>
 * <li>{@link MenuSetPosition} — allows positioning</li>
 * </ul>
 *
 * <p>
 * Not all element support all capabilities. Callers should check
 * whether a element implements a capability before using it.
 */
public interface MenuInterface {

    /**
     * Shows the element implementing the {@link MenuInterface}.
     */
    void show();

    /**
     * Hides the element implementing the {@link MenuInterface}.
     */
    void hide();

    public interface VisibleMenuInterface extends MenuInterface {
        /**
         * Returns whether or not the element implementing the {@link MenuInterface}
         * is visible.
         * 
         * @return {@code true} if the element is visible, {@code false} otherwise
         */
        boolean isVisible();
    }

    /**
     * Capability for elements that support resizing.
     * <p>
     * Implemented by elements such as {@link RectButton}
     */
    public interface MenuSetSize {

        /**
         * Sets the element's size relative to its current size.
         * 
         * @param dWidth  the width offset
         * 
         * @param dHeight the height offset
         */
        void setSize(int width, int height);

        /**
         * Changes the element's size relative to its current size.
         * 
         * @param dWidth  the width offset
         * 
         * @param dHeight the height offset
         */
        void translateSize(int dWidth, int dHeight);
    }

    /**
     * Capability for elements that support positioning.
     * <p>
     * Implemented by elements such as {@link UPSCounter}
     */
    public interface MenuSetPosition {

        /**
         * Sets the element's position with x and y coordinates.
         * 
         * @param x The X coordinate
         * 
         * @param y The Y coordinate
         */
        void setPosition(int x, int y);

        /**
         * Sets the element's position with the {@link Point Points} position.
         * 
         * @param position The point
         */
        void setPosition(Point position);

        /**
         * Moves the element relative to its current position.
         *
         * @param dx the horizontal offset
         * 
         * @param dy the vertical offset
         */
        void translatePosition(int dx, int dy);
    }
}