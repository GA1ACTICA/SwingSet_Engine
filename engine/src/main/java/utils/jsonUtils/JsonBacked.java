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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import gameEngine.interfaces.JsonNotifier;
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
     * file, creating the file if it does not already exist.
     * <p>
     * If this class also implements {@link JsonNotifier} a notification is sent
     * after a successful export.
     *
     * @param path the path to the JSON file
     *
     * @throws IOException if the file cannot be created or written
     */
    public void exportJson(String path) throws IOException {

        try (FileWriter writer = new FileWriter(path)) {
            // Export to Json
            gson.toJson(data, writer);
        }

        if (this instanceof JsonNotifier notifier)
            notifier.successfulExportNotification(path);
    }

    /**
     * Reads JSON from the specified file and replaces the current data object with
     * the deserialized result.
     * <p>
     * If this class also implements {@link JsonNotifier} a notification will be
     * sent if the importation was successful.
     *
     * @param path the path of the JSON file to read
     * 
     * @throws IOException if the file cannot be opened or read
     * 
     * @see #importJson(Path)
     * 
     * @see #importJson(InputStream)
     */
    public void importJson(String path) throws IOException {
        importJson(Path.of(path));
    }

    /**
     * Reads JSON from the specified file and replaces the current data object with
     * the deserialized result.
     * <p>
     * If this class also implements {@link JsonNotifier} a notification will be
     * sent if the importation was successful.
     *
     * @param path the path of the JSON file to read
     * 
     * @throws IOException if the file cannot be opened or read
     * 
     * @see #importJson(InputStream)
     * 
     * @see #importJson(String)
     */
    public void importJson(Path path) throws IOException {
        try (InputStream stream = Files.newInputStream(path)) {
            importJson(stream);
        }

        if (this instanceof JsonNotifier notifier)
            notifier.successfulImportNotification(path.toString());
    }

    /**
     * Reads JSON from the provided input stream and replaces the current data
     * object with the deserialized result.
     *
     * @param stream the input stream supplying the JSON data
     *
     * @throws JsonSyntaxException if the JSON is malformed
     */
    public void importJson(InputStream stream) throws JsonSyntaxException {

        Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);

        // Import Json and set data
        data = gson.fromJson(reader, type);
    }

}
