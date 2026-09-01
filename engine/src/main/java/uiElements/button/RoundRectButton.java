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

package uiElements.button;

import java.awt.Point;
import java.awt.geom.RoundRectangle2D;

import gameEngine.engineModules.EngineContext;

/**
 * A button rendered using a rounded rectangle shape.
 * <p>
 * Behaves identically to {@link RectButton} but uses a rounded rectangle
 * appearance.
 */
public class RoundRectButton extends RectButton {

     /**
      * Creates and registers a rounded rectangular button with the specified
      * dimensions and rounded corners.
      * 
      * @param context   the engine context containing objects involved in rendering,
      *                  updating, and input handling.
      *
      * @param x         the x-coordinate of the rectangle's top-left point.
      * 
      * @param y         the y-coordinate of the rectangle's top-left point.
      * 
      * @param width     the width of the rectangle.
      * 
      * @param height    the height of the rectangle.
      *
      * @param arcWidth  the horizontal diameter of the corner arcs. Typically should
      *                  not exceed the rectangle's width.
      * 
      * @param arcHeight the vertical diameter of the corner arcs. Typically should
      *                  not exceed the rectangle's height.
      */
     public RoundRectButton(EngineContext context, int x, int y, int width, int height,
               int arcWidth,
               int arcHeight) {

          super(context, x, y, width, height);

          this.baseShape = new RoundRectangle2D.Float(x, y, width, height, arcWidth, arcHeight);
          this.rotatedShape = baseShape;

          updateRotatedShape();

     }

     /**
      * Creates and registers a rectangular button with the specified {@link Point
      * Points} and rounded corners.
      * 
      * @param context     the engine context containing objects involved in
      *                    rendering, updating, and input handling.
      * 
      * @param topLeft     the top-left point of the rectangle.
      * 
      * @param bottomRight the bottom-left point of the rectangle.
      * 
      * @param arcWidth    the horizontal diameter of the corner arcs. Typically
      *                    should not exceed the rectangle's width.
      * 
      * @param arcHeight   the vertical diameter of the corner arcs. Typically should
      *                    not exceed the rectangle's height.
      */
     public RoundRectButton(EngineContext context, Point topLeft, Point bottomRight,
               int arcWidth,
               int arcHeight) {

          int x = (int) topLeft.getX();
          int y = (int) topLeft.getY();
          int width = (int) bottomRight.getX() - (int) topLeft.getX();
          int height = (int) bottomRight.getY() - (int) topLeft.getY();

          super(context, x, y, width, height);

          this.baseShape = new RoundRectangle2D.Float(x, y, width, height, arcWidth, arcHeight);
          this.rotatedShape = baseShape;

          updateRotatedShape();

     }

     /**
      * Creates and registers a rectangular button with the specified dimensions,
      * center {@link Point} and rounded corners.
      *
      * @param context   the engine context containing objects involved in rendering,
      *                  updating, and input handling.
      * 
      * @param center    the center point of the rectangle.
      * 
      * @param width     the width of the rectangle.
      * 
      * @param height    the height of the rectangle.
      * 
      * @param arcWidth  the horizontal diameter of the corner arcs. Typically
      *                  should not exceed the rectangle's width.
      * 
      * @param arcHeight the vertical diameter of the corner arcs. Typically should
      *                  not exceed the rectangle's height.
      */
     public RoundRectButton(EngineContext context, Point center, int width, int height,
               int arcWidth,
               int arcHeight) {

          int x = (int) center.getX() - width / 2;
          int y = (int) center.getY() - height / 2;

          super(context, x, y, width, height);

          this.baseShape = new RoundRectangle2D.Float(x, y, width, height, arcWidth, arcHeight);
          this.rotatedShape = baseShape;

          updateRotatedShape();

     }

     /**
      * Creates and registers a circular button with the specified dimensions and
      * center {@link Point}.
      * 
      * @param context the engine context containing objects involved in rendering,
      *                updating, and input handling.
      * 
      * @param center  the center point from where the circle is created.
      * 
      * @param radius  the circles radius.
      */
     public RoundRectButton(EngineContext context, Point center, int radius) {
          int x = center.x - radius;
          int y = center.y - radius;

          int length = radius * 2;

          super(context, x, y, length, length);

          this.baseShape = new RoundRectangle2D.Float(x, y, length, length, length, length);
          this.rotatedShape = baseShape;

          updateRotatedShape();
     }

}
