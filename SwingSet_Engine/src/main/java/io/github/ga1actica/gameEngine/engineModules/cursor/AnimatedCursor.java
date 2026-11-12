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

package io.github.ga1actica.gameEngine.engineModules.cursor;

import io.github.ga1actica.gameEngine.engineState.EngineState;
import io.github.ga1actica.gameEngine.interfaces.JsonNotifier;
import io.github.ga1actica.utils.Utils;
import io.github.ga1actica.utils.jsonUtils.JsonBacked;

class AnimatedCursor extends JsonBacked<AnimatedCursorData> implements JsonNotifier {

    EngineState state;

    protected AnimatedCursor(AnimatedCursorData initialData, EngineState state) {
        super(AnimatedCursorData.class, new AnimatedCursorData());
        this.state = state;
    }

    @Override
    public void successfulExportNotification(String path) {
        if (state.data().debug)
            System.out.println("%s Successfully exported EngineStateData to %s %s".formatted(Utils.ConsoleGREEN, path,
                    Utils.ConsoleRESET));
    }

    @Override
    public void successfulImportNotification(String path) {
        if (state.data().debug)
            System.out.println("%s Successfully imported EngineStateData from %s %s".formatted(Utils.ConsoleGREEN, path,
                    Utils.ConsoleRESET));
    }
}
