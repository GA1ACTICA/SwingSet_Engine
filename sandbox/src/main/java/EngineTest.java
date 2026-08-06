import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import gameEngine.engineModules.ClassFactory;
import gameEngine.engineModules.EngineContext;
import gameEngine.engineModules.Game;
import gameEngine.interfaces.drawables.Drawable;
import gameEngine.interfaces.drawables.UIDrawable.UIElementLayout;
import uiElements.button.RectButton;
import uiElements.checkBox.RectCheckbox;
import uiElements.textField.TextField;
import utils.GraphicsUtils;

public class EngineTest implements Drawable {

    public static void main(String[] args) {
        new Game("Test Window", true) {

            @Override
            protected void init() {
                new EngineTest(context);

                RectButton b = new RectButton(context, mouse, new Point(475, 0), new Point(525, 50));
                RectCheckbox c = new RectCheckbox(context, mouse, new Point(300, 300), new Point(350, 350));
                TextField t = new TextField(context, mouse, keys, new Point(100, 100), new Point(250, 250));

                b.setLayout(UIElementLayout.RIGHT_ALIGNED);

                b.show();
                c.show();
                t.show();
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
        GraphicsUtils.setOpacityFor((Graphics2D) g, 255, (painterG) -> {
            painterG.setColor(Color.BLUE);
            painterG.fillRect(0, 0, 1000, 1000);
        });
    }
}
