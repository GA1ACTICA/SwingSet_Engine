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

package utils.jsonUtils;

import java.awt.Color;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import gameEngine.interfaces.JsonNotifier;
import utils.ErrorManagement;
import utils.jsonUtils.adapters.ColorAdapter;

/**
 * Base class for objects whose state can be persisted to and restored from
 * JSON files.
 *
 * <p>
 * This class manages a data object of type {@code T} and provides methods
 * for serializing it to JSON and deserializing it from JSON using Gson.
 * </p>
 *
 * <p>
 * Subclasses may implement {@code JSONNotifier} to receive notifications
 * when import and export operations complete successfully.
 * </p>
 *
 * @param <T> the type of data managed by this instance
 */
public abstract class JsonBacked<T> {

    private final Gson gson = new GsonBuilder().registerTypeAdapter(Color.class, new ColorAdapter())
            .setPrettyPrinting().create();

    private T data;
    private final Class<T> type;

    /**
     * Creates a new JSON-backed wrapper for the specified data type and initial
     * data.
     *
     * @param clazz       the class representing the data type
     * @param initialData the initial data object
     */
    protected JsonBacked(Class<T> clazz, T initialData) {
        this.data = initialData;
        this.type = clazz;
    }

    /**
     * Returns the managed data object.
     *
     * @return the current data object
     */
    public T data() {
        return data;
    }

    /**
     * Serializes the current data object to JSON and writes it to the specified
     * file or creates a new file.
     *
     * @param path the path of the file to write
     */
    public void exportJson(String path) {

        try (FileWriter writer = new FileWriter(path)) {

            // Export to Json
            gson.toJson(data, writer);

            if (this instanceof JsonNotifier notifier)
                notifier.successfulExportNotification(path);

        } catch (Exception e) {
            ErrorManagement.throwError(e, "Error exporting JSON file ('%s')".formatted(path));
        }
    }

    /**
     * Reads JSON from the specified file and replaces the current data object with
     * the deserialized result.
     *
     * @param path the path of the JSON file to read
     */
    public void importJson(String path) {

        try (FileReader reader = new FileReader(path)) {

            // Import Json and set data
            data = gson.fromJson(reader, type);

            if (this instanceof JsonNotifier notifier)
                notifier.successfulImportNotification(path);

        } catch (Exception e) {
            ErrorManagement.throwError(e, "Error importing JSON file ('%s')".formatted(path));
        }
    }
}
