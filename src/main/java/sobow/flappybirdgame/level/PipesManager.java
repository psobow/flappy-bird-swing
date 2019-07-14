package sobow.flappybirdgame.level;


import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import sobow.flappybirdgame.settings.WindowSettings;

public class PipesManager
{
    private static PipesManager instance;
    private List<Pipe> bottomPipes = new ArrayList<>();
    private List<Pipe> topPipes = new ArrayList<>();
    private Random randomGenerator = new Random();
    private Ground ground = Ground.getInstance();

    private final int MINIMAL_BOTTOM_PIPE_HEIGHT = 50;
    private final int MAXIMUM_BOTTOM_PIPE_HEIGHT = 300;
    private final int GAP_BETWEEN_TWO_PIPES = 150;
    private final int GAP_BETWEEN_PAIR_OF_PIPES = 400;
    private final int FIRST_PAIR_OF_PIPES_HORIZONTAL_POSITION = 700;
    private final int PIPES_SPEED = 4;
    private final int QUANTITY_OF_PIPES_PAIRS = 3;

    private PipesManager() {}

    public static PipesManager getInstance()
    {
        if (instance == null)
        {
            synchronized (PipesManager.class)
            {
                if (instance == null)
                {
                    instance = new PipesManager();
                }
            }
        }
        synchronized (PipesManager.class)
        {
            return instance;
        }
    }

    public void addInitialPipes()
    {
        bottomPipes.clear();
        topPipes.clear();

        for (int i = 0; i < QUANTITY_OF_PIPES_PAIRS; i++)
        {
            addPipePair();
        }
    }


    public void addPipePair()
    {
        int bottomPipeHeight = MINIMAL_BOTTOM_PIPE_HEIGHT + randomGenerator.nextInt(
                MAXIMUM_BOTTOM_PIPE_HEIGHT - MINIMAL_BOTTOM_PIPE_HEIGHT);

        if (bottomPipes.isEmpty() && topPipes.isEmpty())
        {
            bottomPipes.add(new Pipe(FIRST_PAIR_OF_PIPES_HORIZONTAL_POSITION,
                                     WindowSettings.HEIGHT - bottomPipeHeight - ground.getGROUND_HEIGHT(),
                                     bottomPipeHeight));

            topPipes.add(new Pipe(FIRST_PAIR_OF_PIPES_HORIZONTAL_POSITION,
                                  0,
                                  WindowSettings.HEIGHT - bottomPipeHeight - ground.getGROUND_HEIGHT()
                                  - GAP_BETWEEN_TWO_PIPES));
        }
        else
        {
            bottomPipes.add(new Pipe(bottomPipes.get(bottomPipes.size() - 1).x + GAP_BETWEEN_PAIR_OF_PIPES,
                                     WindowSettings.HEIGHT - bottomPipeHeight - ground.getGROUND_HEIGHT(),
                                     bottomPipeHeight));

            topPipes.add(new Pipe(topPipes.get(topPipes.size() - 1).x + GAP_BETWEEN_PAIR_OF_PIPES,
                                  0,
                                  WindowSettings.HEIGHT - bottomPipeHeight - ground.getGROUND_HEIGHT()
                                  - GAP_BETWEEN_TWO_PIPES));
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
        // Check if the oldest pair of pipes is behind window frame and delete that one.
        // also add new pair of pipes
        if (bottomPipes.size() > 0 && bottomPipes.get(0).x + bottomPipes.get(0).width < 0)
        {
            addPipePair();
            bottomPipes.remove(0);
            topPipes.remove(0);
        }
        move();
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

    public int getQUANTITY_OF_PIPES_PAIRS()
    {
        return QUANTITY_OF_PIPES_PAIRS;
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
