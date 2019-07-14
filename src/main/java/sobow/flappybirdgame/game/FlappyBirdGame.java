package sobow.flappybirdgame.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import sobow.flappybirdgame.level.Bird;
import sobow.flappybirdgame.level.CollisionResolver;
import sobow.flappybirdgame.level.Pipe;
import sobow.flappybirdgame.level.PipesManager;
import sobow.flappybirdgame.settings.WindowSettings;

public class FlappyBirdGame implements ActionListener, KeyListener
{
    private static FlappyBirdGame instance;
    private RenderPanel renderPanelInstance;

    private long ticks = 0;
    private Timer timer = new Timer(20, this);
    private Bird bird = Bird.getInstance();
    private PipesManager pipesManager = PipesManager.getInstance();

    private int yAxisBirdMotionFactor = 0;

    private boolean birdAlive = true;

    private boolean collisionWithTop = false;
    private boolean collisionWithBottom = false;
    private boolean collisionWithPipes = false;

    private int playerScore = 0;

    private final int GROUND_HEIGHT = WindowSettings.HEIGHT / 6;
    private final int GRASS_HEIGHT = WindowSettings.HEIGHT / 50;
    private final int DISTANCE_BETWEEN_TOP_AND_GROUND = WindowSettings.HEIGHT - GROUND_HEIGHT;


    private final int BIRD_ACCELERATION_PER_TEN_TICKS_ALONG_Y_AXIS = 1;

    private final int MAXIMUM_POSITIVE_VALUE_OF_BIRD_MOTION_FACTOR = 10;
    private final int MINIMUM_NEGATIVE_VALUE_OF_BIRD_MOTION_FACTOR = -5;

    private final int INFORMATION_FONT_SIZE = 35;


    private final Color BACKGROUND_COLOR = Color.GRAY;
    private final Color SOIL_COLOR = Color.ORANGE.darker().darker();
    private final Color GRASS_COLOR = Color.GREEN.darker().darker().darker();
    private final Color BIRD_COLOR = Color.black;
    private final Color DEAD_BIRD_COLOR = Color.RED.darker().darker();
    private final Color TEXT_COLOR = Color.BLACK;

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

    private FlappyBirdGame()
    {
        MainWindow gameFrame = new MainWindow();
        gameFrame.addKeyListener(this);
        pipesManager.addInitialPipes(bird);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        renderPanelInstance.revalidate();
        renderPanelInstance.repaint();

        ticks++;

        pipesManager.update(bird);

        for (int i = 0; i < pipesManager.getQUANTITY_OF_PIPES_PAIRS(); i++)
        {

            // Score player if he crosed pipes
            if (bird.x == pipesManager.getBottomPipeAt(i).x + Pipe.getWIDTH())
            {
                playerScore++;
            }

            // Check bird collision with pipes
            boolean birdAboveBottomPipe = CollisionResolver.isBirdAboveBottomPipe(bird,
                                                                                  pipesManager.getBottomPipeAt(i));
            boolean isBirdBetweenTwoPipesYAxis = CollisionResolver.isBirdBetweenTwoPipes(bird,
                                                                                         pipesManager.getBottomPipeAt(i),
                                                                                         pipesManager.getTopPipeAt(i));
            if (birdAboveBottomPipe && isBirdBetweenTwoPipesYAxis == false)
            {
                collisionWithPipes = true;
                break;
            }
        }

        // Examine collisions
        collisionWithTop = CollisionResolver.isBirdCollidingWithTop(bird);
        collisionWithBottom = CollisionResolver.isBirdCollidingWithGround(bird, DISTANCE_BETWEEN_TOP_AND_GROUND);
        if (collisionWithTop || collisionWithBottom || collisionWithPipes)
        {
            birdAlive = false;
            // Repainting frame for change bird color after collision
            renderPanelInstance.revalidate();
            renderPanelInstance.repaint();
            timer.stop();
        }

        // Simulate gravitational acceleration
        if (ticks % 5 == 0 && yAxisBirdMotionFactor <= MAXIMUM_POSITIVE_VALUE_OF_BIRD_MOTION_FACTOR)
        {
            yAxisBirdMotionFactor += BIRD_ACCELERATION_PER_TEN_TICKS_ALONG_Y_AXIS;
        }

        // Bird motion
        bird.y += yAxisBirdMotionFactor; // When yAxisBirdMotionFactor value is positive bird move downwards and move upwards if negative
    }

    public void repaint(Graphics g)
    {
        // Paint background
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WindowSettings.WIDTH, WindowSettings.HEIGHT);

        // Paint ground
        g.setColor(SOIL_COLOR);
        g.fillRect(0, DISTANCE_BETWEEN_TOP_AND_GROUND, WindowSettings.WIDTH, GROUND_HEIGHT);

        // Paint grass
        g.setColor(GRASS_COLOR);
        g.fillRect(0, DISTANCE_BETWEEN_TOP_AND_GROUND, WindowSettings.WIDTH, GRASS_HEIGHT);

        // Paint bird
        g.setColor(birdAlive == true ? BIRD_COLOR : DEAD_BIRD_COLOR);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        // Paint pipes
        pipesManager.paintPipes(g);

        // initial information
        g.setColor(TEXT_COLOR);
        g.setFont(new Font("Arial", 1, INFORMATION_FONT_SIZE));
        if (timer.isRunning() == false)
        {
            g.drawString("Press space bar to " + (ticks != 0 ? "re" : "") + "start!",
                         WindowSettings.WIDTH / 4,
                         WindowSettings.HEIGHT / 3);
        }
        // score info
        g.drawString("Score: " + playerScore, 50, WindowSettings.HEIGHT - GROUND_HEIGHT / 2);
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int keyID = e.getKeyCode();
        switch (keyID)
        {
            case KeyEvent.VK_SPACE:
                // Spacebar tap cause change yAxisBirdMotionFactor for negative value and will cause Bird flying gently upward.
                if (timer.isRunning() && birdAlive)
                {
                    yAxisBirdMotionFactor = MINIMUM_NEGATIVE_VALUE_OF_BIRD_MOTION_FACTOR;
                }
                if (timer.isRunning() == false)
                {
                    resetGame();
                }
                break;
            default:
                break;
        }
    }

    private void resetGame()
    {
        if (birdAlive == false)
        {
            birdAlive = true;
            ticks = 0;
            yAxisBirdMotionFactor = 0;
            playerScore = 0;
            collisionWithPipes = false;
            collisionWithBottom = false;
            collisionWithTop = false;
            Bird.resetBirdPosition(); // first we need to reset bird pos before setting up pipes again
            pipesManager.addInitialPipes(bird);
        }

        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }

    public void setRenderPanelInstance(RenderPanel renderPanelInstance)
    {
        this.renderPanelInstance = renderPanelInstance;
    }
}
