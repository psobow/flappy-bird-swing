package sobow.flappybirdgame.level;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class TextMessages
{
    private static TextMessages instance;

    private final Color COLOR = Color.BLACK;
    private final int FONT_SIZE = 35;


    private TextMessages() {}

    public static TextMessages getInstance()
    {
        if (instance == null)
        {
            synchronized (TextMessages.class)
            {
                if (instance == null)
                {
                    instance = new TextMessages();
                }
            }
        }
        synchronized (TextMessages.class)
        {
            return instance;
        }
    }

    public void paint(Graphics g, boolean isTimerRunning, int playerScore)
    {
        g.setColor(COLOR);
        g.setFont(new Font("Arial", 1, FONT_SIZE));
        if (!isTimerRunning)
        {
            g.drawString("Press space bar to fly and begin game", 150, 180);
        }
        g.drawString("Score: " + playerScore, 50, 495);
    }

}

