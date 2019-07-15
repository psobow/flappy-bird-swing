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

public class BoardFlappyBird extends JPanel implements ActionListener
{
    private static BoardFlappyBird instance;

    private Timer timer;
    private Bird bird;
    private Pipes pipes;
    private Ground ground;
    private TextMessages textMessages;
    private ScoreService scoreService;

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
        scoreService = ScoreService.getInstance();
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

    @Override
    public void actionPerformed(ActionEvent e)
    {
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
        repaint();
    }

    private class MyKeyAdapter extends KeyAdapter
    {
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
                repaint();
            }
            else if (!isTimerRunning && !bird.isCollided() && key == KeyEvent.VK_SPACE)
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
        scoreService.reset();
    }
}
