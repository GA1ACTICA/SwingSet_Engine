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

import gameEngine.interfaces.JSONNotifier;
import utils.jsonUtils.JsonBacked;

class AnimatedCursor extends JsonBacked<AnimatedCursorData> implements JSONNotifier {

    protected AnimatedCursor(AnimatedCursorData initialData) {
        super(new AnimatedCursorData());
    }

    @Override
    public void successfulExportNotification(String path) {
    }

    @Override
    public void successfulImportNotification(String path) {
    }
}
