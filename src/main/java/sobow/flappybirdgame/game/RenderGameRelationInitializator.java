package sobow.flappybirdgame.game;

public class RenderGameRelationInitializator
{
    private FlappyBirdGame gameInstance = FlappyBirdGame.getInstance();
    private RenderPanel renderPanelInstance = RenderPanel.getInstance();

    public RenderGameRelationInitializator()
    {
        renderPanelInstance.setGameInstance(gameInstance);
        gameInstance.setRenderPanelInstance(renderPanelInstance);
    }
}
