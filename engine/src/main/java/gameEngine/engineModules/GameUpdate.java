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

import javax.swing.JFrame;

import gameEngine.engineState.EngineState;
import gameEngine.interfaces.Updatable;

public class GameUpdate implements Runnable {

    private boolean running = true;
    private long lastUpdateTime;
    private long currentTime;

    private final EngineState state;
    private final EnginePanel panel;
    private final EngineContext context;

    public GameUpdate(
            Keys keys,
            Mouse mouse,
            EngineState state,
            EnginePanel panel,
            JFrame frame,
            EngineContext context,
            Game game) {
        this.state = state;
        this.panel = panel;
        this.context = context;
        state.setGameStateData(state);

        game.start();
    }

    @Override
    public void run() {

        lastUpdateTime = System.nanoTime();

        while (running) {

            currentTime = System.nanoTime();

            float deltaTime = (currentTime - lastUpdateTime) / 1000000000.0f;

            if (currentTime - lastUpdateTime >= state.data().exampleUpdateInterval) {

                // update all updatables
                for (Updatable u : context.getUpdatables()) {
                    u.update(deltaTime);
                }

                lastUpdateTime = currentTime;
            }

            context.endFrame();
            panel.repaint();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
