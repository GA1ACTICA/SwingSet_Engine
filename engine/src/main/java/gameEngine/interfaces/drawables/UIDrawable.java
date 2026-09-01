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

import gameEngine.interfaces.ZIndexable;

/**
 * Represents a drawable UI element in the engine.
 * <p>
 * Implementations are rendered using a graphics context that is pre-configured
 * by the engine. The context is centered within the window and may be scaled.
 * <p>
 * Draw order is determined by the {@link ZIndexable} interface.
 */
public interface UIDrawable extends Drawable {

    /**
     * UIElementLayout represents different translations that "snap" UI elements to
     * different parts of the window.
     */
    public enum UIElementLayout {
        /**
         * Aligned to the {@code top} of the window.
         */
        TOP_ALIGNED,
        /**
         * Aligned to the {@code bottom} of the window.
         */
        BOTTOM_ALIGNED,
        /**
         * Aligned to the {@code left} of the window.
         */
        LEFT_ALIGNED,
        /**
         * Aligned to the {@code right} of the window.
         */
        RIGHT_ALIGNED,
        /**
         * Aligned to the {@code top left} of the window.
         */
        TOP_LEFT_ALIGNED,
        /**
         * Aligned to the {@code bottom left} of the window. (This the the normal swing
         * behavior)
         */
        BOTTOM_LEFT_ALIGNED,
        /**
         * Aligned to the {@code top right} of the window.
         */
        TOP_RIGHT_ALIGNED,
        /**
         * Aligned to the {@code bottom right} of the window.
         */
        BOTTOM_RIGHT_ALIGNED,
        /**
         * Aligned to the {@code center} of the window.
         */
        CENTERED
    }

    /**
     * Returns the layout of this UI element.
     * 
     * @return the layout
     */
    public UIElementLayout getLayout();

    /**
     * Sets the layout of this UI element.
     * 
     * @param layout the layout
     */
    public void setLayout(UIElementLayout layout);
}
