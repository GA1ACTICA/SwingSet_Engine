import gameEngine.engineModules.Game;

public class EngineTest {
    public static void main(String[] args) {
        new Game() {

            @Override
            protected void init() {
                System.out.println("Everything should have worked");
            }
        };
    }
}
