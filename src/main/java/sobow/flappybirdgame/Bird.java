package sobow.flappybirdgame;

import java.awt.Rectangle;

public class Bird extends Rectangle
{
    private static Bird instance;
    private static final int BIRD_WIDTH = 20;
    private static final int BIRD_HEIGHT = 20;

    private Bird()
    {
        super(BIRD_WIDTH, BIRD_HEIGHT);
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

}
