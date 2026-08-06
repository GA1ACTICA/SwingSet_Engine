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
    public enum UIElementLayout {
        TOP_ALIGNED,
        BOTTOM_ALIGNED,
        LEFT_ALIGNED,
        RIGHT_ALIGNED,
        NONE
    }

    public UIElementLayout getLayout();

    public void setLayout(UIElementLayout layout);
}
