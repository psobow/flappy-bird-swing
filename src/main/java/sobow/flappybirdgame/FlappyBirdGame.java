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
    private MainWindow mainWindow;
    private RenderPanel renderPanelInstance;
    private Timer timer = new Timer(20, this);
    private Bird bird = Bird.getInstance();

    private FlappyBirdGame()
    {
        EventQueue.invokeLater(() ->
                               {
                                   mainWindow = new MainWindow();
                               });

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
        g.setColor(Color.GRAY);
        g.fillRect(0,0,
                   mainWindow.getFRAME_WIDTH(), mainWindow.getFRAME_HEIGHT());

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
