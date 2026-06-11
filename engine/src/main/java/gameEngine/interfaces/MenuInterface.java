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
 * Base interface for all menu components.
 * <p>
 * All menu components support visibility control via {@link #show()} and
 * {@link #hide()}.
 *
 * <p>
 * Additional behavior is defined through optional capability interfaces.
 * A component may implement one or more of these to expose extra functionality.
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
 * Not all components support all capabilities. Callers should check
 * whether a component implements a capability before using it.
 */
public interface MenuInterface {

    void show();

    void hide();

    /**
     * Capability for components that support resizing.
     * <p>
     * Implemented by components such as {@link RectButton}
     */
    public interface MenuSetSize {
        void setSize(int width, int height);

        /**
         * Changes the components size relative to its current size.
         * 
         * @param dWidth  the width offset
         * @param dHeight the height offset
         */
        void translateSize(int dWidth, int dHeight);
    }

    /**
     * Capability for components that support positioning.
     * <p>
     * Implemented by components such as {@link UPSCounter}
     */
    public interface MenuSetPosition {
        void setPosition(int x, int y);

        void setPosition(Point position);

        /**
         * Moves the component relative to its current position.
         *
         * @param dx the horizontal offset
         * @param dy the vertical offset
         */
        void translatePosition(int dx, int dy);
    }
}