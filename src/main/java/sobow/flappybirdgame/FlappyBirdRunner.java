package sobow.flappybirdgame;


import java.awt.EventQueue;

public class FlappyBirdRunner
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(RenderGameRelationInitializator::new);
    }
}
