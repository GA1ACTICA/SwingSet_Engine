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

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

import javax.swing.ImageIcon;

public class FileUtils extends Utils {

    /**
     * Get an image from the specified file path of the type {@code Image}.
     * 
     * @param path Path to the image file, relative to the project
     *             resources.
     * @return The Image loaded from the given file path.
     * 
     * @see Image
     */
    public static Image getImage(Path path) {
        try {
            return new ImageIcon(FileUtils.class.getClassLoader().getResource(path.toString())).getImage();
        } catch (Exception e) {
            ErrorManagement.throwError(e, "Failed to load image at %s".formatted(path));
            return null;
        }

    }

    /**
     * Get an image from the specified file path of the type {@code BufferedImage}.
     * 
     * @param path Path to the image file, relative to the project
     *             resources.
     * @return The BufferedImage loaded from the given file path.
     * 
     * @see BufferedImage
     */
    public static BufferedImage getBufferedImage(Path path, int type) {

        BufferedImage image = convertToBufferedImage(getImage(path), type);

        return image;
    }

    /**
     * Converts an {@code Image} to a {@code BufferedImage}.
     *
     * @param image the image to convert
     * 
     * @param type  the {@code BufferedImage} type,
     *              e.g. {@link BufferedImage#TYPE_INT_ARGB}
     *
     * @return the converted {@code BufferedImage}
     *
     * @see BufferedImage
     */
    public static BufferedImage convertToBufferedImage(Image image, int type) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // Create a BufferedImage with the same width and height as the original image
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                type);

        // Draw the original image onto the BufferedImage using Graphics2D
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);

        g2d.dispose();

        return bufferedImage;
    }
}
