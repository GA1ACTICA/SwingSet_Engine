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

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

import gameEngine.engineModules.ClassFactory;
import gameEngine.engineModules.EngineContext;
import gameEngine.engineModules.EnginePanel;
import gameEngine.engineModules.Mouse;
import gameEngine.engineModules.cursor.AnimatedCursorData.Frame;
import gameEngine.engineState.EngineState;
import gameEngine.interfaces.Updatable;
import gameEngine.interfaces.drawables.CursorDrawable;
import utils.FileUtils;
import utils.GraphicsUtils;

public class CursorManager implements CursorDrawable, Updatable {

    @SuppressWarnings("unused")
    private CursorManager() {
        throw new AssertionError("No instances allowed");
    }

    private static String defaultCursorPath = "cursors/Adwaita 96x96/";

    private static AnimatedCursor rawCursorData = new AnimatedCursor(null);
    private static Frame[] frameDataArray = null;

    private static boolean show = true;

    private static boolean overriding = false;
    private static int width = 24;
    private static int height = 24;
    private static double scaleX;
    private static double scaleY;

    private static List<BufferedImage> cursorImageCache = new ArrayList<BufferedImage>();
    private static BufferedImage[] cursorImageArray;
    private static int cursorArrayIndex = 0;

    private static float currentCursorMillis;
    private static boolean animated = false;
    private static BufferedImage cursorImage = null;
    private static Point hotspot = null;

    private static EngineState state;
    private Mouse mouse;

    public CursorManager(EngineContext context, EnginePanel panel, Mouse mouse, EngineState state) {
        CursorManager.state = state;
        this.mouse = mouse;
        ClassFactory.create(this, context);

        // Hide system cursor
        panel.setCursor(
                Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1,
                        BufferedImage.TYPE_INT_ARGB),
                        new Point(0, 0), "Transparent cursor"));

        setCursor(CursorType.DEFAULT);
    }

    public static boolean isVisible() {
        return show;
    }

    public static void show() {
        show = true;
    }

    public static void hide() {
        show = false;
    }

    /**
     * Sets and displays a cursor.
     * <p>
     * Cursors are predefined in {@link CursorType}, but custom cursor types can
     * also be created by implementing your own {@code CursorType}.
     * <p>
     * The cursor can be locked from being changed using {@link #lockCursor()}.
     * <p>
     * <b>Note:</b> This method should not be called more often than necessary.
     * This is especially important for animated cursors, as doing so may load
     * many images in a short amount of time.
     *
     * @param cursorType the cursor type to set
     *
     * @return {@code true} if the cursor was successfully set,
     *         {@code false} if the change was blocked by
     *         {@link #lockCursor()}
     */
    public static boolean setCursor(CursorType cursorType) {
        Objects.requireNonNull(cursorType, "The CursorType must not be null");

        if (overriding)
            return false;

        String localDefaultCursorPath;

        if (cursorType.builtIn())
            localDefaultCursorPath = defaultCursorPath;
        else
            localDefaultCursorPath = "";

        File resource = Path.of(cursorType.path()).toFile();

        if (resource.toString().contains(".png")) {
            // Loads in a static cursor image
            Path cursor = Path.of(localDefaultCursorPath + resource.toString());

            if (state.data().debug)
                System.out.println(
                        "Path to image for static cursor: " + cursor);

            BufferedImage originalImage = FileUtils.getBufferedImage(cursor, BufferedImage.TYPE_INT_ARGB);

            // Get scale for hotspot calculation.
            scaleX = (double) width / originalImage.getWidth(null);
            scaleY = (double) height / originalImage.getHeight(null);

            cursorImage = GraphicsUtils.downscaleImage(originalImage,
                    width, height);
            hotspot = cursorType.hotspot();
            animated = false;

        } else {
            // Loads in a animated cursor collection with the information from meta.json

            // Populate frameDataArray from meta.json
            rawCursorData.importJSON(AnimatedCursorData.class,
                    "src/" + localDefaultCursorPath + resource.toString() + "/meta.json");

            frameDataArray = rawCursorData.data().getFrames().toArray(new Frame[0]);

            if (frameDataArray != null) {
                for (Frame frame : frameDataArray) {
                    Path imagePath = Path.of(localDefaultCursorPath
                            + resource.getName() + "/"
                            + frame.getImagePath());

                    if (state.data().debug) {
                        System.out.println("Image path: " + imagePath.toString());
                        System.out.println(
                                "Image hotspot: [" + frame.getHotspot()[0] + "," + frame.getHotspot()[1] + "]");
                        System.out.println("Delay duration: " + frame.getDurationMs() + '\n');
                    }

                    cursorImageCache.add(
                            GraphicsUtils.downscaleImage(
                                    FileUtils.getBufferedImage(imagePath, BufferedImage.TYPE_INT_ARGB),
                                    width, height));
                }

                // Get scale for hotspot calculation.
                Path imagePath = Path.of(localDefaultCursorPath
                        + resource.getName() + "/"
                        + frameDataArray[0].getImagePath());

                BufferedImage originalImage = FileUtils.getBufferedImage(imagePath, BufferedImage.TYPE_INT_ARGB);

                scaleX = (double) width / originalImage.getWidth(null);
                scaleY = (double) height / originalImage.getHeight(null);

                // Transform to a array for easier data handling.
                cursorImageArray = cursorImageCache.toArray(new BufferedImage[0]);
                cursorImageCache.clear();

                animated = true;
                updateCursor();
            }
        }

        return true;
    }

    /**
     * Locks the current cursor preventing it from being changed
     * via {@link #setCursor(CursorType) setCursor()}.
     * <p>
     * While the cursor is locked, calls to {@code setCursor(...)} will have
     * no effect and return {@code false}.
     */
    public static void lockCursor() {
        overriding = true;
    }

    /**
     * Unlocks the cursor, allowing it to be changed via
     * {@link #setCursor(CursorType) setCursor()}.
     * <p>
     * After calling this method, {@code setCursor(...)} will resume normal
     * behavior and return {@code true}.
     */
    public static void unlockCursor() {
        overriding = false;
    }

    @Override
    public void draw(Graphics g) {
        if (!show || !mouse.onScreen())
            return;

        int drawX = (int) (mouse.getPoint().x - hotspot.x * scaleX);
        int drawY = (int) (mouse.getPoint().y - hotspot.y * scaleY);

        g.drawImage(cursorImage, drawX, drawY, width, height, null);

        if (state.data().debug) {
            GraphicsUtils.imageBoundingBox(g, cursorImage, drawX, drawY);
            GraphicsUtils.debugShape(g, mouse.getPoint().x, mouse.getPoint().y);
        }
    }

    private float timer;

    @Override
    public void update(float deltaTime) {
        if (!show || !animated)
            return;

        // Reset or set the iterator if it was missing
        if (cursorArrayIndex >= cursorImageArray.length - 1)
            cursorArrayIndex = 0;

        timer += deltaTime;

        if (timer >= currentCursorMillis) {
            timer -= currentCursorMillis;

            updateCursor();
            cursorArrayIndex++;
        }

    }

    /**
     * Updates the information about the current animated cursor. (Hotspot, Image
     * and time delay)
     */
    private static void updateCursor() {

        if (state.data().debugVerbose)
            System.out.println("Updating cursor index: " + cursorArrayIndex);

        cursorImage = cursorImageArray[cursorArrayIndex];
        currentCursorMillis = frameDataArray[cursorArrayIndex].getDurationMs() * 0.001f;
        hotspot = new Point(frameDataArray[cursorArrayIndex].getHotspotX(),
                frameDataArray[cursorArrayIndex].getHotspotY());
    }

}
