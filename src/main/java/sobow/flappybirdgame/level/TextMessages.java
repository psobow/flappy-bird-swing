package sobow.flappybirdgame.level;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import sobow.flappybirdgame.services.ScoreService;


public class TextMessages
{
    private static final Color COLOR = Color.BLACK;

    private static final int FONT_SIZE = 35;
    private static final int CONTROLS_MSG_Y_POS = 180;
    private static final int SCORE_MSG_X_POS = 50;
    private static final int BEST_SCORE_MSG_X_POS = 650;
    private static final int SCORE_MSG_Y_POS = 495;

    private static TextMessages instance;

    private Bird bird;

    private TextMessages()
    {
        bird = Bird.getInstance();
    }

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

    public void paint(Graphics g, boolean isTimerRunning)
    {
        g.setColor(COLOR);
        g.setFont(new Font("Arial", 1, FONT_SIZE));
        if (!isTimerRunning)
        {
            paintControlsInfo(g);
        }
        g.drawString("Score: " + ScoreService.getPlayerScore(), SCORE_MSG_X_POS, SCORE_MSG_Y_POS);
        g.drawString("Best score: " + ScoreService.getBestScore(), BEST_SCORE_MSG_X_POS, SCORE_MSG_Y_POS);
    }

    private void paintControlsInfo(Graphics g)
    {
        String message;
        int messageXPos;
        if (!bird.isCollided())
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

