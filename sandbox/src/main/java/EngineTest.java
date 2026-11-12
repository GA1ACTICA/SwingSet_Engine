import java.awt.Point;

import io.github.ga1actica.gameEngine.engineModules.Game;
import io.github.ga1actica.uiElements.button.RoundRectButton;

public class EngineTest extends Game {

    @Override
    protected void init() {
        onExit((() -> {
            System.out.println("exiting the application");
        }));

        System.out.println(frame.getWidth());

        RoundRectButton b = new RoundRectButton(context, new Point(frame.getWidth() / 2, frame.getHeight() / 2), 100);
        b.show();
        b.onClick(() -> {
            exit();
        });
    }

    public EngineTest() {
        super("EngineTest", true);
    }

    public static void main(String[] args) {
        new EngineTest();
    }

}