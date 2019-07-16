package sobow.flappybirdgame.services;

import sobow.flappybirdgame.level.Bird;
import sobow.flappybirdgame.level.Ground;
import sobow.flappybirdgame.level.Pipes;
import sobow.flappybirdgame.level.Pipes.Pipe;

public class CollisionService
{
    private static Bird bird = Bird.getInstance();
    private static Pipes pipes = Pipes.getInstance();

    public static void resolveCollision()
    {
        resolveBirdCollisionWithTopWallAndGround();
        if (!bird.isCollided())
        {
            resolveBirdCollisionWithHalfOfPipes();
        }
    }

    public static boolean isBirdBetweenFrontPipesHorizontally()
    {
        return isBirdBetweenHorizontally(pipes.getBottomPipeAt(0));
    }


    private static void resolveBirdCollisionWithTopWallAndGround()
    {
        if (collisionWithTop() || collisionWithGround())
        {
            bird.setCollided(true);
        }
    }

    private static boolean collisionWithGround()
    {
        return bird.y + bird.height >= Ground.getDistanceBetweenTopWallAndGround();
    }

    private static boolean collisionWithTop()
    {
        return bird.y <= 0;
    }

    private static void resolveBirdCollisionWithHalfOfPipes()
    {
        for (int i = 0; i < pipes.getQuantityOfPairs() / 2; i++)
        {
            if (isBirdBetweenHorizontally(pipes.getBottomPipeAt(i))
                && !isBirdBetweenVertically(pipes.getBottomPipeAt(i), pipes.getTopPipeAt(i)))
            {
                bird.setCollided(true);
                return;
            }
        }
    }

    private static boolean isBirdBetweenVertically(Pipe bottomPipe, Pipe topPipe)
    {
        return bird.y > topPipe.y + topPipe.height && bird.y + bird.height < bottomPipe.y;
    }

    private static boolean isBirdBetweenHorizontally(Pipe pipe)
    {
        return bird.x + bird.width >= pipe.x && bird.x <= pipe.x + pipe.width;
    }


}
