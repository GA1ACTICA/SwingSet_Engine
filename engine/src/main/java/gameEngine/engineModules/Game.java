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
import java.io.IOException;

import javax.swing.JFrame;

import com.google.gson.JsonSyntaxException;

import gameEngine.engineModules.cursor.CursorManager;
import gameEngine.engineState.EngineState;
import utils.ErrorManagement;
import utils.Utils;

/**
 * Base class for all games using the engine.
 * <p>
 * This class initializes the engine and creates the application's primary
 * resources, including the window, rendering panel, engine state, and keyboard
 * and mouse input handlers. It serves as the central entry point for game
 * initialization, providing subclasses with access to the engine's core
 * components.
 */
public abstract class Game {

    /**
     * The current engine state, containing configuration and runtime properties.
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

    /**
     * Creates a new game with the specified title and resizability.
     * <p>
     * <b>Note:</b> The default config path is: config/EngineState.json.
     * 
     * @param name      the application and window title
     * 
     * @param resizable whether the application window can be resized
     */
    public Game(String name, boolean resizable) {
        this(name, resizable, "");
    }

    /**
     * Creates a new game with the specified title, resizability and config path.
     * 
     * @param name       the application and window title
     * 
     * @param resizable  whether the application window can be resized
     * 
     * @param configPath the path to the config
     */
    public Game(String name, boolean resizable, String configPath) {
        state = new EngineState();

        String defaultConfigPath = "config/EngineState.json";

        if (!configPath.isEmpty())
            defaultConfigPath = configPath;

        try {
            state.importJson(defaultConfigPath);
        } catch (IOException e) {
            noConfigFound();

        } catch (JsonSyntaxException s) {
            if (s.getCause() instanceof IOException)
                noConfigFound();

            else
                ErrorManagement.throwError(s,
                        "The provided EngineState.json contains malformed JSON and could thus not be loaded");
        }

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

    /**
     * Called once on the game thread after the engine has finished initializing and
     * is ready for use.
     * <p>
     * Override this method to perform application initialization, such as
     * creating the UI, instantiating rendering or drawing classes, and
     * allocating other resources that depend on the engine being fully initialized.
     */
    protected abstract void init();

    private void noConfigFound() {
        System.err.println(Utils.ConsoleYELLOW
                + "No default engine state found. One can be supplied in \"config/EngineState.json\""
                + Utils.ConsoleRESET);
    }

}