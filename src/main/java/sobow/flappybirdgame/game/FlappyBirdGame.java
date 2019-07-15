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
import sobow.flappybirdgame.level.Pipes;
import sobow.flappybirdgame.level.TextMessages;
import sobow.flappybirdgame.settings.WindowSettings;

public class FlappyBirdGame implements ActionListener, KeyListener
{
    private static FlappyBirdGame instance;
    private RenderPanel renderPanel;

    private Timer timer = new Timer(20, this);
    private Bird bird = Bird.getInstance();
    private Pipes pipes = Pipes.getInstance();
    private Ground ground = Ground.getInstance();
    private TextMessages textMessages = TextMessages.getInstance();
    private ScoreService scoreService = ScoreService.getInstance();

    private final Color BACKGROUND_COLOR = Color.GRAY.darker();

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
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        renderPanel.repaint();

        scoreService.examineBirdPositionBeforePipesUpdate();
        pipes.update();
        scoreService.examineBirdPositionAfterPipesUpdate();

        if (scoreService.birdPassedFrontPipes())
        {
            scoreService.scorePlayer();
        }

        bird.update();
        bird.resolveCollision(pipes);

        if (bird.isCollided())
        {
            scoreService.updateBestScore();
            timer.stop();
        }
    }

    public void repaint(Graphics g)
    {
        paintBackground(g);
        ground.paint(g);
        bird.paint(g);
        pipes.paint(g);
        textMessages.paint(g, timer.isRunning());
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
            renderPanel.repaint();
        }
        else if (!isTimerRunning && !bird.isCollided() && key == KeyEvent.VK_SPACE)
        {
            timer.start();
            bird.keyPressed(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    private void resetGame()
    {
        bird.reset();
        pipes.reset();
        scoreService.reset();
    }

    private void paintBackground(Graphics g)
    {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WindowSettings.WIDTH, WindowSettings.HEIGHT);
    }

    public void setRenderPanel(RenderPanel renderPanel)
    {
        this.renderPanel = renderPanel;
    }
}
