package sobow.flappybirdgame.game;

import sobow.flappybirdgame.level.Bird;
import sobow.flappybirdgame.level.Pipe;

public class CollisionResolver
{
    public static boolean isBirdAboveBottomPipe(Bird bird, Pipe bottomPipe)
    {
        return bird.x + bird.width >= bottomPipe.x && bird.x <= bottomPipe.x + Pipe.getWIDTH();
    }

    public static boolean isBirdBetweenTwoPipes(Bird bird, Pipe bottomPipe, Pipe topPipe)
    {
        return bird.y > topPipe.y + topPipe.height && bird.y + bird.height < bottomPipe.y;
    }

    public static boolean isBirdCollidingWithTop(Bird bird)
    {
        return bird.y <= 0;
    }

    public static boolean isBirdCollidingWithGround(Bird bird, int distanceBetweenTopAndGround)
    {
        return bird.y + bird.height >= distanceBetweenTopAndGround;
    }
}
