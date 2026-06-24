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

import java.awt.Dimension;
import javax.swing.*;

import gameEngine.engineModules.cursor.CursorManager;
import gameEngine.engineState.EngineState;

public abstract class Game {

    /**
     * The engine's main configuration and state.
     */
    protected final EngineState state;

    /**
     * The engine context containing collections used for rendering, updating,
     * and other engine operations.
     */
    protected final EngineContext context;

    /**
     * The engine panel responsible for the main rendering loop.
     * <p>
     * In addition to its role as a Swing panel, it provides rendering-related
     * functionality such as {@link EnginePanel#getViewportTransform()}.
     */
    protected final EnginePanel panel;

    /**
     * The {@link JFrame} that contains the {@link EnginePanel}.
     */
    protected final JFrame frame;

    /**
     * The keyboard input handler.
     */
    protected final Keys keys;

    /**
     * The mouse input handler.
     */
    protected final Mouse mouse;

    public Game() {
        this("Game_Title");
    }

    public Game(String name) {
        state = new EngineState();

        context = new EngineContext();
        panel = new EnginePanel(state, context);
        frame = new JFrame(name);

        keys = new Keys(state, context);
        mouse = new Mouse(state, context, panel);

        new CursorManager(context, panel, mouse, state);
        final GameUpdate gu = new GameUpdate(keys,
                mouse,
                state,
                panel,
                frame,
                context,
                this);
        // PANEL setup
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(state.data().width, state.data().height));
        panel.setBackground(state.data().backgroundColor);
        panel.setFocusable(true);

        // Add listeners
        panel.addKeyListener(keys);
        panel.addMouseListener(mouse);
        panel.addMouseMotionListener(mouse);
        panel.addMouseWheelListener(mouse);

        // FRAME setup
        frame.setContentPane(panel);
        frame.pack();
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        new Thread(gu).start();
    }

    protected abstract void init();

    void start() {
        init();
    }

}