package sobow.flappybirdgame.game;

import java.awt.Graphics;
import javax.swing.JPanel;

public class RenderPanel extends JPanel
{
    private static RenderPanel instance;
    private FlappyBirdGame gameInstance;

    private RenderPanel()
    {
    }

    public static RenderPanel getInstance()
    {
        if (instance == null)
        {
            synchronized (RenderPanel.class)
            {
                if (instance == null)
                {
                    instance = new RenderPanel();
                }
            }
        }
        synchronized (RenderPanel.class)
        {
            return instance;
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        revalidate();
        gameInstance.repaint(g);
    }

    public void setGameInstance(FlappyBirdGame gameInstance)
    {
        this.gameInstance = gameInstance;
    }
}
