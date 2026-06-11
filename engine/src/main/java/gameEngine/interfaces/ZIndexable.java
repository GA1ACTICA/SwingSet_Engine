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
 * Marks an element as having a z-index that controls its ordering in the
 * rendering pipeline.
 *
 * <p>
 * This ordering defines the visual stacking of elements and may also be
 * used for resolving overlapping interactions.
 * </p>
 */
public interface ZIndexable {

    /**
     * Sets the z-index of this object and updates its rendering priority
     * within the engine context.
     *
     * @param zIndex The new z-index value.
     */
    void setZIndex(int zIndex);

    int getZIndex();
}
