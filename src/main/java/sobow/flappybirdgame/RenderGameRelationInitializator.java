package sobow.flappybirdgame;

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
