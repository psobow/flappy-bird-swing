package sobow.flappybirdgame.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import sobow.flappybirdgame.level.PipesService.Pipe;

public class Bird extends Rectangle
{
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final int INIT_X_POS = 100;
    private static final int INIT_Y_POS = 200;
    private static final int BOOST_FACTOR = -7; // This value describe how much upwards bird will fly after player press the key
    private static final double EARTH_ACCELERATION = 0.4;

    private static Bird instance;

    private float dy; // derivative y axis coordinate
    private boolean isCollided = false;
    private final Color BIRD_COLOR = Color.black;
    private final Color BIRD_COLOR_AFTER_COLLISION = Color.RED.darker().darker();


    private Bird()
    {
        super(INIT_X_POS, INIT_Y_POS, WIDTH, HEIGHT);
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

    public void reset()
    {
        dy = 0;
        instance.x = INIT_X_POS;
        instance.y = INIT_Y_POS;
    }

    public void paint(Graphics g)
    {
        g.setColor(isCollided ? BIRD_COLOR_AFTER_COLLISION : BIRD_COLOR);
        g.fillRect(x, y, width, height);
    }

    public boolean isBetweenHorizontally(Pipe pipe)
    {
        return x + width >= pipe.x && x <= pipe.x + pipe.width;
    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE)
        {
            dy = BOOST_FACTOR;
        }
    }

    public void update()
    {
        accelerateFall();
        fall();
    }

    public void resolveCollision(PipesService pipesService)
    {
        resolveCollisionWithTopWallAndGround();
        if (!isCollided)
        {
            resolveCollisionWithPipes(pipesService);
        }
    }

    private void resolveCollisionWithTopWallAndGround()
    {
        if (collisionWithTop() || collisionWithGround())
        {
            isCollided = true;
        }
    }

    private void resolveCollisionWithPipes(PipesService pipesService)
    {
        if (isBetweenHorizontally(pipesService.getBottomPipeAt(0))
            && !isBetweenVertically(pipesService.getBottomPipeAt(0), pipesService.getTopPipeAt(0)))
        {
            isCollided = true;
        }
    }

    private boolean isBetweenVertically(Pipe bottomPipe, Pipe topPipe)
    {
        return y > topPipe.y + topPipe.height && y + height < bottomPipe.y;
    }

    private boolean collisionWithGround()
    {
        return y + height >= Ground.getDistanceBetweenTopWallAndGround();
    }

    private boolean collisionWithTop()
    {
        return y <= 0;
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
