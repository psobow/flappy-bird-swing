package sobow.flappybirdgame;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Bird extends Rectangle
{
    private static Bird instance;
    private static final Dimension DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();

    private static final int BIRD_WIDTH = 20;
    private static final int BIRD_HEIGHT = 20;
    private static final int X_POS = DIMENSION.width / 4 - (BIRD_WIDTH / 2 + DIMENSION.width / 6);
    private static final int Y_POS = DIMENSION.height / 4 - BIRD_HEIGHT / 2;

    private Bird()
    {
        super(X_POS, Y_POS, BIRD_WIDTH, BIRD_HEIGHT);
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
            instance.x = X_POS;
            instance.y = Y_POS;
        }
    }

}
