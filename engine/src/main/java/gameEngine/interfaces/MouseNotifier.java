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

public interface MouseNotifier {

    /**
     * Invoked when a click event is detected on any part of the window.
     * <p>
     * A click is usually represented as both a press and a release.
     */
    default void mouseClickNotification(int x, int y) {
    };

    /**
     * Invoked when a press event is detected on any part of the window.
     */
    default void mousePressNotification(MouseEvent e) {
    };

    /**
     * Invoked when a release event is detected on any part of the window.
     */
    default void mouseReleaseNotification(MouseEvent e) {
    };

    /**
     * Invoked when a scroll event is detected.
     */
    default void mouseScrollNotification(float deltaScroll) {
    };

    /**
     * Invoked when a mouse movement or dragging event is detected.
     */
    default void mouseMovementNotification(int x, int y, boolean dragging) {
    };

}
