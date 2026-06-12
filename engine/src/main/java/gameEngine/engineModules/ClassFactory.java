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

import java.util.List;

import gameEngine.interfaces.Hoverable;
import gameEngine.interfaces.KeyNotifier;
import gameEngine.interfaces.MouseNotifier;
import gameEngine.interfaces.Updatable;
import gameEngine.interfaces.ZIndexable;
import gameEngine.interfaces.drawables.CursorDrawable;
import gameEngine.interfaces.drawables.Drawable;
import gameEngine.interfaces.drawables.UIDrawable;
import utils.ErrorManagement;

/**
 * Manages the sorting of objects implementing specific interfaces into
 * different lists in {@link EngineContext}.
 */
public final class ClassFactory {

    @SuppressWarnings("unused")
    private ClassFactory() {
        throw new AssertionError("No instances allowed");
    }

    /**
     * Adds an object to a reference list, making it eligible to receive drawing,
     * update, and other engine calls.
     * <p>
     * When used in a drawing context, the object is assigned a default z-index of
     * 0.
     * The z-index can be set manually using
     * {@link #create(Object, EngineContext, int)}.
     *
     * @param object  the object to add to the reference list
     * 
     * @param context the engine context containing objects involved in
     *                rendering, updating, and input handling
     */
    public static void create(Object object, EngineContext context) {
        create(object, context, 0);
    }

    /**
     * Adds an object to a reference list, making it eligible to receive drawing,
     * update, and other engine calls.
     * <p>
     * The provided z-index determines the object's rendering order.
     * <p>
     * <b>Tip:</b> If this method is used outside the object's class, it is
     * generally
     * recommended to use the object's own z-index. This can be retrieved via
     * {@link gameEngine.Interfaces.ZIndexable#getZIndex()}.
     *
     * <pre>{@code
     * Entity player = new Entity(...);
     *
     * ClassFactory.create(player, context, player.getZIndex());
     * }</pre>
     *
     * Another common pattern is registering the object in its constructor:
     *
     * <pre>{@code
     * private int zIndex = 0;
     *
     * public Entity(EngineContext context) {
     *     ClassFactory.create(this, context, zIndex);
     * }
     * }</pre>
     *
     * @param object  the object to add to the reference list
     * 
     * @param context the engine context containing objects involved in
     *                rendering, updating, and input handling
     * 
     * @param zIndex  the z-index assigned to the object
     */
    public static void create(Object object, EngineContext context, int zIndex) {

        if (object instanceof Drawable drawable) {

            List<Drawable> list;

            if (object instanceof UIDrawable) {
                list = context.getBackBufferUIDrawable();
            } else {
                list = context.getBackBufferDrawable();
            }

            int index = 0;
            while (index < list.size() &&
                    list.get(index).getZIndex() <= zIndex) {
                index++;
            }

            // Add sorted entry
            list.add(index, drawable);
        }

        if (object instanceof Updatable updatable) {
            context.getBackBufferUpdatables().add(updatable);
        }

        if (object instanceof CursorDrawable cursorDrawable) {
            context.getBackBufferCursorDrawable().add(cursorDrawable);
        }

        if (object instanceof KeyNotifier keyNotifier) {
            context.getBackBufferKeyNotifiers().add(keyNotifier);
        }

        if (object instanceof MouseNotifier mouseNotifier) {
            context.getBackBufferMouseNotifiers().add(mouseNotifier);
        }

        if (object instanceof Hoverable hoverable) {
            List<Hoverable> list = context.getBackBufferHoverables();

            // Find the appropriate index for insertion (Descending order)
            int index = 0;
            while (index < list.size() &&
                    list.get(index).getZIndex() > zIndex) {
                index++;
            }

            list.add(index, hoverable);
        }
    }

    /**
     * Updates the rendering priority of an object by removing its current entry
     * and re-inserting it in the correct order.
     * <p>
     * This ensures the object is rendered according to its updated z-index.
     *
     * @param object  the object whose order is updated
     * 
     * @param context the engine context containing objects involved in
     *                rendering, updating, and input handling
     * 
     * @param zIndex  the new z-index assigned to the object
     *
     * @throws IllegalArgumentException if {@code object} does not implement
     *                                  {@link gameEngine.Interfaces.ZIndexable
     *                                  ZIndexable}
     */
    public static void updatePriority(Object object, EngineContext context, int zIndex)
            throws IllegalArgumentException {
        if (!(object instanceof ZIndexable)) {
            IllegalArgumentException e = new IllegalArgumentException(
                    object.getClass().getSimpleName() + " must implement ZIndexable");

            ErrorManagement.throwError(e, "Invalid object passed to getPriority");
            throw e;
        }

        if (object instanceof Drawable drawable) {

            List<Drawable> list;

            if (object instanceof UIDrawable) {
                list = context.getBackBufferUIDrawable();
            } else {
                list = context.getBackBufferDrawable();
            }

            // Remove old entry
            list.removeIf(entry -> entry == drawable);

            int index = 0;
            while (index < list.size() &&
                    list.get(index).getZIndex() <= zIndex) {
                index++;
            }

            // Add sorted entry
            list.add(index, drawable);
        }

        if (object instanceof Hoverable hoverable) {

            List<Hoverable> list;

            list = context.getBackBufferHoverables();

            // Remove old entry
            list.removeIf(entry -> entry == hoverable);

            // Find the appropriate index for insertion (Descending order)
            int index = 0;
            while (index < list.size() && list.get(index).getZIndex() > zIndex) {
                index++;
            }

            // Add sorted entry
            list.add(index, hoverable);
        }
    }

    /**
     * Removes the object from all available context list making is uneligible to
     * receive engine calls like updates or drawing.
     * 
     * @param object  the object that is being removed from the lists
     * 
     * @param context the engine context containing objects involved in
     *                rendering, updating, and input handling
     */
    public static void remove(Object object, EngineContext context) {
        for (List<?> list : context.getAllLists()) {
            list.remove(object);
        }
    }
}
