import java.awt.EventQueue;
import sobow.flappybirdgame.RenderGameRelationInitializator;

public class FlappyBirdRunner
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(RenderGameRelationInitializator::new);
    }
}
