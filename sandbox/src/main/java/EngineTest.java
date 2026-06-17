import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import gameEngine.engineModules.ClassFactory;
import gameEngine.engineModules.EngineContext;
import gameEngine.engineModules.Game;
import gameEngine.interfaces.drawables.Drawable;
import utils.GraphicsUtils;

public class EngineTest implements Drawable {

    public static void main(String[] args) {
        new Game() {

            @Override
            protected void init() {
                new EngineTest(context);
                state.exportJson("test.json");
            }

        };
    }

    int zIndex = 1;
    EngineContext context;

    public EngineTest(EngineContext context) {
        ClassFactory.create(this, context, zIndex);
        this.context = context;
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }

    @Override
    public void setZIndex(int zIndex) {
        ClassFactory.updatePriority(this, context, zIndex);
        this.zIndex = zIndex;
    }

    @Override
    public void draw(Graphics g) {
        GraphicsUtils.setOpacityFor((Graphics2D) g, zIndex, (painterG) -> {
            painterG.setColor(Color.RED);
            painterG.fillRect(450, 450, 100, 100);
        });

    }
}
