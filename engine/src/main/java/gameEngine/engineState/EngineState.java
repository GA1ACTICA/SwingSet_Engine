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

import gameEngine.interfaces.JsonNotifier;
import utils.Utils;
import utils.jsonUtils.JsonBacked;
import gameEngine.engineModules.Game;

/**
 * Manages the application's {@link EngineStateData} instance, providing
 * JSON-backed access to the engine's persistent state.
 * <p>
 * {@code EngineState} is managed by {@link Game} and is not intended to be
 * instantiated directly.
 */
public final class EngineState extends JsonBacked<EngineStateData> implements JsonNotifier {

    /**
     * Creates a new {@link EngineState} with a new {@link EngineStateData}
     * instance.
     * <p>
     * This constructor is intended only for internal initialization. Creating
     * additional instances may result in state becoming inconsistent with the
     * shared {@link EngineState} managed by {@link Game}.
     */
    public EngineState() {
        super(EngineStateData.class, new EngineStateData());
    }

    @Override
    public void successfulExportNotification(String path) {
        if (data().debug)
            System.out.println("%s Successfully exported EngineStateData to %s %s".formatted(Utils.ConsoleGREEN, path,
                    Utils.ConsoleRESET));
    }

    @Override
    public void successfulImportNotification(String path) {
        if (data().debug)
            System.out.println("%s Successfully imported EngineStateData from %s %s".formatted(Utils.ConsoleGREEN, path,
                    Utils.ConsoleRESET));
    }

}
