package sobow.flappybirdgame.level;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import sobow.flappybirdgame.game.ScoreService;
import sobow.flappybirdgame.settings.WindowSettings;

public class Pipes
{
    class Pipe extends Rectangle
    {
        private Pipe(int x, int y, int width, int height)
        {
            super(x, y, width, height);
        }

        private void paint(Graphics g)
        {
            g.setColor(COLOR);
            g.fillRect(x, y, width, height);
        }
    }

    private static final Color COLOR = Color.magenta.darker().darker().darker().darker();

    private static final int PIPE_WIDTH = 100;
    private static final int MINIMAL_BOTTOM_PIPE_HEIGHT = 50;
    private static final int MAXIMUM_BOTTOM_PIPE_HEIGHT = 300;
    private static final int GAP_BETWEEN_PIPES = 200;
    private static final int FIRST_PAIR_OF_PIPES_X_POS = 700;
    private static final int QUANTITY_OF_PIPES_PAIRS = 4;
    private static final int INIT_DISTANCE_BETWEEN_PAIR_OF_PIPES = 300;
    private static final int INIT_PIPES_SPEED = 4;
    private static final int INIT_REQUIRED_SCORE_FOR_ACCELERATION = 5;
    private static final double ACCELERATION_FACTOR = 0.5;

    private static Pipes instance;

    private List<Pipe> bottomPipes;
    private List<Pipe> topPipes;
    private Random randomGenerator;

    private float pipesSpeed;
    private int requiredScoreForAcceleration;

    private Pipes()
    {
        bottomPipes = new ArrayList<>();
        topPipes = new ArrayList<>();
        randomGenerator = new Random();
        reset();
    }

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

    public void addPair()
    {
        int bound = MAXIMUM_BOTTOM_PIPE_HEIGHT - MINIMAL_BOTTOM_PIPE_HEIGHT;
        int bottomPipeHeight = MINIMAL_BOTTOM_PIPE_HEIGHT + randomGenerator.nextInt(bound);
        int leftSideHorizontalCoordinate = (bottomPipes.isEmpty() && topPipes.isEmpty() ? FIRST_PAIR_OF_PIPES_X_POS
                                                                                        : bottomPipes.get(bottomPipes.size() - 1).x
                                                                                          + INIT_DISTANCE_BETWEEN_PAIR_OF_PIPES);

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
            bottomPipes.get(i).paint(g);
            topPipes.get(i).paint(g);
        }
    }

    public void update()
    {
        move();
        if (frontPairDisappeared())
        {
            addPair();
            removeFrontPair();
        }

        if (accelerationPossible())
        {
            pipesSpeed += ACCELERATION_FACTOR; // accelerate pipes
            requiredScoreForAcceleration *= 2; // double required score for acceleration
            System.out.println("speed:" + pipesSpeed + "  score:" + ScoreService.getPlayerScore());
        }
    }


    private boolean accelerationPossible()
    {
        return ScoreService.getPlayerScore() == requiredScoreForAcceleration;
    }

    public void reset()
    {
        bottomPipes.clear();
        topPipes.clear();
        pipesSpeed = INIT_PIPES_SPEED;
        requiredScoreForAcceleration = INIT_REQUIRED_SCORE_FOR_ACCELERATION;

        for (int i = 0; i < QUANTITY_OF_PIPES_PAIRS; i++)
        {
            addPair();
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
            bottomPipes.get(i).x = (int) Math.floor(bottomPipes.get(i).x - pipesSpeed);
            topPipes.get(i).x = (int) Math.floor(topPipes.get(i).x - pipesSpeed);
        }
    }

}
