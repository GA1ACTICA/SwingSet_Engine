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
 * Creates a new record of CursorType
 * 
 * This is the general constructor which can be used both for animated and non
 * animated cursors.
 * <p>
 * <b<Note:</b> Consider setting hotspot to {@code null} when creating a
 * animated cursor since the hotspot is derived from the meta.json file located
 * in the cursors directory.
 * 
 * @param hotspot The hotspot from where click originate
 * 
 * @param path    The path to the image or directory of the cursor
 * 
 */
public record CursorType(String path, Point hotspot) {

    public static CursorType ALIAS = new CursorType("alias.png", new Point(72, 14));
    public static CursorType ALL_RESIZE = new CursorType("all-resize.png", new Point(48, 44));
    public static CursorType ALL_SCROLL = new CursorType("all-scroll.png", new Point(44, 44));
    public static CursorType CELL = new CursorType("cell.png", new Point(44, 44));
    public static CursorType COL_RESIZE = new CursorType("col-resize.png", new Point(48, 48));
    public static CursorType CONTEXT_MENU = new CursorType("context-menu.png", new Point(12, 4));
    public static CursorType COPY = new CursorType("copy.png", new Point(12, 4));
    public static CursorType CROSSHAIR = new CursorType("crosshair.png", new Point(44, 44));
    public static CursorType DEFAULT = new CursorType("default.png", new Point(12, 4));
    public static CursorType E_RESIZE = new CursorType("e-resize.png", new Point(76, 52));
    public static CursorType EW_RESIZE = new CursorType("ew-resize.png", new Point(48, 48));
    public static CursorType GRAB = new CursorType("grab.png", new Point(44, 8));
    public static CursorType GRABBING = new CursorType("grabbing.png", new Point(36, 20));
    public static CursorType HELP = new CursorType("help.png", new Point(48, 84));
    public static CursorType MOVE = new CursorType("move.png", new Point(12, 4));
    public static CursorType NE_RESIZE = new CursorType("ne-resize.png", new Point(60, 40));
    public static CursorType NESW_RESIZE = new CursorType("nesw-resize.png", new Point(44, 44));
    public static CursorType NO_DROP = new CursorType("no-drop.png", new Point(12, 4));
    public static CursorType NOT_ALLOWED = new CursorType("not-allowed.png", new Point(48, 48));
    public static CursorType N_RESIZE = new CursorType("n-resize.png", new Point(52, 24));
    public static CursorType NS_RESIZE = new CursorType("ns-resize.png", new Point(48, 52));
    public static CursorType NWSE_RESIZE = new CursorType("nwse-resize.png", new Point(44, 44));
    public static CursorType POINTER = new CursorType("pointer.png", new Point(28, 20));
    public static CursorType ROW_RESIZE = new CursorType("row-resize.png", new Point(48, 52));
    public static CursorType SE_RESIZE = new CursorType("se.resize.png", new Point(60, 60));
    public static CursorType S_RESIZE = new CursorType("s-resize.png", new Point(52, 72));
    public static CursorType SW_RESIZE = new CursorType("sw-resize.png", new Point(40, 60));
    public static CursorType TEXT = new CursorType("text.png", new Point(44, 48));
    public static CursorType VERTICAL_TEXT = new CursorType("vertical-text.png", new Point(48, 44));
    public static CursorType W_RESIZE = new CursorType("w-resize.png", new Point(24, 52));
    public static CursorType X_CURSOR = new CursorType("x-cursor.png", new Point(44, 48));
    public static CursorType ZOOM_IN = new CursorType("zoom-in.png", new Point(44, 40));
    public static CursorType ZOOM_OUT = new CursorType("zoom-out.png", new Point(44, 40));

    public static CursorType PROGRESS = new CursorType("progress", null);
    public static CursorType WAIT = new CursorType("wait", null);

}
