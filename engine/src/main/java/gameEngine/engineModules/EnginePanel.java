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

package gameEngine.engineModules;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import gameEngine.engineState.EngineState;
import gameEngine.interfaces.drawables.CursorDrawable;
import gameEngine.interfaces.drawables.Drawable;
import gameEngine.interfaces.drawables.UIDrawable;
import gameEngine.interfaces.drawables.UIDrawable.UIElementLayout;

/**
 * Handles the {@link JPanel} and the rendering loop used by the engine.
 * <p>
 * {@code EnginePanel} is managed by {@link Game} and is not intended to be
 * instantiated directly.
 */
@SuppressWarnings("serial")
public final class EnginePanel extends JPanel {

    /**
     * The logical width of the coordinate system used by {@link Drawable}.
     */
    public final int logicalWidth = 1000;
    /**
     * The logical height of the coordinate system used by {@link Drawable}.
     */
    public final int logicalHeight = 1000;

    private transient EngineState state;
    private transient EngineContext context;
    private transient boolean exceptionReported;
    private transient AffineTransform viewportTransform;

    EnginePanel(EngineState state, EngineContext context) {
        this.state = state;
        this.context = context;

        setDoubleBuffered(true);
    }

    // rendering engine
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {

            Graphics2D g2d = (Graphics2D) g.create();

            AffineTransform old = g2d.getTransform();

            double scaleX = getWidth() / (double) logicalWidth;
            double scaleY = getHeight() / (double) logicalHeight;
            double scale = Math.min(scaleX, scaleY);

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            // Center + scale
            g2d.translate(
                    (getWidth() - logicalWidth * scale) / 2,
                    (getHeight() - logicalHeight * scale) / 2);
            g2d.scale(scale, scale);

            // Gets the scaled transform
            viewportTransform = g2d.getTransform();

            g2d.setColor(state.data().backgroundColor);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Draw game objects in world space
            for (Drawable drawable : context.getWorldDrawables()) {
                drawable.draw(g2d);
            }

            // Restore transform
            g2d.setTransform(old);

            for (Drawable drawable : context.getUiDrawables()) {

                UIDrawable uiDrawable = (UIDrawable) drawable;

                g2d.setTransform(old);

                switch (uiDrawable.getLayout()) {
                    case UIElementLayout.TOP_ALIGNED:
                        g2d.translate((getWidth() - logicalWidth) / 2, 0);
                        break;

                    case UIElementLayout.BOTTOM_ALIGNED:
                        g2d.translate((getWidth() - logicalWidth) / 2, getHeight() - logicalHeight);
                        break;

                    case UIElementLayout.LEFT_ALIGNED:
                        g2d.translate(0, (getHeight() - logicalHeight) / 2);
                        break;

                    case UIElementLayout.RIGHT_ALIGNED:
                        g2d.translate(getWidth() - logicalWidth, (getHeight() - logicalHeight) / 2);
                        break;

                    case UIElementLayout.TOP_LEFT_ALIGNED:
                        // Nothing happens
                        break;

                    case UIElementLayout.BOTTOM_LEFT_ALIGNED:
                        g2d.translate(0, getHeight() - logicalHeight);
                        break;

                    case UIElementLayout.TOP_RIGHT_ALIGNED:
                        g2d.translate(getWidth() - logicalWidth, 0);
                        break;

                    case UIElementLayout.BOTTOM_RIGHT_ALIGNED:
                        g2d.translate(getWidth() - logicalWidth, getHeight() - logicalHeight);
                        break;

                    default:
                        g2d.translate(
                                (getWidth() - logicalWidth) / 2,
                                (getHeight() - logicalHeight) / 2);
                        break;
                }

                uiDrawable.draw(g2d);
            }

            g2d.setTransform(old);

            // Always draws cursors on top
            for (CursorDrawable d : context.getCursorDrawables()) {
                d.draw(g2d);
            }

            g2d.dispose();

        } catch (Throwable t) {

            if (!exceptionReported) {
                exceptionReported = true;
                t.printStackTrace();
            }
            // Skip rendering this frame
        }

    }

    /**
     * Returns the transform used to map logical (world) coordinates to screen
     * space.
     * <p>
     * This transform scales the logical resolution to fit the current window size
     * while preserving aspect ratio, and centers the result within the window.
     * <p>
     * The transform is equivalent to:
     *
     * <pre>{@code
     * double scaleX = panel.getWidth() / (double) panel.logicalWidth;
     * double scaleY = panel.getHeight() / (double) panel.logicalHeight;
     * double scale = Math.min(scaleX, scaleY);
     *
     * g2d.translate(
     *         (getWidth() - logicalWidth * scale) / 2,
     *         (getHeight() - logicalHeight * scale) / 2);
     * g2d.scale(scale, scale);
     * 
     * AffineTransform viewportTransform = g2d.getTransform();
     * }</pre>
     * 
     * The resulting transform corresponds to {@code g2d.getTransform()} after
     * these operations.
     *
     * @return the transform used when rendering {@link Drawable} objects
     */
    public AffineTransform getViewportTransform() {
        return viewportTransform;
    }

    public Point2D getTranslatedPoint(Point ptSrc, UIElementLayout layout) {
        AffineTransform transform = new AffineTransform();

        switch (layout) {
            case UIElementLayout.TOP_ALIGNED:
                transform.translate(-((getWidth() - logicalWidth) / 2), 0);
                break;

            case UIElementLayout.BOTTOM_ALIGNED:
                transform.translate(-((getWidth() - logicalWidth) / 2),
                        -(getHeight() - logicalHeight));
                break;

            case UIElementLayout.LEFT_ALIGNED:
                transform.translate(0, -((getHeight() - logicalHeight) / 2));
                break;

            case UIElementLayout.RIGHT_ALIGNED:
                transform.translate(-(getWidth() - logicalWidth),
                        -((getHeight() - logicalHeight) / 2));
                break;

            case UIElementLayout.TOP_LEFT_ALIGNED:
                // Nothing happens
                break;

            case UIElementLayout.BOTTOM_LEFT_ALIGNED:
                transform.translate(0, -(getHeight() - logicalHeight));
                break;

            case UIElementLayout.TOP_RIGHT_ALIGNED:
                transform.translate(-(getWidth() - logicalWidth), 0);
                break;

            case UIElementLayout.BOTTOM_RIGHT_ALIGNED:
                transform.translate(-(getWidth() - logicalWidth),
                        -(getHeight() - logicalHeight));
                break;

            default:
                transform.translate(
                        -((getWidth() - logicalWidth) / 2),
                        -((getHeight() - logicalHeight) / 2));
                break;
        }

        return transform.transform(ptSrc, null);
    }
}