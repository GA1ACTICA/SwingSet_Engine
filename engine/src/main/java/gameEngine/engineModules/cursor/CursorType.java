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

package gameEngine.engineModules.cursor;

import java.awt.Point;

/**
 * Represents a mouse cursor definition.
 * <p>
 * A cursor can either be one of the predefined built-in cursor types or
 * a custom cursor loaded from an image resource with a specified hotspot.
 * 
 * @see CursorManager
 */
public final class CursorType {

    private final String path;
    private final Point hotspot;
    private final boolean builtIn;

    private CursorType(String path, Point hotspot, boolean builtIn) {
        this.path = path;
        this.hotspot = hotspot;
        this.builtIn = builtIn;
    }

    /**
     * Creates a new instance of CursorType
     * <p>
     * You can override a builtin CursorType since they are mutable and therefore
     * add your own custom cursor images instead.
     * <p>
     * <b>Note:</b> Consider setting hotspot to {@code null} when creating a
     * animated cursor since the hotspot is derived from the meta.json file located
     * in the cursors directory.
     * 
     * @param hotspot the hotspot from where click originate
     * 
     * @param path    the path to the image or directory of the cursor
     * 
     */
    public CursorType(String path, Point hotspot) {
        this.path = path;
        this.hotspot = hotspot;
        builtIn = false;
    }

    /**
     * Returns the path for the cursor's image resource.
     * 
     * @return the path to the image resource
     */
    public String path() {
        return path;
    }

    /**
     * Returns the hotspot point for the cursor where interactions originate from.
     * 
     * @return the hotspot for the cursor
     */
    public Point hotspot() {
        return hotspot;
    }

    /**
     * Returns whether this cursor is built into the engine.
     *
     * <p>
     * Built-in cursors are created internally by the engine. If a built-in
     * cursor is replaced with a custom cursor, the replacement is not considered
     * built-in.
     *
     * @return {@code true} if this is a built-in engine cursor, {@code false}
     *         otherwise
     */
    public boolean builtIn() {
        return builtIn;
    }

    /**
     * <img src="../../../cursors/alias.png" alt="Alias
     * cursor">
     */
    public static CursorType ALIAS = new CursorType("alias.png", new Point(72, 14), true);

    /**
     * <img src="../../../cursors/all-resize.png" alt="All
     * resize cursor">
     */
    public static CursorType ALL_RESIZE = new CursorType("all-resize.png", new Point(48, 44), true);

    /**
     * <img src="../../../cursors/all-scroll.png" alt="All
     * scroll cursor">
     */
    public static CursorType ALL_SCROLL = new CursorType("all-scroll.png", new Point(44, 44), true);

    /**
     * <img src="../../../cursors/cell.png" alt="Cell cursor">
     */
    public static CursorType CELL = new CursorType("cell.png", new Point(44, 44), true);

    /**
     * <img src="../../../cursors/col-resize.png" alt="Col
     * resize cursor">
     */
    public static CursorType COL_RESIZE = new CursorType("col-resize.png", new Point(48, 48), true);

    /**
     * <img src="../../../cursors/context-menu.png" alt=
     * "Context menu cursor">
     */
    public static CursorType CONTEXT_MENU = new CursorType("context-menu.png", new Point(12, 4), true);

    /**
     * <img src="../../../cursors/copy.png" alt="Copy cursor">
     */
    public static CursorType COPY = new CursorType("copy.png", new Point(12, 4), true);

    /**
     * <img src="../../../cursors/crosshair.png" alt="Crosshair
     * cursor">
     */
    public static CursorType CROSSHAIR = new CursorType("crosshair.png", new Point(44, 44), true);

    /**
     * <img src="../../../cursors/default.png" alt="Default
     * cursor">
     */
    public static CursorType DEFAULT = new CursorType("default.png", new Point(12, 4), true);

    /**
     * <img src="../../../cursors/e-resize.png" alt="East
     * resize cursor">
     */
    public static CursorType E_RESIZE = new CursorType("e-resize.png", new Point(76, 52), true);

    /**
     * <img src="../../../cursors/ew-resize.png" alt="East,
     * west
     * resize cursor">
     */
    public static CursorType EW_RESIZE = new CursorType("ew-resize.png", new Point(48, 48), true);

    /**
     * <img src="../../../cursors/grab.png" alt="Grab cursor">
     */
    public static CursorType GRAB = new CursorType("grab.png", new Point(44, 8), true);

    /**
     * <img src="../../../cursors/grabbing.png" alt="Grabbing
     * cursor">
     */
    public static CursorType GRABBING = new CursorType("grabbing.png", new Point(36, 20), true);

