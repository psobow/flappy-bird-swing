package sobow.flappybirdgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;

public class FlappyBirdGame implements ActionListener, KeyListener
{
    private static FlappyBirdGame instance;
    private RenderPanel renderPanelInstance;

    private long ticks = 0;
    private Timer timer = new Timer(20, this);
    private Bird bird = Bird.getInstance();
    private WindowSettings windowSettings = WindowSettings.getInstance();
    private Random randomGenerator = new Random();

    private List<Pipe> bottomPipes = new ArrayList<>();
    private List<Pipe> topPipes = new ArrayList<>();

    private int yAxisBirdMotionFactor = 0;

    private boolean birdAlive = true;

    private boolean collisionWithTop = false;
    private boolean collisionWithBottom = false;
    private boolean collisionWithPipes = false;

    private int playerScore = 0;

    private final int GROUND_HEIGHT = windowSettings.getWINDOW_HEIGHT() / 6;
    private final int GRASS_HEIGHT = windowSettings.getWINDOW_HEIGHT() / 50;
    private final int DISTANCE_BETWEEN_TOP_AND_GROUND = windowSettings.getWINDOW_HEIGHT() - GROUND_HEIGHT;

    private final int MINIMAL_BOTTOM_PIPE_HEIGHT = 50;
    private final int MAXIMUM_BOTTOM_PIPE_HEIGHT = (int) ((windowSettings.getWINDOW_HEIGHT() - GROUND_HEIGHT) / 1.5);

    private final int GAP_BETWEEN_TWO_PIPES = 100;
    private final int GAP_BETWEEN_PAIR_OF_PIPES = 400;
    private final int INIT_DISTANCE_BETWEEN_BIRD_AND_PIPES = 500;

    private final int BIRD_SPEED_PER_ONE_TICK_ALONG_X_AXIS = 4;
    private final int BIRD_ACCELERATION_PER_TEN_TICKS_ALONG_Y_AXIS = 1;

    private final int MAXIMUM_POSITIVE_VALUE_OF_BIRD_MOTION_FACTOR = 10;
    private final int MINIMUM_NEGATIVE_VALUE_OF_BIRD_MOTION_FACTOR = -5;

    private final int INFORMATION_FONT_SIZE = 35;

