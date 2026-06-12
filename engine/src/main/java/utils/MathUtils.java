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

import java.awt.Point;
import java.util.Objects;

import gameEngine.records.FixResult;

/**
 * Utility methods for math operations.
 */
public class MathUtils extends Utils {

    private MathUtils() {
        super();
    }

    /**
     * Calculates the distance between two {@link Point Points}.
     * 
     * @param pointOne First point
     * 
     * @param pointTwo Second point
     * 
     * @return The distance between the points
     */
    public static double pythagoras(Point pointOne, Point pointTwo) {

        double deltaX = pointTwo.getX() - pointOne.getX();
        double deltaY = pointTwo.getY() - pointOne.getY();

        return Math.hypot(deltaX, deltaY);
    }

    /**
     * Calculates the distance between two represented as {@code x} and {@code y}
     * coordinates.
     * 
     * @param x1 X coordinate for the first point
     * 
     * @param y1 Y coordinate for the first point
     * 
     * @param x2 X coordinate for the second point
     * 
     * @param y2 Y coordinate for the second point
     * 
     * @return The distance between the points
     */
    public static double pythagoras(int x1, int y1, int x2, int y2) {

        double deltaX = x2 - x1;
        double deltaY = y2 - y1;

        return Math.hypot(deltaX, deltaY);
    }

    /**
     * Projects a reference point onto the line segment defined by {@code pointOne}
     * and {@code pointTwo}, returning the closest point on that segment.
     * <p>
     * The returned point is constrained to lie between {@code pointOne} and
     * {@code pointTwo}. If the perpendicular projection of the reference point
     * lies outside the segment, the nearest endpoint is returned instead.
     * <p>
     * This is commonly used for constraining input (e.g. a mouse position)
     * to move along a fixed line.
     *
     * @param reference The point to be projected onto the line segment (e.g. mouse
     *                  position)
     * 
     * @param pointOne  The start point of the line segment
     * 
     * @param pointTwo  The end point of the line segment
     *
     * @return a {@link FixResult} containing:
     *         <ul>
     *         <li>the constrained point on the line segment</li>
     *         <li>a progress value in the range {@code [0.0, 1.0]}, where
     *         {@code 0.0} corresponds to {@code pointOne} and
     *         {@code 1.0} corresponds to {@code pointTwo}</li>
     *         </ul>
     * 
     * @throws NullPointerException if {@code reference}, {@code pointOne}, or
     *                              {@code pointTwo} is {@code null}
     */
    public static FixResult fixToLine(Point reference, Point pointOne, Point pointTwo) {

        Objects.requireNonNull(reference, "reference must not be null");
        Objects.requireNonNull(pointOne, "pointOne must not be null");
        Objects.requireNonNull(pointTwo, "pointTwo must not be null");

        double deltaX = pointTwo.x - pointOne.x;
        double deltaY = pointTwo.y - pointOne.y;

        double mx = reference.x - pointOne.x;
        double my = reference.y - pointOne.y;

        double lengthSquared = deltaX * deltaX + deltaY * deltaY;

        // Start and end are the same point
        if (lengthSquared == 0)
            return new FixResult(new Point(pointOne.x, pointOne.y), 0);

        double progress = (mx * deltaX + my * deltaY) / lengthSquared;

        // Fix "progress" between point one and two
        progress = Math.max(0, Math.min(1, progress));

        int pointX = (int) Math.round(pointOne.x + progress * deltaX);
        int pointY = (int) Math.round(pointOne.y + progress * deltaY);

        return new FixResult(new Point(pointX, pointY), progress);
    }
}
