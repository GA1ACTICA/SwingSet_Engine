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

public final class EngineState extends JsonBacked<EngineStateData> implements JsonNotifier {

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
