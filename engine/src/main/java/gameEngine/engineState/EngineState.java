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

import utils.jsonUtils.JsonBacked;

public class EngineState extends JsonBacked<EngineStateData> {

    EngineState state;

    public EngineState() {
        super(new EngineStateData());
    }

    public void setGameStateData(EngineState state) {
        this.state = state;
    }

    @Override
    protected void successfulExportLog(EngineStateData object, String path) {
        if (state.data().debug)
            System.out.println('\n' + "Successfully exported ('%s') to %s".formatted(object, path));
    }

    @Override
    protected void successfulImportLog(EngineStateData object, String path) {
        if (state.data().debug)
            System.out.println('\n' + "Successfully imported ('%s') to %s".formatted(path, object));
    }

}
