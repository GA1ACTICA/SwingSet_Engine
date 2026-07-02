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

/**
 * Represents an element that can be detected by the mouse cursor.
 * <p>
 * The engine uses this interface to determine which element is currently
 * under the mouse pointer. Hover state is managed by the engine and should
 * not be modified directly by implementations unless explicitly required.
 * <p>
 * Implementations must define their own hitbox via {@link #contains(int, int)}.
 * <p>
 * Rendering order and hover priority are determined by the {@link ZIndexable}
 * interface.
 */
public interface Hoverable extends ZIndexable {

    /**
     * Returns if the {@link Hoverable} element is hovered.
     * 
     * @return {@code true} if the element is hovered,
     *         {@code false} otherwise
     */
    boolean isHovered();

    /**
     * Sets the hovered state of this element.
     * <p>
     * This method is typically called by the engine during mouse processing.
     *
     * @param isHovered {@code true} if the element is hovered,
     *                  {@code false} otherwise
     */
    void setHovered(boolean isHovered);

    /**
     * Checks whether the specified point is within this clickable element.
     *
     * <p>
     * <b>Note:</b> This method does not concern itself if other objects overlap.
     * </p>
     * 
     * <p>
     * This method is used by the input handling system to determine whether
     * the element should respond to mouse interactions.
     * </p>
     *
     * @param mouseX the x-coordinate of the point
     * 
     * @param mouseY the y-coordinate of the point
     * 
     * @return {@code true} if the point is inside the clickable area,
     *         {@code false} otherwise
     */
    boolean contains(int mouseX, int mouseY);

    /**
     * Returns if the {@link Hoverable} element is visible.
     * 
     * @return {@code true} if the element is visible,
     *         {@code false} otherwise
     */
    boolean isVisible();
}
