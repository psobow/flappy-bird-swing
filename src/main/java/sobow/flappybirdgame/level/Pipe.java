package sobow.flappybirdgame.level;

import java.awt.Rectangle;
import sobow.flappybirdgame.settings.WindowSettings;

public class Pipe extends Rectangle
{

    private static final int WIDTH = WindowSettings.WIDTH / 12;

    public Pipe(int x, int y, int height)
    {
        super(x, y, WIDTH, height);
    }

    public static int getWIDTH()
    {
        return WIDTH;
    }
}
