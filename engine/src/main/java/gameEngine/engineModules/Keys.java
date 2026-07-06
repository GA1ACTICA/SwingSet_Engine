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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import gameEngine.engineState.EngineState;
import gameEngine.interfaces.KeyNotifier;

/**
 * Handles key events received from AWT and notifies {@link KeyNotifier}.
 * <p>
 * {@code Keys} is managed by {@link Game} and is not intended to be
 * instantiated directly.
 */
public final class Keys implements KeyListener {

    private Set<Integer> keysPressed = new HashSet<>();
    private final Queue<Character> typedCharacters = new LinkedList<>();

    private final EngineState state;
    private final EngineContext context;

    Keys(EngineState state, EngineContext context) {
        this.state = state;
        this.context = context;
    }

    /**
     * This method is strictly intended for use by the game engine.
     * It is public only because it is defined in an interface, and external
     * callers should avoid invoking it directly.
     * 
     * @param e the {@link java.awt.event.KeyEvent} received from AWT
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        keysPressed.add(keyCode);

        for (KeyNotifier notifier : context.getKeyNotifiers()) {
            notifier.keyPressedNotification(e);
        }

        if (!state.data().debugVerbose)
            return;

        if (Character.isISOControl(e.getKeyChar()))
            System.out.println("ISO Control key: %s was pressed".formatted(keyCode));

        if (!Character.isISOControl(e.getKeyChar()))
            System.out.println("Key: %s %s was pressed".formatted(e.getKeyChar(),
                    keyCode));

    }

    /**
     * This method is strictly intended for use by the game engine.
     * It is public only because it is defined in an interface, and external
     * callers should avoid invoking it directly.
     * 
     * @param e the {@link java.awt.event.KeyEvent} received from AWT
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        keysPressed.remove(keyCode);

        for (KeyNotifier notifier : context.getKeyNotifiers()) {
            notifier.keyReleasedNotification(e);
        }

        if (!state.data().debugVerbose)
            return;

        if (Character.isISOControl(e.getKeyChar()))
            System.out.println("ISO Control key: %s was pressed".formatted(keyCode));

        if (!Character.isISOControl(e.getKeyChar()))
            System.out.println("Key: %s %s was pressed".formatted(e.getKeyChar(),
                    keyCode));
    }

    /**
     * This method is strictly intended for use by the game engine.
     * It is public only because it is defined in an interface, and external
     * callers should avoid invoking it directly.
     * 
     * @param e the {@link java.awt.event.KeyEvent} received from AWT
     */
    @Override
    public void keyTyped(KeyEvent e) {

        char c = e.getKeyChar();

        if (!Character.isISOControl(c)) {
            typedCharacters.offer(c);

            for (KeyNotifier notifier : context.getKeyNotifiers()) {
                notifier.keyTypedNotification(e);
            }
        }
    }

    /**
     * Returns a set of key codes representing all currently pressed keys.
     * Each key code corresponds to a standard {@link java.awt.event.KeyEvent} key
     * code.
     * 
     * @return {@code Set<Integer>} a Set of key codes representing the currently
     *         pressed keys
     */
    public Set<Integer> getKeysPressed() {
        return keysPressed;
    }

    /**
     * Returns the first character in the queue of pressed keys. Characters are case
     * sensitive and will be capitalized if applicable. Non-Unicode characters are
     * ignored.
     * <p>
     * <b>Note:</b>
     * Control characters such as backspace, delete, escape, tab, enter, and
     * modifier keys are filtered out and will not trigger character insertion
     * behavior.
     * 
     * @return the first character in the queue of pressed keys
     */
    public Character pollTypedCharacter() {
        return typedCharacters.poll();
    }
}