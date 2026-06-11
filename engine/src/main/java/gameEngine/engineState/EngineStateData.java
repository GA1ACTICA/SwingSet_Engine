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

public class EngineStateData {

    // here you can store variables that you use
    // for you game that should also be accessible in other classes

    public boolean debug = false;
    public boolean debugVerbose = false; // used in Keys.java and Mouse.java

    /**
     * An example update interval expressed in nanoseconds
     */
    public float exampleUpdateInterval = 1000000000 / 60; // ≈60fps expressed in nanoseconds
    public Color backgroundColor = Color.WHITE;

    // first used to set window dimension and then later used for window scaling
    // and drawing alignment
    public int width = 1000;
    public int height = 1000;
}
