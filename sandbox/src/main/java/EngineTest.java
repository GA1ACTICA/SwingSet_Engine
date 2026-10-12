import java.awt.Point;

import gameEngine.engineModules.Game;
import uiElements.button.RectButton;

public class EngineTest extends Game {

    @Override
    protected void init() {
        onExit((() -> {
            System.out.println("exiting the application");
        }));

        RectButton b = new RectButton(context, new Point(100, 100), new Point(200, 200));
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