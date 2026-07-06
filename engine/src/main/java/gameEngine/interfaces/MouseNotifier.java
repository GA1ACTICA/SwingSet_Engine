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

import java.awt.event.MouseEvent;

/**
 * A notification interface for different kinds of mouse events.
 */
public interface MouseNotifier {

    /**
     * Invoked when a click event is detected on any part of the window.
     * <p>
     * A click is usually represented as both a press and a release.
     * <p>
     * <b>Note:</b> These coordinates are untranslated and may not align to
     * different transforms or scales that may be applied to rendering passes.
     * 
     * @param x the X coordinate
     * 
     * @param y the Y coordinate
     */
    default void mouseClickNotification(int x, int y) {
    };

    /**
     * Invoked when a press event is detected on any part of the window.
     * 
     * @param e the {@link MouseEvent} that happened
     */
    default void mousePressNotification(MouseEvent e) {
    };

    /**
     * Invoked when a release event is detected on any part of the window.
     * 
     * @param e the {@link MouseEvent} that happened
     */
    default void mouseReleaseNotification(MouseEvent e) {
    };

    /**
     * Invoked when a scroll event is detected.
     * 
     * @param deltaScroll the scroll distance during this frame
     */
    default void mouseScrollNotification(float deltaScroll) {
    };

    /**
     * Invoked when a mouse movement or dragging event is detected.
     * <p>
     * <b>Note:</b> These coordinates are untranslated and may not align to
     * different transforms or scales that may be applied to rendering passes.
     * 
     * @param x        the X coordinate
     * 
     * @param y        the Y coordinate
     * 
     * @param dragging {@code True} if the mouse is currently held down otherwise
     *                 {@code false}
     * 
     */
    default void mouseMovementNotification(int x, int y, boolean dragging) {
    };

}
