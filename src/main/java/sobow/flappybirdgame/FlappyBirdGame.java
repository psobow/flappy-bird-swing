package sobow.flappybirdgame;

import java.awt.Color;
import java.awt.EventQueue;
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

    private Timer timer = new Timer(20, this);
    private long ticks = 0;
    private Bird bird = Bird.getInstance();
    private WindowSettings windowSettings = WindowSettings.getInstance();
    private Random randomGenerator = new Random();

    private List<Pipe> bottomPipes = new ArrayList<>();
    private List<Pipe> topPipes = new ArrayList<>();

    private final int GROUND_HEIGHT = windowSettings.getWINDOW_HEIGHT() / 6;
    private final int GRASS_HEIGHT = windowSettings.getWINDOW_HEIGHT() / 50;
    private final int DISTANCE_BETWEEN_TOP_AND_GROUND = windowSettings.getWINDOW_HEIGHT() - GROUND_HEIGHT;

    private final int MINIMAL_BOTTOM_PIPE_HEIGHT = 50;
    private final int MAXIMUM_BOTTOM_PIPE_HEIGHT = (int) ( (windowSettings.getWINDOW_HEIGHT() - GROUND_HEIGHT) / 1.5);

    private final int GAP_BETWEEN_TWO_PIPES = 100;
    private final int GAP_BETWEEN_PAIR_OF_PIPES = 300;
    private final int INIT_DISTANCE_BETWEEN_BIRD_AND_PIPES = 300;
    private final int SPEED_PER_TICK = 5;

    private final Color PIPES_COLOR = Color.cyan.darker().darker();
    private final Color BACKGROUND_COLOR = Color.GRAY;
    private final Color SOIL_COLOR = Color.ORANGE.darker().darker();
    private final Color GRASS_COLOR = Color.GREEN.darker().darker().darker();
    private final Color BIRD_COLOR = Color.black;





    private FlappyBirdGame()
    {
        EventQueue.invokeLater(MainWindow::new);
        addPipePair();
        addPipePair();
        addPipePair();
        addPipePair();
        timer.start();
    }

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

    public void addPipePair()
    {
        int bottomPipeHeight = MINIMAL_BOTTOM_PIPE_HEIGHT + randomGenerator.nextInt(MAXIMUM_BOTTOM_PIPE_HEIGHT - MINIMAL_BOTTOM_PIPE_HEIGHT);

        if (bottomPipes.size() == 0 && topPipes.size() == 0)
        {
            bottomPipes.add(new Pipe(bird.x + INIT_DISTANCE_BETWEEN_BIRD_AND_PIPES,
                               windowSettings.getWINDOW_HEIGHT() - bottomPipeHeight - GROUND_HEIGHT,
                               bottomPipeHeight));

            topPipes.add(new Pipe(bird.x + INIT_DISTANCE_BETWEEN_BIRD_AND_PIPES,
                               0,
                               windowSettings.getWINDOW_HEIGHT() - bottomPipeHeight - GROUND_HEIGHT - GAP_BETWEEN_TWO_PIPES));
        }
        else
        {
            bottomPipes.add(new Pipe(bottomPipes.get(bottomPipes.size() - 1).x + GAP_BETWEEN_PAIR_OF_PIPES,
                               windowSettings.getWINDOW_HEIGHT() - bottomPipeHeight - GROUND_HEIGHT,
                               bottomPipeHeight));

            topPipes.add(new Pipe(topPipes.get(topPipes.size() - 1).x + GAP_BETWEEN_PAIR_OF_PIPES,
                               0,
                               windowSettings.getWINDOW_HEIGHT() - bottomPipeHeight - GROUND_HEIGHT - GAP_BETWEEN_TWO_PIPES));
        }
    }

    public void paintPipe(Graphics g, Pipe pipe, Color color)
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
        g.setColor(BIRD_COLOR);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        // Paint pipes
        for (int i = 0; i < bottomPipes.size(); i++)
        {
            paintPipe(g, bottomPipes.get(i), PIPES_COLOR);
            paintPipe(g, topPipes.get(i), PIPES_COLOR);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        renderPanelInstance.revalidate();
        renderPanelInstance.repaint();

        ticks++;


        if (bottomPipes.size() > 0 &&
                    bottomPipes.get(0).x + bottomPipes.get(0).width < 0)
        {
            addPipePair();
            bottomPipes.remove(0);
            topPipes.remove(0);
        }

        for(int i = 0; i < bottomPipes.size(); i++)
        {
            bottomPipes.get(i).x -= SPEED_PER_TICK;
            topPipes.get(i).x -= SPEED_PER_TICK;
        }
    }


    public void setRenderPanelInstance(RenderPanel renderPanelInstance)
    {
        this.renderPanelInstance = renderPanelInstance;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int keyID = e.getKeyCode();
        switch (keyID)
        {
            case KeyEvent.VK_SPACE:
                // TODO: zaimplementować poruszanie w góre
                break;

            default:
        }
    }


    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
