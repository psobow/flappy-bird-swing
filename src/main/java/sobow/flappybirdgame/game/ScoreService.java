package sobow.flappybirdgame.game;

import sobow.flappybirdgame.level.Bird;
import sobow.flappybirdgame.level.Pipes;

public class ScoreService
{
    private static ScoreService instance;

    private Bird bird;
    private Pipes pipes;

    private int playerScore;
    private int bestScore;
    private boolean beforePipesUpdate;
    private boolean afterPipesUpdate;

    private ScoreService()
    {
        bird = Bird.getInstance();
        pipes = Pipes.getInstance();
        playerScore = 0;
        bestScore = 0;
    }

    public static ScoreService getInstance()
    {
        if (instance == null)
        {
            synchronized (ScoreService.class)
            {
                if (instance == null)
                {
                    instance = new ScoreService();
                }
            }
        }
        synchronized (ScoreService.class)
        {
            return instance;
        }
    }

    public void examineBirdPositionBeforePipesUpdate()
    {
        beforePipesUpdate = bird.isBetweenHorizontally(pipes.getBottomPipeAt(0));
    }

    public void examineBirdPositionAfterPipesUpdate()
    {
        afterPipesUpdate = bird.isBetweenHorizontally(pipes.getBottomPipeAt(0));
    }

    public boolean birdPassedFrontPipes()
    {
        return beforePipesUpdate && !afterPipesUpdate;
    }

    public void scorePlayer()
    {
        playerScore++;
    }

    public void reset()
    {
        playerScore = 0;
    }

    public void updateBestScore()
    {
        if (playerScore > bestScore)
        {
            bestScore = playerScore;
        }
    }

    public int getPlayerScore()
    {
        return playerScore;
    }

    public int getBestScore()
    {
        return bestScore;
    }
}
