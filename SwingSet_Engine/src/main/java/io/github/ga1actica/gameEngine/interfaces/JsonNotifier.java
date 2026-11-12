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

package io.github.ga1actica.gameEngine.interfaces;

import io.github.ga1actica.utils.jsonUtils.JsonBacked;

/**
 * A simple notifier for importing and exporting to and from JSON files.
 */
public interface JsonNotifier {

    /**
     * Invoked when exporting to a JSON file was successfully executed.
     * <p>
     * <b>Note:</b> This method is only supposed to be invoked by
     * {@link JsonBacked}.
     * 
     * @param path the path that the JSON was exported to
     */
    public void successfulExportNotification(String path);

    /**
     * Invoked when importing from a JSON file was successfully executed.
     * <p>
     * <b>Note:</b> This method is only supposed to be invoked by
     * {@link JsonBacked}.
     * 
     * @param path the path that the JSON was imported from
     */
    public void successfulImportNotification(String path);
}
