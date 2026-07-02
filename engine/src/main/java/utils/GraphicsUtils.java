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

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import gameEngine.interfaces.Painter;

/**
 * Utility methods for graphics operations.
 */
public class GraphicsUtils extends Utils {
    private GraphicsUtils() {
    }

    /**
     * What type of mask used with
     * {@link GraphicsUtils#createMask(Graphics2D, Shape, MaskType, Painter)}.
     */
    public enum MaskType {
        /**
         * Keeps everything inside the mask shape.
         */
        INSIDE,
        /**
         * Keeps everything outside the mask shape.
         */
        OUTSIDE
    }

    /**
     * Returns a new {@link BufferedImage} containing the given image rotated
     * around its center by the specified angle.
     * <p>
     * The rotation angle is specified in degrees. Positive values result in a
     * clockwise visual rotation in screen coordinates.
     * <p>
     * The dimensions of the returned image may differ from the original to fully
     * contain the rotated image.
     *
     * @param image The image to rotate
     * 
     * @param angle The rotation angle in degrees
     *
     * @return a new {@code BufferedImage} containing the rotated image
     *
     * @throws NullPointerException if {@code image} is {@code null}
     */
    public static BufferedImage rotateImage(BufferedImage image, double angle) {

        Objects.requireNonNull(image, "image must not be null");

        int width = image.getWidth();
        int height = image.getHeight();

        // Calculate the new image size after rotation
        int newWidth = (int) Math.floor(
                width * Math.abs(Math.cos(Math.toRadians(angle)))
                        + height * Math.abs(Math.sin(Math.toRadians(angle))));
        int newHeight = (int) Math.floor(
                width * Math.abs(Math.sin(Math.toRadians(angle)))
                        + height * Math.abs(Math.cos(Math.toRadians(angle))));

        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, image.getType());

        // Get Graphics2D from the new image
        Graphics2D g2d = rotatedImage.createGraphics();

        // Enable high-quality interpolation
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Set up the rotation point to be the center of the image
        g2d.rotate(Math.toRadians(angle), newWidth / 2.0, newHeight / 2.0);
        g2d.translate((newWidth - width) / 2.0, (newHeight - height) / 2.0);

        // Draw the original image onto the rotated graphics context
        g2d.drawImage(image, 0, 0, null);

        g2d.dispose();

