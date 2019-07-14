import java.awt.EventQueue;
import sobow.flappybirdgame.game.RenderGameRelationInitializator;

public class FlappyBirdRunner
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(RenderGameRelationInitializator::new);
    }
}

/*
TODO: utworzyć klase ScoreResolver i przenieść tam odpowiedzialnośc podliczania punktów
TODO: przenieść render panel do flappy bird game, może tez klase mainWindow przenieść
TODO: zaimplementować zwięszkaszenie się prędkości
TODO: zaimplementować losowe szerokości rur
 */