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

    public boolean debug = false;
    public boolean debugVerbose = false;

    public float updateInterval = 1000000000 / 60;
    public Color backgroundColor = Color.WHITE;

    public int width = 1000;
    public int height = 1000;
}