        return rotatedImage;
    }

    /**
     * Rotates the {@link Graphics2D} context around a given point, executes the
     * specified drawing action, and then restores the original transform.
     * <p>
     * The rotation is applied only for the duration of {@code drawAction} and the
     * {@code AffineTransform} is restored after {@code drawAction} is complete.
     *
     * @param g2d           The {@code Graphics2D} context to rotate
     * 
     * @param angle         The rotation angle in degrees (positive values rotate
     *                      clockwise in screen coordinates)
     * 
     * @param rotationPoint The point around which the graphics context is rotated
     * 
     * @param drawAction    The drawing operation to be executed while the rotation
     *                      is applied
     * 
     * @throws NullPointerException if {@code rotationPoint} or
     *                              {@code drawAction} is {@code null}
     */
    public static void rotateGraphics(
            Graphics2D g2d,
            double angle,
            Point rotationPoint,
            Painter drawAction) {

        Objects.requireNonNull(rotationPoint, "rotationPoint must not be null");
        Objects.requireNonNull(drawAction, "drawAction must not be null");

        AffineTransform oldTransform = g2d.getTransform();

        // Rotate around "rotationPoint"
        g2d.rotate(
                Math.toRadians(angle),
                rotationPoint.getX(),
                rotationPoint.getY());

        // Draw
        drawAction.paint(g2d);

        // Restore original transform
        g2d.setTransform(oldTransform);

        // DO NOT DISPOSE GRAPHICS!!!
    }

    /**
     * Creates a mask restricting drawing to either inside or outside of the masks
     * rectangular bounds.
     * 
     * @param g2d     The graphics context
     * 
     * @param mask    The shape of the mask
     * 
     * @param type    The type of the mask
     * 
     * @param painter The custom drawing action
     * 
     * @throws NullPointerException if {@code mask}, {@code type} or {@code painter}
     *                              is {@code null}
     */
    public static void createMask(
            Graphics2D g2d,
            Shape mask,
            MaskType type,
            Painter painter) {

        Objects.requireNonNull(g2d, "g2d must not be null");
        Objects.requireNonNull(mask, "mask must not be null");
        Objects.requireNonNull(painter, "painter must not be null");

        Rectangle2D bounds = mask.getBounds2D();
        int x = (int) bounds.getX();
        int y = (int) bounds.getY();
        int width = (int) Math.ceil(bounds.getWidth());
        int height = (int) Math.ceil(bounds.getHeight());

        BufferedImage maskImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        BufferedImage contentImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Creating the mask
        Graphics2D mg = maskImage.createGraphics();
        try {
            mg.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            mg.translate(-x, -y);
            mg.fill(mask);
        } finally {
            mg.dispose();
        }

        // Render Painter content
        Graphics2D cg = contentImage.createGraphics();
        try {
            cg.setRenderingHints(g2d.getRenderingHints());

            AffineTransform tx = g2d.getTransform();
            cg.setTransform(tx);
            cg.translate(-x, -y);

            painter.paint(cg);
        } finally {
            cg.dispose();
        }

        // Applying the mask
        Graphics2D rg = contentImage.createGraphics();
        try {
            switch (type) {
                case INSIDE:
                    rg.setComposite((AlphaComposite.getInstance(AlphaComposite.DST_IN)));
                    break;

                case OUTSIDE:
                    rg.setComposite((AlphaComposite.getInstance(AlphaComposite.DST_OUT)));
                    break;
            }

            rg.drawImage(maskImage, 0, 0, null);
        } finally {
            rg.dispose();
        }

        // Draw result to normal graphics context
        g2d.drawImage(contentImage, x, y, null);

    }

    /**
     * Scales an image to the specified target size.
     * <p>
     * The image is downscaled progressively by repeatedly halving its size
     * using {@code bicubic} interpolation via {@link RenderingHints}.
     * The scaling is preformed in multiple steps helps preserve image quality when
     * scaling across large size differences.
     *
     * @param image        the image to scale
     * @param targetWidth  the target width in pixels
     * @param targetHeight the target height in pixels
     *
     * @return the scaled image
     *
     * @throws NullPointerException if {@code image} is {@code null}
     */
    public static BufferedImage downscaleImage(
            BufferedImage image,
            int targetWidth,
            int targetHeight) {

        Objects.requireNonNull(image, "Image must not be null");

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage current = image;

        // Half the size until it is close to the target width and height.
        while (width / 2 >= targetWidth &&
                height / 2 >= targetHeight) {

            width /= 2;
            height /= 2;

            BufferedImage temp = new BufferedImage(
                    width,
                    height,
                    BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = temp.createGraphics();

            g.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            g.setRenderingHint(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);

            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g.drawImage(current, 0, 0, width, height, null);

            g.dispose();

            current = temp;
        }

        // Final render pass.
        BufferedImage result = new BufferedImage(
                targetWidth,
                targetHeight,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = result.createGraphics();

        g.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        g.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(current, 0, 0,
                targetWidth, targetHeight, null);

        g.dispose();

        return result;
    }

    /**
     * A simple red "X" centered at the x and y position.
     * <p>
     * This can be used to visualize coordinates when debugging position related
     * issues.
     * 
     * @param g The graphics context
     * 
     * @param x The x coordinate
     * 
     * @param y The y coordinate
     * 
     * @throws NullPointerException if {@code g} is {@code null}.
     * 
     */
    public static void debugShape(Graphics g, int x, int y) {
        Objects.requireNonNull(g, "g must not be null");
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(x - 5, y - 5, x + 5, y + 5);
        g2d.drawLine(x - 5, y + 5, x + 5, y - 5);

    }

    /**
     * A simple red rectangle that shows the bounding for the image as long as the x
     * and y coordinates are the same as the images'.
     * <p>
     * This can be used to visualize the images size when debugging image and
     * position related issues.
     * 
     * @param g     The graphics context
     * 
     * @param image The image
     * 
     * @param x     The x coordinate
     * 
     * @param y     The y coordinate
     * 
     * @throws NullPointerException if {@code g} or {@code image} is {@code null}.
     */
    public static void imageBoundingBox(Graphics g, Image image, int x, int y) {
        Objects.requireNonNull(g, "g must not be null");
        Objects.requireNonNull(image, "image must not be null");

        Graphics2D g2d = (Graphics2D) g;

        int width = image.getWidth(null);
        int height = image.getHeight(null);

        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(x, y, width, height);
    }

    /**
     * Set the opacity for the given {@link Painter} and draw its contents.
     * <p>
     * The {@code opacity} must fall in the range of 0 - 255 where {@code 0} is
     * fully transparent and {@code 255} if fully opaque.
     * 
     * @param g2d     The graphics context used when rendering the painter
     * 
     * @param opacity The opacity for the painters contents
     * 
     * @param painter The custom drawing action
     * 
     * @throws IllegalArgumentException if {@code opacity} if outside the range of
     *                                  {@code 0 - 255}
     */
    public static void setOpacityFor(Graphics2D g2d, int opacity, Painter painter) {
        Objects.requireNonNull(g2d, "g2d must not be null");
        Objects.requireNonNull(painter, "painter must not be null");

        if (opacity > 255 || opacity < 0) {
            ErrorManagement.reportError(
                    new IllegalArgumentException("Opacity: %s out of range for 0 - 255".formatted(opacity)),
                    "The provided opacity was outside of the allowed range.");
            return;
        }

        Composite oldComposite = g2d.getComposite();

        try {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ((float) opacity / 255)));
            painter.paint(g2d);
        } finally {
            g2d.setComposite(oldComposite);
        }
    }

}
