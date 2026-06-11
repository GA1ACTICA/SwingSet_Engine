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

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class AnimatedCursorData {

    private List<Frame> frames;

    List<Frame> getFrames() {
        return frames;
    }

    static class Frame {

        @SerializedName("image")
        private String imagePath;

        private int durationMs;
        private int[] hotspot;

        String getImagePath() {
            return imagePath;
        }

        int getDurationMs() {
            return durationMs;
        }

        int[] getHotspot() {
            return hotspot;
        }

        int getHotspotX() {
            return hotspot[0];
        }

        int getHotspotY() {
            return hotspot[1];
        }
    }
}
