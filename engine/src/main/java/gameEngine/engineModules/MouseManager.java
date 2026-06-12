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

package gameEngine.engineModules;

import java.awt.Point;

import gameEngine.interfaces.Clickable;
import gameEngine.interfaces.Hoverable;

/**
 * Manages the mouse such as hover priority and click events
 */
class MouseManager {

    static Hoverable lastHovered = null;
    static Hoverable topMost = null;
    static Clickable currentTopMost = null;

    static void handlePriority(EngineContext context, Point mousePoint) {

        // Find the topmost hoverable under the mouse
        for (Hoverable hoverable : context.getHoverables()) {
            if (!hoverable.isVisible())
                continue;

            if (hoverable.contains(mousePoint.x, mousePoint.y)) {
                topMost = hoverable;
                return;
            } else {
                topMost = null;
            }
        }

    }

    static void handleClick(EngineContext context, Point mousePoint, boolean mouseState) {

        // mouse DOWN
        if (mouseState) {

            if (currentTopMost == null && topMost instanceof Clickable clickable) {
                currentTopMost = clickable;
                clickable.onPressed();
            }

            for (Hoverable hoverable : context.getHoverables()) {
                if (!hoverable.isVisible())
                    continue;
                if (hoverable instanceof Clickable clickable)
                    clickable.notifyPress((Clickable) topMost);

            }

            // mouse UP
        } else {

            if (currentTopMost != null) {

                if (currentTopMost == topMost) {
                    currentTopMost.executeOnClick();
                }

                currentTopMost.onReleased();
                currentTopMost = null;
            }

            for (Hoverable hoverable : context.getHoverables()) {
                if (!hoverable.isVisible())
                    continue;
                if (hoverable instanceof Clickable clickable)
                    clickable.notifyClick((Clickable) topMost);

            }
        }

    }

    static void handleHover(EngineContext context, Point mousePoint) {

        // If hover target changed
        if (lastHovered != topMost) {

            // Fire exit on previous
            if (lastHovered != null)
                lastHovered.setHovered(false);

            // Fire enter on new
            if (topMost != null)
                topMost.setHovered(true);

            lastHovered = topMost;
        }
    }
}
