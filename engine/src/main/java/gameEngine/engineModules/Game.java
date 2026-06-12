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

    protected final EngineState state;

    protected final EngineContext context;
    protected final EnginePanel panel;
    protected final JFrame frame;

    protected final Keys keys;
    protected final Mouse mouse;

    protected final CursorManager cursor;

    protected final GameUpdate gu;

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

        cursor = new CursorManager(context, panel, mouse, state);

        gu = new GameUpdate(keys,
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