    /**
     * <img src="../../../cursors/help.png" alt="Help cursor">
     */
    public static CursorType HELP = new CursorType("help.png", new Point(48, 84), true);

    /**
     * <img src="../../../cursors/move.png" alt="Move cursor">
     */
    public static CursorType MOVE = new CursorType("move.png", new Point(12, 4), true);

    /**
     * <img src="../../../cursors/ne-resize.png" alt=
     * "North-east resize cursor">
     */
    public static CursorType NE_RESIZE = new CursorType("ne-resize.png", new Point(60, 40), true);

    /**
     * <img src="../../../cursors/nesw-resize.png" alt=
     * "North-east, south-west resize cursor">
     */
    public static CursorType NESW_RESIZE = new CursorType("nesw-resize.png", new Point(44, 44), true);
    /**
     * 
     * <img src="../../../cursors/no-drop.png" alt="No drop
     * cursor">
     */
    public static CursorType NO_DROP = new CursorType("no-drop.png", new Point(12, 4), true);

    /**
     * <img src="../../../cursors/not-allowed.png" alt="Not
     * allowed cursor">
     */
    public static CursorType NOT_ALLOWED = new CursorType("not-allowed.png", new Point(48, 48), true);

    /**
     * <img src="../../../cursors/n-resize.png" alt="North
     * resize cursor">
     */
    public static CursorType N_RESIZE = new CursorType("n-resize.png", new Point(52, 24), true);

    /**
     * <img src="../../../cursors/ns-resize.png" alt="North,
     * south resize cursor">
     */
    public static CursorType NS_RESIZE = new CursorType("ns-resize.png", new Point(48, 52), true);

    /**
     * <img src="../../../cursors/nwse-resize.png" alt=
     * "North-west, south-east resize cursor">
     */
    public static CursorType NWSE_RESIZE = new CursorType("nwse-resize.png", new Point(44, 44), true);

    /**
     * <img src="../../../cursors/pointer.png" alt="Pointer
     * cursor">
     */
    public static CursorType POINTER = new CursorType("pointer.png", new Point(28, 20), true);

    /**
     * <img src="../../../cursors/row-resize.png" alt="Row
     * resize cursor">
     */
    public static CursorType ROW_RESIZE = new CursorType("row-resize.png", new Point(48, 52), true);

    /**
     * <img src="../../../cursors/se-resize.png" alt=
     * "South-east resize cursor">
     */
    public static CursorType SE_RESIZE = new CursorType("se-resize.png", new Point(60, 60), true);

    /**
     * <img src="../../../cursors/s-resize.png" alt="South
     * resize cursor">
     */
    public static CursorType S_RESIZE = new CursorType("s-resize.png", new Point(52, 72), true);

    /**
     * <img src="../../../cursors/sw-resize.png" alt=
     * "South-west resize cursor">
     */
    public static CursorType SW_RESIZE = new CursorType("sw-resize.png", new Point(40, 60), true);

    /**
     * <img src="../../../cursors/text.png" alt="Text cursor">
     */
    public static CursorType TEXT = new CursorType("text.png", new Point(44, 48), true);

    /**
     * <img src="../../../cursors/vertical-text.png" alt=
     * "Vertical text cursor">
     */
    public static CursorType VERTICAL_TEXT = new CursorType("vertical-text.png", new Point(48, 44), true);

    /**
     * <img src="../../../cursors/w-resize.png" alt="West
     * resize cursor">
     */
    public static CursorType W_RESIZE = new CursorType("w-resize.png", new Point(24, 52), true);

    /**
     * <img src="../../../cursors/x-cursor.png" alt="X cursor">
     */
    public static CursorType X_CURSOR = new CursorType("x-cursor.png", new Point(44, 48), true);

    /**
     * <img src="../../../cursors/zoom-in.png" alt="Zoom in
     * cursor">
     */
    public static CursorType ZOOM_IN = new CursorType("zoom-in.png", new Point(44, 40), true);

    /**
     * <img src="../../../cursors/zoom-out.png" alt="Zoom out
     * cursor">
     */
    public static CursorType ZOOM_OUT = new CursorType("zoom-out.png", new Point(44, 40), true);

    // --- Animated ---

    /**
     * <img src="../../../cursors/progress/progress_0001.png"
     * alt="Progress cursor">
     */
    public static CursorType PROGRESS = new CursorType("progress", null, true);

    /**
     * <img src="../../../cursors/wait/wait_0001.png" alt="Wait
     * cursor">
     */
    public static CursorType WAIT = new CursorType("wait", null, true);

}
