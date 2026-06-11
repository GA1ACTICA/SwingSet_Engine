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

/**
 * Represents the drawable used for rendering the engine's custom cursor.
 * <p>
 * This interface is controlled by the engine and should not be implemented
 * by game objects. The cursor is always rendered on the top layer without
 * any transformations or scaling applied.
 */
@FunctionalInterface
public interface CursorDrawable {

    /**
     * Draws the cursor using the provided graphics context.
     *
     * @param g the {@code Graphics} context to draw with
     */
    void draw(Graphics g);
}
