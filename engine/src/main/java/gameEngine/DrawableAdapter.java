package gameEngine;

import java.awt.Graphics;

import gameEngine.engineModules.ClassFactory;
import gameEngine.engineModules.EngineContext;
import gameEngine.interfaces.drawables.Drawable;

/**
 * A convenience class that already implements the bare minimum for the
 * {@link Drawable} interface.
 */
public class DrawableAdapter implements Drawable {

    /**
     * The z-index.
     * <p>
     * The default z-index for {@link UIDrawableAdapter} is {@code 0}.
     */
    protected int zIndex = 0;

    /**
     * The engine context containing objects involved in rendering,
     * updating, and input handling.
     */
    protected EngineContext context;

    /**
     * Creates a instance of {@link DrawableAdapter}.
     * <p>
     * Drawing actions can be preformed by overriding the draw method.
     * 
     * <pre>
     * {@code
     * @Override
     * public void draw(Graphics g) {
     *     Graphics2D g2d = (Graphics2D) g;
     * }
     * }
     * </pre>
     * 
     * @param context the engine context containing objects involved in rendering,
     *                updating, and input handling.
     */
    public DrawableAdapter(EngineContext context) {
        this.context = context;
    }

    @Override
    public void setZIndex(int zIndex) {
        ClassFactory.updatePriority(this, context, zIndex);
        this.zIndex = zIndex;
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }

    @Override
    public void draw(Graphics g) {
    }

}
