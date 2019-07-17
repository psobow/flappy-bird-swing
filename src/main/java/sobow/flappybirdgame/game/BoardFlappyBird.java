package sobow.flappybirdgame.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import sobow.flappybirdgame.level.Bird;
import sobow.flappybirdgame.level.Ground;
import sobow.flappybirdgame.level.Pipes;
import sobow.flappybirdgame.level.TextMessages;
import sobow.flappybirdgame.services.CollisionService;
import sobow.flappybirdgame.services.ScoreService;

public class BoardFlappyBird extends JPanel implements ActionListener
{
    private static BoardFlappyBird instance;

    private Timer timer;
    private Bird bird;
    private Pipes pipes;
    private Ground ground;
    private TextMessages textMessages;

    public static BoardFlappyBird getInstance()
    {
        if (instance == null)
        {
            synchronized (BoardFlappyBird.class)
            {
                if (instance == null)
                {
                    instance = new BoardFlappyBird();
                }
            }
        }
        synchronized (BoardFlappyBird.class)
        {
            return instance;
        }
    }

    private BoardFlappyBird()
    {
        initBoard();
    }

    private void initBoard()
    {
        setBackground(Color.GRAY.darker());
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
        timer = new Timer(20, this);
        bird = Bird.getInstance();
        pipes = Pipes.getInstance();
        ground = Ground.getInstance();
        textMessages = TextMessages.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        ScoreService.examineBirdPositionBeforePipesUpdate();
        pipes.update();
        ScoreService.examineBirdPositionAfterPipesUpdate();

        if (ScoreService.birdPassedFrontPipes())
        {
            ScoreService.scorePlayer();
        }

        bird.update();
        CollisionService.resolveCollision();

        if (bird.isCollided())
        {
            ScoreService.updateBestScore();
            timer.stop();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        revalidate();
        ground.paint(g);
        bird.paint(g);
        pipes.paint(g);
        textMessages.paint(g, timer.isRunning());
    }

    private class MyKeyAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            boolean isTimerRunning = timer.isRunning();
            int key = e.getKeyCode();
            if (isTimerRunning)
            {
                bird.keyPressed(e);
            }
            else if (bird.isCollided() && key == KeyEvent.VK_ENTER)
            {
                resetGame();
                repaint();
            }
            else if (!bird.isCollided() && key == KeyEvent.VK_SPACE)
            {
                timer.start();
                bird.keyPressed(e);
            }
        }
    }

    private void resetGame()
    {
        bird.reset();
        pipes.reset();
        ScoreService.reset();
    }
}
