package sobow.flappybirdgame.services;


public class ScoreService
{
    private static int playerScore;
    private static int bestScore;
    private static boolean beforePipesUpdate;
    private static boolean afterPipesUpdate;

    private ScoreService() {}


    public static void examineBirdPositionBeforePipesUpdate()
    {
        beforePipesUpdate = CollisionService.isBirdBetweenFrontPipesHorizontally();
    }

    public static void examineBirdPositionAfterPipesUpdate()
    {
        afterPipesUpdate = CollisionService.isBirdBetweenFrontPipesHorizontally();
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
