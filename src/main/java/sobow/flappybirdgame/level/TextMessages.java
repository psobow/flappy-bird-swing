package sobow.flappybirdgame.level;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class TextMessages
{
    private static TextMessages instance;

    private static final Color COLOR = Color.BLACK;
    private static final int FONT_SIZE = 35;
    private static final int CONTROLS_MSG_Y_POS = 180;
    private static final int SCORE_MSG_Y_POS = 495;

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

    public void paint(Graphics g, boolean isTimerRunning, boolean isBirdCollided, int playerScore, int bestScore)
    {
        g.setColor(COLOR);
        g.setFont(new Font("Arial", 1, FONT_SIZE));
        if (!isTimerRunning)
        {
            paintControlsInfo(g, isBirdCollided);
        }
        g.drawString("Score: " + playerScore, 50, SCORE_MSG_Y_POS);
        g.drawString("Best score: " + bestScore, 650, SCORE_MSG_Y_POS);
    }

    private void paintControlsInfo(Graphics g, boolean isBirdCollided)
    {
        String message;
        int messageXPos;
        if (!isBirdCollided)
        {
            message = "Press space bar to fly up and begin game";
            messageXPos = 120;
        }
        else
        {
            message = "Press enter to reset level";
            messageXPos = 270;
        }
        g.drawString(message, messageXPos, CONTROLS_MSG_Y_POS);
    }
}

