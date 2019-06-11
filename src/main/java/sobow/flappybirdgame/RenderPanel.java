package sobow.flappybirdgame;

import java.awt.Graphics;
import javax.swing.JPanel;

public class RenderPanel extends JPanel
{
    private FlappyBirdGame instance = FlappyBirdGame.getInstance();

    RenderPanel()
    {

    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        instance.repaint(g);
    }
}
