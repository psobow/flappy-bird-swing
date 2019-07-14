package sobow.flappybirdgame.settings;

import java.awt.Dimension;
import java.awt.Toolkit;

public class WindowSettings
{
    private static WindowSettings instance;

    private final Dimension DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    private final int WINDOW_WIDTH;
    private final int WINDOW_HEIGHT;

    private WindowSettings()
    {
        WINDOW_WIDTH = DIMENSION.width / 2;
        WINDOW_HEIGHT = DIMENSION.height / 2;
    }

    public static WindowSettings getInstance()
    {
        if (instance == null)
        {
            synchronized (WindowSettings.class)
            {
                if (instance == null)
                {
                    instance = new WindowSettings();
                }
            }
        }
        synchronized (WindowSettings.class)
        {
            return instance;
        }
    }

    public int getWINDOW_WIDTH()
    {
        return WINDOW_WIDTH;
    }

    public int getWINDOW_HEIGHT()
    {
        return WINDOW_HEIGHT;
    }
}
