package sobow.flappybirdgame;


import javax.swing.JFrame;

public class MainWindow extends JFrame
{
    private final WindowSettings WINDOW_SETTINGS = WindowSettings.getInstance();
    private final RenderPanel RENDER_PANEL = RenderPanel.getInstance();

    public MainWindow()
    {
        add(RENDER_PANEL);
        setTitle("Flappy Bird");
        setSize(WINDOW_SETTINGS.getWINDOW_WIDTH(), WINDOW_SETTINGS.getWINDOW_HEIGHT());
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
