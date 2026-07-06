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

package gameEngine.records;

import java.awt.Point;

import utils.MathUtils;

/**
 * The {@code FixResult} record encapsulates the result of the
 * {@link MathUtils#fixToLine(Point, Point, Point)
 * fixToLine(Point, Point, Point)}
 * method.
 * <p>
 * It contains the calculated point constrained to a line segment and a
 * progress value representing the relative position of that point between
 * the segment's start and end points.
 * </p>
 *
 * <p>
 * The progress value is a decimal in the range {@code [0.0, 1.0]}, where:
 * <ul>
 * <li>{@code 0.0} corresponds to the start point</li>
 * <li>{@code 1.0} corresponds to the end point</li>
 * </ul>
 * 
 * @param point    the calculated point constrained to the line segment
 * 
 * @param progress the progress value representing the relative position of that
 *                 point between the segment's start and end points
 */
public record FixResult(Point point, double progress) {
}
