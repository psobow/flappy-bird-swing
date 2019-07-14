package sobow.flappybirdgame.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import sobow.flappybirdgame.level.Bird;
import sobow.flappybirdgame.level.Ground;
import sobow.flappybirdgame.level.PipesService;
import sobow.flappybirdgame.level.TextMessages;
import sobow.flappybirdgame.settings.WindowSettings;

public class FlappyBirdGame implements ActionListener, KeyListener
{
    private static FlappyBirdGame instance;
    private RenderPanel renderPanelInstance;

    private Timer timer = new Timer(20, this);
    private Bird bird = Bird.getInstance();
    private PipesService pipesService = PipesService.getInstance();
    private Ground ground = Ground.getInstance();
    private TextMessages textMessages = TextMessages.getInstance();

    private int playerScore = 0;
    private int bestScore = 0;

    private final Color BACKGROUND_COLOR = Color.GRAY;

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
        pipesService.initiate();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        renderPanelInstance.revalidate();
        renderPanelInstance.repaint();

        boolean beforePipesUpdate = bird.isBetweenHorizontally(pipesService.getBottomPipeAt(0));
        pipesService.update();
        boolean afterPipesUpdate = bird.isBetweenHorizontally(pipesService.getBottomPipeAt(0));

        if (beforePipesUpdate && !afterPipesUpdate)
        {
            playerScore++;
        }

        bird.update();

        bird.resolveCollision(pipesService);

        if (bird.isCollided())
        {
            if (playerScore > bestScore)
            {
                bestScore = playerScore;
            }
            timer.stop();
        }
    }

    public void repaint(Graphics g)
    {
        paintBackground(g);
        ground.paint(g);
        bird.paint(g);
        pipesService.paint(g);
        textMessages.paint(g, timer.isRunning(), bird.isCollided(), playerScore, bestScore);
    }

    private void paintBackground(Graphics g)
    {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WindowSettings.WIDTH, WindowSettings.HEIGHT);
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
        else if (!isTimerRunning && bird.isCollided() && key == KeyEvent.VK_ENTER)
        {
            resetGame();
            renderPanelInstance.revalidate();
            renderPanelInstance.repaint();
        }
        else if (!isTimerRunning && !bird.isCollided() && key == KeyEvent.VK_SPACE)
        {
            timer.start();
            bird.keyPressed(e);
        }
    }

    private void resetGame()
    {
        bird.setCollided(false);
        playerScore = 0;
        bird.reset();
        pipesService.initiate();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public void setRenderPanelInstance(RenderPanel renderPanelInstance)
    {
        this.renderPanelInstance = renderPanelInstance;
    }
}
