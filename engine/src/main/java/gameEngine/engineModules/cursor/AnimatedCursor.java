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

import utils.jsonUtils.JsonBacked;

public class AnimatedCursor extends JsonBacked<AnimatedCursorData> {

    protected AnimatedCursor(AnimatedCursorData initialData) {
        super(new AnimatedCursorData());
    }

    @Override
    protected void successfulExportLog(AnimatedCursorData object, String path) {
        System.out.println();
    }

    @Override
    protected void successfulImportLog(AnimatedCursorData data, String path) {
        System.out.println("Successfully imported" + path + '\n');
    }
}
