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

package utils.jsonUtils.adapters;

import java.io.IOException;

import java.awt.Color;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class ColorAdapter extends TypeAdapter<Color> {

    @Override
    public void write(JsonWriter writer, Color color) throws IOException {
        if (color == null) {
            writer.nullValue();
            return;
        }

        String rgba = color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "," + color.getAlpha();
        writer.value(rgba);
    }

    @Override
    public Color read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        String[] parts = in.nextString().split(",");

        int[] rgba = new int[4];
        for (int index = 0; index < 4; index++) {
            rgba[index] = Integer.parseInt(parts[index]);
        }

        return new Color(rgba[0], rgba[1], rgba[2], rgba[3]);
    }

}
