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
 * Instances of this class are immutable.
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
     * 
     * You can override a builtin CursorType since they are mutable and therefore
     * add your own custom cursor images instead.
     * <p>
     * <b>Note:</b> Consider setting hotspot to {@code null} when creating a
     * animated cursor since the hotspot is derived from the meta.json file located
     * in the cursors directory.
     * 
     * @param hotspot The hotspot from where click originate
     * 
     * @param path    The path to the image or directory of the cursor
     * 
     */
    public CursorType(String path, Point hotspot) {
        this.path = path;
        this.hotspot = hotspot;
        builtIn = false;
    }

    public String path() {
        return path;
    }

    public Point hotspot() {
        return hotspot;
    }

    public boolean builtIn() {
        return builtIn;
    }

    public static CursorType ALIAS = new CursorType("alias.png", new Point(72, 14), true);
    public static CursorType ALL_RESIZE = new CursorType("all-resize.png", new Point(48, 44), true);
    public static CursorType ALL_SCROLL = new CursorType("all-scroll.png", new Point(44, 44), true);
    public static CursorType CELL = new CursorType("cell.png", new Point(44, 44), true);
    public static CursorType COL_RESIZE = new CursorType("col-resize.png", new Point(48, 48), true);
    public static CursorType CONTEXT_MENU = new CursorType("context-menu.png", new Point(12, 4), true);
    public static CursorType COPY = new CursorType("copy.png", new Point(12, 4), true);
    public static CursorType CROSSHAIR = new CursorType("crosshair.png", new Point(44, 44), true);
    public static CursorType DEFAULT = new CursorType("default.png", new Point(12, 4), true);
    public static CursorType E_RESIZE = new CursorType("e-resize.png", new Point(76, 52), true);
    public static CursorType EW_RESIZE = new CursorType("ew-resize.png", new Point(48, 48), true);
    public static CursorType GRAB = new CursorType("grab.png", new Point(44, 8), true);
    public static CursorType GRABBING = new CursorType("grabbing.png", new Point(36, 20), true);
    public static CursorType HELP = new CursorType("help.png", new Point(48, 84), true);
    public static CursorType MOVE = new CursorType("move.png", new Point(12, 4), true);
    public static CursorType NE_RESIZE = new CursorType("ne-resize.png", new Point(60, 40), true);
    public static CursorType NESW_RESIZE = new CursorType("nesw-resize.png", new Point(44, 44), true);
    public static CursorType NO_DROP = new CursorType("no-drop.png", new Point(12, 4), true);
    public static CursorType NOT_ALLOWED = new CursorType("not-allowed.png", new Point(48, 48), true);
    public static CursorType N_RESIZE = new CursorType("n-resize.png", new Point(52, 24), true);
    public static CursorType NS_RESIZE = new CursorType("ns-resize.png", new Point(48, 52), true);
    public static CursorType NWSE_RESIZE = new CursorType("nwse-resize.png", new Point(44, 44), true);
    public static CursorType POINTER = new CursorType("pointer.png", new Point(28, 20), true);
    public static CursorType ROW_RESIZE = new CursorType("row-resize.png", new Point(48, 52), true);
    public static CursorType SE_RESIZE = new CursorType("se.resize.png", new Point(60, 60), true);
    public static CursorType S_RESIZE = new CursorType("s-resize.png", new Point(52, 72), true);
    public static CursorType SW_RESIZE = new CursorType("sw-resize.png", new Point(40, 60), true);
    public static CursorType TEXT = new CursorType("text.png", new Point(44, 48), true);
    public static CursorType VERTICAL_TEXT = new CursorType("vertical-text.png", new Point(48, 44), true);
    public static CursorType W_RESIZE = new CursorType("w-resize.png", new Point(24, 52), true);
    public static CursorType X_CURSOR = new CursorType("x-cursor.png", new Point(44, 48), true);
    public static CursorType ZOOM_IN = new CursorType("zoom-in.png", new Point(44, 40), true);
    public static CursorType ZOOM_OUT = new CursorType("zoom-out.png", new Point(44, 40), true);

    public static CursorType PROGRESS = new CursorType("progress", null, true);
    public static CursorType WAIT = new CursorType("wait", null, true);

}
