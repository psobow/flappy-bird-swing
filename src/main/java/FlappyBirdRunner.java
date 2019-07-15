import java.awt.EventQueue;
import sobow.flappybirdgame.game.MainWindow;

public class FlappyBirdRunner
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() -> new MainWindow());
    }
}

/*

TODO: zaimplementować zwięszkaszenie się prędkości
TODO: zaimplementować losowe szerokości rur
 */