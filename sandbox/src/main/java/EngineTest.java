import java.awt.Graphics;
import java.awt.Point;

import io.github.ga1actica.gameEngine.engineModules.ClassFactory;
import io.github.ga1actica.gameEngine.engineModules.Game;
import io.github.ga1actica.gameEngine.interfaces.drawables.Drawable;
import io.github.ga1actica.gameEngine.interfaces.drawables.UIDrawable.UIElementLayout;
import io.github.ga1actica.uiElements.checkBox.RoundRectCheckbox;
import io.github.ga1actica.utils.Utils;

public class EngineTest extends Game implements Drawable {

    int zIndex;

    @Override
    protected void init() {
        setFullscreen();

        ClassFactory.create(this, context, zIndex);

        onExit((() -> {
            System.out.println("exiting the application");
        }));

        RoundRectCheckbox b = new RoundRectCheckbox(context,
                new Point(panel.getWidth() - 100, 100),
                100);
        b.show();
        b.setLayout(UIElementLayout.TOP_RIGHT_ALIGNED);

        b.onClick(() -> {
            setViewportDimensions(panel.getWidth(), panel.getHeight());
        });
    }

    public EngineTest() {
        super("EngineTest", true);
    }

    public static void main(String[] args) {
        new EngineTest();
    }

    @Override
    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Utils.rgba(0, 238, 255, 0.4));
        g.fillRect(0, 0, 1920, 100);
    }

}