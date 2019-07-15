package sobow.flappybirdgame.game;

import sobow.flappybirdgame.level.Bird;
import sobow.flappybirdgame.level.Pipes;

public class ScoreService
{
    private static Bird bird = Bird.getInstance();
    private static Pipes pipes = Pipes.getInstance();

    private static int playerScore;
    private static int bestScore;
    private static boolean beforePipesUpdate;
    private static boolean afterPipesUpdate;

    private ScoreService() {}


    public static void examineBirdPositionBeforePipesUpdate()
    {
        beforePipesUpdate = bird.isBetweenHorizontally(pipes.getBottomPipeAt(0));
    }

    public static void examineBirdPositionAfterPipesUpdate()
    {
        afterPipesUpdate = bird.isBetweenHorizontally(pipes.getBottomPipeAt(0));
    }

    public static boolean birdPassedFrontPipes()
    {
        return beforePipesUpdate && !afterPipesUpdate;
    }

    public static void scorePlayer()
    {
        playerScore++;
    }

    public static void reset()
    {
        playerScore = 0;
    }

    public static void updateBestScore()
    {
        if (playerScore > bestScore)
        {
            bestScore = playerScore;
        }
    }

    public static int getPlayerScore()
    {
        return playerScore;
    }

    public static int getBestScore()
    {
        return bestScore;
    }
}
