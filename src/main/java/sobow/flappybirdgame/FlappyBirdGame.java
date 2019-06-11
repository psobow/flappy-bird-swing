package sobow.flappybirdgame;

import java.awt.Graphics;

public class FlappyBirdGame
{

    private static FlappyBirdGame instance;

    private FlappyBirdGame()
    {

    }

    public static FlappyBirdGame getInstance()
    {
        if (instance == null)
        {
            synchronized (FlappyBirdGame.class)
            {
                if (instance == null)
                {
                    instance = new FlappyBirdGame();
                }
            }
        }
        return instance;
    }

    public void repaint(Graphics g)
    {

    }
}
