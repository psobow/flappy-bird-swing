package sobow.flappybirdgame.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import sobow.flappybirdgame.settings.WindowSettings;

public class Ground extends Rectangle
{
    private static final int DISTANCE_BETWEEN_TOP_WALL_AND_GROUND = 450;
    private static final int GROUND_HEIGHT = 90;
    private static final int GRASS_HEIGHT = 10;

    private static final Color SOIL_COLOR = Color.ORANGE.darker().darker();
    private static final Color GRASS_COLOR = Color.GREEN.darker().darker().darker();

    private static Ground instance;

    private Ground() {}

    public static Ground getInstance()
    {
        if (instance == null)
        {
            synchronized (Ground.class)
            {
                if (instance == null)
                {
                    instance = new Ground();
                }
            }
        }
        synchronized (Ground.class)
        {
            return instance;
        }
    }

    public void paint(Graphics g)
    {
        g.setColor(SOIL_COLOR);
        g.fillRect(0, DISTANCE_BETWEEN_TOP_WALL_AND_GROUND, WindowSettings.WIDTH, GROUND_HEIGHT);

        g.setColor(GRASS_COLOR);
        g.fillRect(0, DISTANCE_BETWEEN_TOP_WALL_AND_GROUND, WindowSettings.WIDTH, GRASS_HEIGHT);
    }

    public static int getGroundHeight()
    {
        return GROUND_HEIGHT;
    }

    public static int getDistanceBetweenTopWallAndGround()
    {
        return DISTANCE_BETWEEN_TOP_WALL_AND_GROUND;
    }
}
