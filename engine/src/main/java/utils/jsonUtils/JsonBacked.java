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

import gameEngine.interfaces.JSONNotifier;
import utils.ErrorManagement;
import utils.jsonUtils.adapters.ColorAdapter;

public abstract class JsonBacked<T> {

    static final Gson gson = new GsonBuilder().registerTypeAdapter(Color.class, new ColorAdapter())
            .setPrettyPrinting().create();

    protected T data;

    protected JsonBacked(T initialData) {
        this.data = initialData;
    }

    /**
     * @return T
     */
    public T data() {
        return data;
    }

    /**
     * Very work in progress!
     * 
     * {@code .exportJSON("conf.json");}
     * 
     * @param object
     * @param path
     */
    public void exportJSON(String path) {

        try (FileWriter writer = new FileWriter(path)) {

            // Export to Json
            gson.toJson(data, writer);

            if (this instanceof JSONNotifier notifier)
                notifier.successfulExportNotification(path);

        } catch (Exception e) {
            ErrorManagement.throwError(e, "Error exporting JSON file ('%s')".formatted(path));
        }
    }

    /**
     * Very work in progress!
     * 
     * {@code .importJSON(GameStateData.class, "conf.json");}
     * 
     * @param clazz
     * @param path
     */
    public void importJSON(Class<T> clazz, String path) {

        try (FileReader reader = new FileReader(path)) {

            // Import Json and set data
            data = gson.fromJson(reader, clazz);

            if (this instanceof JSONNotifier notifier)
                notifier.successfulImportNotification(path);

        } catch (Exception e) {
            ErrorManagement.throwError(e, "Error importing JSON file ('%s')".formatted(path));
        }
    }
}
