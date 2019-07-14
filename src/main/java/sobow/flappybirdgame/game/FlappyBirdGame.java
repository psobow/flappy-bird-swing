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
import sobow.flappybirdgame.level.Ground;
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
    private Ground ground = Ground.getInstance();

    private boolean collisionWithTop = false;
    private boolean collisionWithBottom = false;
    private boolean collisionWithPipes = false;

    private int playerScore = 0;

    private final int DISTANCE_BETWEEN_TOP_AND_GRASS = 450;
    private final int INFORMATION_FONT_SIZE = 35;


    private final Color BACKGROUND_COLOR = Color.GRAY;
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
        pipesManager.addInitialPipes();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        renderPanelInstance.revalidate();
        renderPanelInstance.repaint();

        ticks++;

        boolean beforePipesUpdate = bird.isBetweenFrontPipesHorizontally(pipesManager.getBottomPipeAt(0));
        pipesManager.update();
        boolean afterPipesUpdate = bird.isBetweenFrontPipesHorizontally(pipesManager.getBottomPipeAt(0));

        if (beforePipesUpdate && !afterPipesUpdate)
        {
            playerScore++;
        }

        bird.update();

        for (int i = 0; i < pipesManager.getQUANTITY_OF_PIPES_PAIRS(); i++)
        {
            // Check bird collision with pipes
            boolean birdAboveBottomPipe = CollisionResolver.isBirdAboveBottomPipe(bird, pipesManager.getBottomPipeAt(i));
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
        collisionWithBottom = CollisionResolver.isBirdCollidingWithGround(bird, DISTANCE_BETWEEN_TOP_AND_GRASS);
        if (collisionWithTop || collisionWithBottom || collisionWithPipes)
        {
            bird.setCollided(true);
            timer.stop();
        }
    }

    public void repaint(Graphics g)
    {
        // Paint background
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WindowSettings.WIDTH, WindowSettings.HEIGHT);

        ground.paint(g);

        bird.paint(g);

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
        g.drawString("Score: " + playerScore, 50, 495);
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        boolean isTimerRunning = timer.isRunning();
        int key = e.getKeyCode();
        if (isTimerRunning && !bird.isCollided())
        {
            bird.keyPressed(e);
        }
        else if (!isTimerRunning && !bird.isCollided() && key == KeyEvent.VK_SPACE)
        {
            timer.start();
        }
        else if (!isTimerRunning && bird.isCollided() && key == KeyEvent.VK_SPACE)
        {
            resetGame();
        }
    }

    private void resetGame()
    {
        bird.setCollided(false);
        ticks = 0;
        playerScore = 0;
        collisionWithPipes = false;
        collisionWithBottom = false;
        collisionWithTop = false;
        bird.reset();
        pipesManager.addInitialPipes();

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
