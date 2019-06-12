package sobow.flappybirdgame;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class FlappyBirdGame implements ActionListener
{
    private static FlappyBirdGame instance;

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
        return instance;
    }



    private RenderPanel renderPanelInstance;
    private Timer timer = new Timer(20, this);

    public void repaint(Graphics g)
    {
        System.out.println("Witam");
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
