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
import uiElements.slider.Slider;
import uiElements.textField.TextField;
import utils.GraphicsUtils;

public class EngineTest implements Drawable {

    public static void main(String[] args) {
        new Game("Test Window", true) {

            @Override
            protected void init() {
                new EngineTest(context);

                RectButton b = new RectButton(context, new Point(950, 0), new Point(1000, 50));
                RectCheckbox c = new RectCheckbox(context, new Point(375, 375), new Point(425, 425));
                TextField t = new TextField(context, panel, keys, new Point(100, 100), new Point(250, 250));
                Slider s = new Slider(context, panel, mouse, new Point(700, 700), new Point(800, 900));

                b.setLayout(UIElementLayout.TOP_RIGHT_ALIGNED);
                b.onClick(() -> {
                    System.out.println("1");
                });

                b.show();
                c.show();
                t.show();
                s.show();

                t.onClick(() -> {
                    System.out.print("test");
                });
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
        GraphicsUtils.setOpacityFor((Graphics2D) g, 50, (painterG) -> {
            painterG.setColor(Color.BLUE);
            painterG.fillRect(0, 0, 1000, 1000);
        });
    }
}
