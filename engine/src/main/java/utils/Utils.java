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

import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;

/**
 * Base class containing generic utility methods.
 * 
 * @see GraphicsUtils
 * @see FileUtils
 * @see MathUtils
 * @see ErrorManagement
 */
public class Utils {
    Utils() {
    }

    /**
     * Resets the color used when writing to the terminal
     * <p>
     * This is injected into the string that is being written to the terminal at the
     * desired location
     */
    public static final String ConsoleRESET = "\u001B[0m";
    /**
     * Sets the color used when writing to the terminal to {@code Red}
     * <p>
     * This is injected into the string that is being written to the terminal at the
     * desired location
     */
    public static final String ConsoleRED = "\u001B[31m";
    /**
     * Sets the color used when writing to the terminal to {@code Green}
     * <p>
     * This is injected into the string that is being written to the terminal at the
     * desired location
     */
    public static final String ConsoleGREEN = "\u001B[32m";
    /**
     * Sets the color used when writing to the terminal to {@code Yellow}
     * <p>
     * This is injected into the string that is being written to the terminal at the
     * desired location
     */
    public static final String ConsoleYELLOW = "\u001B[33m";

    /**
     * Returns a resized version of the supplied {@link java.awt.Font Font} whose
     * rendered height is greater than or equal to the target height.
     * <p>
     * The font size is increased one point at a time starting from size {@code 1}
     * until the measured height of the font reaches or exceeds the target height.
     * <p>
     * Font height is measured using
     * {@link java.awt.font.LineMetrics LineMetrics} with the supplied dummy text.
     * <p>
     * <b>Note:</b> The returned font may exceed the target height since the first
     * font size whose height is greater than or equal to the target is returned.
     *
     * @param font         the base font used to derive resized fonts
     *
     * @param dummyText    the text used when calculating font metrics
     *
     * @param targetHeight the minimum desired rendered font height
     *
     * @return a resized font whose rendered height is greater than or equal to the
     *         target height
     */
    public static Font matchFontToHeight(Font font, String dummyText, int targetHeight) {

        int size = 1;
        FontRenderContext renderContext = new FontRenderContext(null, true, true);

        while (true) {
            LineMetrics metrics = font.getLineMetrics(dummyText, renderContext);

            if (metrics.getHeight() >= targetHeight) {
                return font;
            }

            size++;

            font = font.deriveFont((float) size);

        }
    }

    /**
     * Creates a color from red, green, blue, and alpha components. Every value
     * should fall between (0-255).
     *
     * @param r red channel value
     * 
     * @param g green channel value
     * 
     * @param b blue channel value
     * 
     * @param a alpha channel value
     * 
     * @return a new Color instance
     * 
     * @throws IllegalArgumentException if {@code r}, {@code g}, {@code b} or
     *                                  {@code a} are outside the range of 0 to
     *                                  255.
     * 
     * @see #rgb(int, int, int)
     */
    public static Color rgba(int r, int g, int b, double a) {
        return new Color(r, g, b, (int) (a * 255));
    }

    /**
     * Creates an opaque color from red, green, and blue components. Every value
     * should fall between (0-255).
     *
     * @param r red channel value
     * 
     * @param g green channel value
     * 
     * @param b blue channel value
     * 
     * @return a new Color instance
     * 
     * @throws IllegalArgumentException if {@code r}, {@code g} or {@code b} are
     *                                  outside the range of 0 to 255.
     * 
     * @see #rgba(int, int, int, int)
     */
    public static Color rgb(int r, int g, int b) {
        return new Color(r, g, b);
    }

    /**
     * Merges two colors by dividing the sum of each color component and the
     * rounding the answer to the nearest integer.
     * <p>
     * <b>Note:</b> The alpha is ignored from the two colors and is instead always
     * {@code 255}.
     * 
     * @param firstColor     the fist color
     * 
     * @param secondaryColor the second color
     * 
     * @return the merged colors
     */
    public static Color mergeRGBColor(Color firstColor, Color secondaryColor) {
        return new Color(Math.round((firstColor.getRed() + secondaryColor.getRed()) / 2),
                Math.round((firstColor.getGreen() + secondaryColor.getGreen()) / 2),
                Math.round((firstColor.getBlue() + secondaryColor.getBlue()) / 2));
    }

    /**
     * Merges two colors by dividing the sum of each color component and the
     * rounding the answer to the nearest integer.
     * 
     * @param firstColor     the fist color
     * 
     * @param secondaryColor the second color
     * 
     * @return the merged colors
     */
    public static Color mergeRGBAColor(Color firstColor, Color secondaryColor) {
        return new Color(Math.round((firstColor.getRed() + secondaryColor.getRed()) / 2),
                Math.round((firstColor.getGreen() + secondaryColor.getGreen()) / 2),
                Math.round((firstColor.getBlue() + secondaryColor.getBlue()) / 2),
                Math.round((firstColor.getAlpha() + secondaryColor.getAlpha()) / 2));
    }
}
