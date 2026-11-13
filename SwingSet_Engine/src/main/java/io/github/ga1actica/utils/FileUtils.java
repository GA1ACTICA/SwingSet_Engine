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

package io.github.ga1actica.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.imageio.ImageIO;

/**
 * Utility methods for file operations.
 */
public class FileUtils extends Utils {

    private FileUtils() {
    }

    /**
     * Get an image from the specified file path.
     * <p>
     * <b>Note:</b> The type of BufferedImage isn't guaranteed and should always be
     * checked if necessary.
     * 
     * @param path path to the image file, relative to the project
     *             resources.
     * 
     * @return the buffered image loaded from the given file path.
     * 
     */
    public static BufferedImage getBufferedImage(String path) {

        if (Path.of(path).isAbsolute()) {
            try {
                return ImageIO.read(new File(path));
            } catch (IOException e) {
                ErrorManagement.throwError(e, "Failed to load resource from absolute path: " + path);
            }
        }

        try (InputStream input = FileUtils.class
                .getClassLoader()
                .getResourceAsStream(path)) {

            if (input == null) {
                ErrorManagement.throwError(new IllegalArgumentException(),
                        "Failed to load resource from classpath path: " + path);
            }

            return ImageIO.read(input);
        } catch (IOException e) {
            ErrorManagement.throwError(e, "Failed to load resource from classpath path: " + path);
        }

        // Shouldn't be accessible;
        return null;
    }

}
