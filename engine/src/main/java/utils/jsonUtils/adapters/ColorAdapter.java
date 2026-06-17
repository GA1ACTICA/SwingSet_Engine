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
        writer.beginArray();
        writer.value(color.getRed());
        writer.value(color.getGreen());
        writer.value(color.getBlue());
        writer.value(color.getAlpha());
        writer.endArray();
    }

    @Override
    public Color read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        int rgba[] = new int[4];

        in.beginArray();
        for (int i = 0; i < rgba.length; i++)
            rgba[i] = in.nextInt();

        return new Color(rgba[0], rgba[1], rgba[2], rgba[3]);
    }

}
