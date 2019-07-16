package sobow.flappybirdgame.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Bird extends Rectangle
{
    private static final Color COLOR = Color.black;
    private static final Color AFTER_COLLISION = Color.RED.darker().darker();

    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final int INIT_X_POS = 100;
    private static final int INIT_Y_POS = 200;
    private static final int BOOST_FACTOR = -7; // This value describe how much upwards bird will fly after player press the key
    private static final double EARTH_ACCELERATION = 0.4;

    private static Bird instance;

    private float dy; // derivative y axis coordinate
    private boolean isCollided;

    private Bird()
    {
        super(INIT_X_POS, INIT_Y_POS, WIDTH, HEIGHT);
        reset();
    }

    public void reset()
    {
        dy = 0;
        isCollided = false;
        x = INIT_X_POS;
        y = INIT_Y_POS;
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

    public void paint(Graphics g)
    {
        g.setColor(isCollided ? AFTER_COLLISION : COLOR);
        g.fillRect(x, y, width, height);
    }

    public void update()
    {
        accelerateFall();
        fall();
    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE)
        {
            dy = BOOST_FACTOR;
        }
    }

    public boolean isCollided()
    {
        return isCollided;
    }

    public void setCollided(boolean collided)
    {
        isCollided = collided;
    }



    private void accelerateFall()
    {
        dy += EARTH_ACCELERATION;
    }

    private void fall()
    {
        y += dy;
    }
}
