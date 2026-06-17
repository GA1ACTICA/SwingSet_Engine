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

package gameEngine.engineModules.cursor;

import gameEngine.engineState.EngineState;
import gameEngine.interfaces.JsonNotifier;
import utils.Utils;
import utils.jsonUtils.JsonBacked;

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
