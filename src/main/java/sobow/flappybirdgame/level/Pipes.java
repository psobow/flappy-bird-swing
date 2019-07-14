package sobow.flappybirdgame.level;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import sobow.flappybirdgame.settings.WindowSettings;

public class Pipes
{
    class Pipe extends Rectangle
    {
        public Pipe(int x, int y, int width, int height)
        {
            super(x, y, width, height);
        }
    }

    private static final int PIPE_WIDTH = 100;
    private static final int MINIMAL_BOTTOM_PIPE_HEIGHT = 50;
    private static final int MAXIMUM_BOTTOM_PIPE_HEIGHT = 300;
    private static final int GAP_BETWEEN_PIPES = 150;
    private static final int DISTANCE_BETWEEN_PAIR_OF_PIPES = 400;
    private static final int FIRST_PAIR_OF_PIPES_HORIZONTAL_POSITION = 700;
    private static final int PIPES_SPEED = 4;
    private static final int QUANTITY_OF_PIPES_PAIRS = 3;

    private final Color COLOR = Color.magenta.darker().darker().darker().darker();

    private static Pipes instance;

    private List<Pipe> bottomPipes = new ArrayList<>();
    private List<Pipe> topPipes = new ArrayList<>();
    private Random randomGenerator = new Random();

    private Pipes() {}

    public static Pipes getInstance()
    {
        if (instance == null)
        {
            synchronized (Pipes.class)
            {
                if (instance == null)
                {
                    instance = new Pipes();
                }
            }
        }
        synchronized (Pipes.class)
        {
            return instance;
        }
    }

    public void reset()
    {
        bottomPipes.clear();
        topPipes.clear();
        for (int i = 0; i < QUANTITY_OF_PIPES_PAIRS; i++)
        {
            addPair();
        }
    }

    public void addPair()
    {
        int bottomPipeHeight = MINIMAL_BOTTOM_PIPE_HEIGHT + randomGenerator.nextInt(
                MAXIMUM_BOTTOM_PIPE_HEIGHT - MINIMAL_BOTTOM_PIPE_HEIGHT);

        int leftSideHorizontalCoordinate = (bottomPipes.isEmpty() && topPipes.isEmpty()
                                            ? FIRST_PAIR_OF_PIPES_HORIZONTAL_POSITION
                                            : bottomPipes.get(bottomPipes.size() - 1).x
                                              + DISTANCE_BETWEEN_PAIR_OF_PIPES);

        bottomPipes.add(new Pipe(leftSideHorizontalCoordinate,
                                 WindowSettings.HEIGHT - bottomPipeHeight - Ground.getGroundHeight(),
                                 PIPE_WIDTH,
                                 bottomPipeHeight));

        topPipes.add(new Pipe(leftSideHorizontalCoordinate,
                              0,
                              PIPE_WIDTH,
                              WindowSettings.HEIGHT - bottomPipeHeight - Ground.getGroundHeight() - GAP_BETWEEN_PIPES));

    }

    public void paint(Graphics g)
    {
        for (int i = 0; i < QUANTITY_OF_PIPES_PAIRS; i++)
        {
            g.setColor(COLOR);
            g.fillRect(bottomPipes.get(i).x, bottomPipes.get(i).y, bottomPipes.get(i).width, bottomPipes.get(i).height);
            g.fillRect(topPipes.get(i).x, topPipes.get(i).y, topPipes.get(i).width, topPipes.get(i).height);
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

    public Pipe getBottomPipeAt(int index)
    {
        return bottomPipes.get(index);
    }

    public Pipe getTopPipeAt(int index)
    {
        return topPipes.get(index);
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
}
