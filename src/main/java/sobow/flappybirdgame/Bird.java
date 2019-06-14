package sobow.flappybirdgame;

import java.awt.Rectangle;

public class Bird extends Rectangle
{
    private static Bird instance;
    private static final WindowSettings WINDOW_SETTINGS = WindowSettings.getInstance();

    private static final int BIRD_WIDTH = WINDOW_SETTINGS.getWINDOW_HEIGHT() / 25;
    private static final int BIRD_HEIGHT =  WINDOW_SETTINGS.getWINDOW_HEIGHT() / 25;
    private static final int INIT_X_POS = WINDOW_SETTINGS.getWINDOW_WIDTH() / 2 - (BIRD_WIDTH / 2 + WINDOW_SETTINGS.getWINDOW_WIDTH() / 3);
    private static final int INIT_Y_POS = WINDOW_SETTINGS.getWINDOW_HEIGHT() / 2 - BIRD_HEIGHT / 2;

    private Bird()
    {
        super(INIT_X_POS, INIT_Y_POS, BIRD_WIDTH, BIRD_HEIGHT);
    }

    public static Bird getInstance()
    {
        if (instance == null)
        {
            synchronized (Bird.class)
            {
                if (instance == null)
                {
                    instance = new Bird();
                }
            }
        }

        synchronized (Bird.class)
        {
            return instance;
        }
    }

    public static void resetBirdPosition()
    {
        if (instance != null)
        {
            instance.x = INIT_X_POS;
            instance.y = INIT_Y_POS;
        }
    }

}
