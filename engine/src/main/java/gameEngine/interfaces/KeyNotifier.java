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

package gameEngine.interfaces;

import java.awt.event.KeyEvent;
import gameEngine.engineModules.Keys;

/**
 * Receives notifications during special events received in {@link Keys} such as
 * {@link #keyPressedNotification(KeyEvent)},
 * {@link #keyPressedNotification(KeyEvent)} and
 * {@link #keyReleasedNotification(KeyEvent)}.
 */
public interface KeyNotifier {
    /**
     * Invoked when a key input produces a printable character.
     * <p>
     * This event is intended for text input handling rather than physical key
     * state tracking.
     * <p>
     * <b>Note:</b>
     * Control characters such as backspace, delete, escape, tab, enter, and
     * modifier keys are filtered out and will not trigger character insertion
     * behavior.
     * <p>
     * Depending on the platform and user keyboard repeat settings, this method
     * may be invoked repeatedly while a key is held down.
     * 
     * @param e The {@link KeyEvent} received from awt
     *
     * @see Keys#pollTypedCharacter() pollTypedCharacter()
     */
    default void keyTypedNotification(KeyEvent e) {
    }

    /**
     * Invoked when a physical key is pressed down.
     * <p>
     * Intended for gameplay controls, hotkeys, and input state handling.
     * <p>
     * This method may repeatedly fire while a key is held down depending on the
     * operating system keyboard repeat settings.
     * 
     * @param e The {@link KeyEvent} received from awt
     */
    default void keyPressedNotification(KeyEvent e) {
    }

    /**
     * Invoked when a physical key is released.
     * <p>
     * Intended for gameplay controls and input state handling.
     * 
     * @param e The {@link KeyEvent} received from awt
     */
    default void keyReleasedNotification(KeyEvent e) {
    }

}
