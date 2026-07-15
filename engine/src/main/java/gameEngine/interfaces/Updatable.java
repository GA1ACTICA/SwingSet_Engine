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

import gameEngine.engineState.EngineStateData;

/**
 * Represents an object that participates in the game loop and updates
 * its state every tick/frame.
 *
 * <p>
 * Implementations should contain logic such as movement, physics,
 * AI behavior, or time-based state changes.
 * </p>
 */
@FunctionalInterface
public interface Updatable {
    /**
     * Performs a single update step.
     * 
     * @param deltaTime the time since the last update invocation
     * 
     * @see EngineStateData#updateInterval
     */
    void update(float deltaTime);
}
