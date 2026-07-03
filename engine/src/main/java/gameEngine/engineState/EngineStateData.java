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

package gameEngine.engineState;

import java.awt.Color;

import gameEngine.interfaces.Updatable;

/**
 * 
 * Contains fields that govern how the engine behaves that also can be loaded
 * from JSON.
 * 
 * @see EngineState
 */
public final class EngineStateData {

    /**
     * Basic debug providing minimal engine messages. Good for game debugging.
     */
    public boolean debug = false;

    /**
     * Let me know everything debug. Good when you want your terminal filled with
     * information about everything happening.
     */
    public boolean debugVerbose = false;

    /**
     * The update interval that is invoking {@link Updatable#update(float)}.
     * <p>
     * {@code 1000000000 / 60} is around 60 ups.
     */
    public float updateInterval = 1000000000 / 60;

    /**
     * The background color used for the window.
     * <p>
     * <b>Note:</b> Transparent colors has varied compatibility depending on
     * platform.
     */
    public Color backgroundColor = Color.WHITE;

    /**
     * The initial window width.
     */
    public int width = 1000;

    /**
     * The initial window height.
     */
    public int height = 1000;
}
