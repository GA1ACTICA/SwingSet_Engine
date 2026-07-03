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

package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

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
        URL url = FileUtils.class.getClassLoader().getResource(path);

        if (url == null)
            ErrorManagement.reportError(new IllegalArgumentException("Classpath resource not found: " + path),
                    "Failed to locate image at " + path);

        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            ErrorManagement.throwError(e, "Failed to load image at %s".formatted(path));
            return null;
        }
    }

}
