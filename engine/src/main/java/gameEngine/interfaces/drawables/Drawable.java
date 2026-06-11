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

package gameEngine.interfaces.drawables;

import java.awt.Graphics;

import gameEngine.interfaces.ZIndexable;

/**
 * Represents a drawable element in the engine.
 * <p>
 * Implementations are rendered using a graphics context that is pre-configured
 * by the engine. The context is centered within the window and may be scaled.
 * <p>
 * Draw order is determined by the {@link ZIndexable} interface.
 */
public interface Drawable extends ZIndexable {

    /**
     * Draws the element using the provided graphics context.
     *
     * @param g the {@code Graphics} context to draw with
     */
    void draw(Graphics g);
}