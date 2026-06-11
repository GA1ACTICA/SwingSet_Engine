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

import java.awt.Graphics2D;

import uiElements.menuContainer.GraphicalUIContainer;

/**
 * Functional interface representing a custom drawing action.
 * <p>
 * This interface allows predefining rendering logic that can be executed
 * during a component's draw phase.
 * <p>
 * Implementations receive a {@link Graphics2D} context and may perform
 * arbitrary drawing operations using it.
 *
 * <p>
 * Example usage from {@link GraphicalUIContainer}:
 * </p>
 *
 * <pre>{@code
 * private Painter customDrawAction = (g) -> {
 *     g.setColor(new Color(10, 10, 10, 125));
 *     g.fillRect(x, y, width, height);
 * 
 *     g.setColor(Color.BLACK);
 *     g.setFont(new Font("SansSerif", Font.PLAIN, 25));
 *     g.drawString("This is a menu", x + (int) (width / 2), y + (int) (height / 2));
 * };
 *
 * public void draw(Graphics g) {
 *     if (!show)
 *         return;
 *
 *     customDrawAction.paint((Graphics2D) g);
 * }
 * }</pre>
 */
@FunctionalInterface
public interface Painter {
    void paint(Graphics2D g);
}
