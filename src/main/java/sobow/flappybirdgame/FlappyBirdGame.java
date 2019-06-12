package sobow.flappybirdgame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class FlappyBirdGame implements ActionListener
{
    private static FlappyBirdGame instance;
    private RenderPanel renderPanelInstance;

    private Timer timer = new Timer(20, this);
    private Bird bird = Bird.getInstance();
    private WindowSettings windowSettings = WindowSettings.getInstance();

    private final int GROUND_HEIGHT = windowSettings.getWINDOW_HEIGHT() / 6;
    private final int GRASS_HEIGHT = windowSettings.getWINDOW_HEIGHT() / 50;
    private final int DISTANCE_BETWEEN_TOP_AND_GROUND = windowSettings.getWINDOW_HEIGHT() - GROUND_HEIGHT;


    private FlappyBirdGame()
    {
        EventQueue.invokeLater(MainWindow::new);
        timer.start();
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
        synchronized (FlappyBirdGame.class)
        {
            return instance;
        }
    }



    public void repaint(Graphics g)
    {
        // Paint background
        g.setColor(Color.GRAY);
        g.fillRect(0,0,
                   windowSettings.getWINDOW_WIDTH(), windowSettings.getWINDOW_HEIGHT());

        // Paint ground
        g.setColor(Color.ORANGE);
        g.fillRect(0, DISTANCE_BETWEEN_TOP_AND_GROUND,
                   windowSettings.getWINDOW_WIDTH(), GROUND_HEIGHT);

        // Paint grass
        g.setColor(Color.GREEN);
        g.fillRect(0, DISTANCE_BETWEEN_TOP_AND_GROUND,
                   windowSettings.getWINDOW_WIDTH(), GRASS_HEIGHT);

        // Paint bird
        g.setColor(Color.black);
        g.fillRect(bird.x, bird.y,
                   bird.width, bird.height);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        renderPanelInstance.repaint();
    }


    public void setRenderPanelInstance(RenderPanel renderPanelInstance)
    {
        this.renderPanelInstance = renderPanelInstance;
    }
}
