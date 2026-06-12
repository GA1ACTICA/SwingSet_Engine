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

import java.awt.geom.AffineTransform;

import javax.swing.*;

import gameEngine.engineState.EngineState;
import gameEngine.interfaces.drawables.CursorDrawable;
import gameEngine.interfaces.drawables.Drawable;

import java.awt.*;

public final class EnginePanel extends JPanel {

    // logical space dimension
    public final int logicalWidth = 1000;
    public final int logicalHeight = 1000;

    private EngineState state;
    private EngineContext context;

    private boolean exceptionReported;

    private AffineTransform viewportTransform;

    public EnginePanel(EngineState state, EngineContext context) {
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
            g2d.fillRect(0, 0, logicalWidth, logicalHeight);

            // Draw game objects in world space
            for (Drawable drawable : context.getWorldDrawables()) {
                drawable.draw(g2d);
            }

            // Restore transform
            g2d.setTransform(old);

            // TODO: Watch how other games handle UI (This doesn't look good)

            // Draw game objects in UI space
            for (Drawable drawable : context.getUiDrawables()) {
                drawable.draw(g2d);
            }

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
     * double scaleX = getWidth() / (double) logicalWidth;
     * double scaleY = getHeight() / (double) logicalHeight;
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
}