    private final Color PIPES_COLOR = Color.cyan.darker().darker();
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
        addInitialPipes();
    }

    private void addInitialPipes()
    {
        bottomPipes.clear();
        topPipes.clear();

        // Calculate minimal quantity of pipe pair which will fit into window frame
        int quantityOfPipePairs = getMinimalQuantityOfPipePairsPerFrame();

        // Add just amount of pair pipes which fit into window frame
        for (int i = 0; i < quantityOfPipePairs; i++)
        {
            addPipePair();
        }
    }

    private int getMinimalQuantityOfPipePairsPerFrame()
    {
        int x = 0;
        int quantityOfPipePairs = 0;
        while (x < windowSettings.getWINDOW_WIDTH())
        {
            x += Pipe.getWIDTH() + GAP_BETWEEN_PAIR_OF_PIPES;
            quantityOfPipePairs++;
        }
        return quantityOfPipePairs + 1;
    }

    private void addPipePair()
    {
        int bottomPipeHeight = MINIMAL_BOTTOM_PIPE_HEIGHT + randomGenerator.nextInt(
                MAXIMUM_BOTTOM_PIPE_HEIGHT - MINIMAL_BOTTOM_PIPE_HEIGHT);

        if (bottomPipes.isEmpty() && topPipes.isEmpty())
        {
            bottomPipes.add(new Pipe(bird.x + INIT_DISTANCE_BETWEEN_BIRD_AND_PIPES,
                                     windowSettings.getWINDOW_HEIGHT() - bottomPipeHeight - GROUND_HEIGHT,
                                     bottomPipeHeight));

            topPipes.add(new Pipe(bird.x + INIT_DISTANCE_BETWEEN_BIRD_AND_PIPES,
                                  0,
                                  windowSettings.getWINDOW_HEIGHT() - bottomPipeHeight - GROUND_HEIGHT
                                  - GAP_BETWEEN_TWO_PIPES));
        }
        else
        {
            bottomPipes.add(new Pipe(bottomPipes.get(bottomPipes.size() - 1).x + GAP_BETWEEN_PAIR_OF_PIPES,
                                     windowSettings.getWINDOW_HEIGHT() - bottomPipeHeight - GROUND_HEIGHT,
                                     bottomPipeHeight));

            topPipes.add(new Pipe(topPipes.get(topPipes.size() - 1).x + GAP_BETWEEN_PAIR_OF_PIPES,
                                  0,
                                  windowSettings.getWINDOW_HEIGHT() - bottomPipeHeight - GROUND_HEIGHT
                                  - GAP_BETWEEN_TWO_PIPES));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        renderPanelInstance.revalidate();
        renderPanelInstance.repaint();

        ticks++;

        // Check if the oldest pair of pipes is behind window frame and delete that one.
        // also add new pair of pipes
        if (bottomPipes.size() > 0 && bottomPipes.get(0).x + bottomPipes.get(0).width < 0)
        {
            addPipePair();
            bottomPipes.remove(0);
            topPipes.remove(0);
        }

        for (int i = 0; i < bottomPipes.size(); i++)
        {
            // Simulate motion of bird along X axis by moving pair of pipes towards left frame side.
            bottomPipes.get(i).x -= BIRD_SPEED_PER_ONE_TICK_ALONG_X_AXIS;
            topPipes.get(i).x -= BIRD_SPEED_PER_ONE_TICK_ALONG_X_AXIS;

            // Score player if he crosed pipes
            if (bird.x == bottomPipes.get(i).x + Pipe.getWIDTH())
            {
                playerScore++;
            }

            // Check bird collision with pipes
            boolean birdAboveBottomPipe = CollisionResolver.isBirdAboveBottomPipe(bird, bottomPipes.get(i));
            boolean isBirdBetweenTwoPipesYAxis = CollisionResolver.isBirdBetweenTwoPipes(bird,
                                                                                         bottomPipes.get(i),
                                                                                         topPipes.get(i));
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

    private void paintPipe(Graphics g, Pipe pipe, Color color)
    {
        g.setColor(color);
        g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
    }

    public void repaint(Graphics g)
    {
        // Paint background
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, windowSettings.getWINDOW_WIDTH(), windowSettings.getWINDOW_HEIGHT());

        // Paint ground
        g.setColor(SOIL_COLOR);
        g.fillRect(0, DISTANCE_BETWEEN_TOP_AND_GROUND, windowSettings.getWINDOW_WIDTH(), GROUND_HEIGHT);

        // Paint grass
        g.setColor(GRASS_COLOR);
        g.fillRect(0, DISTANCE_BETWEEN_TOP_AND_GROUND, windowSettings.getWINDOW_WIDTH(), GRASS_HEIGHT);

        // Paint bird
        g.setColor(birdAlive == true ? BIRD_COLOR : DEAD_BIRD_COLOR);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        // Paint pipes
        for (int i = 0; i < bottomPipes.size(); i++)
        {
            paintPipe(g, bottomPipes.get(i), PIPES_COLOR);
            paintPipe(g, topPipes.get(i), PIPES_COLOR);
        }

        // initial information
        g.setColor(TEXT_COLOR);
        g.setFont(new Font("Arial", 1, INFORMATION_FONT_SIZE));
        if (timer.isRunning() == false)
        {
            g.drawString("Press space bar to " + (ticks != 0 ? "re" : "") + "start!",
                         windowSettings.getWINDOW_WIDTH() / 4,
                         windowSettings.getWINDOW_HEIGHT() / 3);
        }
        // score info
        g.drawString("Score: " + playerScore, 50, windowSettings.getWINDOW_HEIGHT() - GROUND_HEIGHT / 2);
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
            addInitialPipes();
            birdAlive = true;
            ticks = 0;
            yAxisBirdMotionFactor = 0;
            playerScore = 0;
            collisionWithPipes = false;
            collisionWithBottom = false;
            collisionWithTop = false;
            Bird.resetBirdPosition();
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
