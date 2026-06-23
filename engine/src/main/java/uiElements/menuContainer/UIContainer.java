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

package uiElements.menuContainer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import gameEngine.interfaces.MenuInterface;
import gameEngine.interfaces.MenuInterface.MenuSetPosition;
import gameEngine.interfaces.MenuInterface.MenuSetSize;

public class UIContainer implements MenuInterface, MenuSetSize, MenuSetPosition {

    private List<MenuInterface> items = new ArrayList<>();

    /**
     * Adds a {@link MenuInterface} item to this element.
     * <p>
     * All added items support basic visibility control via
     * {@link MenuInterface#show()} and {@link MenuInterface#hide()}.
     *
     * <p>
     * Items may also implement additional optional capabilities such as
     * {@link MenuInterface.MenuSetColor} or {@link MenuInterface.MenuSetSize}.
     * These capabilities are not guaranteed and must be checked before use.
     *
     * <p>
     * Example usage:
     * 
     * <pre>{@code
     * Slider volumeSlider = new Slider(...);
     * RectCheckbox fullscreenToggle = new RectCheckbox(...);
     *
     * UIContainer settingsMenu = new UIContainer();
     *
     * settingsMenu.add(volumeSlider);
     * settingsMenu.add(fullscreenToggle);
     *
     * openMenuButton.onClick(() -> {
     *     settingsMenu.show();
     * });
     * }</pre>
     *
     * @param item the menu item to add
     */
    public void add(MenuInterface item) {
        items.add(item);
    }

    @Override
    public void show() {
        for (MenuInterface item : items) {
            item.show();
        }
    }

    @Override
    public void hide() {
        for (MenuInterface item : items) {
            item.hide();
        }
    }

    @Override
    public void setSize(int width, int height) {
        for (MenuInterface item : items) {
            if (item instanceof MenuSetSize r) {
                r.setSize(width, height);
            }
        }
    }

    @Override
    public void translateSize(int dWidth, int dHeight) {
        for (MenuInterface item : items) {
            if (item instanceof MenuSetSize r) {
                r.translateSize(dWidth, dHeight);
            }
        }
    }

    @Override
    public void setPosition(int x, int y) {
        for (MenuInterface item : items) {
            if (item instanceof MenuSetPosition r) {
                r.setPosition(x, y);
            }
        }
    }

    @Override
    public void setPosition(Point position) {
        for (MenuInterface item : items) {
            if (item instanceof MenuSetPosition r) {
                r.setPosition(position);
            }
        }
    }

    @Override
    public void translatePosition(int dx, int dy) {
        for (MenuInterface item : items) {
            if (item instanceof MenuSetPosition r) {
                r.translatePosition(dx, dy);
            }
        }
    }
}