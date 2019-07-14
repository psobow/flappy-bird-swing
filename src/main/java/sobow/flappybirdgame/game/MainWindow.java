package sobow.flappybirdgame.game;


import javax.swing.JFrame;
import sobow.flappybirdgame.settings.WindowSettings;

public class MainWindow extends JFrame
{
    public MainWindow()
    {
        add(RenderPanel.getInstance());
        setTitle(WindowSettings.TITLE);
        setSize(WindowSettings.WIDTH, WindowSettings.HEIGHT);
        setLocationRelativeTo(null);
        setResizable(WindowSettings.IS_RESIZABLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(WindowSettings.IS_VISIBLE);
    }
}
