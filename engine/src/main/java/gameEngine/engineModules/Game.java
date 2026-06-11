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

    protected final EngineState state = new EngineState();

    protected final EngineContext context = new EngineContext();
    protected final EnginePanel panel = new EnginePanel(state, context);
    protected final JFrame frame = new JFrame("Game_Title");

    protected final Keys keys = new Keys(state, context);
    protected final Mouse mouse = new Mouse(state, context, panel);

    protected final CursorManager cursor = new CursorManager(context, panel, mouse, state);

    protected final GameUpdate gu = new GameUpdate(keys,
            mouse,
            state,
            panel,
            frame,
            context,
            this);

    public Game() {

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