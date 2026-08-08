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
import java.awt.geom.Point2D;

import gameEngine.engineModules.cursor.CursorManager;
import gameEngine.interfaces.Clickable;
import gameEngine.interfaces.Hoverable;
import gameEngine.interfaces.drawables.UIDrawable;

/**
 * Manages the mouse such as hover priority and click events
 */
class MouseManager {

    static Hoverable lastHovered = null;
    static Hoverable topMost = null;
    static Clickable currentTopMost = null;

    static void handlePriority(EngineContext context, Point mousePoint, EnginePanel panel) {
        if (!CursorManager.isVisible())
            return;

        // Find the topmost hoverable under the mouse
        for (Hoverable hoverable : context.getHoverables()) {
            if (!hoverable.isVisible())
                continue;

            Point2D translatedMousePoint = mousePoint;

            if (hoverable instanceof UIDrawable uiDrawable)
                translatedMousePoint = panel.getTranslatedPoint(mousePoint, uiDrawable.getLayout());

            if (hoverable.contains(
                    (int) translatedMousePoint.getX(),
                    (int) translatedMousePoint.getY())) {

                topMost = hoverable;

                return;
            } else {
                topMost = null;
            }
        }

    }

    static void handleClick(EngineContext context, boolean mouseState) {
        if (!CursorManager.isVisible())
            return;

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

    static void handleHover(EngineContext context) {
        if (!CursorManager.isVisible())
            return;

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
