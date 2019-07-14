package sobow.flappybirdgame.level;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import sobow.flappybirdgame.settings.WindowSettings;

public class PipesService
{
    class Pipe extends Rectangle
    {
        private static final int WIDTH = 100;
        private final Color COLOR = Color.magenta.darker().darker().darker().darker();

        public Pipe(int x, int y, int height)
        {
            super(x, y, WIDTH, height);
        }

        public void paint(Graphics g)
        {
            g.setColor(COLOR);
            g.fillRect(x, y, width, height);
        }
    }

    private static PipesService instance;
    private List<Pipe> bottomPipes = new ArrayList<>();
    private List<Pipe> topPipes = new ArrayList<>();
    private Random randomGenerator = new Random();

    private static final int MINIMAL_BOTTOM_PIPE_HEIGHT = 50;
    private static final int MAXIMUM_BOTTOM_PIPE_HEIGHT = 300;
    private static final int GAP_BETWEEN_PIPES = 150;
    private static final int DISTANCE_BETWEEN_PAIR_OF_PIPES = 400;
    private static final int FIRST_PAIR_OF_PIPES_HORIZONTAL_POSITION = 700;
    private static final int PIPES_SPEED = 4;
    private static final int QUANTITY_OF_PIPES_PAIRS = 3;

    private PipesService() {}

    public static PipesService getInstance()
    {
        if (instance == null)
        {
            synchronized (PipesService.class)
            {
                if (instance == null)
                {
                    instance = new PipesService();
                }
            }
        }
        synchronized (PipesService.class)
        {
            return instance;
        }
    }

    public void initiate()
    {
        reset();
        for (int i = 0; i < QUANTITY_OF_PIPES_PAIRS; i++)
        {
            addPair();
        }
    }

    private void reset()
    {
        bottomPipes.clear();
        topPipes.clear();
    }

    public void addPair()
    {
        int bottomPipeHeight = MINIMAL_BOTTOM_PIPE_HEIGHT + randomGenerator.nextInt(
                MAXIMUM_BOTTOM_PIPE_HEIGHT - MINIMAL_BOTTOM_PIPE_HEIGHT);

        if (bottomPipes.isEmpty() && topPipes.isEmpty())
        {
            bottomPipes.add(new Pipe(FIRST_PAIR_OF_PIPES_HORIZONTAL_POSITION,
                                     WindowSettings.HEIGHT - bottomPipeHeight - Ground.getGroundHeight(),
                                     bottomPipeHeight));

            topPipes.add(new Pipe(FIRST_PAIR_OF_PIPES_HORIZONTAL_POSITION,
                                  0,
                                  WindowSettings.HEIGHT - bottomPipeHeight - Ground.getGroundHeight()
                                  - GAP_BETWEEN_PIPES));
        }
        else
        {
            bottomPipes.add(new Pipe(bottomPipes.get(bottomPipes.size() - 1).x + DISTANCE_BETWEEN_PAIR_OF_PIPES,
                                     WindowSettings.HEIGHT - bottomPipeHeight - Ground.getGroundHeight(),
                                     bottomPipeHeight));

            topPipes.add(new Pipe(topPipes.get(topPipes.size() - 1).x + DISTANCE_BETWEEN_PAIR_OF_PIPES,
                                  0,
                                  WindowSettings.HEIGHT - bottomPipeHeight - Ground.getGroundHeight()
                                  - GAP_BETWEEN_PIPES));
        }
    }

    public void paint(Graphics g)
    {
        for (int i = 0; i < QUANTITY_OF_PIPES_PAIRS; i++)
        {
            bottomPipes.get(i).paint(g);
            topPipes.get(i).paint(g);
        }
    }

    public void update()
    {
        if (frontPairDisappeared())
        {
            addPair();
            removeFrontPair();
        }
        move();
    }

    private void removeFrontPair()
    {
        bottomPipes.remove(0);
        topPipes.remove(0);
    }

    private boolean frontPairDisappeared()
    {
        return bottomPipes.size() > 0 && bottomPipes.get(0).x + bottomPipes.get(0).width < 0;
    }

    private void move()
    {
        for (int i = 0; i < QUANTITY_OF_PIPES_PAIRS; i++)
        {
            // Simulate motion of bird along X axis by moving pair of pipes towards left frame side.
            bottomPipes.get(i).x -= PIPES_SPEED;
            topPipes.get(i).x -= PIPES_SPEED;
        }
    }

    public Pipe getBottomPipeAt(int index)
    {
        return bottomPipes.get(index);
    }

    public Pipe getTopPipeAt(int index)
    {
        return topPipes.get(index);
    }
}
